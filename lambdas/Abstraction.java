package lambdas;

import java.util.Set;

public class Abstraction extends Term {
	
	private String formalArg;
	private Term lambdaBody;
	
	public Abstraction(String argName, Term lambdaBody) {
		this.formalArg = argName;
		this.lambdaBody = lambdaBody;
	}
	
	public Abstraction(Variable var, Term lambdaBody) {
		this.formalArg = var.toString();
		this.lambdaBody = lambdaBody;
	}
	
	@Override
	public Set<String> freeVars(Context ctx) {
		Context ctx2 = ctx.extend(formalArg, null);
		return lambdaBody.freeVars(ctx2);
	}

	@Override
	public boolean isValue() {
		return true;
	}

	@Override
	public Term eval(Context ctx) {
		return this;
	}

	@Override
	public Term substitute(Context ctx, String variable, Term value) {
		Context ctx2 = ctx.extend(formalArg, null);
		return lambdaBody.substitute(ctx2, variable, value);
	}

	public Term evalBody(Context ctx, Term actualArg) {
		if (!actualArg.isValue())
			throw new RuntimeException("Passing non-value as argument of function.");
		return lambdaBody.substitute(ctx, formalArg, actualArg).eval(ctx);
	}
	
	@Override
	public String toString() {
		return Utils.LAMBDA + formalArg + "." + lambdaBody;
	}

	@Override
	public String printAST() {
		return "ABS(" + formalArg + ", " + lambdaBody.printAST() + ")";
	}
	
}
