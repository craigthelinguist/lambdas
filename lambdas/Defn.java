package lambdas;

import java.util.HashSet;
import java.util.Set;

public class Defn extends Term {

	private String name;
	private Term defn;
	
	public Defn(String name, Term defn) {
		this.name = name;
		this.defn = defn;
	}
	
	public Defn(Variable var, Term defn) {
		this.name = var.toString();
		this.defn = defn;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Term getBody() {
		return this.defn;
	}

	@Override
	public Set<String> freeVars(Context ctx) {
		return new HashSet<>();
	}

	@Override
	public boolean isValue() {
		return false;
	}

	@Override
	public Term eval(Context ctx) {
		return this;
	}

	@Override
	public Term substitute(Context ctx, String variable, Term value) {
		return this;
	}

	@Override
	public String printAST() {
		return "DEFN(" + name + ", " + defn.printAST();
	}
	
	
	
}
