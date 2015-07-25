package creator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class GraphicConnection {
	GraphicState start;
	GraphicState end;
	String target;

	static final int textPadding = 15;
	static Font font = new Font("Verdana", Font.PLAIN, 14);
	static FontMetrics metrics;
	int midX;
	int midY;
	int radius = 0;
	public GraphicConnection(GraphicState start, GraphicState end) {
		this.start = start;
		this.end = end;
	}

	public void paint(Graphics gd) {
		Graphics2D g = (Graphics2D) gd;

		if (metrics == null) {
			metrics = g.getFontMetrics();
		}
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(2));

		int startX = start.getSendX();
		int startY = start.getSendY(end);
		int endY = end.getRecieveY();
		int endX = end.getRecieveX(start);

		int dx = endX - startX;
		int dy = endY - startY;

		g.setColor(Color.BLACK);

		g.fillOval(endX - 5, endY - 5, 10, 10);

		midX = (int) (startX + (dx * 0.5));
		midY = (int) (startY + (dy * 0.5));
		g.drawLine(startX, startY, endX, endY);
		if (target != null && target.length() > 0) {
			int width = metrics.stringWidth(target);
			int diameter = width + textPadding;
			radius = diameter / 2;
			g.setColor(Color.WHITE);
			g.fillOval(midX - radius, midY - radius, diameter, diameter);
			g.setColor(Color.GREEN);
			g.drawOval(midX - radius, midY - radius, diameter, diameter);
			g.drawString(target, midX - (width / 2), midY + 5);

		} else {
			int diameter = textPadding + 20;
			radius = diameter / 2;
			g.setColor(Color.WHITE);
			g.fillOval(midX - radius, midY - radius, diameter, diameter);
			g.setColor(Color.GREEN);
			g.drawOval(midX - radius, midY - radius, diameter, diameter);
		}

	}
	public GraphicState getStartState(){
		return start;
	}
	public GraphicState getEndState(){
		return end;
	}
	public String getAlphabet(){
		if(target == null){
			return "";
		}else{
			return target;
		}
	}
	public boolean hasConnectionTo(GraphicState state){
		return (state == start || state == end);
	}
	public boolean collidies(int x, int y){
		double distance = Math.pow((Math.pow(midX - x, 2) + Math.pow(midY - y, 2)),  .5);
		return (distance < radius);
	}
	public void setChars(String s) {
		target = s;
	}
}
