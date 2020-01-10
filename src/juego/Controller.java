package juego;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * La clase Controller es el controlador del juego de disparos. Se encarga de los eventos de la vista que afectan
 * a las bolas. 
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class Controller extends MouseAdapter implements ActionListener, MouseMotionListener {
	private CannonModel cannonModel; // Cannon with capacity to turn and shoot balls
	private BallSetModel ballSetModel; // Balls to destroy with the cannon
	private int ballNumber = 20; // This will be recalculate based on window size
	private int radio = 20; // Balls radio
	private ArrayList<BallModel> firedBalls; // Balls from cannon
	private View view;	
	private Timer timer;
	private int delay = 20;
	private int numberColors = 9; // Total number of random colors
	private AudioClip hitSound;
	private AudioClip noHitSound;	
	
	public Controller() {
		timer = new Timer(delay, new TimerListener());
		firedBalls = new ArrayList<BallModel>();
		hitSound = Applet.newAudioClip(this.getClass().getResource("hit.wav"));
		noHitSound = Applet.newAudioClip(this.getClass().getResource("noHit.wav"));		
	}
	
	
	/**
	 * Metodo para mantener el cañon apuntando al puntero del mouse
	 */
	public void mouseMoved(MouseEvent e) {
		// Repainting balls to destroy if none is left
		if(ballSetModel.size() == 0) {
			ballNumber = (int)(view.getWidth() / (radio * 2.0));							
			ballSetModel.createRow(0, view.getHeight(), ballNumber, radio);	
		}
		
		//Centering cannon
		cannonModel.setOrigin(view.getWidth() / 2, 0); // Necessary for applet?
								
		//Mouse position converted to normal coordinates(axis Y not inverted)
		int convertedX = (int)(e.getX());
		int convertedY = (int)(view.getHeight() - e.getY());			
	
		cannonModel.calculateAngleToPoint(convertedX, convertedY);	
	}
		
	/**
	 * Metodo para disparar el cañon cuando se hace click izquierdo. Se pone una nueva bola en el cañon
	 */
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {				
			firedBalls.add(cannonModel.shoot());
			cannonModel.setBallColor(randomColor());			
			timer.start();
		}
	}
	
	/**
	 * Metodo para añadir los modelos que representan los objetos del juego de disparos
	 * @param cannonModel Cañon
	 * @param ballSetModel Conjunto de bolas a destruir
	 */
	public void addModel(CannonModel cannonModel, BallSetModel ballSetModel) {
		this.cannonModel = cannonModel;
		this.ballSetModel = ballSetModel;
	}
	
	/** Metodo para añadir la vista que representara gráficamente los modelos del juego del cañon
	 * @param view
	 */
	public void addView(View view) {	
		this.view = view;
	}
	
	/**
	 * Metodo para inicializar los modelos: Conjunto de bolas y cañon
	 */
	public void init() {
		ballNumber = (int)(view.getWidth() / (radio * 2.0));
				
		cannonModel.setOrigin(view.getWidth() / 2, 0);
		cannonModel.setLenght(150);
		cannonModel.setBallColor(randomColor());
		cannonModel.setAngle(90);		
		ballSetModel.createRow(0, view.getHeight(), ballNumber, radio);		
	}
	
	/**
	 * @return int Color aleatorio que luego traducira la vista
	 */
	int randomColor() {
		int color = (int)(Math.random() * numberColors);		
		return color;
	}
		
	public void actionPerformed(ActionEvent e) {}
	
	
	/**
	 * La clase interna de Controller que comprueba colisiones de bolas y borrar las que se salgan de los limites.
	 * Se comprueba con la ayuda del timer.
	 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
	 * @version 1.0
	 * @subject Programación de aplicaciones interactivas
	 * @organization Universidad de La Laguna
	 * @since 09-05-2017
	 */
	class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {			
			for(int i = 0; i < firedBalls.size(); i++) {				
				int positionX = firedBalls.get(i).getPosition().getX();
				int positionY = firedBalls.get(i).getPosition().getY();
				
				// If out of limits, must be deleted
				if ((positionX < 0) || (view.getWidth() < positionX) || (view.getHeight() < positionY)) { 
					firedBalls.remove(i);
					i--;
				}
				else {
					firedBalls.get(i).calculateTrayectory();
				}
			}			
			view.updateFiredBalls(firedBalls);
			view.repaint();
			
			// Cases collisions: 
			// 1-Collision with ball with the same color 
			// 2-Collision with two balls 
			// others-collision with one ball with different color 
			int numberCollision = ballSetModel.checkCollision(firedBalls);
			switch(numberCollision) {
				case 1: 
					noHitSound.stop();
					hitSound.stop();
					hitSound.play();
					break;
				case 2: 
					noHitSound.stop();
					hitSound.stop();
					noHitSound.play();								
			}
		}
	}
	
	
}
