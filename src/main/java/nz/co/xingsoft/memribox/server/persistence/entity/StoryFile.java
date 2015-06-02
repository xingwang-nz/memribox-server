package nz.co.xingsoft.memribox.server.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import nz.co.xingsoft.memribox.server.common.StoryDataType;
import nz.co.xingsoft.memribox.server.common.StoryFileType;

@Entity
@Table(name = "story_file")
@PrimaryKeyJoinColumn(name = "ID")
public class StoryFile
        extends StoryData {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "stored_file_name")
    private String storedFileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private StoryFileType fileType;

    @Column(name = "meta_info")
    private String metaInfo;

    public StoryFile() {
        super();
        setDataType(StoryDataType.FILE);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getStoredFileName() {
        return storedFileName;
    }

    public void setStoredFileName(final String storedFileName) {
        this.storedFileName = storedFileName;
    }

    public StoryFileType getFileType() {
        return fileType;
    }

    public void setFileType(final StoryFileType fileType) {
        this.fileType = fileType;
    }

    public String getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(final String metaInfo) {
        this.metaInfo = metaInfo;
    }

}
