package nz.co.xingsoft.memribox.server.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import nz.co.xingsoft.memribox.server.common.StoryDataType;

@Entity
@Table(name = "story_data")
@Inheritance(strategy = InheritanceType.JOINED)
// @formatter:off
@NamedQueries({ 
        @NamedQuery(name = StoryData.NAMED_QUERY_FIND_ALL_STORY_FILES_FOR_STORY, query = "select f from StoryFile f where f.story.id = :storyId"),
        @NamedQuery(name = StoryData.NAMED_QUERY_FIND_ALL_STORY_TEXTS_FOR_STORY, query = "select t from StoryText t where t.story.id = :storyId") })
// @formatter:on
public abstract class StoryData
        extends EntityBean {

    public static final String NAMED_QUERY_FIND_ALL_STORY_FILES_FOR_STORY = "StoryData.findAllStoryFilesForStory";
    public static final String NAMED_QUERY_FIND_ALL_STORY_TEXTS_FOR_STORY = "StoryData.findAllStoryTextsForStory";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private StoryDataType dataType;

    @Column(name = "created_time")
    private Date createdTime;

    public Story getStory() {
        return story;
    }

    public void setStory(final Story story) {
        this.story = story;
    }

    public StoryDataType getDataType() {
        return dataType;
    }

    public void setDataType(final StoryDataType dataType) {
        this.dataType = dataType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(final Date createdTime) {
        this.createdTime = createdTime;
    }

}
