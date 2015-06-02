package nz.co.xingsoft.memribox.server.business.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.business.dto.ErrorCode;
import nz.co.xingsoft.memribox.server.business.dto.StoryDto;
import nz.co.xingsoft.memribox.server.business.exception.BusinessLogicException;
import nz.co.xingsoft.memribox.server.persistence.dao.StoryDao;
import nz.co.xingsoft.memribox.server.persistence.dao.UserDao;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.User;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class StoryService {

    @Inject
    private StoryDao storyDao;

    @Inject
    private UserDao userDao;

    @Inject
    private DozerBeanMapper beanMapper;

    public StoryDto addStory(final String username, final StoryDto storyDto)
            throws BusinessLogicException {

        final User user = loadUser(username);

        final Story story = beanMapper.map(storyDto, Story.class);
        story.setCreatedTime(new Date());

        storyDao.addStory(user, story);

        storyDto.setId(story.getId());

        return storyDto;

    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Story> getAllSotroies(final String username)
            throws BusinessLogicException {

        return (List<Story>) CollectionUtils.collect(storyDao.getAllStoriesForUser(username), new Transformer() {
            @Override
            public Object transform(final Object input) {
                return beanMapper.map(input, StoryDto.class);
            }
        });
    }

    private User loadUser(final String username)
            throws BusinessLogicException {
        final User user = userDao.getUserByUserName(username);
        if (user == null) {
            throw new BusinessLogicException(ErrorCode.USER_NOT_EXISTS, String.format("User %s does not exist", username));
        }

        return user;
    }

}
