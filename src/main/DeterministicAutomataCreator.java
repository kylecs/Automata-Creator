package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import creator.GraphicConnection;
import creator.GraphicMachineCreator;
import creator.GraphicState;
import machine.Alphabet;
import machine.Machine;
import machine.TransitionFunctionBuilder;
import menu.Menu;

public class DeterministicAutomataCreator {
	GraphicMachineCreator creator;
	Machine machine;
	
	
	boolean lastresult = false;
	public DeterministicAutomataCreator() {

		creator = new GraphicMachineCreator(this);

		

	}

	public void interpretButton(int id) {
		switch (id) {
		case Menu.ENTER_STRING:
			String input = JOptionPane.showInputDialog(null,
					"Please enter input", "Enter input");
			creator.setInput(input);
			break;
		case Menu.EVAL:
			String in = creator.getInput();
			if(in == null || in.equals("")){
				System.out.println("ERROR: Input is requred");
				return;
			}
			Machine mach = createMachine(creator.getStates(), creator.getConnections());
			if(mach == null) return;
			JOptionPane.showMessageDialog(null, "The result is " + mach.eval(in));
			break;
		case Menu.STEP_FORWARD:
			if(machine == null){
				machine = createMachine(creator.getStates(), creator.getConnections());
				if(machine == null) return;
				machine.setInput(creator.getInput());
				creator.setActiveState(0);
				return;
			}
		
			int newState = machine.stepForward();
			if(newState == -1){
				creator.setActiveState(-1);
				creator.setCurrentChar("");
				
				JOptionPane.showMessageDialog(null, "The result is " +machine.getResult());
				machine.reset();
				machine = null;
				return;
			}
			//System.out.println("New state: " + newState);
			creator.setActiveState(newState);
			creator.setCurrentChar(machine.getCurrentChar());
			break;
		case Menu.STEP_BACK:
			if(machine == null) return;
			int state = machine.stepBack();
			String curChar = machine.getCurrentChar();
			creator.setActiveState(state);
			creator.setCurrentChar(curChar);
			break;
		}
	}
	
	public int currentPosition(){
		if(machine == null){
			return -1;
		}
		return machine.getPosition();
	}
	public boolean isValid(List<GraphicState> states, List<GraphicConnection> connections){
		if(states.size() < 1){
			System.out.println("ERROR: At least 1 state is required");
			return false;
		}
		if(connections.size() < 1){
			System.out.println("ERROR: At least 1 connection is requred");
			return false;
		}
		List<Integer> accept = new ArrayList<Integer>();
		for (GraphicState s : states) {
			if (s.isAccept())
				accept.add(s.stateId());
		}
		if (accept.size() == 0) {
			System.out.println("ERROR: At least 1 accept state is required");
			return false;
		}
		boolean connectionsValid = true;
		for(GraphicConnection c: connections){
			if(c.getAlphabet().equals("") || c.getAlphabet() == null) connectionsValid = false;
		}
		if(!connectionsValid){
			System.out.println("ERROR: Connnections without an assigned condition are not permitted");
			return false;
		}
		
		
		
		return true;
		
	}

	public Machine createMachine(List<GraphicState> states,
			List<GraphicConnection> connections) {
		if(!isValid(states, connections)) return null;
		
		
		String salphabet = "";

		for (GraphicConnection c : connections) {
			salphabet = salphabet + c.getAlphabet();
		}
		Alphabet alphabet = new Alphabet(salphabet);
		int nStates = states.size();

		int startState = 0;
		TransitionFunctionBuilder build = new TransitionFunctionBuilder(
				nStates, alphabet);
		List<Integer> accept = new ArrayList<Integer>();
		for (GraphicState s : states) {
			if (s.isAccept())
				accept.add(s.stateId());
		}
		
		int[] saccept = new int[accept.size()];
		int n = 0;
		for (int i : accept) {
			saccept[n] = i;
			n++;
		}
		for (GraphicConnection c : connections) {
			String alph = c.getAlphabet();
			List<Character> chars = new ArrayList<Character>();
			while (!alph.isEmpty()) {
				char ch = alph.charAt(0);
				alph = alph.substring(1, alph.length());
				if (!chars.contains(ch)) {
					chars.add(ch);
				}
			}

			// for each character in the connection, map the connection in the
			// transition funtion builder
			for (char a : chars) {
				build.addConnection(c.getStartState().stateId(), a, c
						.getEndState().stateId());
			}

		}
		int[][] transition = build.finalizeTransition();
		Machine ret = new Machine(nStates, alphabet, transition, startState,
				saccept);
		return ret;
	}
}
