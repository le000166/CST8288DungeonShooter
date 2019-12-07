package dungeonshooter.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import dungeonshooter.entity.Entity;
import dungeonshooter.entity.property.Sprite;
import utility.Point;
import utility.RandUtil;

import java.util.ArrayList;
import java.util.List;

import dungeonshooter.entity.property.HitBox;

/**
 * DONE
 */

public class PolyShape implements Entity {
	private int pointCount;
	private double[][] points;
	private double minX;
	private double minY;
	private double maxX;
	private double maxY;
	private HitBox hitbox;
	private Sprite sprite;

	public PolyShape() {


		// Code in Constructor of PolyShape
		//initialize Hitbox
		hitbox = new HitBox() {
			protected boolean hasIntersectFull() {
				return true;
				//Anonymous Block
			}

			protected double[][] getPoints() {
				return points;
			}
		};
		
		//initialize for sprite
		sprite = new Sprite() {
			//Anonymous Block 
			{
				setFill(new ImagePattern(new Image("file:assets/concrete/dsc_1621.png")));
			}

			public void draw(GraphicsContext gc) {
				gc.setLineWidth(getWidth());
				if (getStroke() != null) {
					gc.setStroke(getStroke());
					gc.strokePolygon(points[0], points[1], pointCount);
				}
				if (getFill() != null) {
					gc.setFill(getFill());
					gc.fillPolygon(points[0], points[1], pointCount);
				}
			}
		};
	}

	public PolyShape randomize(double centerX, double centerY, double size, int minPoints, int maxPoints) {
		pointCount = RandUtil.getInt(minPoints, maxPoints);
		points = new double[2][pointCount];
		final Point center = new Point(centerX, centerY);
		List<Point> unsortedPoints = new ArrayList<>(pointCount);
		centerX = 0;
		centerY = 0;
		// create random points using the Point class
		for (int i = 0; i < pointCount; i++) {
			Point randP = center.randomPoint(size);
			unsortedPoints.add(randP);
			// keep the total of all x and y values to calculate the new center
			centerX += randP.x();
			centerY += randP.y();
		}
		// set the new center
		center.set(centerX / pointCount, centerY / pointCount);
		// sort all the points based on their angular relationship to center
		// this prevents the lines from crossing each other.
		unsortedPoints.sort((p1, p2) -> center.angle(p1) > center.angle(p2) ? 1 : -1);
		int i = 0;
		// set the min and max of x to first index of unsortedPoints
		// set the min and max of y to first index of unsortedPoints
		minX = maxX = unsortedPoints.get(0).x();
		minY = maxY = unsortedPoints.get(0).y();
		// store all points in the points array
		for (Point p : unsortedPoints) {
			// check each point for potential min and max
			updateMinMax(p.x(), p.y());
			points[0][i] = p.x();
			points[1][i] = p.y();
			i++;
		}
		hitbox.setBounds(minX, minY, maxX-minX, maxY-minY);
		return this;
	}

//	setPoints can take many points. this method uses "..." which is called vararg. 
//	tread it the same as array inside of a function. nums is a one dimensional array, 
//	every 2 index is considered one point. it is formatted as x1,y1,x2,y2,x3,y3... . 
//	this method will initialize rest of variables in PolyShape object. min and max variables are used in 
//	this method to determine the 4 corners of PolyShape so bounds can be initialized. read the sequence diagram.

	public PolyShape setPoints(double... nums) {
//		initialize the value of minX and maxX to first index of nums.
//		initialize the value of minY and maxY to second index of nums.

//		initialize the points array to 2 rows and pointCount of columns

		minX = nums[0];
		minY = nums[1];
		maxX = nums[0];
		maxY = nums[1];

//		initialize the value of pointCount to the number of points in nums (not the length)  
		pointCount = nums.length / 2;

		points = new double[2][pointCount];

//	in points array each column is one point (x,y). 
//	first row is x and
//	second row is y.
//	now place the nums values which are in x1,y1,x2,y2,.. sequence inside of
//	points array. you need to keep track of index for each array and increment
//	them separately.

		for (int i = 0, j = 0; i < nums.length && j < points[0].length; i += 2, j++) {
			updateMinMax(nums[i], nums[i + 1]);
			points[0][j] = nums[i];
			points[1][j] = nums[i + 1];

		}

//	RectangleBounds(x : double = minX, y : double = minY, w : double = maxX-minX, h : double = maxY-minY)
		hitbox.setBounds(minX, minY, maxX-minX, maxY-minY);

		return this;

	}

	private void updateMinMax(double x, double y) {
		if (x < minX)
			minX = x;
		else if (x > maxX)
			maxX = x;
		if (y < minY)
			minY = y;
		else if (y > maxY)
			maxY = y;
	}

	public int pointCOunt() {
		return pointCount;
	}

	public double[][] points() {
		return points;
	}

	public double pX(int index) {
		return points[0][index];
	}

	public double pY(int index) {
		return points[1][index];
	}

	
	
	public void update() {
		
		
		
	}

	public boolean hasHitbox() {
		return true;
	}

	public boolean isDrawable() {
		return true;
	}

	public Sprite getDrawable() {
		return sprite;
	}

	public HitBox getHitBox() {
		return hitbox;
	}

	@Override
	public boolean hasHitBox() {
		// TODO Auto-generated method stub
		return true;
	}

}
