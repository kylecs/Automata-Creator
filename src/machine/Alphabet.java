package machine;
import java.util.ArrayList;
import java.util.List;


public class Alphabet {
	private List<Character> chars = new ArrayList<Character>();
	public Alphabet(String chars){
		for(char c: chars.toCharArray()){
			if(!this.chars.contains(c)){
				this.chars.add(c);
			}
		}
	}
	public boolean isValid(char c){
		return chars.contains(c);
	}
	public boolean isValid(String s){
		if(s.length() != 1) return false;
		return chars.contains(s.toCharArray()[0]);
	}
	public boolean isEmpty(){
		return chars.isEmpty();
	}
	public int getSize(){
		return chars.size();
	}
	public int getCharacterNumber(char c){
		return chars.indexOf(c);
	}
}
