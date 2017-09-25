package arithlang;

import static arithlang.AST.*;
import static arithlang.Value.*;

import java.util.List;

import arithlang.AST.MaddExp;
import arithlang.AST.MclrExp;
import arithlang.AST.MrecExp;
import arithlang.AST.MsubExp;

public class Evaluator implements Visitor<Value> {

	Printer.Formatter ts = new Printer.Formatter();
	
	private NumVal mem = new NumVal(0);

	Value valueOf(Program p) {
		// Value of a program in this language is the value of the expression
		return (Value) p.accept(this);
	}

	@Override
	public Value visit(AddExp e) {
		List<Exp> operands = e.all();
		double result = 0;
		for (Exp exp : operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
			result += intermediate.v(); // Semantics of AddExp in terms of the target language.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(NumExp e) {
		return new NumVal(e.v());
	}

	@Override
	public Value visit(DivExp e) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double result = lVal.v();
		for (int i = 1; i < operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			result = result / rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(MultExp e) {
		List<Exp> operands = e.all();
		double result = 1;
		for (Exp exp : operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
			result *= intermediate.v(); // Semantics of MultExp.
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(Program p) {
		return (Value) p.e().accept(this);
	}

	@Override
	public Value visit(SubExp e) {
		List<Exp> operands = e.all();
		NumVal lVal = (NumVal) operands.get(0).accept(this);
		double result = lVal.v();
		for (int i = 1; i < operands.size(); i++) {
			NumVal rVal = (NumVal) operands.get(i).accept(this);
			result = result - rVal.v();
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(PrimeExp e) {
		List<Exp> operands = e.all();
		NumVal val = (NumVal) operands.get(0).accept(this);
		double result = 1; // True

		if (val.v() < 0) {
			System.out.println("prime operand should not be negative");
		} else if (val.v() <= 1 && val.v() >= 0) {
			result = 0; // False
			return new NumVal(result);
		} else if (val.v() <= 3) {
			return new NumVal(result);
		} else if ((val.v() % 2) == 0 || (val.v() % 3) == 0) {
			result = 0; // False
			return new NumVal(result);
		}
		int i = 5;
		while ((i * i) <= val.v()) {
			if (val.v() % i == 0 || val.v() % (i + 2) == 0) {
				result = 0; // False
				return new NumVal(result);
			} else {
				i = i + 6;
			}
		}
		return new NumVal(result);
	}

	@Override
	public Value visit(MrecExp e) {
		return mem;
	}

	@Override
	public Value visit(MclrExp e) {
		mem = new NumVal(0);
		return mem;
	}

	@Override
	public Value visit(MaddExp e) {
		List<Exp> operands = e.all();
		double result = mem.v();
		for (Exp exp : operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
			result += intermediate.v(); // Semantics of AddExp in terms of the target language.
		}
		mem = new NumVal(result);
		return mem;
	}

	@Override
	public Value visit(MsubExp e) {
		List<Exp> operands = e.all();
		double result = mem.v();
		for (Exp exp : operands) {
			NumVal intermediate = (NumVal) exp.accept(this); // Dynamic type-checking
			result -= intermediate.v(); // Semantics of AddExp in terms of the target language.
		}
		mem = new NumVal(result);
		return mem;
	}


}
