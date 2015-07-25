package creator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	GraphicMachineCreator creator;
	public Keyboard(GraphicMachineCreator creator){
		this.creator = creator;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		creator.keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
