package creator;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Screen extends JPanel{
	JFrame frame;
	GraphicMachineCreator creator;
	public Screen(GraphicMachineCreator creator){
		this.creator = creator;
		frame = new JFrame("MACHINE");
		frame.setVisible(true);
		frame.setSize(1280, 1024);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(this);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		creator.paint(g);
		
		
	}
}
