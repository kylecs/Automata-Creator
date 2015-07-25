package machine;


public class TransitionFunctionBuilder {
	int[][] transition;
	int numStates;
	Alphabet alphabet;
	int defaultEndpoint = -1;
	public TransitionFunctionBuilder(int numStates, Alphabet alphabet){
		this.numStates = numStates;
		this.alphabet = alphabet;
		transition = new int[numStates][alphabet.getSize()];
		for(int a = 0; a < numStates; a++){
			for(int b = 0; b < alphabet.getSize(); b++){
				transition[a][b] = -1;
			}
		}
	}
	public void addConnection(int startState, char input, int endState){
		transition[startState][alphabet.getCharacterNumber(input)] = endState;
	}
	public int[][] finalizeTransition(){
		for(int a = 0; a < numStates; a++){
			for(int b = 0; b < alphabet.getSize(); b++){
				if(transition[a][b] == -1){
					if(defaultEndpoint != -1){
						transition[a][b] = defaultEndpoint;
					}else{
						transition[a][b] = a;
					}
					
				}
			}
		}
		
		
		return transition;
	}
	public void setDefaultEndpoint(int n){
		defaultEndpoint = n;
	}
}
