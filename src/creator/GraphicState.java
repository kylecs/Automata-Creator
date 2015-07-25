package creator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GraphicState {
	int centerX;
	int centerY;
	
	static final int RADIUS = 30;
	static Font defaultFont = new Font("Verdana", Font.BOLD, 14);
	int stateId;
	boolean entryNode;
	boolean current = false;
	boolean accept = false;
	public GraphicState(int stateId, boolean entryNode, int x, int y){
		this.centerX = x;
		this.centerY = y;
		this.stateId = stateId;
		this.entryNode = entryNode;
	}
	public void paint(Graphics dg){
		Graphics2D g = (Graphics2D) dg;
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.BLACK);
		if(entryNode){
			g.setColor(Color.GREEN);
		}
		if(accept){
			g.setColor(Color.BLUE);
		}
		if(current){
			g.setColor(Color.RED);
		}
		g.drawOval(centerX - (RADIUS), centerY - (RADIUS), RADIUS * 2, RADIUS*2);
		g.setFont(defaultFont);
		FontMetrics metrics = g.getFontMetrics();
		int tw = metrics.stringWidth(String.valueOf(stateId));
		g.drawString(String.valueOf(stateId), centerX - (tw / 2), centerY);
	}
	public void setCurrent(boolean current){
		this.current = current;
	}
	public void toggleAccept(){
		if(entryNode) return;
		accept = !accept;
	}
	public boolean isAccept(){
		return accept;
	}
	public int stateId(){
		return stateId;
	}
	public boolean intersects(int x, int y){
		double distance = Math.pow((Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2)),  .5);
		return (distance < RADIUS);
	}
	//sending attachment points will be on top and bottom
	public int getSendX(){
		return centerX;
	}
	public int getSendY(GraphicState state){
		if(state.centerY > centerY){
			return centerY + RADIUS;
		}else{
			return centerY - RADIUS;
		}
	}
	public int getSendY(int x, int y){
		if(y > centerY){
			return centerY + RADIUS;
		}else{
			return centerY - RADIUS;
		}
	}
	public int getRecieveX(GraphicState state){
		if(state.centerX > centerX){
			return centerX + RADIUS;
		}else{
			return centerX - RADIUS;
		}
	}
	public int getRecieveY(){
		return centerY;
	}
}
