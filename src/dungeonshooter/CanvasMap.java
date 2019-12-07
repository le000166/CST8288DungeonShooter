package dungeonshooter;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;

import dungeonshooter.animator.Animator;
import dungeonshooter.entity.Bullet;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.Player;
import dungeonshooter.entity.PolyShape;
import dungeonshooter.entity.property.HitBox;


/**
 * this class represents the drawing area. it is backed by {@link Canvas} class.
 * this class itself does not handle any of the drawing. this task is
 * accomplished by the {@link AnimationTimer}.
 * 
 * @author Shahriar (Shawn) Emami
 * @version Jan 13, 2019
 */
public class CanvasMap {

	
	private Canvas map;
	private Animator animator;
	

	
	private BooleanProperty drawBounds;
	private BooleanProperty drawFPS;
	private PolyShape border;
	
	private List<Entity> players;
	private List<Entity> projectiles;
	private List<Entity> buffer;
	//because we are using other methods in PolyShape so better use PolyShape than Entity
	private List<PolyShape> staticShapes;
	
	private List<Entity> enemies;
	
	
	public CanvasMap() {
		drawBounds = new SimpleBooleanProperty();
		drawFPS = new SimpleBooleanProperty();
		animator = new Animator();
		border = new PolyShape();
		projectiles = new ArrayList<>(500);
		buffer = new ArrayList<>(500);
		staticShapes = new ArrayList<>(50);
		players = new ArrayList<>(1);
		enemies = new ArrayList<>(5);
		border.setPoints(0,0,700,0,700,700,0,700);
		border.getDrawable().setFill( new ImagePattern( new Image( "file:assets/floor/StoneFloorTexture.png"), 0, 0, 256, 256, false));
	}
	
	
	//boolean type return
	public boolean getDrawFPS() {
		return drawFPS.get();
	}
	
	public boolean getDrawBounds() {
		return drawBounds.get();
	}
	
	public BooleanProperty drawBoundsProperty() {
		return drawBounds;
	}

	public BooleanProperty drawFPSProperty() {
		return drawFPS;
	}


	/**
	 * create a constructor and initialize all class variables.
	 */

	
	
	public void addSampleShapes() {

		
		PolyShape s = new PolyShape().setPoints( 100, 100, 200, 100, 200, 200, 100, 200);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().randomize( 350, 150, 100, 3, 10);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().setPoints( 600, 100, 500, 100, 450, 200, 550, 200);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().randomize( 550, 350, 100, 3, 10);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().setPoints( 600, 600, 500, 600, 550, 500);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().randomize( 350, 550, 100, 3, 10);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().setPoints( 100, 600, 200, 600, 250, 500, 150, 500);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		s = new PolyShape().randomize( 150, 350, 100, 3, 10);
		s.getDrawable().setStroke( Color.BLACK).setWidth( 1.5);
		staticShapes.add( s);
		

	} 
	


	public CanvasMap setAnimator(Animator newAnimator) {
		if (animator != null) {
			this.stop();
		} 
			animator = newAnimator;
		return this;
	}



	public void start() {
		animator.start();
		
	}


	public void stop() {
		animator.stop();
	}



	public Canvas getCanvas() {
		return map;
	}


	public GraphicsContext gc() {
		return map.getGraphicsContext2D();
	}

	public double h() {
		return map.getHeight();
	}

	public double w() {
		return map.getWidth();
	}
	
	public CanvasMap setDrawingCanvas(Canvas map) {
		//error checking for null
		 this.map = map;
		 
		 if(map != null) {
			 
			this.map.widthProperty().addListener((data, oldvalue, newvalue) -> border.setPoints(0,0,w(),0,w(),h(),0,h())); 
			this.map.heightProperty().addListener((data, oldvalue, newvalue) -> border.setPoints(0,0,w(),0,w(),h(),0,h()));
			
		 }
		 //add changelistener for canvas and update border size
		 return this;
		
	}
	
	public List<Entity> enemies() {
		return enemies;
		
	}
	
	
	
	public List<PolyShape> staticShapes() {
		return staticShapes;
		
	}
	
	public List<Entity> players() {
		return players;
		
	}
	
	
	public List<Entity> projectiles() {
		return projectiles;
		
	}
	
	
	
	public void fireBullet(Bullet bullet) {
		buffer.add(bullet);
	}
	
	public void updateProjectilesList() {
		projectiles.addAll(buffer);
		buffer.clear();
	}
	
	public PolyShape getMapShape() {
		return border;
		
	}
	
	public boolean inMap(HitBox hitbox) {
		return border.getHitBox().containsBounds(hitbox);
		
	}
	
}

