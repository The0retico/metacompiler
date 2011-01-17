package parser.ebnf;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AbstractSyntaxTree implements List<AbstractSyntaxTree> {
	private final List<AbstractSyntaxTree> children;
	private final String value;

	public AbstractSyntaxTree(final String string) {
		value = string;
		children = new LinkedList<AbstractSyntaxTree>();
	}

	@Override
	public boolean add(final AbstractSyntaxTree e) {
		return children.add(e);
	}

	@Override
	public void add(final int index, final AbstractSyntaxTree element) {
		children.add(index, element);
	}

	@Override
	public boolean addAll(final Collection<? extends AbstractSyntaxTree> c) {
		return children.addAll(c);
	}

	@Override
	public boolean addAll(final int index,
			final Collection<? extends AbstractSyntaxTree> c) {
		return children.addAll(index, c);
	}

	@Override
	public void clear() {
		children.clear();
	}

	@Override
	public boolean contains(final Object o) {
		return children.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return children.containsAll(c);
	}

	@Override
	public boolean equals(final Object o) {
		return children.equals(o);
	}

	@Override
	public AbstractSyntaxTree get(final int index) {
		return children.get(index);
	}

	@Override
	public int hashCode() {
		return children.hashCode();
	}

	@Override
	public int indexOf(final Object o) {
		return children.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return children.isEmpty();
	}

	@Override
	public Iterator<AbstractSyntaxTree> iterator() {
		return children.iterator();
	}

	@Override
	public int lastIndexOf(final Object o) {
		return children.lastIndexOf(o);
	}

	@Override
	public ListIterator<AbstractSyntaxTree> listIterator() {
		return children.listIterator();
	}

	@Override
	public ListIterator<AbstractSyntaxTree> listIterator(final int index) {
		return children.listIterator(index);
	}

	@Override
	public AbstractSyntaxTree remove(final int index) {
		return children.remove(index);
	}

	@Override
	public boolean remove(final Object o) {
		return children.remove(o);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return children.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return children.retainAll(c);
	}

	@Override
	public AbstractSyntaxTree set(final int index,
			final AbstractSyntaxTree element) {
		return children.set(index, element);
	}

	@Override
	public int size() {
		return children.size();
	}

	@Override
	public List<AbstractSyntaxTree> subList(final int fromIndex,
			final int toIndex) {
		return children.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return children.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return children.toArray(a);
	}
}
