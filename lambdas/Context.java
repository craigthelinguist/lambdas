package lambdas;

public class Context {
	
	private String varName;
	private Term binding;
	private Context previous;
	
	private static Context EMPTY_CONTEXT = new Context(null, null, null);
	
	private Context(String varName, Term binding, Context previous) {
		this.varName = varName;
		this.binding = binding;
		this.previous = previous;
	}
	
	public static Context EmptyContext() {
		return Context.EMPTY_CONTEXT;
	}
	
	public Context extend(String var, Term value) {
		return new Context(var, value, this);
	}

	public boolean defines(String var) {
		if (varName == null) return false;
		if (varName.equals(var)) return true;
		return previous.defines(var);
	}
	
	public Term findBinding(String goal) {
		if (varName == null) return null;
		if (varName.equals(goal)) return binding;
		return previous.findBinding(goal);
	}
	
}
