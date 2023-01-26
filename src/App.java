import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class App {
    public static void main(String[] args) {
        JFrame fullFrame = new JFrame();
        Rectangle screenBounds = Monitors.getScreenBounds();
        fullFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fullFrame.setUndecorated(true);
        fullFrame.setSize(screenBounds.width, screenBounds.height);
        fullFrame.setLocation(screenBounds.x, screenBounds.y);
        fullFrame.setBackground(new Color(0, 0, 0, 1));
        fullFrame.setAlwaysOnTop(true);
        fullFrame.setVisible(true);

        JPanel captureFrame = new JPanel();
        captureFrame.setSize(0, 0);
        captureFrame.setBackground(new Color(25, 25, 25, 80));
        captureFrame.setBorder(new LineBorder(Color.WHITE, 1));
        fullFrame.add(captureFrame);

        new MouseListener(new MouseCallbacks() {
            private boolean isMousePressed = false;

            private int nativeDragStartX, nativeDragStartY;
            private int dragStartX, dragStartY;

            @Override
            public void onMousePressed(NativeMouseEvent e) {
                if (e.getButton() != 1) return;

                isMousePressed = true;
                nativeDragStartX = e.getX();
                nativeDragStartY = e.getY();
                Point mousePos = fullFrame.getMousePosition();
                dragStartX = mousePos.x;
                dragStartY = mousePos.y;

                captureFrame.setLocation(dragStartX, dragStartY);
                captureFrame.setSize(0, 0);
            }

            @Override
            public void onMouseReleased(NativeMouseEvent e) {
                if (e.getButton() != 1) return;

                isMousePressed = false;

                Rectangle captureFrameBounds = captureFrame.getBounds();

                captureFrame.setSize(0, 0);

                try {
                    Thread.sleep(50); // Allow time for the capture frame be hidden.
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                Rectangle screenshotBounds = new Rectangle(nativeDragStartX, nativeDragStartY, captureFrameBounds.width, captureFrameBounds.height);

                File output = new File("screenshot.png");

                try {
                    Screenshot.takeScreenshot(output, screenshotBounds);

                    String text = OCR.doOCR(output);

                    StringSelection selection = new StringSelection(text);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                output.delete();

                System.exit(0);
            }

            @Override
            public void onMouseDragged(NativeMouseEvent e) {
                if (!isMousePressed) return;

                int x = e.getX();
                int y = e.getY();

                captureFrame.setSize(x - nativeDragStartX, y - nativeDragStartY);
            }
        });
    }
}