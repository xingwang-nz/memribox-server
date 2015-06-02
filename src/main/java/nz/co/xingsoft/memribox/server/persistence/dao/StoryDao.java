package nz.co.xingsoft.memribox.server.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.springframework.stereotype.Component;

@Component
public class StoryDao
        extends AbstractDao<Story> {

    @Inject
    private CommonDao commonDao;

    public List<Story> getAllStoriesForUser(final String username) {
        return commonDao.getListResultByNamedQuery(Story.NAMED_QUERY_FIND_ALL_STORIES_FOR_USER, new QueryParameter("username", username));
    }

    public void addStory(final User user, final Story story) {

        story.setCreatedTime(new Date());
        story.setUser(user);

        commonDao.save(story);
    }

}
