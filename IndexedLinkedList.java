package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

//import telran.tests.PersonAgeComparator;
import telran.util.Array;

//import telran.util.Array.ArrayIterator;

public class IndexedLinkedList<T> implements IndexedList<T> {
	//public T[] array;
	private static class Node<T> {
		public T obj;
		public Node<T> next;
		public Node<T> prev;

		public Node(T obj) {
			this.obj = obj;
		}

	}

	private class ListIterator implements Iterator<T> {
		Node<T> current = head;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T res = current.obj;
			current = current.next;
			return res;
		}

		@Override
		public void remove() {
			if (current == null) {
				removeTail();
			} else {
				removeNode(current.prev);
			}
		}

	}
	public Array<T> array;
	private Node<T> head;
	private Node<T> tail;
	private int size;

	public IndexedLinkedList() {
	}

	public IndexedLinkedList(int dummy) {
	}

	@Override
	public Iterator<T> iterator() {
		return new ListIterator();
	}
public void reverse() {
	 int newInd = size() -1;
	reverseValues(0, newInd);
}

private boolean reverseValues(int oldInd, int newInd) {
	
	if(oldInd == newInd || oldInd > newInd) {
		return true;
	}
	T tmpObj = get(newInd);
	set(newInd, get(oldInd));
	set(oldInd, tmpObj);
	
	return reverseValues(oldInd + 1, newInd - 1);
}
	@Override
	public void add(T obj) {
		T[] array = null;
		Node<T> newNode = new Node<>(obj);
		addNodeTail(newNode);

	}

	@Override
	public boolean add(int index, T obj) {
		T[] array = null;
		boolean res = true;
		Node<T> newNode = new Node<>(obj);

		if (index == 0) {
			addNodeHead(newNode);
		} else if (index == size) {
			addNodeTail(newNode);
		} else if (isValidIndex(index)) {
			Node<T> beforeNode = getNode(index);
			addNodeMiddle(newNode, beforeNode);
		} else {
			res = false;
		}
		return res;

	}

	private void addNodeMiddle(Node<T> newNode, Node<T> beforeNode) {
		newNode.next = beforeNode;
		newNode.prev = beforeNode.prev;
		beforeNode.prev.next = newNode;
		beforeNode.prev = newNode;
		size++;

	}

	private void addNodeHead(Node<T> newNode) {
		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		size++;

	}

	private void addNodeTail(Node<T> newNode) {
		if (head == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		size++;

	}

	private Node<T> getNode(int ind) {
		return ind < size / 2 ? getFromLeft(ind) : getFromRight(ind);
	}

	@Override
	public int binarySearch(T pattern) {
		if(this.array == null) {
			sort();
			
		}
		int result= this.array.binarySearch(pattern);
		
		return result;
	}

	@Override
	public int binarySearch(T pattern, Comparator<T> comp) {
		if(this.array == null) {
			sort(comp);
			
		}
		int result= this.array.binarySearch(pattern, comp);
		
		return result;
	}

	@Override
	public IndexedList<T> filter(Predicate<T> predicate) {
		IndexedList<T> filteredList = new IndexedLinkedList<>();
		T res = null;
		ListIterator it = new ListIterator();
		while (it.hasNext()) {
			res = it.next();
			if (predicate.test(res)) {
				filteredList.add(res);
			}
		}
		return filteredList;
	}

	@Override
	public T get(int ind) {
		T res = null;
		if (isValidIndex(ind)) {
			Node<T> nodeRes = getNode(ind);
			res = nodeRes.obj;
		}
		return res;
	}

	private Node<T> getFromRight(int ind) {
		Node<T> current = tail;
		for (int i = size - 1; i > ind; i--) {
			current = current.prev;
		}
		return current;
	}

	private Node<T> getFromLeft(int ind) {

		Node<T> current = head;
		for (int i = 0; i < ind; i++) {
			current = current.next;
		}
		return current;
	}

	private boolean isValidIndex(int ind) {
		return ind >= 0 && ind < size;
	}

	@Override
	public int indexOf(Object pattern) {

		// Variant 1 *******************
		if (pattern == null) {
			return -1;
		}
		int index = 0;
		Node<T> current = head;
		while (index < size && !pattern.equals(current.obj)) {
			index++;
			current = current.next;
		}
		return index < size ? index : -1;

		// Variant 2 *******************

//		int res = -1;
//		if (pattern != null) {
//			Node<T> current = head;
//			for (int i = 0; i < size; i++) {
//				current = current.next;
//				if (pattern.equals(current)) {
//					res = i;
//					break;
//				}
//			}
//		}
//		return res;
	}

	@Override
	public int lastIndexOf(Object pattern) {

		// Variant 1 *******************
		if (pattern == null) {
			return -1;
		}
		int index = size - 1;
		Node<T> current = tail;
		while (index >= index && !pattern.equals(current.obj)) {
			index++;
			current = current.prev;
		}
		return index < size ? index : -1;

		// Variant 2 *******************

//		int res = -1;
//		if (pattern != null) {
//			Node<T> current = tail;
//			for (int i = size - 1; i >= 0; i--) {
//				current = current.prev;
//				if (pattern.equals(current)) {
//					res = i;
//					break;
//				}
//			}
//		}
//		return res;
	}

	@Override
	public Object remove(int ind) {
		Object res = null;
		if (isValidIndex(ind)) {
			Node<T> removedNode = getNode(ind);
			res = removedNode.obj;
			removeNode(removedNode);
		}
		return res;
	}

	private void removeNode(Node<T> removedNode) {
		if (removedNode == head) {
			removeHead();
		} else if (removedNode == tail) {
			removeTail();
		} else {
			removeNodeMiddle(removedNode);
		}
	}

	private void removeNodeMiddle(Node<T> removedNode) {
		removedNode.next.prev = removedNode.prev;
		removedNode.prev.next = removedNode.next;
		size--;
	}

	private void removeTail() {
		if (head == tail) {
			head = tail = null;
		} else {
			tail.prev.next = null;
			tail = tail.prev;
		}
		size--;

	}

	private void removeHead() {
		if (head == tail) {
			head = tail = null;
		} else {
			head.next.prev = null;
			head = head.next;
		}
		size--;
	}

	@Override
	public Object remove(Object pattern) {
		// Variant 1 (with two pass over the list)
//		int index = indexOf(pattern);
//		Object res = null;
//		if(index >= 0) {
//			Node<T> node = getNode(index);
//			res = node.obj;
//			removeNode(node);
//		}
		// Variant 2 (with only one pass over the list)
		Node<T> current = head;
		while (current != null && !pattern.equals(current.obj)) {
			current = current.next;
		}
		Object res = null;
		if (current != null) {
			res = current.obj;
			removeNode(current);
		}
		return res;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int original = size;
		T item;
		ListIterator it = new ListIterator();
		while (it.hasNext()) {
			item = it.next();
			if (predicate.test(item)) {
				it.remove();
			}
		}
		size = size();
		return size < original;
	}

	@Override
	public Object set(int ind, T newObj) {
		T res = null;
		if (isValidIndex(ind)) {
			array = null;
			Node<T> nodeRes = getNode(ind);
			nodeRes.obj = newObj;
			res = nodeRes.obj;
		}
		return res;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void sort() {
		Array<T> arrayForSort = new Array<T>();
		T item;
		size = size();
		ListIterator it = new ListIterator();
		for(int i = 0; i <size;  i++) {
			if(it.hasNext()) {
				item = it.next();
				arrayForSort.add(item);
			}
		}
		arrayForSort.sort();
		this.array = arrayForSort;
		for(int i = 0; i <size;  i++) 
		{
				set(i,arrayForSort.get(i));
		}
	}

	@Override
	public void sort(Comparator<T> comp) {
		Array<T> arrayForSort = new Array<T>();
		T item;
		size = size();
		ListIterator it = new ListIterator();
		for(int i = 0; i <size;  i++) {
			if(it.hasNext()) {
				item = it.next();
				arrayForSort.add(item);
			}
		}
		arrayForSort.sort(comp);
		this.array = arrayForSort;
		for(int i = 0; i <size;  i++) 
		{
				set(i,arrayForSort.get(i));
		}
	}

}
