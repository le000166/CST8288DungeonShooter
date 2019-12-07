package dungeonshooter.entity;

import dungeonshooter.entity.property.HitBox;
import dungeonshooter.entity.property.Sprite;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import utility.Point;

import java.util.List;

import dungeonshooter.CanvasMap;

/**
 * DONE
 * */

public class Enemy implements Entity {

	private Rotate rotationEnemy;
	private double angle;
	private double playerFrame = 0;
	private double muzzleFrame = 0;
	private Point pos;
	private Point dimension;
	private Point prev;
	private Sprite sprite;
	private HitBox hitbox;

	private CanvasMap map;
	

	public Enemy(double x, double y, double w, double h) {
		rotationEnemy = new Rotate();
		pos = new Point(x - w / 20, y - h / 20);
		prev = new Point(pos);
		dimension = new Point(w, h);
		
		
		
		

		// Create sprite
		sprite = new Sprite() {
			// player and muzzle each have 20 and 16 set of images than can be loaded
			private final Image[] ENEMY = new Image[20];
			{
				// load the images
				for (int i = 0; i < ENEMY.length; i++) {
					ENEMY[i] = new Image("file:assets/Poses/idle.png");
				}
			}
			
			
			@Override
			public void draw(GraphicsContext gc) {
				gc.save();
				// draw player image
				gc.drawImage(ENEMY[(int) playerFrame], pos.x(), pos.y(), dimension.x(), dimension.y());
				gc.restore(); // this number is how fast the next frame of player animation will be drawn. The
								// higher the faster.
				
			}

		};
		
		// create HitBox
		double size = h * .74;
		hitbox = new HitBox().setBounds(pos.x() + dimension.x() * .303 - size / 2,
				pos.y() + dimension.y() * .58 - size / 2, size, size);

	}

	
	public Enemy setMap(CanvasMap map) {
		this.map = map;
		return this;
	}

	public double getEnemyCenterX() {
		return pos.x() + dimension.x() * .303;
	}

	public double getEnemyCenterY() {
		return pos.y() + dimension.y() * .58;
	}

	public double getRifleMuzzleX() {
		return pos.x() + dimension.x() * .93;
	}

	public double getRifleMuzzleY() {
		return pos.y() + dimension.y() * .73;
	}

	public void calculateAngles() {
//		angle = Math.toDegrees( Math.atan2( myPlayer.getInput().y() - getEnemyCenterY(), myPlayer.getInput().y() - getEnemyCenterX())); 
		rotationEnemy.setAngle(this.angle);
		rotationEnemy.setPivotX(getEnemyCenterX());
		rotationEnemy.setPivotY(getEnemyCenterY());
	
	}

	public void stepBack() {
		//This will undo last move.
		hitbox.undoTranslate();
		pos.move(prev);
	}

	
	/**
	 * have access pos:Point
	 * Player::Update
	 * hitbox:HitBox
	 * prev:Point
	 * Player:CalculateAngles
	 * */
	public void update() {
		List<Entity> players = map.players();
		Player myPlayer = (Player) players.get(0);	
		
		
	}

	public boolean isDrawable() {
		return true;
	}

	@Override
	public boolean hasHitBox() {
		// TODO Auto-generated method stub
		return true;
	}

	public HitBox getHitBox() {
		return hitbox;
	}

	public Sprite getDrawable() {
		return sprite;

	}

}
