package nz.co.xingsoft.memribox.server.client;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import nz.co.xingsoft.memribox.server.business.dto.AuthToken;
import nz.co.xingsoft.memribox.server.business.dto.UserDetailsDto;
import nz.co.xingsoft.memribox.server.common.ResourcesConstants;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class JerseyClientIntegrationTest {

    // private static final String HOST = "memriboxpreprod.cloudapp.net";
    protected static final String HOST = "localhost";

    protected static final int PORT = 8080;

    protected static final String SERVICE_PATH = "http://" + HOST + ":" + 8080 + "/memribox-server";

    protected Client client;

    protected SimpleDateFormat defaultDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected ObjectMapper jsonMapper = new ObjectMapper();

    protected File testSourceFileFolder;
    protected File testDownloadFileFolder;

    @Before
    public void init() {
        client = Client.create();

        final SerializationConfig serConfig = jsonMapper.getSerializationConfig();
        serConfig.withDateFormat(defaultDateFormatter);

        final DeserializationConfig deserializationConfig = jsonMapper.getDeserializationConfig();
        deserializationConfig.withDateFormat(defaultDateFormatter);

        jsonMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        jsonMapper.setDateFormat(defaultDateFormatter);

        testSourceFileFolder = new File(System.getProperty("user.home"), "temp/memribox/test-source-files");
        testDownloadFileFolder = new File(System.getProperty("user.home"), "temp/memribox/test-download-files");

        if (!testDownloadFileFolder.exists()) {

            testDownloadFileFolder.mkdirs();
        }

    }

    protected ServerResponse assertResponse(final String name, final ClientResponse response) {

        final ServerResponse serverResponse = new ServerResponse();
        serverResponse.setResponseCode(response.getStatus());
        serverResponse.setResponseString(response.getEntity(String.class));

        System.out.println(serverResponse);

        if (response.getStatus() != 200) {
            Fail.fail(name + "service failed");
        }

        return serverResponse;
    }

    public class ServerResponse {
        private int responseCode;
        private String responseString;

        public boolean isSuccessful() {
            return responseCode == 200;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(final int responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseString() {
            return responseString;
        }

        public void setResponseString(final String responseString) {
            this.responseString = responseString;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(isSuccessful() ? "Successful" : "Failed, ");
            builder.append("response code: ").append(responseCode).append("\n");
            builder.append("response String:\n ").append(responseString);
            return builder.toString();
        }

    }

    @Test
    public void testGetCurrentUserString() {
        final WebResource resource = getWebResource("services/user");
        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.APPLICATION_JSON).header(ResourcesConstants.HEADER_AUTH, token).get(ClientResponse.class);
        System.out.println(response.getStatus());

        System.out.println(response.getEntity(String.class));
    }

    @Test
    public void testGetCurrentUser() {
        final WebResource resource = getWebResource("services/user");
        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header(ResourcesConstants.HEADER_AUTH, token).get(ClientResponse.class);
        System.out.println(response.getStatus());

        final UserDetailsDto user = response.getEntity(UserDetailsDto.class);

        System.out.println(user.getUsername());
        System.out.println(user.getUserRoles());
    }

    @Test
    public void testAuth() {
        auth("xing");
    }

    protected String auth(final String username) {
        final WebResource webResource = getWebResource("services/user/auth");

        final MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
        formData.add("username", username);
        formData.add("password", "12345");

        final ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, formData);

        if (response.getStatus() == 200) {
            final AuthToken authToken = response.getEntity(AuthToken.class);
            System.out.println(String.format("Auth OK, authToken = %s", authToken.getToken()));
            return authToken.getToken();
        } else {
            throw new RuntimeException("Auth failed : " + response.getStatus() + ", " + response.getEntity(String.class));
        }

    }

    protected WebResource getWebResource(final String path) {
        return client.resource(SERVICE_PATH + "/" + path);
    }

    protected String marshalJasonObject(final Object object)
            throws JsonGenerationException, JsonMappingException, IOException {

        // return jsonMapper.writeValueAsString(object);
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);

    }

    protected String format(final String json)
            throws IOException, JsonParseException {
        final JsonFactory jsonFactory = new JsonFactory();
        final JsonParser jsonParser = jsonFactory.createJsonParser(json);
        final StringWriter sw = new StringWriter();
        final JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);
        jsonGenerator.useDefaultPrettyPrinter();

        while (jsonParser.nextToken() != null) {
            jsonGenerator.copyCurrentEvent(jsonParser);
        }
        jsonParser.close();
        jsonGenerator.close();

        final String result = sw.toString();

        return result;
    }

}
