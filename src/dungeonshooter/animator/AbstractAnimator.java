package dungeonshooter.animator;

import javafx.animation.AnimationTimer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

import dungeonshooter.CanvasMap;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.FpsCounter;
import dungeonshooter.entity.PolyShape;
import utility.Point;

/**
 * this class must extend {@link AnimationTimer}. job of this class is to hold
 * common functionality among animators.
 * 
 * @author Shahriar (Shawn) Emami
 * @version Jan 13, 2019
 */
public abstract class AbstractAnimator extends AnimationTimer {

	private FpsCounter fps;

	/**
	 * this array has in order x, y, scalar of intersect point with ray and scalar
	 * of intersect with line segment.
	 */

	/**
	 * create a protected class variable of type {@link CanvasMap} and name it map.
	 */
	protected CanvasMap map;
	/**
	 * create a protected class variable of type {@link Point} and name it mouse.
	 */
	protected Point mouse;

	protected AbstractAnimator() {
		// constructor initialize fpsCounter object. add fill, stroke and width as
		// desired.
		mouse = new Point();
		fps = new FpsCounter(10, 25);
		fps.getDrawable().setFill(Color.BLACK).setStroke(Color.BROWN).setWidth(1);

	}

	/**
	 * create a setter called setCanvas to inject (set) the {@link CanvasMap}
	 * 
	 * @param map - {@link CanvasMap} object
	 */
	public void setCanvas(CanvasMap c) {
		this.map = c;
	}

	/**
	 * create a method called mouseDragged that is called every time the position of
	 * mouse changes.
	 * 
	 * @param e - {@link MouseEvent} object that hold the details of the mouse. use
	 *          {@link MouseEvent#getX} and {@link MouseEvent#getY}
	 */

	// clearAndFill(gc : GraphicsContext, background : Color) : void
	public void clearAndFill(GraphicsContext gc, Color background) {
		gc.setFill(background);
		gc.clearRect(0, 0, map.w(), map.h());
		gc.fillRect(0, 0, map.w(), map.h());
	}

	public void drawEntities(GraphicsContext gc) {
		map.updateProjectilesList();
		Consumer<Entity> draw = e -> {
			if (e.isDrawable()) {
				e.getDrawable().draw(gc);

				if (map.getDrawBounds()) {
					e.getHitBox().getDrawable().draw(gc);
				}

			}
		};

		draw.accept(map.getMapShape());

		for (PolyShape e : map.staticShapes()) {
			draw.accept(e);

		}

		for (Entity e : map.projectiles()) {
			draw.accept(e);
		}

		for (Entity e : map.players()) {
			draw.accept(e);
		}

		for (Entity e : map.enemies()) {
			draw.accept(e);
		}

	}

	/**
	 * <p>
	 * create a method called handle that is inherited from
	 * {@link AnimationTimer#handle(long)}. this method is called by JavaFX
	 * application, it should not be called directly.
	 * </p>
	 * <p>
	 * inside of this method call the abstract handle method
	 * {@link AbstractAnimator#handle(GraphicsContext, long)}.
	 * {@link GraphicsContext} can be retrieved from {@link CanvasMap#gc()}
	 * </p>
	 * 
	 * @param now - current time in nanoseconds, represents the time that this
	 *            function is called.
	 */
	@Override
	public void handle(long now) {
		GraphicsContext gc = map.gc();

		// Checking to getDrawLightSource
		if (map.getDrawFPS()) {
			this.fps.calculateFPS(now);

		}
		handle(now, gc);
		// Checking for DrawFps so Calculate FPS

		// if(map.getDrawFPS())

		// Checking for ShapeJoints and DrawBounds
		if (map.getDrawBounds())

			for (int i = 0; i < map.staticShapes().size(); i++) {
				if (map.getDrawBounds())
					map.staticShapes().get(i).getHitBox().getDrawable().draw(gc);
				;
			}

		if (map.getDrawFPS()) {

			fps.getDrawable().draw(gc);
		}
		// Checking for fps drawing

	}

	/**
	 * create a protected abstract method called handle, this method to be
	 * overridden by subclasses.
	 * 
	 * @param gc  - {@link GraphicsContext} object.
	 * @param now - current time in nanoseconds, represents the time that this
	 *            function is called.
	 */

	// protected is restricted only to subclasses
	protected abstract void handle(long now, GraphicsContext gc);
}
