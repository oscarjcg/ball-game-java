package juego;

import java.util.*;

/**
 * La clase CannonModel representa el modelo cañon que dispara en el juego.
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class CannonModel extends Observable {
	private int angle; //With axis x
	private Point originPosition;
	private Point pointerPosition;	
	private int lenght;
	private BallModel ball;
	
	/**
	 * Metodo constructor del cañon que lo posiciona y construye su bola.
	 */
	public CannonModel() {
		originPosition = new Point(0,0);
		pointerPosition = new Point(0,0);
		ball = new BallModel();
		
		setAngle(45);
		setOrigin(50, 0);
		setPointer(50, 25);
		setLenght(25);
		ball.setPosition(50, 0);
				
		calculatePointer();
		
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(getOrigin());			
		aux.add(getPointer());
		aux.add(ball.getPosition());
		aux.add(ball.getColor());
		aux.add(ball.getRadio());

		setChanged();
		notifyObservers(aux);
	}
	
	/**
	 * Metodo que calcula el final del cañon en base a un punto de origen, un angulo y una distancia
	 */
	void calculatePointer() {
		// Updates pointer with current angle
		int x = (int)(getOrigin().getX() + getLenght() * Math.cos(Math.toRadians(getAngle())));
		int y = (int)(getOrigin().getY() + getLenght() * Math.sin(Math.toRadians(getAngle())));		
		setPointer(x, y);
	}
	
	
	/**
	 * Metodo que actualiza el angulo y automaticamente calcula la nueva position del apuntador
	 * @param angle Angulo del cañon
	 */
	void setAngle(int angle) {
		this.angle = angle;		
		calculatePointer(); // Calculates new pointer position with new angle
		
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(getOrigin());			
		aux.add(getPointer());
		aux.add(ball.getPosition());
		aux.add(ball.getColor());
		aux.add(ball.getRadio());

		setChanged();
		notifyObservers(aux);
	}
	
	/**
	 * Metodo que calcula el angulo entre un punto y el eje X(El origen del cañon). Actualiza el angulo
	 * @param objectiveX Coordenada X del lugar a donde apuntar
	 * @param objectiveY Coordenada Y del lugar a donde apuntar
	 */
	public void calculateAngleToPoint(int objectiveX,int objectiveY) {
		boolean divisionCero = false;
		double incline = 0.0;
		//m = incline of imaginary rect(cannon origin, objective)
		//degrees = arctag(m)
		try {
			// Calculates m
			incline = (objectiveY - getOrigin().getY()) / (double)(objectiveX - getOrigin().getX());			
		}
		catch(Exception e) {
			divisionCero = true;
		}
		
		if(divisionCero) {
			setAngle((int)Math.atan(Math.toRadians(90)));
		}
		else {					
			double degrees = Math.toDegrees(Math.atan(incline));
			if(degrees < 0) {
				degrees = 180.0 + degrees;
			}
			
			setAngle((int)degrees);
		}
	}
	
	int getAngle() {
		return angle;
	}
			
	public void setOrigin(int originX, int originY) {
		originPosition.setX(originX);
		originPosition.setY(originY);
		if(!ball.getMoving()) { //Useless?
			ball.setPosition(originX, originY);
		}
				
	}
	
	public Point getOrigin() {
		return originPosition.getPoint();
	}
	
	void setPointer(int pointerX, int pointerY) {
		pointerPosition.setX(pointerX);
		pointerPosition.setY(pointerY);
	}
	
	Point getPointer() {
		return pointerPosition.getPoint();
	}
		
	void setLenght(int lenght) {
		this.lenght = lenght;
	}
	
	int getLenght() {
		return lenght;
	}
	
	/**
	 * Metodo que dispara una bola, sacandola del cañon. El cañon se recarga co otra bola
	 * @return BallModel Bola que se acaba de disparar
	 */
	public BallModel shoot() {			
		// Firing proyectile
		ball.setMoving(true);
		ball.saveDirection(originPosition.getPoint(), getAngle());
		
		// New proyectile
		BallModel firedBall = ball;		
		ball = new BallModel();		
		ball.setPosition(getOrigin().getX(), getOrigin().getY());
						
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(getOrigin());			
		aux.add(getPointer());
		aux.add(ball.getPosition());
		aux.add(ball.getColor());
		aux.add(ball.getRadio());

		setChanged();
		notifyObservers(aux);
		
		return firedBall;
	}
	/*
	public void calculateBallTrayectory() {
		ball.calculateTrayectory();
		
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(getOrigin());			
		aux.add(getPointer());
		aux.add(ball.getPosition());
		aux.add(ball.getColor());
		aux.add(ball.getRadio());

		setChanged();
		notifyObservers(aux);
	}*/
	
	/**
	 * Metodo para actualizar el color de la bola del cañon
	 * @param color
	 */
	public void setBallColor(int color) {
		ball.setColor(color);
		
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(getOrigin());			
		aux.add(getPointer());
		aux.add(ball.getPosition());
		aux.add(ball.getColor());
		aux.add(ball.getRadio());

		setChanged();
		notifyObservers(aux);
	}
	
	public int getBallColor() {
		return ball.getColor();
	}
}
