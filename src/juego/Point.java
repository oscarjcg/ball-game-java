package juego;

/**
 * La clase Point representa una coordenada
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class Point {
	private int positionX;
	private int positionY;
	
	public Point(int x, int y) {
		positionX = x;
		positionY = y;
	}
	
	public int getX() {
		return positionX;
	}
	
	public int getY() {
		return positionY;
	}
		
	public Point getPoint() {
		return new Point(positionX, positionY);
	}
	
	public void setX(int positionX) {
		this.positionX = positionX;
	}
	
	public void setY(int positionY) {
		this.positionY = positionY;
	}
		
	public String toString() {
		return "( " + positionX + " " + positionY + " )";
	}
}
