package lambdas;

import java.util.Set;

public class Application extends Term {
	
	private Term left;
	private Term right;
	
	public Application(Term left, Term right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public Set<String> freeVars(Context ctx) {
		Set<String> freeVariables = left.freeVars(ctx);
		freeVariables.addAll(right.freeVars(ctx));
		return freeVariables;
	}

	@Override
	public boolean isValue() {
		return false;
	}

	@Override
	public Term eval(Context ctx) {
		Term left2 = left.eval(ctx);
		Term right2 = right.eval(ctx);
		if (!left2.isValue()) {
			throw new RuntimeException("Could not reduce left-hand side of an application.");
		}
		Abstraction functionDef = (Abstraction) left2;
		return functionDef.evalBody(ctx, right2);
	}

	@Override
	public Term substitute(Context ctx, String variable, Term value) {
		Term left2 = left.substitute(ctx, variable, value);
		Term right2 = right.substitute(ctx, variable, value);
		return new Application(left2, right2);
	}
	
	@Override
	public String toString() {
		return "(" + left + ") " + right;
	}
	
	@Override
	public String printAST() {
		return "APP(" + left.printAST() + ", " + right.printAST() + ")";
	}
	
}
