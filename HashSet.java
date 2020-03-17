package teltan.util;

import java.util.Iterator;
import java.util.function.Predicate;

import telran.util.IndexedLinkedList;
import telran.util.IndexedList;
import telran.util.Set;
@SuppressWarnings("unchecked")
public class HashSet<T> implements Set<T> {

private static final float FACTOR = 0.75f;
IndexedList<T>[] hashTable;

int size;
public HashSet(int initialSize) {
	hashTable = new IndexedList[initialSize];
	
}
public HashSet() {
	this(16);
}
public class HashSetIterator<T> implements Iterator<T> 
{ 
int index=0;
		IndexedList<T> current=hashTable[index];
		Iterator<T> iter=current.iterator();
		
		@Override
		public boolean hasNext() 
		{
			return (iter.hasNext()) ? true : false;
		}
		@Override
		public T next()
		{ if(iter.hasNext())
		{
			return iter.next();
		}
		else 
		{   index++;
			for(int i=index; i<hashTable.length; i++ )
		{
			if(hashTable[i]!=null&&hashTable[i].size()>0) 
		{
			current=hashTable[i];
			iter=current.iterator();
			index=i;
			break;
			
		}
			
			
		}
		return next();		
			
		}
			
			
		}
		@Override
		public void remove() {iter.remove();}
	
}
@Override
public Iterator<T> iterator() {
	return new HashSetIterator();

	
}


	@Override
	public boolean add(T obj) {
		if(contains(obj))
			return false;
		size++;
		if (size > FACTOR * hashTable.length) {
			recreateHashTable();
		}
		int index = getHashTableIndex(obj);
		if(hashTable[index] == null) {
			hashTable[index] = new IndexedLinkedList<T>();
		}
		hashTable[index].add(obj);
	return true;	

	}

	private void recreateHashTable() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2);
		for(IndexedList<T> list: hashTable) {
			if (list != null) {
				for(T obj: list) {
					tmp.add(obj);
				}
			}
		}
		hashTable = tmp.hashTable;
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
		return size;
	}

	@Override
	public boolean contains(T pattern) {
		int index = getHashTableIndex(pattern);
		return hashTable[index] != null &&
				hashTable[index].indexOf(pattern) >= 0;
	}

	private int getHashTableIndex(T pattern) {
		int hashCode = pattern.hashCode();
		int index = Math.abs(hashCode) % hashTable.length;
		return index;
	}

}
