package machine;


public class Machine {
	/*each state is defined only as an integer
	 * 
	 * upon interpreting the input string, the machine will track only the current state and use the transition array to move through states
	 * 
	 * upon reaching the end of the string to parse, the current state will be check with the accept states and the result will be determines
	 * 
	 * the transition function is defined as a two dimentional array where the first dimenison is occupied by the state number and the second by the potential character input, but converted to an integer. the location of this array will
	 * be occupied by the state that the machine will go to upon recieving this input
	 * 
	 * 
	 * 
	 */
	private int numStates = 0;
	private Alphabet alphabet;
	private int[][] transition;
	private int startState;
	private int[] acceptStates;
	public Machine(int numStates, Alphabet alphabet, int[][] transition, int startState, int[] acceptStates){
		this.numStates = numStates;
		this.alphabet = alphabet;
		this.transition = transition;
		this.startState = startState;
		this.acceptStates = acceptStates;
	}
	
	
	public boolean eval(String sinput){
		char[] input = sinput.toCharArray();
		for(char c: input){
			if(!alphabet.isValid(c)) return false;
		}
		int curState = startState;
		for(char c: input){
			curState = transition[curState][alphabet.getCharacterNumber(c)];
		}
		for(int i: acceptStates){
			if(i == curState) return true;
		}
		return false;
	}
	
	String input = "";
	int pos = 0;
	int curState = startState;
	public int stepForward(){
		if(pos > input.length()) return -1;
		char cur = input.toCharArray()[pos];
		curState = transition[curState][alphabet.getCharacterNumber(cur)];
		pos++;
		if(pos == input.length()) return -1;
		return curState;
	}
	int targetPos = 0;
	public int stepBack(){
		targetPos = pos - 1;
		pos = 0;
		curState = startState;
		while(pos < targetPos){
			stepForward();
		}
		return curState;
	}
	
	public int getPosition(){
		return pos - 1;
	}
	public boolean getResult(){
		System.out.println(curState);
		for(int i: acceptStates){
			if(i == curState) return true;
		}
		return false;
	}
	public void reset(){
		//input = "";
		pos = 0;
		curState = startState;
	}
	public String getCurrentChar(){
		return input.substring(pos, pos + 1);
	}
	
	
	public void setInput(String input){
		this.input = input;
	}
	
}
