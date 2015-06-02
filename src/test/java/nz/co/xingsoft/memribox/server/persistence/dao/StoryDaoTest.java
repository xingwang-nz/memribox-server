package nz.co.xingsoft.memribox.server.persistence.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.junit.Test;

public class StoryDaoTest
        extends AbstractDaoTest {

    @Inject
    private StoryDao storyDao;

    @Test
    public void testAddStoriesForUser() {
        final User user = createTestUser();
        commonDao.save(user);

        Story story = createTestStory(user);
        storyDao.addStory(user, story);

        story = createTestStory(user);
        commonDao.save(story);

        final List<Story> stories = storyDao.getAllStoriesForUser(TEST_USER_NAME);

        assertThat(stories).hasSize(2);
    }
}
