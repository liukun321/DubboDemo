package com.mixiusi.server;

import java.util.Objects;

public class Pair<F, S> {
	public final F first;
	public final S second;

	/**
	 * Constructor for a Pair.
	 *
	 * @param first
	 *            the first object in the Pair
	 * @param second
	 *            the second object in the pair
	 */
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Checks the two objects for equality by delegating to their respective
	 * {@link Object#equals(Object)} methods.
	 *
	 * @param o
	 *            the {@link Pair} to which this one is to be checked for
	 *            equality
	 * @return true if the underlying objects of the Pair are both considered
	 *         equal
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Pair)) {
			return false;
		}
		Pair<?, ?> p = (Pair<?, ?>) o;
		return Objects.equals(p.first, first) && Objects.equals(p.second, second);
	}

	/**
	 * Compute a hash code using the hash codes of the underlying objects
	 *
	 * @return a hashcode of the Pair
	 */
	@Override
	public int hashCode() {
		return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
	}

	/**
	 * Convenience method for creating an appropriately typed pair.
	 * 
	 * @param a
	 *            the first object in the Pair
	 * @param b
	 *            the second object in the pair
	 * @return a Pair that is templatized with the types of a and b
	 */
	public static <A, B> Pair<A, B> create(A a, B b) {
		return new Pair<A, B>(a, b);
	}
}

/*
 * public class GenericPair { private Short first; private Short second;
 * 
 * public GenericPair(Short a,Short b){ this.first = a; this.second = b; }
 * 
 * /* public E getFirst() { return first; } public void setFirst(E first) {
 * this.first = first; } public F getSecond() { return second; } public void
 * setSecond(F second) { this.second = second;
 * 
 * }
 */
/*
 * @Override public String toString() { // TODO Auto-generated method stub
 * return this.first.toString() + this.second.toString(); }
 * 
 * @Override public int hashCode() { // TODO Auto-generated method stub return
 * this.first.hashCode() + this.second.hashCode(); } }
 */
