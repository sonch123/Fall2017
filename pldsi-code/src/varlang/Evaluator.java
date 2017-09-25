package varlang;
import static varlang.AST.*;
import static varlang.Value.*;

import java.util.List;
import java.util.ArrayList;

import varlang.AST.*;
import varlang.Env.*;

public class Evaluator implements Visitor<Value> {
	
	Value valueOf(Program p) {
		Env env = new EmptyEnv();
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this, env);
	}
	
	@Override
	public Value visit(AddExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 0;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result += intermediate.v(); //Semantics of AddExp in terms of the target language.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(NumExp e, Env env) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e, Env env) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this, env);
		double result = lVal.v(); 
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this, env);
			result = result / rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(MultExp e, Env env) {
		List<Exp> operands = e.all();
		double result = 1;
		for(Exp exp: operands) {
			NumVal intermediate = (NumVal) exp.accept(this, env); // Dynamic type-checking
			result *= intermediate.v(); //Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p, Env env) {
		return (Value) p.e().accept(this, env);
	}

	@Override
	public Value visit(SubExp e, Env env) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this, env);
		double result = lVal.v();
		for(int i=1; i<operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this, env);
			result = result - rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(VarExp e, Env env) {
		// Previously, all variables had value 42. New semantics.
		return env.get(e.name());
	}	

	@Override
	public Value visit(LetExp e, Env env) { // New for varlang.
		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps();
		List<Value> values = new ArrayList<Value>(value_exps.size());
		
		Env new_env = env;
		
		for(int i = 0; i < value_exps.size(); i++) {
			Exp exp = value_exps.get(i);
			values.add((Value)exp.accept(this, new_env));
			new_env = new ExtendEnv(new_env, names.get(i), values.get(i));
		}

		return (Value) e.body().accept(this, new_env);		
	}

	@Override
	public Value visit(LeteExp e, Env env) {

		List<String> names = e.names();
		List<Exp> value_exps = e.value_exps(); 
		List<Value> values = new ArrayList<Value>(value_exps.size());
		NumExp num = e.num();
		
		Env new_env = env;
		
		Exp exp = value_exps.get(0);
		values.add((Value)exp.accept(this, new_env));
		new_env = new ExtendEnv(new_env, names.get(0), values.get(0));
		
		NumVal x = (NumVal) num.accept(this, new_env);
		NumVal y = (NumVal) e.body().accept(this, new_env);
		double result = x.v() + y.v();
		
		return (Value) new NumVal(result);
	}
 	
	@Override
	public Value visit(DecExp e, Env env) {
		
		NumVal num = (NumVal) e.num().accept(this, env);
		NumVal body =(NumVal) e.body().accept(this, env);

		double result = body.v() - num.v() ;
		return (Value) new NumVal(result);
	}	

}
