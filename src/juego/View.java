package juego;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.*;

/**
 * La clase View se encarga de representar graficamente los modelos del juego de disparos
 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
 * @version 1.0
 * @subject Programación de aplicaciones interactivas
 * @organization Universidad de La Laguna
 * @since 09-05-2017
 */
public class View extends JPanel implements Observer {
	private ArrayList<Object> cannonArray; // Cannon
	private ArrayList<Ellipse2D.Double> ballsArray; // Balls to destroy
	private ArrayList<Integer> ballsArrayColor; // Color balls to destroy
	private ArrayList<BallModel> firedBalls;
		
	public View() {	
		ImageIcon icon = new ImageIcon(this.getClass().getResource("informationImage.png"));
		JLabel image = new JLabel(icon);
	
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		cannonArray = new ArrayList<Object>();
		cannonArray.add(new Point(50, 0));
		cannonArray.add(new Point(50, 25));
		firedBalls = new ArrayList<BallModel>();
		image.addMouseListener(new InformationListener());
		
		add(image, BorderLayout.EAST);
	}	
		
	public void init() {
		//Canon inicial state
		cannonArray = new ArrayList<Object>();
		cannonArray.add(new Point(getWidth() / 2, 0));
		cannonArray.add(new Point(getWidth() / 2, 25));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2D = (Graphics2D)g;		
		
		// Drawing ballSet		
		for(int i = 0; i < ballsArray.size(); i++) {
			g2D.setColor(getColor(ballsArrayColor.get(i)));
			double positionX = ballsArray.get(i).getX();
			double positionY = getHeight() - ballsArray.get(i).getY();
			double radio = ballsArray.get(i).getWidth() / 2.0;
			g2D.fill(new Ellipse2D.Double(positionX, positionY, radio * 2, radio * 2));
			
		}		
	
		// Drawing cannon	
		g.setColor(Color.BLACK);
		int originX = ((Point)cannonArray.get(0)).getX();
		int originY = getHeight() - ((Point)cannonArray.get(0)).getY();
							
		int pointerX = ((Point)cannonArray.get(1)).getX();
		int pointerY = getHeight() - ((Point)cannonArray.get(1)).getY();
						
		g.drawLine(originX, originY, pointerX, pointerY);
		
		// Drawing cannon ball				
		int radio = (int)cannonArray.get(4);
		g2D.setColor(getColor((int)cannonArray.get(3)));
		g.fillOval(((Point)cannonArray.get(2)).getX() - radio, getHeight() - ((Point)cannonArray.get(2)).getY() - radio, radio * 2, radio * 2);
		
		//Drawing fired balls
		radio = 0;
		for(int i = 0; i < firedBalls.size(); i++) {
			g2D.setColor(getColor(firedBalls.get(i).getColor()));
			int positionX = firedBalls.get(i).getPosition().getX();
			int positionY = getHeight() - firedBalls.get(i).getPosition().getY();
			radio = firedBalls.get(i).getRadio(); 
			g.fillOval(positionX - radio, positionY - radio, radio * 2, radio * 2);
		}
		
	}
	
	public void update(Observable obs, Object obj) {
		if(obs.getClass().equals(CannonModel.class)) {		
			cannonArray = (ArrayList<Object>)obj;
			repaint();
		}
		
		if(obs.getClass().equals(BallSetModel.class)) {		
			ArrayList<Object> aux = (ArrayList<Object>)obj;
			ballsArray = (ArrayList<Ellipse2D.Double>)aux.get(0);
			ballsArrayColor = (ArrayList<Integer>)aux.get(1);		
			repaint();
		}
		
		if(obs.getClass().equals(BallModel.class)) {		
			ballsArray = (ArrayList<Ellipse2D.Double>)obj;
			repaint();
		}
	}
	
	public void addController(ActionListener controller) {
		addMouseMotionListener((MouseMotionListener)controller);
		this.addMouseListener((MouseListener)controller);
	}
	
	public void updateFiredBalls(ArrayList<BallModel> firedBalls) {
		this.firedBalls = firedBalls;
	}
	
	public Color getColor(int color) {
		switch(color) {
		case 0: return Color.CYAN;
		case 1: return Color.BLUE;
		case 2: return Color.YELLOW;
		case 3: return Color.RED;
		case 4: return Color.GRAY;
		case 5: return Color.GREEN;
		case 6: return Color.MAGENTA;
		case 7: return Color.ORANGE;
		case 8: return Color.PINK;		
		default: return Color.BLACK;
		}
				
	}
	
	/**
	 * Clase interna que se encarga de mostrar una ventana con informacion
	 * @author Oscar Catari Gutiérrez - E-mail: oscarcatari@outlook.es
	 * @version 1.0
	 * @subject Programación de aplicaciones interactivas
	 * @organization Universidad de La Laguna
	 * @since 09-05-2017
	 */
	class InformationListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e)  {			
			JFrame frame = new JFrame();
			
			JPanel informationPanel = new JPanel();
			informationPanel.setBackground(Color.WHITE);
			informationPanel.setLayout(new GridLayout(0, 1, 10, 10));
			
			informationPanel.add(new JLabel("Asignatura: Programación de aplicaciones interactivas"));
			informationPanel.add(new JLabel("Práctica: Juego de disparos"));
			informationPanel.add(new JLabel("Descripción: Destruir las bolas segun su color con un cañon"));
			informationPanel.add(new JLabel("Alumno: Oscar Catari Gutierrez"));
			informationPanel.add(new JLabel("Contacto: oscarcatari@outlook.es"));
			informationPanel.add(new JLabel("Universidad: Universidad de La Laguna"));
			informationPanel.add(new JLabel("Facultad: ETTSI"));
			
			frame.add(informationPanel, BorderLayout.CENTER);
			frame.setTitle("Juego de bolas de disparo");
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(600, 400);
			frame.setVisible(true);
		}
	}
}
