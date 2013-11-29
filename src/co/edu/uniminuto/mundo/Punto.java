package co.edu.uniminuto.mundo;

import android.graphics.Paint;

public class Punto {

	private int x;
	private int y;
	private Paint pincel;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Paint getPincel() {
		return pincel;
	}
	public void setPincel(Paint pincel) {
		this.pincel = pincel;
	}
	
	public Punto(int x, int y){
		this.y = y;
		this.x = x;
	}
	
	public Punto(int x, int y, Paint pincel){
		this(x, y);
		this.pincel = pincel;
	}
}
