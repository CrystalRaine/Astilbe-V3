package truthTableGen;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class InputParser {

	private List<Character> operators = Arrays.asList('¬', '(', ')', '⊼','⊽', '∧', '⊕', '∨', '→', '↔');
	private List<String> vars = new ArrayList<String>(); 
	private List<String> phrases;

	private String input = "";

	private int phraseCount = 0;
	private static final int PROOF_GEN_LEVELS = 5;

	public InputParser() {
	}

	public List<String> getVars() {
		List<String> v = new ArrayList<String>();
		for (String s : vars) {
			if (s.length() == 1) {
				v.add(s);
			}
		}
		return v;
	}

	public void setInputText(String input) {
		input.strip();
		input = input.replaceAll(" ", ""); // remove spaces

		input = input.replaceAll("\\\\vee", "∨"); // replace different methods of typing operations with standard
													// symbols
		input = input.replaceAll("\\\\lor", "∨");
		input = input.replaceAll("\\\\leftrightarrow", "↔");
		input = input.replaceAll("\\\\rightarrow", "→");
		input = input.replaceAll("\\\\wedge", "∧");
		input = input.replaceAll("\\\\land", "∧");
		input = input.replaceAll("nor", "⊽"); // âŠ½
		input = input.replaceAll("nand", "⊼"); // âŠ¼
		input = input.replaceAll("<->", "↔");
		input = input.replaceAll("<-->", "↔");
		input = input.replaceAll("bico", "↔");
		input = input.replaceAll("bi", "↔");
		input = input.replaceAll("->", "→");
		input = input.replaceAll("-->", "→");
		input = input.replaceAll("implies", "→");
		input = input.replaceAll("then", "→");
		input = input.replaceAll("not", "¬");
		input = input.replaceAll("!", "¬");
		input = input.replaceAll("xor", "⊕");
		input = input.replaceAll("and", "∧");
		input = input.replaceAll("&&", "∧");
		input = input.replaceAll("&", "∧");
		input = input.replaceAll("\\^", "∧");
		input = input.replaceAll("or", "∨");
		input = input.replaceAll("\\|\\|", "∨");
		input = input.replaceAll("\\|", "∨");
		input = input.replaceAll("v", "∨");

		this.input = input;
	}

	/**
	 * checks to make sure the current input is valid, then updates headers
	 * 
	 * @return if the current text is a valid binary equation
	 */
	public boolean update() {

		List<String> sections = new ArrayList<String>();

		vars.clear();

		phrases = new ArrayList<String>();
		String ins = input;

		Scanner sc = new Scanner(ins);
		sc.useDelimiter(",");

		while (sc.hasNext()) { // get all sections
			sections.add(sc.next());
			phraseCount++;
		}

		for (String s : sections) { // check validity
			input = s;
			if (!validate()) {
				sc.close();
				return false;
			}
		}

		for (String in : sections) { // for each phrase
			input = in;

			parenthesize();

			if (input.length() > 1) { // add any phrases not added to the lists
				if (!vars.contains(peel(input, 0))) {
					vars.add(peel(input, 0));
				}
				phrases.add(peel(input, 0));
			}
		}
		sc.close();
		return true;
	}

	/**
	 * adds variables to the vars list, and checks if the equation is evaluable
	 * 
	 * @return if input is valid
	 */
	private boolean validate() { // checks if a input is a valid equation
		boolean prevIsVar = false;
		boolean prevIsNot = false;
		int withinParens = 0;

		outer: for (int i = 0; i < input.length(); i++) { // for every character
			if (withinParens < 0) {
				return false;
			}
			if (input.charAt(i) == '¬' && prevIsNot) {
				return false;
			}
			if (input.charAt(i) == '¬') {
				prevIsNot = true;
			} else {
				prevIsNot = false;
			}
			if (input.charAt(i) == '(' && !prevIsVar) {
				withinParens++;
				continue outer;
			} else if (input.charAt(i) == ')' && prevIsVar) {
				withinParens--;
				continue outer;
			}
			if (input.charAt(i) == '¬' && !prevIsVar) {
				continue outer;
			} else if (input.charAt(i) == '¬') {
				return false;
			}
			if (operators.contains(input.charAt(i))) {
				if (prevIsVar) {
					prevIsVar = false;
				} else {
					return false;
				}
			} else {
				if (!prevIsVar) {
					if (!vars.contains(Character.toString(input.charAt(i))) && input.charAt(i) != 'T'
							&& input.charAt(i) != 'F') {
						vars.add(Character.toString(input.charAt(i)));
					}
					prevIsVar = true;
				} else {
					return false;
				}
			}
		}
		return prevIsVar && withinParens == 0;
	}

	public void parenthesize() {
		for (int i = 0; i < input.length(); i++) { // for every character
			if (input.charAt(i) == '¬') { // put parenthesees around NOT terms
				String inside;
				if (input.charAt(i + 1) == '(') {
					int end = (i + 1) + peel(input, i + 1).length() + 1;
					inside = input.substring(i, end + 1);
					input = input.substring(0, i) + "(" + inside + ")" + input.substring(end + 1);
					i += 2;
				} else {
					inside = input.substring(i, i + 2);
					input = input.substring(0, i) + "(" + inside + ")" + input.substring(i + 2);
					i++;
				}
			}
		}

		for (int i = 3; i < operators.size(); i++) { // for every operator. Starts after "NOT", "(", and ")" in the list
			for (int j = 0; j < input.length(); j++) { // check each character, in order of operations
				if (operators.get(i) == input.charAt(j)) { // if the scanned char is the checked for operator
					int start = j - 1, end = j + 1;
					if (input.charAt(j - 1) == ')') {
						start = j - peel(input, j - 1).length() - 2;
					}
					if (input.charAt(j + 1) == '(') {
						end = j + peel(input, j + 1).length() + 2;
					}
					String inside = input.substring(start, end + 1);
					if ((end + 1) < input.length() && (start - 1) >= 0 && input.charAt(end + 1) == ')'
							&& input.charAt(start - 1) == '(') { // if already surrounded by parentheses
						if (!vars.contains(inside)) { // add to vars
							vars.add(inside);
						}
						continue;
					}
					input = input.substring(0, start) + "(" + inside + ")" + input.substring(end + 1);
					if (!vars.contains(inside)) { // add to vars
						vars.add(inside);
					}
					j++;
				}
			}
		}
	}

	public String getInput() {
		return input;
	}

	public boolean getValue(String s, int row) {
		if (s.equals("T")) {
			return true;
		}
		if (s.equals("F")) {
			return false;
		}
		if (this.getVars().contains(s)) { // alternates for initial variables
			int col = vars.indexOf(s);
			int totalVars = getVars().size();

			int rowsToSwitch = (int) Math.pow(2, totalVars - col - 1);
			int tOrF = (row / rowsToSwitch) % 2;

			return tOrF == 1 ? false : true;
		} else { // compounds
			return decompose(s, row);
		}
	}

	private boolean decompose(String s, int row) {
		if (s.charAt(0) == '¬') { // not
			if (s.charAt(1) == '(') {
				return !getValue(peel(s, 1), row);
			} else {
				return !getValue(s.substring(1, 2), row);
			}
		}
		if (s.length() == 3) { // both terms are variables
			return calculate(getValue(s.substring(0, 1), row), s.substring(1, 2).charAt(0),
					getValue(s.substring(2, 3), row));
		} else {
			String startChar = Character.toString(s.charAt(0));
			String endChar = Character.toString(s.charAt(s.length() - 1));
			if (getVars().contains(startChar) || startChar.equals("T") || startChar.equals("F")) { // first term is a
																									// variable
				return calculate(getValue(startChar, row), s.charAt(1), getValue(peel(s, 2), row));
			} else if (getVars().contains(endChar) || endChar.equals("T") || endChar.equals("F")) { // last term is a
																									// variable
				return calculate(getValue(peel(s, 0), row), s.charAt(s.length() - 2), getValue(endChar, row));
			} else { // both sides are parentheses
				String startPeel = peel(s, 0);
				return calculate(getValue(startPeel, row), s.charAt(startPeel.length() + 2),
						getValue(peel(s, s.length() - 1), row));
			}
		}
	}

	public static String peel(String s, int indexStart) {
		int open = 0;
		int close = 0;
		for (int i = indexStart; i < s.length();) {
			if (s.charAt(i) == '(') {
				open++;
			} else if (s.charAt(i) == ')') {
				close++;
			}
			if (open == close) {
				if (indexStart + 1 < i) {
					return s.substring(indexStart + 1, i);
				} else {
					return s.substring(i + 1, indexStart);
				}
			}
			if (close > open) {
				i--;
			} else {
				i++;
			}
		}
		return s;
	}

	private boolean calculate(boolean a, char c, boolean b){
        switch (c){
            case '⊼':
                return !(a && b);
            case '⊽':
                return !(a || b);
            case '∧':
                return a && b;
            case '⊕':
                return (a || b) && !(a && b);
            case '∨':
                return a || b;
            case '→':
                return (!a) || (a && b);
            case '↔':
                return a == b;
            default:
                return false;
        }
    }

	public List<String> getHeaders() {
		return vars;
	}

	public String[] getProofEquations() throws Exception {
		return new String[] { phrases.get(0), phrases.get(1) };
	}

	public List<String[]> createProof() {
		List<String[]> NEA = new ArrayList<>();
		if (phrases.size() == 2) {
			List<String[]> shortestProof;
			List<List<String[]>> proofList = recursiveProofGen(PROOF_GEN_LEVELS);
			if (proofList.size() > 0) {
				shortestProof = proofList.get(0);
				for (List<String[]> proof : proofList) {
					if (proof.size() < shortestProof.size()) {
						shortestProof = proof;
					}
				}
				return shortestProof;
			} else {
				NEA.add(new String[] { "", "No Proof Found" });
			}
		} else {
			NEA.add(new String[] { "", "Incorrect Argument Count" });
		}
		return NEA;
	}

	private List<List<String[]>> recursiveProofGen(int level) {
		level--;
		if (level < 0) {
			return new ArrayList<List<String[]>>();
		}

		return new ArrayList<List<String[]>>();
	}

	public List<String> getEquations() {
		return phrases;
	}
}
