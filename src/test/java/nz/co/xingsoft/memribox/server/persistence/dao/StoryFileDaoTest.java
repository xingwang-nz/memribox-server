package nz.co.xingsoft.memribox.server.persistence.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryFile;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.junit.Test;

public class StoryFileDaoTest
        extends AbstractDaoTest {

    @Inject
    private StoryFileDao storyFileDao;

    @Test
    public void testAddFileAndRetrieveFiles() {
        final User user = createTestUser();
        commonDao.save(user);

        final Story story = createTestStory(user);
        commonDao.save(story);

        storyFileDao.addFile(story.getId(), "file1");
        storyFileDao.addFile(story.getId(), "file2");

        final List<StoryFile> files = storyFileDao.getAllFiles(story.getId());
        assertThat(files).hasSize(2);

    }

}
