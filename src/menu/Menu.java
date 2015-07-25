package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import creator.GraphicMachineCreator;

public class Menu {
	static Font font = new Font("Verdana", Font.PLAIN, 16);
	static Font bold = new Font("Verdana", Font.BOLD, 16);
	FontMetrics metrics;
	GraphicMachineCreator creator;
	int mode = 0;
	
	public static final int EVAL = 0;
	public static final int STEP_FORWARD = 1;
	public static final int STEP_BACK = 2;
	public static final int ENTER_STRING = 3;
	
	List<Button> buttons = new ArrayList<Button>();
	public Menu(GraphicMachineCreator creator){
		this.creator = creator;
		
		
		
		int midx = creator.screen.getWidth() / 2;
		int boty = creator.screen.getHeight();
		int midpadding = 40;
		int btnHeight = 50;
		addButton(EVAL, 105, 0, 100, btnHeight, "Evaluate");
		addButton(ENTER_STRING, 0, 0, 100, btnHeight, "Enter input");
		
		
		addButton(STEP_FORWARD, (midx - midpadding) - (100), boty - btnHeight - 50, 100, btnHeight, ">");
		addButton(STEP_BACK, midx + (midpadding), boty - btnHeight - 50, 100, btnHeight, "<");
		resetPositions();
		
	}
	public void paint(Graphics g){
		g.setFont(font);
		if(metrics == null) metrics = g.getFontMetrics();
		int xOff = 5;
		int yBot = creator.screen.getHeight() - 10;
		int yInc = metrics.getHeight();
		String mode1 = "Add / Move Mode (1)";
		String mode2 = "Connection Mode (2)";
		String mode3 = "Delete Mode (3)";
		String mode4 = "Select Accept Mode (4)";
		g.setColor(Color.BLACK);
		if(mode == 0){
			g.setColor(Color.RED);
		}
		g.drawString(mode1, creator.screen.getWidth() - (xOff + metrics.stringWidth(mode1)), yBot - (yInc * 3));
		g.setColor(Color.BLACK);
		if(mode == 1){
			g.setColor(Color.RED);
		}
		g.drawString(mode2, creator.screen.getWidth() - (xOff + metrics.stringWidth(mode2)), yBot - (yInc * 2));
		g.setColor(Color.BLACK);
		
		if(mode == 2){
			g.setColor(Color.RED);
		}
		g.drawString(mode3, creator.screen.getWidth() - (xOff + metrics.stringWidth(mode3)), yBot - (yInc * 1));
		g.setColor(Color.BLACK);
		
		if(mode == 3){
			g.setColor(Color.RED);
		}
		g.drawString(mode4, creator.screen.getWidth() - (xOff + metrics.stringWidth(mode4)), yBot - (yInc * 0));
		g.setColor(Color.BLACK);
		for(Button b: buttons){
			b.paint(g);
		}
		
		
		//draw step menus
	}
	public void resetPositions(){
		int midx = creator.screen.getWidth() / 2;
		int boty = creator.screen.getHeight();
		int midpadding = 40;
		int btnHeight = 50;
		for(Button b: buttons){
			if(b.getId() == STEP_BACK){
				b.x = (midx - midpadding) - 100;
				b.y = boty - btnHeight;
			}else if(b.getId() == STEP_FORWARD){
				b.x = (midx + midpadding );
				b.y = boty - btnHeight;
			}
		}
	}
	public boolean onClick(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		for(Button b: buttons){
			if(b.intersects(x, y)){
				creator.registerPress(b.getId());
				return true;
			}
		}
		
		
		
		
		return false;
	}
	public void addButton(int id, int x, int y, int width, int height, String text){
		buttons.add(new Button(this,id,  x, y, width, height, text));
	}
	public void setMode(int mode){
		this.mode = mode;
	}
}
