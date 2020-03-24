package telran.util;

public interface SortedSet<T> extends Set<T>{
T getMin();	//minimal element
T getMax();	//maximal element
SortedSet<T> subset(T from, boolean isIncludedFrom, T to, boolean isIncludedTo); //range either open or close range
 //all element that either strong less/equal than key
default SortedSet<T> head(T key, boolean isIncluded) {
	SortedSet<T> headSet;  
	headSet = subset(getMin(),true,key,isIncluded);
	return headSet;
}
  //> or =; all elements that either strong greater or greater/equal than key
default SortedSet<T> tail(T key, boolean isIncluded) {
	SortedSet<T> tailSet;
	tailSet = subset(key,isIncluded,getMax(),true);
	return tailSet;
}
}
