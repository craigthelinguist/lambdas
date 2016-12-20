package lambdas;

import java.util.HashSet;
import java.util.Set;

public class Variable extends Term {

	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	@Override
	public Set<String> freeVars(Context ctx) {
		Set<String> vars = new HashSet<>();
		if (!ctx.defines(name)) vars.add(name);
		return vars;
	}

	@Override
	public boolean isValue() {
		return false;
	}

	@Override
	public Term eval(Context ctx) {
		Term toReplace = ctx.findBinding(name);
		return toReplace == null ? this : toReplace;
	}

	@Override
	public Term substitute(Context ctx, String variable, Term value) {
		if (name.equals(variable) && !ctx.defines(name)) return value;
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String printAST() {
		return "VAR(" + name + ")";
	}
	
}
