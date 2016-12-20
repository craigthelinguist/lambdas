package lambdas;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

	private String text;
	private int index;
	
	private char[] SYMBOLS = new char[]{
		Utils.LAMBDA, '.', '(', ')', '\\', '='
	};

	private boolean isSymbol(char ch) {
		for (int i = 0; i < SYMBOLS.length; i++) {
			if (SYMBOLS[i] == ch) return true;
		}
		return false;
	}
	
	private boolean done() {
		return index >= text.length();
	}
	
	private void skipWhitespace() {
		while (!done() && Character.isWhitespace(text.charAt(index)))
			index++;
	}
	
	private boolean alphaNumeric(char ch) {
		return Character.isAlphabetic(ch) || Character.isDigit(ch);
	}
	
	private String lexIdentifier() throws LexingException {
		if (!Character.isAlphabetic(text.charAt(index)))
			throw new LexingException("Identifier must start with a letter.");
		StringBuilder sb = new StringBuilder();
		while (!done() && alphaNumeric(text.charAt(index))) {
			sb.append(text.charAt(index++));
		}
		return sb.toString();
	}
	
	private String lexToken() throws LexingException {
		skipWhitespace();
		if (done()) return null;
		char ch = text.charAt(index);
		if (isSymbol(ch)) {
			if (ch == '\\') ch = Utils.LAMBDA;
			index++;
			return "" + ch;
		}
		else return lexIdentifier();
	}
	
	public List<String> lex(String text) throws LexingException {
		this.text = text;
		this.index = 0;
		List<String> tokens = new ArrayList<>();
		while (!done()) {
			String token = lexToken();
			if (token != null) tokens.add(token);
		}
		this.index = 0;
		return tokens;
	}

}
