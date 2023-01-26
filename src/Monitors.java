import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;

public class Monitors {
    public static GraphicsDevice[] getScreenDevices() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    }

    // https://stackoverflow.com/a/17130444
    public static Rectangle getScreenBounds() {
        Rectangle bounds = new Rectangle();

        for (GraphicsDevice gd : getScreenDevices()) {
            bounds.add(gd.getDefaultConfiguration().getBounds());
        }

        return bounds;
    }
}