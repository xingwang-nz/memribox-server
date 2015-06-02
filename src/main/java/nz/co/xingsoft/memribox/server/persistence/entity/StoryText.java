package nz.co.xingsoft.memribox.server.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import nz.co.xingsoft.memribox.server.common.StoryDataType;

@Entity
@Table(name = "story_text")
@PrimaryKeyJoinColumn(name = "ID")
public class StoryText
        extends StoryData {

    private String text;

    public StoryText() {
        super();
        setDataType(StoryDataType.TEXT);
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
