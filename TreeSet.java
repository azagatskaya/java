package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

public class TreeSet<T> implements SortedSet<T> {
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

		return new TreeIterator();
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
		return true;
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
		Node<T> current = root;
		Node<T> parent = null;
		while (current != null) {
			parent = current;
			int resComp = comparator.compare(obj, current.obj);
			if (resComp == 0) {
				parent = null;
				break;
			}
			current = resComp < 0 ? current.left : current.right;

		}
		return parent;
	}

	@Override
	public Set<T> filter(Predicate<T> predicate) {
		TreeSet<T> res = new TreeSet<>();
		for (T obj : this) {
			if (predicate.test(obj)) {
				res.add(obj);
			}
		}
		return res;
	}

	@Override
	public Object remove(Object pattern) {
		Object res = null;
		Node<T> node = findNode(pattern);
		if (node != null) {
			res = node.obj;
			removeNode(node);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	private Node<T> findNode(Object pattern) {
		Node<T> current = root;
		int compRes;
		while (current != null && (compRes = comparator.compare((T) pattern, current.obj)) != 0) {
			current = compRes < 0 ? current.left : current.right;
		}
		return current;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public boolean contains(T pattern) {

		return size > 0 && getParent(pattern) == null;
	}

	private Node<T> getLeastNode(Node<T> node) {
		Node<T> current = node;
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	private Node<T> getParentFromLeft(Node<T> node) {
		while (node.parent != null && node.parent.right == node) {
			node = node.parent;
		}
		return node.parent;
	}

	private void removeNode(Node<T> node) {
		if (isJunction(node)) {
			Node<T> substitute = getLeastNode(node.right);
			node.obj = substitute.obj;
			node = substitute;
		}
		removeNonJunctionNode(node);
		size--;
	}

	private void removeNonJunctionNode(Node<T> node) {

		Node<T> parent = node.parent;
		Node<T> child = node.left == null ? node.right : node.left;
		if (parent == null) {
			// removing root as non-junction node
			root = child;

		} else if (parent.left == node) {
			parent.left = child;
		} else
			parent.right = child;
		if (child != null) {
			child.parent = parent;
		}

	}

	private boolean isJunction(Node<T> node) {

		return node.left != null && node.right != null;
	}

	private class TreeIterator implements Iterator<T> {
		Node<T> current = root != null ? getLeastNode(root) : null;
		Node<T> previous;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T res = current.obj;
			previous = current;
			current = current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);
			return res;
		}

		@Override
		public void remove() {
			if (isJunction(previous)) {
				current = previous;
			}
			removeNode(previous);
		}
	}

	@Override
	public T getMin() {
		return getLeastNode(root).obj;
	}

	@Override
	public T getMax() {
		Node<T> current = root;
		while (current.right != null) {
			current = current.right;
		}
		return current.obj;
	}

	@Override
	public SortedSet<T> subset(T from, boolean isIncludedFrom, T to, boolean isIncludedTo) {

		SortedSet<T> sortedSet = new TreeSet<>();
		Node<T> current = findNode(from);
		current = current == null ? getParent(from) : current;
		int fromRes, toRes;
		fromRes = comparator.compare(from, current.obj); // 1: from > res; -1: from < res;
		if ((fromRes == 0 && isIncludedFrom == true) || fromRes < 0) {
			sortedSet.add(current.obj);
		}
		current = current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);
		toRes = comparator.compare(to, current.obj); // 1: to > res; -1: to < res;
		while (toRes > 0 || toRes == 0) {
			if (toRes == 0) {
				if (isIncludedTo == true) {
					sortedSet.add(current.obj);
					break;
				} else {
					break;
				}
			}
			sortedSet.add(current.obj);
			current = current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);
			toRes = comparator.compare(to, current.obj);
		}
		return sortedSet;
	}
}
