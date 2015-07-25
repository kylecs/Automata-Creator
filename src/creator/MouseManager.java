package creator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener{
	GraphicMachineCreator creator;
	public MouseManager(GraphicMachineCreator creator){
		this.creator = creator;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		creator.mousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		creator.mouseRelease(e);
		
	}
}
