package nz.co.xingsoft.memribox.server.persistence.dao;

import java.util.Date;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.SpringApplicationContextTest;
import nz.co.xingsoft.memribox.server.common.Gender;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.junit.Ignore;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@TransactionConfiguration(defaultRollback = true)
@Transactional
@Ignore
public class AbstractDaoTest
        extends SpringApplicationContextTest {

    protected static final String TEST_USER_NAME = "dummy-user";

    @Inject
    protected CommonDao commonDao;

    protected User createTestUser() {
        // add a new user
        final User user = new User();
        user.setCreatedTime(new Date());
        user.setEmail("xingwang-10.nz@gmail.com");
        user.setFirstName("xing");
        user.setLastName("wang");
        // password = 12345
        user.setPassword("bce4ce313eb55fd2882e5acaca0648bae15a101d07ee884ed66535b595c2178f");
        user.setUsername(TEST_USER_NAME);
        user.setBirthDay(new Date());
        user.setGender(Gender.M);
        user.setNativeTongue("Chinese");
        user.setHomeCountry("China");

        return user;
    }

    protected Story createTestStory(final User user) {
        final Story story = new Story();
        story.setUser(user);
        story.setCreatedTime(new Date());
        story.setSummary("this is my story symmary");
        story.setTimeLine(new Date());
        story.setTitle("First story");

        return story;
    }

}
