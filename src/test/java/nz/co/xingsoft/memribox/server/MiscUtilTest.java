package nz.co.xingsoft.memribox.server;

import java.io.File;
import java.net.URISyntaxException;

import nz.co.xingsoft.memribox.server.util.MiscUtil;

import org.junit.Before;
import org.junit.Test;

public class MiscUtilTest {

    private MiscUtil miscUtil;

    @Before
    public void init() {
        miscUtil = new MiscUtil();
    }

    @Test
    public void testCropAndResizeImage()
            throws URISyntaxException {

        final File imageFile = new File(this.getClass().getClassLoader().getResource("images/default-profile.jpg").toURI());

        miscUtil.cropAndResizeImage(imageFile, new File("target", imageFile.getName()), 100, 100);

    }

}
