/**
 * 
 */
package sk.scerbak.lambdainterpreter;

import static sk.scerbak.lambdainterpreter.Calculus.apply;
import static sk.scerbak.lambdainterpreter.Calculus.def;
import static sk.scerbak.lambdainterpreter.Calculus.var;

import java.util.LinkedList;
import java.util.List;

/**
 * @author The0retico
 * 
 */
enum Constant implements IExpression {
	SUCC(def("n", "f", "x")
			.apply(var("f"), apply(var("n"), var("f"), var("x")))), PRED(def(
			"n", "f", "x").apply(var("n"),
			def("g", "h").apply(var("h"), apply(var("g"), var("f"))),
			def("u").var("x"), def("u").var("u"))), PLUS(
			def("m", "n", "f", "x").apply(var("m"), var("f"),
					apply(var("n"), var("f"), var("x")))), MULT(def("m", "n",
			"f").apply(var("n"), apply(var("m"), var("f")))), TRUE(
			def("x", "y").var("x")), FALSE(def("x", "y").var("y")), ISZERO(def(
			"n").apply(var("n"), def("x", FALSE), TRUE)), IF(def("c", "x", "y")
			.apply(var("c"), var("x"), var("y"))), NOT(def("x").apply(var("x"),
			FALSE, TRUE)), AND(def("x", "y").apply(var("x"), var("y"), FALSE)), OR(
			def("x", "y").apply(var("x"), TRUE, var("y"))), PAIR(def("x", "y",
			"c").apply(var("c"), var("x"), var("y"))), LEFT(def("x").apply(
			var("x"), TRUE)), RIGHT(def("x").apply(var("x"), FALSE)), Y(
			def("f").apply(def("x").apply(var("f"), apply(var("x"), var("x"))),
					def("x").apply(var("f"), apply(var("x"), var("x")))));

	private final IExpression body;

	Constant(final IExpression expression) {
		body = expression;
	}

	@Override
	public void accept(final IVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean alphaEquals(final IExpression other) {
		return body.alphaEquals(other);
	}

	@Override
	public boolean free(final String variable) {
		return false;
	}

	/**
	 * @return the body expression
	 */
	public IExpression getExpression() {
		return body;
	}

	@Override
	public boolean isReducible() {
		return false;
	}

	@Override
	public IExpression normalForm() {
		return this;
	}

	@Override
	public IExpression oneStepBetaReduce() {
		return this;
	}

	@Override
	public IExpression substitute(final String variable,
			final IExpression expression) {
		final IExpression result;
		if (body != null) {
			result = body.substitute(variable, expression);
		} else {
			result = this;
		}
		return result;
	}

	@Override
	public List<IExpression> subterm() {
		final List<IExpression> result = new LinkedList<IExpression>();
		result.add(this);
		return result;
	}

}
