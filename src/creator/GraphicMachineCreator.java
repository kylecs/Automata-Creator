package creator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import main.DeterministicAutomataCreator;
import menu.Menu;

public class GraphicMachineCreator {
	public Screen screen;
	ThreadUpdate update;
	MouseManager mouse;
	Keyboard keyboard;
	List<GraphicState> states = new ArrayList<GraphicState>();
	List<GraphicConnection> connections = new ArrayList<GraphicConnection>();
	static final int ADD_MOVE_MODE = 0;
	static final int CONNECT_MODE = 1;
	static final int REMOVE_MODE = 2;
	static final int SELECT_ACCEPT_MODE = 3;
	int mode = ADD_MOVE_MODE;
	String input = "";
	String current = "";
	static Font font = new Font("Verdana", Font.PLAIN, 14);
	static Font bigFont = new Font("Verdana", Font.BOLD, 16);
	FontMetrics metrics;
	Menu menu;
	DeterministicAutomataCreator creator;
	/*
	 * 
	 * DONE
	 * debug strange arrow behavior: sometimes doesnt draw down fix problem with
	 * negative slopes and square roots
	 * 
	 * fix where connections are made when they should not be add state
	 * connection points to solve multiple connections between two states add
	 * arrow when creating connection
	 * 
	 * 
	 * TODO
	 *  machine creation mechanism add debug/
	 * visualization mode
	 * add step forward and step backwards functions, add buttons for these and eval and enter string
	 */
	public GraphicMachineCreator(DeterministicAutomataCreator creator) {
		this.creator = creator;
		keyboard = new Keyboard(this);
		screen = new Screen(this);
		update = new ThreadUpdate(this);
		mouse = new MouseManager(this);
		menu = new Menu(this);
		screen.addMouseListener(mouse);
		screen.frame.addKeyListener(keyboard);

		// temporary mode switching
		
	}
	public void setInput(String input){
		this.input = input;
	}
	
	
	int count = 0;
	public void update() {
		count ++;
		if(count >= 10){
			count = 0;
			if(menu != null){
				menu.resetPositions();
			}
		}
		screen.repaint();

		// state dragging logic

		if (ismoving) {
			Point p = screen.getMousePosition();
			if (p == null)
				return;
			int mx = p.x;
			int my = p.y;
			moving.centerX = mx;
			moving.centerY = my;
		}

	}
	public String getInput(){
		return input;
	}
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
		for (GraphicState state : states) {
			state.paint(g);
		}
		for (GraphicConnection gc : connections) {
			gc.paint(g);
		}
		
		menu.paint(g);
		
		
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.BLACK);
		//g.setFont(font);
		g.setFont(bigFont);
		if(metrics == null) metrics = g.getFontMetrics();
		g.drawString("Input: ", 215, 30);
		
		int begin = metrics.stringWidth("Input: ") + 215;
		int pos = creator.currentPosition();
		if(!input.equals("") && pos != -1){
			String part1 = input.substring(0, pos);
			String target = input.substring(pos, pos + 1);
			String part2 = input.substring(pos + 1, input.length());
			int p1l = metrics.stringWidth(part1);
			int tarl = metrics.stringWidth(target);
			g.drawString(part1, begin, 30);
			g.setColor(Color.RED);
			g.drawString(target, begin + p1l, 30);
			g.setColor(Color.BLACK);
			g.drawString(part2, begin + p1l + tarl, 30);
		}else{
			g.drawString(input, begin, 30);
		}
		g.drawString(current, (screen.getWidth() / 2) - 4, screen.getHeight() - 20);
		
		if (dragging) {
			Point p = screen.getMousePosition();
			if (p == null)
				return;
			int mx = p.x;
			int my = p.y;
			int sx = start.getSendX();
			int sy = start.getSendY(mx, my);
			int ex;
			int ey;
			GraphicState state;
			if ((state = getCollision(mx, my)) != null) {
				ex = state.getRecieveX(start);
				ey = state.getRecieveY();
			} else {
				ex = mx;
				ey = my;
			}
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(sx, sy, ex, ey);
			g2.fillOval(ex - 5, ey - 5, 10, 10);
		}
	}
	public void registerPress(int id){
		creator.interpretButton(id);
	}
	int curStateId = -1;
	public void setActiveState(int stateId){
		
		if(curStateId != -1){
			getState(curStateId).setCurrent(false);
		}
		if(stateId == -1){
			return;
		}
		curStateId = stateId;
		getState(stateId).setCurrent(true);
	}
	public void setCurrentChar(String s){
		current = s;
	}
	public void setMode(int mode){
		menu.setMode(mode);
		this.mode = mode;
		dragging = false;
		ismoving = false;
		System.out.println("Setting mode: " + mode);
	}

	boolean dragging = false;
	GraphicState start = null;

	boolean ismoving = false;
	GraphicState moving = null;
	int startx = 0;
	int starty = 0;

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		GraphicState clicked = getCollision(x, y);
		boolean intersects = (clicked != null);
		
		GraphicConnection clickedConnection = getConnectionCollision(x, y);
		boolean connectionClicked = (clickedConnection != null);
		
		
		if(menu.onClick(e)) return;
		switch (mode) {
		case ADD_MOVE_MODE:
			if (e.getButton() == 1) {

				if (intersects && !ismoving) {
					ismoving = true;
					moving = clicked;
					startx = moving.centerX;
					starty = moving.centerY;
				} else {
					addState(x, y);
				}
				
				//System.out.println("intersects " + intersects + " ismoving "+ ismoving + " movingid "+ ((moving != null) ? moving.stateId : "null"));
				 
				 
			}
			break;
		case CONNECT_MODE:
			if (e.getButton() == 1) {
				if (dragging && intersects) {
					addConnection(start, clicked);
					dragging = false;
				} else if (!dragging && intersects) {
					dragging = true;
					start = clicked;
				} else {
					dragging = false;
				}
			} else if (e.getButton() == 3) {
				dragging = false;
			}
			if(connectionClicked){
				String input = JOptionPane.showInputDialog(null, "Please enter target characters", "Input");
				clickedConnection.setChars(input);
			}
			//for debugging intersections
			//System.out.println("intersects " + intersects + " dragging " + dragging);
			break;
		case REMOVE_MODE:
			if(e.getButton() == 1){
				if(intersects){
					Iterator<GraphicConnection> i = connections.iterator();
					while(i.hasNext()){
						GraphicConnection c = i.next();
						if(c.hasConnectionTo(clicked)){
							i.remove();
						}
						
					}
					
					states.remove(clicked);
					resetIds();
				}
				if(connectionClicked){
					connections.remove(clickedConnection);
				}
			}
			break;
		case SELECT_ACCEPT_MODE:
			if(e.getButton() == 1){
				if(intersects){
					clicked.toggleAccept();
				}
			}
			break;
		}

	}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_1){
			setMode(0);
		}else if(e.getKeyCode() == KeyEvent.VK_2){
			setMode(1);
		}else if(e.getKeyCode() == KeyEvent.VK_3){
			setMode(2);
		}
		else if(e.getKeyCode() == KeyEvent.VK_4){
			setMode(3);
		}
	}

	
	public void mouseRelease(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		GraphicState clicked = getCollision(x, y);
		boolean intersects = (clicked != null);

		
		if (ismoving) {

			if (intersects) {
				ismoving = false;
				System.out.println("RESET");//doesn't work without this line, timing issue?
				moving.centerX = startx;
				moving.centerY = starty;
			} else {
				ismoving = false;
				moving.centerX = x;
				moving.centerY = y;
			}
		}
		//System.out.println("intersects " + intersects);

	}
	public List<GraphicState> getStates(){
		return states;
	}
	public List<GraphicConnection> getConnections(){
		return connections;
	}
	public void resetIds(){
		int i = 0;
		boolean startExists = false;
		for(GraphicState s: states){
			s.stateId = i;
			i++;
			if(s.entryNode) startExists = true;
		}
		if(!startExists && states.size() > 0){
			states.get(0).entryNode = true;
		}
	}
	public GraphicState getCollision(int x, int y) {
		for (GraphicState state : states) {
			if(ismoving){
				if(state != moving && state.intersects(x, y)) return state;
			}else{
				if (state.intersects(x, y))
					return state;
			}
			}
			
		return null;
	}
	public GraphicConnection getConnectionCollision(int x, int y){
		for(GraphicConnection c: connections){
			//System.out.println(c.midX + " " + c.midY + " " + x + " " + y);
			if(c.collidies(x, y)){
				return c;
			}
		}
		return null;
	}

	public void addState(int x, int y) {
		states.add(new GraphicState(states.size(), states.size() == 0, x, y));
	}

	public void addConnection(GraphicState start, GraphicState end) {
		if (start == end)
			return;
		connections.add(new GraphicConnection(start, end));

	}
	public GraphicState getState(int stateId ){
		for(GraphicState s: states){
			if(s.stateId() == stateId) return s;
		}
		return null;
	}
	

}
