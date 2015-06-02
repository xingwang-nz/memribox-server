package nz.co.xingsoft.memribox.server.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import nz.co.xingsoft.memribox.server.business.dto.StoryDto;
import nz.co.xingsoft.memribox.server.common.ResourcesConstants;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.JavaType;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class StoryIntegrationTest
        extends JerseyClientIntegrationTest {
    @Test
    public void testAddStoryWithJSONString()
            throws JsonGenerationException, JsonMappingException, IOException {
        final WebResource webResource = getWebResource("services/story/add");

        final String token = auth("xing");

        final StoryDto story = new StoryDto();
        story.setSummary("this is my third story happened 5 years agao");
        story.setTimeLine(new Date());
        story.setTitle("New life 3 in NZ");

        System.out.println(marshalJasonObject(story));

        final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header(ResourcesConstants.HEADER_AUTH, token).post(ClientResponse.class, marshalJasonObject(story));

        assertResponse("testAddStory", response);

    }

    @Test
    public void testAddStoryWithObject()
            throws JsonGenerationException, JsonMappingException, IOException {
        final WebResource webResource = getWebResource("services/story/add");

        final String token = auth("toby");

        final StoryDto story = new StoryDto();
        story.setSummary("this is second story happened 9 years agao");
        story.setTimeLine(new Date());
        story.setTitle("New life in NZ- continued");

        System.out.println(marshalJasonObject(story));

        final ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header(ResourcesConstants.HEADER_AUTH, token).post(ClientResponse.class, marshalJasonObject(story));

        assertResponse("testAddStory", response);

    }

    @Test
    public void testGetStoryList()
            throws JsonParseException, JsonMappingException, IOException {
        final WebResource webResource = getWebResource("services/story/list");
        final String token = auth("xing");

        final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).header(ResourcesConstants.HEADER_AUTH, token).get(ClientResponse.class);

        final ServerResponse serverResponse = assertResponse("testGetStoryList", response);
        if (serverResponse.isSuccessful()) {

            final JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, StoryDto.class);

            final List<StoryDto> stories = jsonMapper.readValue(serverResponse.getResponseString(), type);
            System.out.println(stories.size());
        }

    }
}
