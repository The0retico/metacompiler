package grammar.ebnf;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Grammar implements List<Rule> {

	private final List<Rule> rules;

	public Grammar() {
		rules = new LinkedList<Rule>();
	}

	@Override
	public void add(final int index, final Rule element) {
		rules.add(index, element);
	}

	@Override
	public boolean add(final Rule e) {
		return rules.add(e);
	}

	@Override
	public boolean addAll(final Collection<? extends Rule> c) {
		return rules.addAll(c);
	}

	@Override
	public boolean addAll(final int index, final Collection<? extends Rule> c) {
		return rules.addAll(index, c);
	}

	@Override
	public void clear() {
		rules.clear();
	}

	@Override
	public boolean contains(final Object obj) {
		return rules.contains(obj);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		return rules.containsAll(c);
	}

	@Override
	public boolean equals(final Object obj) {
		return rules.equals(obj);
	}

	@Override
	public Rule get(final int i) {
		return rules.get(i);
	}

	@Override
	public int hashCode() {
		return rules.hashCode();
	}

	@Override
	public int indexOf(final Object obj) {
		return rules.indexOf(obj);
	}

	@Override
	public boolean isEmpty() {
		return rules.isEmpty();
	}

	@Override
	public Iterator<Rule> iterator() {
		return rules.iterator();
	}

	@Override
	public int lastIndexOf(final Object obj) {
		return rules.lastIndexOf(obj);
	}

	@Override
	public ListIterator<Rule> listIterator() {
		return rules.listIterator();
	}

	@Override
	public ListIterator<Rule> listIterator(final int i) {
		return rules.listIterator(i);
	}

	@Override
	public Rule remove(final int i) {
		return rules.remove(i);
	}

	@Override
	public boolean remove(final Object obj) {
		return rules.remove(obj);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		return rules.removeAll(c);
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		return rules.retainAll(c);
	}

	@Override
	public Rule set(final int index, final Rule element) {
		return rules.set(index, element);
	}

	@Override
	public int size() {
		return rules.size();
	}

	@Override
	public List<Rule> subList(final int i, final int j) {
		return rules.subList(i, j);
	}

	@Override
	public Object[] toArray() {
		return rules.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return rules.toArray(a);
	}

}