package nz.co.xingsoft.memribox.server.business.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.ValidationException;

import nz.co.xingsoft.memribox.server.business.dto.ErrorCode;
import nz.co.xingsoft.memribox.server.business.dto.StoryDataListDto;
import nz.co.xingsoft.memribox.server.business.dto.StoryFileDto;
import nz.co.xingsoft.memribox.server.business.dto.StoryTextDto;
import nz.co.xingsoft.memribox.server.business.exception.BusinessLogicException;
import nz.co.xingsoft.memribox.server.business.exception.InternalServerException;
import nz.co.xingsoft.memribox.server.business.exception.StoryDataNotFoundException;
import nz.co.xingsoft.memribox.server.persistence.dao.CommonDao;
import nz.co.xingsoft.memribox.server.persistence.dao.StoryDao;
import nz.co.xingsoft.memribox.server.persistence.dao.StoryFileDao;
import nz.co.xingsoft.memribox.server.persistence.dao.StoryTextDao;
import nz.co.xingsoft.memribox.server.persistence.dao.UserDao;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryFile;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryText;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sun.jersey.core.util.Base64;

/**
 * This service class is used to manage service data, i.e. story files including text and media files
 */
@Component
public class StoryDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryDataService.class);

    @Inject
    private StoryDao storyDao;

    @Inject
    private StoryTextDao storyTextDao;

    @Inject
    private StoryFileDao storyFileDao;

    @Inject
    private UserDao userDao;

    @Inject
    private CommonDao commonDao;

    @Inject
    private DozerBeanMapper beanMapper;

    @Inject
    private StoryDataRepositoryService storyDataRepositoryService;

    /**
     * add the text for the story
     * 
     * @param storyId
     * @param text
     * @return storyText primary ID
     * @throws Exception
     */
    @Transactional
    public long addText(final long storyId, final String text)
            throws Exception {
        return storyTextDao.addStoryText(storyId, text).getId();
    }

    @Transactional(readOnly = true)
    public StoryDataListDto getAllStoryTexts(final long storyId) {
        final StoryDataListDto dataListDto = new StoryDataListDto();

        final List<StoryText> storyTextList = storyTextDao.getAllTexts(storyId);

        for (final StoryText storyText : storyTextList) {
            dataListDto.addStoryText(beanMapper.map(storyText, StoryTextDto.class));
        }

        return dataListDto;
    }

    /**
     * handle streaming file upload
     * 
     * @param username
     * @param storyId
     * @param filename
     * @param in
     * @return
     * @throws Exception
     */
    @Transactional
    public long addFile(final String username, final long storyId, final String filename, final InputStream in)
            throws Exception {

        final Story story = commonDao.retrieve(Story.class, storyId);

        if (story == null) {
            throw new BusinessLogicException(ErrorCode.STORY_NOT_EXIST);
        }

        final User storyOwner = story.getUser();

        if (!username.equalsIgnoreCase(storyOwner.getUsername())) {
            throw new BusinessLogicException(ErrorCode.WRONG_USER);
        }

        final StoryFile storyFile = storyFileDao.addFile(storyId, filename);

        final String storedFilename = storyFile.getId() + "_" + filename;

        storyFile.setStoredFileName(storedFilename);

        commonDao.update(storyFile);

        LOGGER.info("Save file {} for story {}", storedFilename, storyId);

        try (FileOutputStream out = new FileOutputStream(new File(storyDataRepositoryService.getUserFolder(username), storedFilename));) {
            IOUtils.copy(in, out);
        }

        return storyFile.getId();
    }

    /**
     * handle based64 json file upload
     * 
     * @param username
     * @param storyId
     * @param storyFileDto
     * @return
     * @throws Exception
     */
    @Transactional
    public long addFile(final String username, final long storyId, @Valid final StoryFileDto storyFileDto)
            throws Exception {

        final StoryFile storyFile = storyFileDao.addFile(storyId, storyFileDto.getFileName());

        final String storedFilename = storyFile.getId() + "_" + storyFileDto.getFileName();

        storyFile.setStoredFileName(storedFilename);
        storyFile.setFileType(storyFileDto.getFileType());

        commonDao.update(storyFile);

        LOGGER.info("Save file {} for story {}", storedFilename, storyId);

        final byte[] fileContentBytes = Base64.decode(storyFileDto.getContent());
        FileUtils.writeByteArrayToFile(new File(storyDataRepositoryService.getUserFolder(username), storedFilename), fileContentBytes);

        return storyFile.getId();
    }

    @Transactional(readOnly = true)
    public StoryFile getStoryFile(final Long storyFileId)
            throws StoryDataNotFoundException, InternalServerException {

        if (storyFileId == null || storyFileId <= 0) {
            throw new ValidationException(String.format("Invalid legacyFileId %s", storyFileId));
        }

        final StoryFile storyFile = commonDao.retrieve(StoryFile.class, storyFileId);
        if (storyFile == null) {
            throw new StoryDataNotFoundException(ErrorCode.RESOURCE_NOT_FOUND, String.format("Story file with id %s not found", storyFileId));
        }

        return storyFile;

    }

    /**
     * This method only get list of files without the contents
     * 
     * @param username
     * @return
     */
    @Transactional(readOnly = true)
    public StoryDataListDto getAllStoryFiles(final long storyId) {
        final StoryDataListDto dataListDto = new StoryDataListDto();

        final List<StoryFile> storyFiles = storyFileDao.getAllFiles(storyId);

        for (final StoryFile file : storyFiles) {
            dataListDto.addStoryFile(beanMapper.map(file, StoryFileDto.class));
        }

        return dataListDto;
    }

}
