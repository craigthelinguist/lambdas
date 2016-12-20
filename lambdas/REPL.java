package lambdas;

import java.util.List;
import java.util.Scanner;

public class REPL {

	private Context ctx;
	private Scanner sc;
	private Lexer lexer;
	private Parser parser;
	
	public REPL() {
		sc = new Scanner(System.in);
		ctx = Context.EmptyContext();
		lexer = new Lexer();
		parser = new Parser();
	}
	
	public void run() {
		while (true) {
			try {
			
				// Lex the input.
				System.out.flush();
				System.out.print("> ");
				String input = sc.nextLine();
				List<String> tokens = lexer.lex(input);
				
				// Check if they're defining something.
				if (tokens.size() > 2 && tokens.get(1).equals("=")) {
					addDefn(tokens);
					continue;
				}
				
				// Otherwise evaluate the term.
				Term prog = parser.parse(tokens);
				Term val = prog.eval(ctx);
				System.out.println(val.toString());
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void addDefn(List<String> tokens) throws ParsingException {
		List<String> defnName = tokens.subList(0, 1);
		List<String> defnBody = tokens.subList(2, tokens.size());
		Term lhs = parser.parse(defnName);
		if (!(lhs instanceof Variable)) {
			throw new ParsingException("Left-hand side of definition must be an identifier.");
		}
		Term rhs = parser.parse(defnBody);
		ctx = ctx.extend(((Variable)lhs).toString(), rhs);
	}
	
	public static void main(String[] args) {
		new REPL().run();
	}
	
}
