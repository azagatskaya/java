package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

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
		// TODO Auto-generated method stub
		return null;
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

	private Node<T> findNode(Object pattern) {
		
		Node<T> current = root;
		Node<T> foundNode = null;
		while (current != null) {
			int resComp = comparator.compare((T) pattern, current.obj);
			if (resComp == 0) {
				foundNode = current;
				break;
			}
			current = resComp < 0 ? current.left : current.right;
		}
		return foundNode;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		return false;
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
		Node<T> child = new Node<>(null);
		child = node.left != null ? node.left : node.right;
		if (node.parent == null) {
			if (child != null) {
				child.parent = null;
				child = root;
			}
		} else if (child != null) {
			child.parent = node.parent;
			if (node.parent.left == node) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
		}
	}

	private boolean isJunction(Node<T> node) {
		return node.left != null && node.right != null;
	}

	private class TreeIterator implements Iterator<T> {
		Node<T> current = getLeastNode(root);

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T res = current.obj;
			current = current.right != null ? getLeastNode(current.right) :
				getParentFromLeft(current);
			return res;
		}

		@Override
		public void remove() {
			// TODO
		}
	}

}
