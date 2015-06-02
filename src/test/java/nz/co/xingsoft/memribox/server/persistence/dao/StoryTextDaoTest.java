package nz.co.xingsoft.memribox.server.persistence.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryText;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.junit.Test;

public class StoryTextDaoTest
        extends AbstractDaoTest {

    @Inject
    private StoryTextDao storyTextDao;

    @Test
    public void testAddTestsAndRetrieve() {

        final User user = createTestUser();
        commonDao.save(user);

        final Story story = createTestStory(user);
        commonDao.save(story);

        storyTextDao.addStoryText(story.getId(), "text1");
        storyTextDao.addStoryText(story.getId(), "text2");

        final List<StoryText> texts = storyTextDao.getAllTexts(story.getId());
        assertThat(texts).hasSize(2);

    }

}
