package nz.co.xingsoft.memribox.server.business.dto;

import java.util.Date;

public class StoryDataDto {

    private Long id;

    private Date createdTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(final Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
