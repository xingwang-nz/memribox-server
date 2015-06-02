package nz.co.xingsoft.memribox.server.util;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.io.Opener;
import ij.process.ImageProcessor;
import ij.process.StackProcessor;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MiscUtil {

    private static final Logger logger = LoggerFactory.getLogger(MiscUtil.class);

    public void cropAndResizeImage(final File imageFile, final File newImageFile, final int newWidth, final int newHeight) {

        final Opener opener = new Opener();

        final ImagePlus imp = opener.openImage(imageFile.getAbsolutePath());

        final ImageProcessor ip = imp.getProcessor();
        StackProcessor sp = new StackProcessor(imp.getStack(), ip);

        final int width = imp.getWidth();
        final int height = imp.getHeight();

        int cropWidth = 0;
        int cropHeight = 0;

        if (width > height) {
            cropWidth = height;
            cropHeight = height;
        } else {
            cropWidth = width;
            cropHeight = width;
        }

        int x = -1;
        int y = -1;

        if (width == height) {
            x = 0;
            y = 0;
        } else if (width > height) {
            x = (width - height) / 2;
            y = 0;
        } else if (width < height) {
            x = 0;
            y = (height - width) / 2;
        }

        logger.debug("cropWidth {}", cropWidth);
        logger.debug("cropHeight {}", cropHeight);

        final ImageStack croppedStack = sp.crop(x, y, cropWidth, cropHeight);

        imp.setStack(null, croppedStack);

        sp = new StackProcessor(imp.getStack(), imp.getProcessor());

        final ImageStack resizedStack = sp.resize(newWidth, newHeight, true);

        imp.setStack(null, resizedStack);

        IJ.save(imp, newImageFile.getAbsolutePath());

    }

}
