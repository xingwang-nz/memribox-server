package nz.co.xingsoft.memribox.server.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryData;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryFile;

import org.springframework.stereotype.Component;

@Component
public class StoryFileDao
        extends AbstractDao<StoryFile> {

    @Inject
    private CommonDao commonDao;

    public StoryFile addFile(final Long storyId, final String filename) {

        final Story story = commonDao.retrieve(Story.class, storyId);

        final StoryFile file = new StoryFile();
        file.setCreatedTime(new Date());
        file.setFileName(filename);
        file.setStoredFileName(filename);
        file.setMetaInfo("");
        file.setStory(story);

        commonDao.save(file);

        return file;
    }

    public List<StoryFile> getAllFiles(final Long storyId) {

        return commonDao.getListResultByNamedQuery(StoryData.NAMED_QUERY_FIND_ALL_STORY_FILES_FOR_STORY, new QueryParameter("storyId", storyId));

    }
}
