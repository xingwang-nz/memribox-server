package nz.co.xingsoft.memribox.server.business.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StoryDataRepositoryService {

    @Value("${story.data.stored.folder}")
    private File storyDataStoredFolder;

    public File locateStoryFile(final String username, final String filename) {
        return new File(getUserFolder(username), filename);
    }

    public File getUserFolder(final String username) {
        final File folder = new File(storyDataStoredFolder, username);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder;

    }

}
