package nz.co.xingsoft.memribox.server.business.dto;

public class StoryTextDto
        extends StoryDataDto {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
