package nz.co.xingsoft.memribox.server.persistence.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "story")
@NamedQueries({ @NamedQuery(name = Story.NAMED_QUERY_FIND_ALL_STORIES_FOR_USER, query = "select s from Story s where upper(s.user.username) = :username") })
public class Story
        extends EntityBean {

    public static final String NAMED_QUERY_FIND_ALL_STORIES_FOR_USER = "Story.findAllForUser";

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Temporal(TemporalType.DATE)
    @Column(name = "time_line", nullable = false)
    private Date timeLine;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = StoryData.class)
    private Set<StoryText> storyTexts = new HashSet<>();

    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = StoryData.class)
    private Set<StoryFile> storyFiles = new HashSet<>();

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public Date getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(final Date timeLine) {
        this.timeLine = timeLine;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(final Date createdTime) {
        this.createdTime = createdTime;
    }

    public Set<StoryText> getStoryTexts() {
        return storyTexts;
    }

    public void setStoryTexts(final Set<StoryText> storyTexts) {
        this.storyTexts = storyTexts;
    }

    public Set<StoryFile> getStoryFiles() {
        return storyFiles;
    }

    public void setStoryFiles(final Set<StoryFile> storyFiles) {
        this.storyFiles = storyFiles;
    }

    public void addStoryFile(final StoryFile storyFile) {
        this.storyFiles.add(storyFile);
    }

    public void addStoryText(final StoryText storyText) {
        this.storyTexts.add(storyText);
    }

}
