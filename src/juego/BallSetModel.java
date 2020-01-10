package juego;

import java.awt.geom.*;
import java.util.*;

/**
 * La BallSetModel representa el modelo del conjunto de bolas a destruir en el juego de disparos
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class BallSetModel extends Observable {	
	private ArrayList<Ellipse2D.Double> ballsArray; 
	private ArrayList<Integer> ballsArrayColor;
	private int numberColors = 9;
	
	public BallSetModel() {
		ballsArray = new ArrayList<Ellipse2D.Double>();
		ballsArrayColor = new ArrayList<Integer>();
	}
	
	/**
	 * Metodo para crear un conjunto de bolas en una fila
	 * @param originX Coordenada X de la primera bola
	 * @param originY Coordenada Y de la primera bola
	 * @param ballNumber Numero de bolas a crear
	 * @param radio Radio de las bolas que se van a crear
	 */
	public void createRow(int originX, int originY, int ballNumber, int radio) {
		ballsArray = new ArrayList<Ellipse2D.Double>();
		
		for(int i = 0; i < ballNumber; i++) {
			ballsArray.add(new Ellipse2D.Double(originX, originY, radio * 2, radio * 2));
			ballsArrayColor.add(randomColor());
			originX = originX + radio * 2;			
		}
		
		ArrayList<Object> aux = new ArrayList<Object>();
		
		aux.add(ballsArray);
		aux.add(ballsArrayColor);
		
		setChanged();
		notifyObservers(aux);
	}
	
	/**
	 * Metodo que calcula las colisiones entre las bolas disparadas y las bolas a destruir
	 * @param firedBalls Bolas que ha disparado el cañon
	 * @return int Numero de colisiones encontradas
	 */
	public int checkCollision(ArrayList<BallModel> firedBalls) {	
		int numberCollision = 0;
		
		for(int i = 0; i < firedBalls.size(); i++) {
			int positionX = firedBalls.get(i).getPosition().getX();
			int positionY = firedBalls.get(i).getPosition().getY();
			double precision = 0.6; // 1.0 max- 0.5 less
			int radio = (int)(firedBalls.get(i).getRadio() * precision);
			//Rectangle containing a ball on movement
			Rectangle2D rectangle = new Rectangle2D.Double(positionX, positionY, radio * 2, radio * 2);
			
			int countIntersection = 0;
			int ballToRemove = -1;
			//If there is an intersection between a firedBall and a normal ball, then the normal one is removed
			for(int j = 0; j < ballsArray.size(); j++) {
				if(ballsArray.get(j).intersects(rectangle)) {				
					ballToRemove = j;					
					countIntersection++;
				}
			}
			//Deleting normal balls with one collision and same color
			if (countIntersection == 1) { 			
				if ((firedBalls.get(i).getColor() == ballsArrayColor.get(ballToRemove))) {
					ballsArray.remove(ballToRemove);
					ballsArrayColor.remove(ballToRemove);
					numberCollision = 1;
				}
				firedBalls.remove(i);
			}
			if (countIntersection == 2) {			
				firedBalls.remove(i);
				i--;
				numberCollision = 2;
			}
					
		}
		return numberCollision;
	}
	
	int randomColor() {
		int color = (int)(Math.random() * numberColors);	
		return color;
	}
	
	public int size() {
		return ballsArray.size();
	}
}
