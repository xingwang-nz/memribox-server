package nz.co.xingsoft.memribox.server.business.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StoryDataListDto
        implements Serializable {

    private List<StoryTextDto> textList = new ArrayList<>();

    private List<StoryFileDto> fileList = new ArrayList<>();

    public List<StoryTextDto> getTextList() {
        return textList;
    }

    public void addStoryFile(final StoryFileDto legacyFileDto) {
        fileList.add(legacyFileDto);
    }

    public List<StoryFileDto> getFileList() {
        return fileList;
    }

    public void addStoryText(final StoryTextDto storyTextDto) {
        textList.add(storyTextDto);
    }

}
