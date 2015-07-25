package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Button {
	Menu menu;
	int x;
	int y;
	int width;
	int height; 
	String text;
	int id;
	static Font font = new Font("Verdana", Font.PLAIN, 14);
	static FontMetrics metrics;
	public Button(Menu menu,int id,  int x, int y, int width, int height, String text){
		this.menu = menu;
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	public int getId(){
		return id;
	}
	public boolean intersects(int x, int y){
		return (x > this.x && y > this.y && x < (this.x + width) && y < (this.y + height));
	}
	public void paint(Graphics g){
		g.setFont(font);
		if(metrics == null) metrics = g.getFontMetrics();
		
		int sheight = metrics.getHeight();
		int swidth = metrics.stringWidth(text);
		
		int midx = x + (width / 2);
		int midy = y + (height / 2);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.drawString(text, midx - (swidth / 2), midy +(sheight / 2) - 5);
	}
}
