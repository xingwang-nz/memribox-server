package nz.co.xingsoft.memribox.server.business.services;

import javax.inject.Inject;

import nz.co.xingsoft.memribox.server.SpringApplicationContextTest;
import nz.co.xingsoft.memribox.server.business.dto.StoryDataListDto;

import org.junit.Test;

public class StoryDataServiceTest
        extends SpringApplicationContextTest {

    @Inject
    private StoryDataService storyDataService;

    @Test
    public void testGetAllFileForUser() {

        final StoryDataListDto legacyDataListDto = storyDataService.getAllStoryFiles(1L);

        System.out.println(legacyDataListDto.getFileList().size());

    }

}
