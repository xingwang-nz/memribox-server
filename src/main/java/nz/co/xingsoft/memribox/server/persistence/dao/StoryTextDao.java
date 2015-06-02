package nz.co.xingsoft.memribox.server.persistence.dao;

import java.util.Date;
import java.util.List;

import nz.co.xingsoft.memribox.server.common.QueryParameter;
import nz.co.xingsoft.memribox.server.persistence.entity.Story;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryData;
import nz.co.xingsoft.memribox.server.persistence.entity.StoryText;

import org.springframework.stereotype.Component;

@Component
public class StoryTextDao
        extends AbstractDao<StoryText> {

    public StoryText addStoryText(final Long storyId, final String text) {
        final Story story = commonDao.retrieve(Story.class, storyId);

        final StoryText storyText = new StoryText();
        storyText.setCreatedTime(new Date());
        storyText.setText(text);
        storyText.setStory(story);

        commonDao.save(storyText);

        return storyText;
    }

    public List<StoryText> getAllTexts(final Long storyId) {
        return commonDao.getListResultByNamedQuery(StoryData.NAMED_QUERY_FIND_ALL_STORY_TEXTS_FOR_STORY, new QueryParameter("storyId", storyId));
    }
}
