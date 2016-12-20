package lambdas;

import java.util.List;

public class Parser {
	
	private static final String LAMBDA = "" + Utils.LAMBDA;
	private static final String DOT = ".";
	private static final String LEFT_PAREN = "(";
	private static final String RIGHT_PAREN = ")";
	private static final String EQUALS = "=";
	
	private List<String> tokens;
	private int index;
	
	private boolean gobble(String expected) {
		boolean result = expect(expected);
		if (result) index++;
		return result;
	}
	
	private boolean expect(String expected) {
		return !done() && tokens.get(index).equals(expected);
	}
	
	private boolean expect(String expected, int index) {
		return !done() && tokens.get(index).equals(expected);
	}
	
	private String next() throws ParsingException {
		String token = peek();
		index++;
		return token;
	}
	
	private String peek() throws ParsingException {
		if (done())
			throw new ParsingException("Expected a token but reached end of file");
		return tokens.get(index);
	}
	
	private boolean done() {
		return index >= tokens.size();
	}
	
	private Variable parseVariable() throws ParsingException {
		return new Variable(next());
	}
	
	private Abstraction parseAbstraction() throws ParsingException {
		gobble(LAMBDA);
		Variable var = parseVariable();
		gobble(DOT);
		Term body = parseTerm();
		return new Abstraction(var, body);
	}
	
	private Application parseApplication() throws ParsingException {
		gobble(LEFT_PAREN);
		Term leftSide = parseTerm();
		gobble(RIGHT_PAREN);
		Term rightSide = parseTerm();
		return new Application(leftSide, rightSide);
	}
	
	private Term parseTerm() throws ParsingException {

		// Function literal.
		if (expect(LAMBDA)) {
			return parseAbstraction();
		}
		
		// Application.
		if (expect(LEFT_PAREN)) {
			return parseApplication();
		}
		
		// Otherwise it's a variable. If there's nothing else other than this
		// variable, then the program is just a variable on its own.
		Variable var = parseVariable();
		if (done() || expect(RIGHT_PAREN)) return var;
		
		// Otherwise the program is application, where LHS is a variable.
		Term rhs = parseTerm();
		return new Application(var, rhs);
		
	}
	
	public Term parse(List<String> tokens) throws ParsingException{
		this.tokens = tokens;
		this.index = 0;
		return parseTerm();
	}
	
}
