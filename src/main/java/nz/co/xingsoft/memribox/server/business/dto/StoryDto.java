package nz.co.xingsoft.memribox.server.business.dto;

import java.util.Date;

import nz.co.xingsoft.memribox.server.util.CustomDateDeserializer;
import nz.co.xingsoft.memribox.server.util.CustomDateSerializer;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class StoryDto {

    private Long id;

    private String title;

    private String summary;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date timeLine;

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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
