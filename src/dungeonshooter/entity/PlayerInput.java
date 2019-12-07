package dungeonshooter.entity;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import utility.InputAdapter;
import utility.Point;

public class PlayerInput  {

	private double x;
	private double y;
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean leftClick = false;
	private boolean rightClick = false;
	private boolean middleClick = false;
	private boolean space = false;
	private boolean shift = false;
	private InputAdapter adapter;
	private Point mouse;

	public PlayerInput(InputAdapter adapter) {
		mouse = new Point();
		adapter.forceFocusWhenMouseEnters();
		adapter.registerMouseMovment(this::moved, this::dragged);
		adapter.registerMouseClick(this::mousePressed, this::mouseReleased);
		adapter.registerKey(this::keyPressed, this::keyReleased);
	}

	public boolean hasMoved() {

		return left || right || up || down;

	}

	public int leftOrRight() {

		if (left) {
			return -1;
		} else if (right) {
			return 1;
		} else {
			return 0;
		}

	}

	public int upOrDown() {
		if (up) {
			return -1;
		} else if (down) {
			return 1;
		}else {
			return 0;
		}
	}

	public boolean leftClicked() {
		return leftClick;

	}

	public boolean rightClicked() {
		return rightClick;

	}

	public boolean middleClicked() {
		return middleClick;

	}

	public double x() {
		return x;

	}

	public double y() {
		return y;

	}

	protected void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		leftClick = e.isPrimaryButtonDown();
		rightClick = e.isSecondaryButtonDown();
		middleClick = e.isMiddleButtonDown();
		//TODO call super.
		//super.mousePressed();
		
	}

	// Working... super method does not work
	// what is mouse related boolean
	protected void mouseReleased(MouseEvent e) {

		leftClick = false;
		rightClick = false;
		middleClick = false;

		//TODO call super
		//super.mouseReleased(e);
	}

	public void changeKeyStatus(KeyCode key, boolean isPressed) {
		switch (key) {
		case W:
			up = isPressed;
			break;
		case A:
			left = isPressed;
			break;
		case D:
			right = isPressed;
			break;
		case S:
			down = isPressed;
			break;
		case SPACE:
			space = isPressed;
			break;
		case SHIFT:
			shift = isPressed;
			break;
		}

	}

	protected void keyPressed(KeyEvent key) {

		this.changeKeyStatus(key.getCode(), true);

	}

	protected void keyReleased(KeyEvent key) {
		this.changeKeyStatus(key.getCode(), false);
	}

	public boolean isSpace() {
		return space;
	}

	public boolean isShift() {
		return shift;
	}

	// moved and dragged methods are overridden to record x and y of mouse.
	protected void moved(MouseEvent e) {

		mouse.x(e.getX());
		mouse.y(e.getY());

	}

	protected void dragged(MouseEvent e) {
		mouse.set(e.getX(), e.getY());
	}

}
