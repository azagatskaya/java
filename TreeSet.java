package telran.util;

import java.util.ArrayList;
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

	private static final int SPACES_PER_LEVEL = 2;

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

		return root != null ? getLeastNode(root).obj : null;
	}

	@Override
	public T getMax() {

		return root != null ? getMostNode(root).obj : null;
	}

	private Node<T> getMostNode(Node<T> node) {
		while (node.right != null) {
			node = node.right;
		}

		return node;
	}

	@Override
	public SortedSet<T> subset(T from, boolean isIncludedFrom, T to, boolean isIncludedTo) {

		SortedSet<T> res = new TreeSet<>(comparator);
		if (isValidArguments(from, isIncludedFrom, to, isIncludedTo)) {
			Node<T> nodeFrom = findClosestGreaterEqual(from, isIncludedFrom);
			res = nodeFrom == null ? res : getSetFromNodeToObject(res, nodeFrom, to, isIncludedTo);
		}
		return res;

	}

//	@Override
//	public SortedSet<T> subset(T from, boolean isIncludedFrom, T to, boolean isIncludedTo) {
//		SortedSet<T> sortedSet = new TreeSet<>();
//		Node<T> current = findNode(from);
//		current = current == null ? getParent(from) : current;
//		int fromRes, toRes;
//		fromRes = comparator.compare(from, current.obj); // 1: from > res; -1: from < res;
//		if ((fromRes == 0 && isIncludedFrom == true) || fromRes < 0) {
//			sortedSet.add(current.obj);
//		}
//		current = getNext(current);//current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);
//		toRes = comparator.compare(to, current.obj); // 1: to > res; -1: to < res;
//		while (toRes > 0 || toRes == 0) {
//			if (toRes == 0) {
//				if (isIncludedTo == true) {
//					sortedSet.add(current.obj);
//					break;
//				} else {
//					break;
//				}
//			}
//			sortedSet.add(current.obj);
//			current = getNext(current);// current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);
//			toRes = comparator.compare(to, current.obj);
//		}
//		return sortedSet;
//	}

	private boolean isValidArguments(T from, boolean isIncludedFrom, T to, boolean isIncludedTo) {
		if (from == null || to == null)
			return false;
		int compRes = comparator.compare(from, to);
		return compRes < 0 || (compRes == 0 && isIncludedFrom && isIncludedTo);
	}

	private SortedSet<T> getSetFromNodeToObject(SortedSet<T> set, Node<T> nodeFrom, T to, boolean isIncluded) {
		SortedSet<T> res = set;
		Node<T> node = nodeFrom;
		while (node != null) {
			res.add(node.obj);
			node = getNext(node, to, isIncluded);
		}
		return res;
	}

	private Node<T> getNext(Node<T> current, T to, boolean isIncluded) {
		Node<T> res = current.right != null ? getLeastNode(current.right) : getParentFromLeft(current);
		if (res == null) {
			return null;
		}
		int compRes = comparator.compare(to, res.obj);
		return compRes < 0 || (compRes == 0 && !isIncluded) ? null : res;
	}

	private Node<T> findClosestGreaterEqual(T pattern, boolean isIncluded) {
		Node<T> current = root;
		Node<T> res = null;
		while (current != null) {
			int resComp = comparator.compare(pattern, current.obj);
			if (resComp < 0) {
				res = current;
				current = current.left;
			} else if (resComp > 0) {
				current = current.right;
			} else {
				if (isIncluded) {
					res = current;
				} else {
					if (current.right != null) {
						res = getLeastNode(current.right);
					}
				}
				break;
			}
		}
		return res;
	}

	public void rotatedTreeDisplay() {
		rotatedDisplay(root, 0);

	}

	public int height() {
		return height(root);
	}

	private int height(Node<T> root) {
		int res = 0;
		if (root != null) {
			int heightRight = height(root.right);// height of the right subtree
			int heightLeft = height(root.left);
			res = 1 + Math.max(heightRight, heightLeft);
		}

		return res;

	}

	public int width() {
		return width(root);
	}

	private int width(Node<T> root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 1;
		}
		return width(root.left) + width(root.right);
	}

	private void rotatedDisplay(Node<T> root, int level) {
		if (root != null) {
			rotatedDisplay(root.right, level + 1);
			displayRoot(root, level);
			rotatedDisplay(root.left, level + 1);
		}

	}

	private void displayRoot(Node<T> root, int level) {
		printOffset(level);
		System.out.println(root.obj);

	}

	private void printOffset(int level) {
		int limit = level * SPACES_PER_LEVEL;
		for (int i = 0; i < limit; i++) {
			System.out.print(" ");
		}

	}

	public ArrayList<ArrayList<T>> getObjectsByLevels() {
		ArrayList<ArrayList<T>> levelsArray = new ArrayList<>();
		int level = 0;
		checkLevel(root, level, levelsArray);
		return levelsArray;
	}

	private void checkLevel(Node<T> root, int level, ArrayList<ArrayList<T>> levelsArray) {
		if (root != null) {
			checkLevel(root.right, level + 1, levelsArray);
			levelsArray.get(level).add(root.obj);
			checkLevel(root.left, level + 1, levelsArray);
		}
	}

}
