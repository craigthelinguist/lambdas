package lambdas;

import java.util.Set;

public abstract class Term {
	
	public abstract Set<String> freeVars(Context ctx);
	
	public abstract boolean isValue();
	
	public abstract Term eval(Context ctx);
	
	public abstract Term substitute(Context ctx, String variable, Term value);

	public abstract String printAST();
	
}
