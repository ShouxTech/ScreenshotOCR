import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public interface MouseCallbacks {
	void onMousePressed(NativeMouseEvent e);
	void onMouseReleased(NativeMouseEvent e);
	void onMouseDragged(NativeMouseEvent e);
}