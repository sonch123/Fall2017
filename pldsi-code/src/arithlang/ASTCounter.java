package arithlang;

import static arithlang.AST.*;


public class ASTCounter implements AST.Visitor<Integer> {

	
	public void print(Program p) {
		System.out.println("2.The count of AST nodes : " + p.accept(this));
	}

	@Override
	public Integer visit(NumExp e) {
		return 1;
	}

	@Override
	public Integer visit(AddExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}

	@Override
	public Integer visit(SubExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}

	@Override
	public Integer visit(MultExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}

	@Override
	public Integer visit(DivExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}

	@Override
	public Integer visit(Program p) {
		return (int) p.e().accept(this) + 1;
	}

	@Override
	public Integer visit(PrimeExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}

	@Override
	public Integer visit(MrecExp e) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Integer visit(MclrExp e) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Integer visit(MaddExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}

	@Override
	public Integer visit(MsubExp e) {
		int result = 0;
		for (AST.Exp exp : e.all())
			result = result + (int) exp.accept(this);
		return result + 1;
	}


}
