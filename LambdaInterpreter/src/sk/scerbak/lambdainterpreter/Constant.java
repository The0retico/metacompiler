package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;

/**
 * @author The0retico Predefined constant or internal function.
 */
class Constant extends Symbol implements IExpression {

	/**
	 * Name of this constant.
	 */
	private final String label;

	/**
	 * Expression representing this constant.
	 */
	private final IExpression expression;

	/**
	 * @return the expression
	 */
	public IExpression getExpression() {
		return expression;
	}

	private static final IExpression succ = def("n", "f", "x").apply(var("f"),
			apply(var("n"), var("f"), var("x")));

	private static final IExpression plus = def("m", "n", "f", "x").apply(
			var("m"), var("f"), apply(var("n"), var("f"), var("x")));

	private static final IExpression mult = def("m", "n", "f").apply(var("n"),
			apply(var("m"), var("f")));

	private static final IExpression ltrue = def("x", "y").var("x");
	private static final IExpression lfalse = def("x", "y").var("y");
	private static final IExpression and = def("x", "y").apply(var("x"),
			var("y"), lfalse);
	private static final IExpression or = def("x", "y").apply(var("x"), ltrue,
			var("y"));
	private static final IExpression not = def("x").apply(var("x"), lfalse,
			ltrue);
	private static final IExpression pair = def("x", "y", "c").apply(var("c"),
			var("x"), var("y"));
	private static final IExpression left = def("x").apply(var("x"), ltrue);
	private static final IExpression right = def("x").apply(var("x"), lfalse);
	private static final IExpression pred = Parser
			.fromString("(n|(f|(x|((n (g|(h|(h (g f))))) (u|x) (u|u)))))");
	private static final IExpression lif = def("c", "x", "y").apply(var("c"),
			var("x"), var("y"));
	private static final IExpression iszero = def("n").apply(var("n"),
			def("x", lfalse), ltrue);
	private static final IExpression combinatorY = def("f").apply(
			def("x").apply(var("f"), apply(var("x"), var("x"))),
			def("x").apply(var("f"), apply(var("x"), var("x"))));

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Constant)) {
			return this.expression.equals(obj);
		}
		Constant other = (Constant) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	/**
	 * @param constantLabel
	 *            uppercase string name of this lambda constant
	 */
	public Constant(final String constantLabel) {
		super();
		this.label = constantLabel;
		if ("TRUE".equals(constantLabel)) {
			this.expression = ltrue;
		} else if ("FALSE".equals(constantLabel)) {
			this.expression = lfalse;
		} else if ("AND".equals(constantLabel)) {
			this.expression = and;
		} else if ("OR".equals(constantLabel)) {
			this.expression = or;
		} else if ("NOT".equals(constantLabel)) {
			this.expression = not;
		} else if ("PAIR".equals(constantLabel)) {
			this.expression = pair;
		} else if ("LEFT".equals(constantLabel)) {
			this.expression = left;
		} else if ("RIGHT".equals(constantLabel)) {
			this.expression = right;
		} else if ("Y".equals(constantLabel)) {
			this.expression = combinatorY;
		} else if ("IF".equals(constantLabel)) {
			this.expression = lif;
		} else if ("ZERO?".equals(constantLabel)) {
			this.expression = iszero;
		} else if ("MULT".equals(constantLabel)) {
			this.expression = mult;
		} else if ("PLUS".equals(constantLabel)) {
			this.expression = plus;
		} else if ("PRED".equals(constantLabel)) {
			this.expression = pred;
		} else if ("SUCC".equals(constantLabel)) {
			this.expression = succ;
		} else {
			this.expression = null;
		}
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		final IExpression result;
		if (this.expression == null) {
			result = this;
		} else {
			result = this.expression.substitute(variable, expression);
		}
		return result;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public String toString() {
		return this.label;
	}

	@Override
	public boolean free(final String variable) {
		return false;
	}

	@Override
	public boolean isReducible() {
		return this.expression != null;
	}
}
