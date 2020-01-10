package juego;

import java.awt.Color;

import javax.swing.*;


/**
 * La clase Disparos es un Applet. Se encarga de poner en funcionamiento el conjunto de clases 
 * modelo-vista-controlador que ejecutarán un juego de disparos con bolas de colores. 
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class Disparos extends JApplet {
	private Controller controller = new Controller();
	
	/**
	 * Constructor que crea los componentes del juego de disparos
	 */
	public Disparos() {
		CannonModel cannonModel = new CannonModel();
		BallSetModel ballSetModel = new BallSetModel();		
		View view = new View();
		
		cannonModel.addObserver(view);
		ballSetModel.addObserver(view);
		
		controller.addModel(cannonModel, ballSetModel);
		controller.addView(view);
		
		view.addController(controller);	
		add(view);
		controller.init();	
	}	
	
	/**
	 * Metodo para iniciar el controlador
	 */
	public void init() {
		controller.init();
	}

	/**
	 * Metodo pricipal para que el applet sea standalone
	 * @param args Ninguno 
	 */
	public static void main(String[] args) {	
		JFrame frame = new JFrame("Proyectil");
		
		Disparos program = new Disparos();
		frame.add(program);
		
		frame.setTitle("Juego de bolas de disparo");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 600);
		frame.setVisible(true);
		
		program.init();
	}

}
