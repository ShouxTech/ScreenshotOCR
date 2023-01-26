import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Screenshot {
    public static void takeScreenshot(File output, Rectangle bounds) throws AWTException, IOException {
        BufferedImage image = new Robot().createScreenCapture(bounds);
        ImageIO.write(image, "png", output);
    }
}