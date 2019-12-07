package dungeonshooter.entity.property;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class Sprite implements Drawable<Sprite> {
	
	
	private double strokeWidth;
	private Paint fill;
	private Paint stroke;
	
	
	//setFill( new ImagePattern( new Image( "file:assets/concrete/dsc_1621.png")));
	public Sprite setFill(Paint color) {
		fill = color;
		return this;
		
	}
	
	public Sprite setStroke(Paint paint) {
		stroke = paint;
		return this;
		
	}
	
	public Sprite setWidth(double width) {
		strokeWidth = width;
		return this;
		
	}
	
	public double getWidth() {
		return strokeWidth;
		
	}
	
	public abstract void draw(GraphicsContext gc);
	
	public Paint getFill() {
		return fill;
		
	}
	
	public Paint getStroke() {
		return stroke;
		
	}
	

}
