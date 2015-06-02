package nz.co.xingsoft.memribox.server.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import nz.co.xingsoft.memribox.server.business.dto.StoryDataListDto;
import nz.co.xingsoft.memribox.server.business.dto.StoryFileDto;
import nz.co.xingsoft.memribox.server.common.ResourcesConstants;
import nz.co.xingsoft.memribox.server.common.StoryFileType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.junit.Test;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class StoryDataIntegrationTest
        extends JerseyClientIntegrationTest {

    @Test
    public void testAddStoryText()
            throws JsonParseException, ClientHandlerException, UniformInterfaceException, IOException {

        final WebResource resource = getWebResource("services/story/1/text/add");

        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.TEXT_PLAIN).header(ResourcesConstants.HEADER_AUTH, token)
                .post(ClientResponse.class, "second text blah blah blah");

        assertResponse("testAddStoryText", response);

    }

    @Test
    public void testGetStoryTexts()
            throws JsonParseException, ClientHandlerException, UniformInterfaceException, IOException {

        final WebResource resource = getWebResource("services/story/1/text/list");

        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.TEXT_PLAIN).header(ResourcesConstants.HEADER_AUTH, token).get(ClientResponse.class);

        final ServerResponse serverResponse = assertResponse("testAddStoryText", response);

        if (serverResponse.isSuccessful()) {
            final StoryDataListDto storyDataListDto = jsonMapper.readValue(serverResponse.getResponseString(), StoryDataListDto.class);
            System.out.println(storyDataListDto.getTextList().size());
        }
    }

    private static final String pdfFile = "OPT-flow.pdf";
    private static final String imageFile1 = "wallpapger1.jpg";
    private static final String imageFile2 = "wallpapger2.jpg";
    private static final String videoFile1 = "Google.mp4";
    private static final String videoFile2 = "Cruise-Ship.mp4";
    private static final String videoFile3 = "Tropical-Island.mp4";

    @Test
    public void testUploadStoryFile()
            throws JsonParseException, ClientHandlerException, UniformInterfaceException, IOException {

        final WebResource resource = getWebResource("services/story/1/file/upload");

        final File fileToUpload = new File(testSourceFileFolder, imageFile1);

        final FormDataMultiPart multiPart = new FormDataMultiPart();

        multiPart.bodyPart(new FileDataBodyPart("file", fileToUpload, MediaType.APPLICATION_OCTET_STREAM_TYPE));

        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).header(ResourcesConstants.HEADER_AUTH, token)
                .post(ClientResponse.class, multiPart);

        assertResponse("testAddStoryText", response);

    }

    /**
     * Test upload file use JSON with base64
     * 
     * @throws JsonParseException
     * @throws ClientHandlerException
     * @throws UniformInterfaceException
     * @throws IOException
     */
    @Test
    public void testAddStoryTextJson()
            throws JsonParseException, ClientHandlerException, UniformInterfaceException, IOException {

        final WebResource resource = getWebResource("services/story/1/file/add");

        final File file = new File(testSourceFileFolder, imageFile2);

        final String fileContent = new String(Base64.encodeBase64(FileUtils.readFileToByteArray(file)));

        final StoryFileDto fileDto = new StoryFileDto();
        fileDto.setFileType(StoryFileType.VIDEO);
        fileDto.setContent(fileContent);
        fileDto.setFileName(file.getName());

        final String filJson = marshalJasonObject(fileDto);

        System.out.println(filJson);

        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header(ResourcesConstants.HEADER_AUTH, token).post(ClientResponse.class, filJson);

        assertResponse("testAddStoryTextJson", response);

    }

    @Test
    public void testGetStoryFiles()
            throws JsonParseException, ClientHandlerException, UniformInterfaceException, IOException {

        final WebResource resource = getWebResource("services/story/1/file/list");

        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.TEXT_PLAIN).header(ResourcesConstants.HEADER_AUTH, token).get(ClientResponse.class);

        final ServerResponse serverResponse = assertResponse("testGetStoryFiles", response);

        if (serverResponse.isSuccessful()) {
            final StoryDataListDto storyDataListDto = jsonMapper.readValue(serverResponse.getResponseString(), StoryDataListDto.class);
            System.out.println(storyDataListDto.getFileList().size());
        }
    }

    @Test
    public void testDownloadFile()
            throws ClientHandlerException, UniformInterfaceException, IOException {
        final WebResource resource = getWebResource("services/story/1/file/download/23");

        final String token = auth("xing");

        final ClientResponse response = resource.type(MediaType.APPLICATION_OCTET_STREAM).header(ResourcesConstants.HEADER_AUTH, token)
                .get(ClientResponse.class);

        final String contentDisposition = response.getHeaders().get("Content-Disposition").get(0);
        final String filename = response.getHeaders().get("File-Name").get(0);

        final File outputFile = new File(testDownloadFileFolder, filename);

        final File downloadFile = response.getEntity(File.class);

        try (final InputStream inputStream = new FileInputStream(downloadFile); FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }

        // System.out.println(response.getStatus());
        // System.out.println(response.getEntity(String.class));

    }
}
