package tests;

import java.util.Scanner;

import lambdas.Lexer;
import lambdas.LexingException;
import lambdas.Parser;
import lambdas.ParsingException;
import lambdas.Term;

public class ParsingTests {

	public static void main(String[] args) throws LexingException, ParsingException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String prog = sc.nextLine();
			Term term = new Parser().parse(new Lexer().lex(prog));
			System.out.println(term.printAST());
		}
	}
	
}
