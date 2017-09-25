package arithlang;

import static arithlang.AST.*;

import arithlang.AST.MaddExp;
import arithlang.AST.MclrExp;
import arithlang.AST.MrecExp;
import arithlang.AST.MsubExp;

public class Printer {
	
	public void print(Value v) {
		System.out.println("1.The result of the expression : " + v.toString());
	}

	public static class Formatter implements AST.Visitor<String> {

		public String visit(Program p) {
			return (String) p.e().accept(this);
		}

		public String visit(NumExp e) {
			return "" + e.v();
		}

		public String visit(AddExp e) {
			String result = "(+";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

		public String visit(SubExp e) {
			String result = "(-";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

		public String visit(MultExp e) {
			String result = "(*";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

		public String visit(DivExp e) {
			String result = "(/";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

		@Override
		public String visit(PrimeExp e) {
			String result = "(Prime";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
			// TODO Auto-generated method stub
		}

		@Override
		public String visit(MrecExp e) {
			String result = "(Mrec";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
			// TODO Auto-generated method stub
		}

		@Override
		public String visit(MclrExp e) {
			String result = "(Mclr";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

		@Override
		public String visit(MaddExp e) {
			String result = "(M+";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

		@Override
		public String visit(MsubExp e) {
			String result = "(M-";
			for (AST.Exp exp : e.all())
				result += " " + exp.accept(this);
			return result + ")";
		}

	}
}
