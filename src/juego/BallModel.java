package juego;

import java.util.ArrayList;
import java.util.Observable;

/**
 * La clase BallModel representa una bola del juego de disparos
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class BallModel {
	private Point position;
	private int radio;
	private int color;
	private boolean moving;
	private Point origin;
	private int distance; // Distance travelled
	private int velocity = 10; // Increment of distance
	private int angle;
	
	public BallModel() {
		position = new Point(0, 0);
		radio = 20;
		color = 0;
		moving = false;
	}
	
	public void setPosition(int positionX, int positionY) {
		position.setX(positionX);
		position.setY(positionY);
	}
	
	public Point getPosition() {
		return new Point(position.getX(), position.getY());
	}
	
	public void setRadio(int radio) {
		this.radio = radio;
	}
	
	public int getRadio() {
		return radio;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public boolean getMoving() {
		return moving;
	}
	
	/**
	 * Metodo para actulizar la posicion de la bola con un incremento
	 */
	public void calculateTrayectory() {		
		int x = (int)(origin.getX() + distance * Math.cos(Math.toRadians(angle)));
		int y = (int)(origin.getY() + distance * Math.sin(Math.toRadians(angle)));
		
		position.setX(x);
		position.setY(y);	
		distance = distance + velocity;
	}
	
	/**
	 * Metodo para guardar el estado del cañon en el momento que se disparo la bola
	 * @param origin Origen del cañon en el momento que se disparo la bola
	 * @param angle Angulo del cañon en el momento que se disparo la bola
	 */
	public void saveDirection(Point origin, int angle) {
		distance = 0;
		this.origin = origin;
		this.angle = angle;
	}
}
