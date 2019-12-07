package dungeonshooter.entity.property;

import dungeonshooter.entity.Entity;

import dungeonshooter.entity.geometry.RectangleBounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.IntersectUtil;
import utility.Point;

public class HitBox implements Entity {

	private Point prev;
	private RectangleBounds bounds;

	private Sprite sprite;
	private double[][] points;
	private double[] result;

	public HitBox() {
		// TODO Auto-generated constructor stub
		prev = new Point();
		points = new double[2][4];
		result = new double[4];
		prev = new Point();

		sprite = new Sprite() {
			@Override
			public void draw(GraphicsContext gc) {
				gc.setStroke(getStroke());
				gc.setLineWidth(getWidth());
				gc.strokeRect(bounds.x(), bounds.y(), bounds.w(), bounds.h());
			}

			
		};
		sprite.setStroke(Color.RED).setWidth(3);
		
	}

	public HitBox setBounds(RectangleBounds bounds) {
		this.bounds = bounds;
		return this;
	}

	public HitBox setBounds(double x, double y, double w, double h) {
		this.bounds = new RectangleBounds(x, y, w, h);
		return this;

	}

	public HitBox translate(double dx, double dy) {
		prev.move(bounds.startPos());
		bounds.translate(dx, dy);
		return this;
	}

	//This sets the position to previous state.
	public HitBox undoTranslate() {
		bounds.move(prev);
		return this;
		
	}

	/**
	 * containsBounds checks if a hitbox is completely within current hitbox
	 * 
	 * */
	public boolean containsBounds(HitBox box) {
		return bounds.contains(box.getBound());
	}

	public boolean intersectBounds(HitBox box) {
		return bounds.intersects(box.getBound());
	}

	/**
	 * intersectBounds check if a hitbox is within and or overlapping the current box
	 * @param box
	 * @return
	 */
	public boolean intersectFull(HitBox box) {
		
		if (intersectBounds(box)) {
			return intersectFull(box.getPoints());
		} else {
			return false;
		}
		
		
	}

	/**
	 * check for intersection between all line segments of points in current hitbox and points passes as argument/
	 * @param otherPoints
	 * @return
	 */
	
	/**
	 * intersectBounds check if a hitbox is within and or overlapping the current
	 * box 
	 * This method will have 2 for loops one nested in other. 
	 * Outer loop will go through current hitbox points 
	 * while inner loop goes through otherpoints. 
	 * ii. In the inner loop IntersectUtil.getIntersection() method to check if lines
	 * cross. 
	 * iii. If the result of IntersectUtil.getIntersection() is true check to
	 * see if result[2] <= 1 is also true. 
	 * We want to make sure lines are crossing each other on segments specified. 
	 * If both are true return true otherwise
	 * continue looking. 
	 * iv. Return false if nothing found.
	 */
	//Question how to write
	protected boolean intersectFull(double[][] otherPoints) {
		
		points = getPoints();
		
		for (int i = 0, j = points[0].length-1; i<points[0].length; i++, j = i -1) {
			for (int k = 0, h = otherPoints[0].length - 1; k < otherPoints[0].length; k++, h = k - 1) {
				//boolean doesIntersect = IntersectUtil.getIntersection(intersectResult, startX, startY, startX + endX, startY + endY,s.pX(i),s.pY(i),s.pX(j), s.pY(j));
				boolean doesIntersect = IntersectUtil.getIntersection(result, points[0][i], points[1][i], points[0][j], points[1][j],otherPoints[0][k],otherPoints[1][k],otherPoints[0][h], otherPoints[1][h]);
				if(doesIntersect && result[2] <= 1) {
					return true;
				}
				
			}
			
		}
		
		return false;
	}

	protected boolean hasIntersectFull() {
		return false;
	}

	protected double[][] getPoints() {
		return bounds.toArray(points);
	}

	public void update() {
	}

	public HitBox getHitBox() {
		return this;
	}

	public boolean isDrawable() {
		return true;
	}

	public Sprite getDrawable() {
		return sprite;
	}


	@Override
	public String toString() {
		return "HitBox";
	}

	public RectangleBounds getBound() {
		return bounds;
	}

	public Point getPrev() {
		return prev;
	}

	
	public boolean hasHitBox() {
		return false;
	}

}
