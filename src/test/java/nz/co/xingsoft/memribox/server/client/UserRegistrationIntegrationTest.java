package nz.co.xingsoft.memribox.server.client;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import nz.co.xingsoft.memribox.server.business.dto.ErrorResponse;
import nz.co.xingsoft.memribox.server.business.dto.RegistrationRequest;
import nz.co.xingsoft.memribox.server.common.Gender;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.joda.time.DateTime;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class UserRegistrationIntegrationTest
        extends JerseyClientIntegrationTest {

    /**
     * Pass payload as object
     */
    @Test
    public void testRegistrationWithJsonObject() {

        final WebResource webResource = getWebResource("services/user/register");

        final RegistrationRequest request = new RegistrationRequest();
        request.setUsername("john");
        request.setFirstName("John");
        request.setLastName("Waters");

        final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, request);

        System.out.println("response code: " + response.getStatus());
        if (response.getStatus() == 200) {
            System.out.println("Successful");
        } else {
            final ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            System.out.println(errorResponse.getErrorCode());
            System.out.println(errorResponse.getMessage());
        }
    }

    /**
     * Pass payload as JSON string
     */
    @Test
    public void testRegistrationWithJsonString()
            throws JsonGenerationException, JsonMappingException, IOException {

        final WebResource webResource = getWebResource("services/user/register");

        final RegistrationRequest request = new RegistrationRequest();
        request.setUsername("john");
        request.setFirstName("John");
        request.setLastName("Waters");
        request.setPassword("12345");

        final DateTime dt = new DateTime();
        final Date birthday = dt.withYear(1970).toDate();
        request.setBirthDate(birthday);

        request.setGender(Gender.M);
        request.setNativeTongue("Chinese");
        request.setHomeCountry("China");

        final String jasonInput = marshalJasonObject(request);

        System.out.println(jasonInput);

        final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, jasonInput);

        System.out.println("response code: " + response.getStatus());
        System.out.println(response.getEntity(String.class));
        if (response.getStatus() == 200) {
            System.out.println("Successful");
        } else if (response.getStatus() == 400) {
            // final ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            // System.out.println(errorResponse.getErrorCode());
            // System.out.println(errorResponse.getMessage());
        } else {
            System.out.println(response.getEntity(String.class));
        }
    }

}
