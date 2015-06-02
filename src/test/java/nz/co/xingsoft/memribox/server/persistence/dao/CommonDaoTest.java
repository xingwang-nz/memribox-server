package nz.co.xingsoft.memribox.server.persistence.dao;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Date;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.common.StoryFileType;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryFile;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryText;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class CommonDaoTest
        extends AbstractDaoTest {

    @Inject
    private UserDao userDao;

    @Test
    public void testLoadUsers() {
        final User user = userDao.getUserByUserName("xing");
        System.out.println(user.getRoles().size());
    }

    @Test
    public void testAddNewUserAndFile() {

        final User user = createTestUser();

        commonDao.save(user);

        // locate the user
        final User newUser = userDao.getUserByUserName(TEST_USER_NAME);
        assertThat(newUser).isNotNull();

    }

    @Test
    public void testAddStoryWithFileAndText() {

        final User user = createTestUser();
        commonDao.save(user);

        final Story story = createTestStory(user);
        commonDao.save(story);
        System.out.println(story.getId());

        final StoryFile storyFile = new StoryFile();
        storyFile.setCreatedTime(new Date());
        storyFile.setFileName("testfile.png");
        storyFile.setFileType(StoryFileType.IMG);
        storyFile.setMetaInfo("");
        storyFile.setStoredFileName("1-");
        storyFile.setStory(story);
        story.addStoryFile(storyFile);

        commonDao.update(story);
        // commonDao.save(storyFile);

        final StoryText storyText = new StoryText();
        storyText.setCreatedTime(new Date());
        storyText.setText("text ..");
        storyText.setStory(story);
        story.addStoryText(storyText);

        commonDao.update(story);
        // commonDao.save(storyText);

    }
}
