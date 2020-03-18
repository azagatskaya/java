package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

import org.omg.CORBA.Current;

public class TreeSet<T> implements Set<T> {
	private static class Node<T> {
		T obj;
		Node<T> parent;
		Node<T> left; // reference to a less (relative to comparator)
		Node<T> right; // reference to a greater

		public Node(T obj) {
			this.obj = obj;
		}
	}

	Comparator<T> comparator;
	Node<T> root;
	int size;

	public TreeSet(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>) Comparator.naturalOrder());
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T obj) {
		if (root == null) {
			addRoot(obj); // addRoot creates new Node that will be root
			return true;
		}
		Node<T> parent = getParent(obj);
		// if obj already exists (compare return 0) -> returns false

		if (parent == null) {
			return false;
		}
		Node<T> newNode = new Node<>(obj);
		if (comparator.compare(obj, parent.obj) < 0) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}
		size++;
		newNode.parent = parent;
		return false;
	}

	private void addRoot(T obj) {
		size = 1;
		root = new Node<>(obj);

	}

	private Node<T> getParent(T obj) {
		// beginning from the root we are going either to left or to right
		// going to left -> current = current.left
		// going to right -> current = current.right
		// node from which you have come to null will be parent
		if (size == 1) {
			return root;
		}
		Node<T> current = root;
		Node<T> prevNode = current;
		while(current != null) {
			prevNode = current;
			if (comparator.compare(obj, current.obj) < 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return prevNode;
	}

	@Override
	public Set<T> filter(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(Object pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean contains(T pattern) {
		// TODO Auto-generated method stub
		return false;
	}

}
