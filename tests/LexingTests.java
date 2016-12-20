package tests;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import lambdas.Lexer;
import lambdas.LexingException;
import lambdas.Utils;

import static org.junit.Assert.*;

public class LexingTests {

	private static final String LAMBDA = "" + Utils.LAMBDA;
	private static final String DOT = ".";
	private static final String LEFT_PAREN = "(";
	private static final String RIGHT_PAREN = ")";
	private static final String EQUALS = "=";
	
	private void test(String text2lex, String[] expected) throws LexingException {
		List<String> actual = new Lexer().lex(text2lex);
		for (int i = 0; i < expected.length; i++) {
			String errMsg = "expected " + expected[i] + " but got " + actual.get(i);
			assertTrue(errMsg, expected[i].equals(actual.get(i)));
		}
		assertTrue("wrong number of tokens", expected.length == actual.size());
	}
	
	@Test
	public void lexVariable() throws LexingException {
		test("x", new String[]{ "x" });
	}
	
	@Test
	public void lexApplication() throws LexingException {
		test("x y", new String[]{ "x", "y" });
	}
	
	@Test
	public void lexApplication2() throws LexingException {
		String prog = "(" + LAMBDA + "x.x)" + LAMBDA + "x.x";
		test(prog, new String[]{
			LEFT_PAREN, LAMBDA, "x", DOT, "x", RIGHT_PAREN,
			LAMBDA, "x", DOT, "x"
		});
	}
	
	@Test
	public void lexAbstraction1() throws LexingException {
		test(LAMBDA + "x.x", new String[]{ LAMBDA, "x", DOT, "x" });
	}
	
	@Test
	public void lexAbstraction2() throws LexingException {
		test("\\" + "x.x", new String[]{ LAMBDA, "x", DOT, "x" });
	}
	
	@Test
	public void testWhitespace1() throws LexingException {
		test("  x ", new String[]{ "x" });
	}
	
	@Test
	public void testWhitespace2() throws LexingException {
		test(LAMBDA + " x . x ", new String[]{ LAMBDA, "x", DOT, "x" });
	}
	
	public static <T> String prettyPrint(List<T> elems) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < elems.size(); i++) {
			sb.append(elems.get(i).toString());
			if (i < elems.size() - 1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void main(String[] args) throws LexingException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			String prog = sc.nextLine();
			List<String> tokens = new Lexer().lex(prog);
			System.out.println(prettyPrint(tokens));
		}
	}
	
}
