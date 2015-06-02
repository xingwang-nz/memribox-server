package nz.co.xingsoft.memribox.server.business.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import nz.co.xingsoft.memribox.server.common.StoryFileType;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class StoryFileDto
        extends StoryDataDto {

    @NotBlank(message = "fileName is blank")
    private String fileName;

    @NotNull(message = "file type is not provided")
    private StoryFileType fileType;

    @NotBlank(message = "file content is blank")
    private String content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public StoryFileType getFileType() {
        return fileType;
    }

    public void setFileType(final StoryFileType fileType) {
        this.fileType = fileType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

}
