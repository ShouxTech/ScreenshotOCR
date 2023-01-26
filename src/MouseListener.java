import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class MouseListener implements NativeMouseInputListener {
	private MouseCallbacks mouseCallbacks;

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		try {
			this.mouseCallbacks.onMousePressed(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		try {
			this.mouseCallbacks.onMouseReleased(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		try {
			this.mouseCallbacks.onMouseDragged(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public MouseListener(MouseCallbacks mouseCallbacks) {
		this.mouseCallbacks = mouseCallbacks;

		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
	}
}