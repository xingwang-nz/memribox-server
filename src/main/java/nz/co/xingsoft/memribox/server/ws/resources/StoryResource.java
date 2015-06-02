package nz.co.xingsoft.memribox.server.ws.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import nz.co.xingsoft.memribox.server.business.dto.IdDto;
import nz.co.xingsoft.memribox.server.business.dto.StoryDataListDto;
import nz.co.xingsoft.memribox.server.business.dto.StoryDto;
import nz.co.xingsoft.memribox.server.business.dto.StoryFileDto;
import nz.co.xingsoft.memribox.server.business.exception.InternalServerException;
import nz.co.xingsoft.memribox.server.business.services.StoryDataRepositoryService;
import nz.co.xingsoft.memribox.server.business.services.StoryDataService;
import nz.co.xingsoft.memribox.server.business.services.StoryService;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryFile;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.JResponse;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("story")
@Service
public class StoryResource
        extends RestResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryResource.class);

    @Inject
    private StoryService storyService;

    @Inject
    private StoryDataService storyDataService;

    @Inject
    private StoryDataRepositoryService storyDataRepositoryService;

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> addStory(final StoryDto storyDto) {
        try {
            final String username = resourceUtil.getCurrentUsername();
            final StoryDto newStoryDto = storyService.addStory(username, storyDto);
            final IdDto dto = new IdDto();
            dto.setId(newStoryDto.getId());
            return JResponse.status(Status.OK).entity(dto).build();
        } catch (final Exception e) {
            return errorHandlerResource.processException("addStory", e);
        }
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> retrieveStories() {
        try {
            final String username = resourceUtil.getCurrentUsername();

            return JResponse.status(Status.OK).entity(storyService.getAllSotroies(username)).build();
        } catch (final Exception e) {
            return errorHandlerResource.processException("addStory", e);
        }
    }

    @POST
    @Path("{storyId}/text/add")
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> addStroyText(@PathParam("storyId") final long storyId, final String text) {

        try {

            LOGGER.info("add text for story ({}) ", storyId);

            final IdDto dto = new IdDto();
            dto.setId(storyDataService.addText(storyId, text));

            return JResponse.status(Status.OK).entity(dto).build();

        } catch (final Exception e) {
            return errorHandlerResource.processException("addStroyText", e);
        }
    }

    @GET
    @Path("{storyId}/text/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> listStoryTexts(@PathParam("storyId") final long storyId) {
        try {

            LOGGER.info("get list of text  for story '{}'", storyId);

            final StoryDataListDto storyDataListDto = storyDataService.getAllStoryTexts(storyId);

            return JResponse.status(Status.OK).entity(storyDataListDto).build();

        } catch (final Exception e) {
            return errorHandlerResource.processException("listTexts", e);
        }
    }

    /**
     * upload file using form upload
     * 
     * @param storyId
     * @param uploadedInputStream
     * @param fileDetail
     * @return
     */
    @POST
    @Path("{storyId}/file/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> uploadStoryFile(@PathParam("storyId") final long storyId, @FormDataParam("file") final InputStream uploadedInputStream,
            @FormDataParam("file") final FormDataContentDisposition fileDetail) {

        try {
            final String username = resourceUtil.getCurrentUsername();

            LOGGER.info("uploaded file {} for user '{}', stroyId {} ", fileDetail.getFileName(), username, storyId);

            final long fileId = storyDataService.addFile(username, storyId, fileDetail.getFileName(), uploadedInputStream);

            final IdDto dto = new IdDto();

            dto.setId(fileId);

            return JResponse.status(Status.OK).entity(dto).build();

        } catch (final Exception e) {
            return errorHandlerResource.processException("addStoryFile", e);
        }
    }

    /**
     * upload file using based64
     * 
     * @param storyId
     * @param uploadedInputStream
     * @param fileDetail
     * @return
     */
    @POST
    @Path("{storyId}/file/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> addStoryFile(@PathParam("storyId") final long storyId, final StoryFileDto storyFileDto) {

        try {
            final String username = resourceUtil.getCurrentUsername();

            LOGGER.info("uploaded file {} for user '{}', stroyId {} ", storyFileDto.getFileName(), username, storyId);

            final long fileId = storyDataService.addFile(username, storyId, storyFileDto);

            final IdDto dto = new IdDto();

            dto.setId(fileId);

            return JResponse.status(Status.OK).entity(dto).build();

        } catch (final Exception e) {
            return errorHandlerResource.processException("addStoryFile", e);
        }
    }

    @GET
    @Path("{storyId}/file/list")
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse<?> listStoryFiles(@PathParam("storyId") final long storyId) {
        try {

            LOGGER.info("get list story media files for story '{}'", storyId);

            final StoryDataListDto legacyDataListDto = storyDataService.getAllStoryFiles(storyId);

            return JResponse.status(Status.OK).entity(legacyDataListDto).build();

        } catch (final Exception e) {
            return errorHandlerResource.processException("listStoryFiles", e);
        }
    }

    @GET
    @Path("{storyId}/file/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public JResponse<?> downloadStoryFile(@PathParam("id") final Long fileId) {
        try {
            final String username = resourceUtil.getCurrentUsername();

            final StoryFile storyFile = storyDataService.getStoryFile(fileId);

            final File file = storyDataRepositoryService.locateStoryFile(username, storyFile.getStoredFileName());

            if (!file.exists()) {
                throw new InternalServerException(String.format("File %s dose not exist, please contact your administor", storyFile.getStoredFileName()));
            }

            final ContentDisposition contentDisposition = ContentDisposition.type("attachment").fileName(storyFile.getFileName()).creationDate(new Date())
                    .build();

            final StreamingOutput streamingOutput = new StreamingOutput() {
                @Override
                public void write(final OutputStream output)
                        throws IOException {
                    output.write(IOUtils.toByteArray(new FileInputStream(file)));
                }
            };

            return JResponse.status(Status.OK).header("Content-Disposition", contentDisposition).header("File-Name", storyFile.getFileName())
                    .entity(streamingOutput).build();

        } catch (final Exception e) {
            return errorHandlerResource.processException("fileList", e);
        }
    }

}
