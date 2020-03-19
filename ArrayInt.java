package telran.util;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayInt {
	
/**
 * searches for index of a given number in a given array
 * @param ar - array of numbers
 * @param number - number for search
 * @return index value or -1
 */
	public static int search(int ar[], int number) {
	for(int i = 0; i < ar.length; i++) {
		if (ar[i] == number) {
			return i;
		}
	}
	return -1;
}
	/**
	 * sorting of numbers
	 * @param ar
	 */
	public static void sort(int[] ar) {
		boolean flSort = false;
		int length = ar.length;
		do {
			flSort = true;
			length--;
			for (int i = 0; i < length; i++) {
				if (ar[i] > ar[i + 1]) {
					int tmp = ar[i];
					ar[i] = ar[i + 1];
					ar[i + 1] = tmp;
					flSort = false;
				}
			}
			
			
		}while (!flSort);
	}
	/**
	 * searches for index of a given number in a given sorted array
	 * @param ar - array of numbers
	 * @param number - number for search
	 * @return index value or -1
	 */
	public static int binarySearch(int[] ar, int number) {
		int left = 0;
		int right = ar.length - 1;
		int middle = (left + right) / 2;
		while (left <= right && ar[middle] != number) {
			if (number < ar[middle]) {
				right = middle - 1;
			} else {
				left = middle + 1;
			}
			middle = (left + right) / 2;
		}
		int res = left > right ? -(left + 1) : middle;
		if (res >= 0) {
			//searching for first index of the equaled values
			while(res >= 0 && ar[res] == number) {
				res--;
			}
			res++;
		}
		return res;
	}
	/**
	 * 
	 * @param number
	 * @return amount of elements equaled to number
	 */
	public static int countSorted(int ar[], int number) {
		int ind = binarySearch(ar, number);
		int count = 0;
		if (ind >= 0) {
			while(ind < ar.length && ar[ind] == number) {
				count++;
				ind++;
			}
		}
		return count;
	}
	public static int [] insert (int ar[], int index, int number) {
		int res[] = ar;
		if (index >= 0 && index <= ar.length) {
			res = new int[ar.length + 1];
			System.arraycopy(ar, 0, res, 0, index);
			System.arraycopy(ar, index, res, index + 1, ar.length - index);
			res[index] = number;
		}
		return res;
	}
	public static int[] remove(int[] ar, int index) {
		int res[] = ar;
		if (index >= 0 && index < ar.length) {
			res = new int[ar.length - 1];
			System.arraycopy(ar,0,res,0,index);
			System.arraycopy(ar, index + 1, res, index, res.length - index);
		}
		return res;
	}
	public static int[] insertSorted(int[] ar, int number) {
		int index = binarySearch(ar, number);
		if (index < 0) { 
			index = -index - 1;
		}
		int res[] = insert(ar, index, number);
		return res;
	}
/*	So far the methods union, intersection, difference have complexity O[N^2]. 
	You should rewrite these methods so that complexity will be O[N]. 
	Hint: to apply HashSet from the HW #11, 
	taking into consideration that the method contain of HashSet has complexity O[1]
	/* methods of the HW #3 */
	/**
	 * Assumption: no repeated numbers in each array, but
	 * numbers in first array may be repeated in the second
	 * @param ar1 - first array
	 * @param ar2 - second array
	 * @return array containing numbers of first and second arrays 
	 * with no repetitions 
	 */
	public static int[] union (int ar1[], int ar2[]) {
		int count = 0;
		HashSet<Integer> res = new HashSet<>();
		for (Integer num : ar1) {
			if(res.add(num)) {
				count++;
			}
		}
		for (Integer num : ar2) {
			if(res.add(num)) {
				count++;
			}
		}
		Iterator<Integer> it = res.iterator();
		int[] ar3 = new int[count];
		int i = 0;
			while(it.hasNext()) {
				ar3[i] = it.next();
				i++;
			}
			sort(ar3);
		return ar3;
		
//		int res[] = Arrays.copyOf(ar1, ar1.length + ar2.length);
//		int resInd = ar1.length;
//		Arrays.sort(ar1);
//		for (int i = 0; i < ar2.length; i++) {
//			if (Arrays.binarySearch(ar1, ar2[i]) < 0) {
//				res[resInd++] = ar2[i];
//			}
//		}
//		return Arrays.copyOf(res, resInd);
	}
	/**
	 * Assumption: no repeated numbers in each array, but
	 * numbers in first array may be repeated in the second
	 * @param ar1 - first array
	 * @param ar2 - second array
	 * @return array containing common numbers between first and second arrays 
	 * with no repetitions 
	 */
	public static int[] intersection (int ar1[], int ar2[]) {
		
		HashSet<Integer> tmp = new HashSet<>();
		HashSet<Integer> res = new HashSet<>();
		
		for (Integer num : ar1) {
			if(tmp.add(num)) {
			}
		}
		int count = 0;
		for (Integer num : ar2) {
			if(tmp.contains(num)) {
				res.add(num);
				count++;
			}
		}
		
		Iterator<Integer> it = res.iterator();
		int[] ar3 = new int[count];
		int i = 0;
			while(it.hasNext()) {
				ar3[i] = it.next();
				i++;
			}
			sort(ar3);
		return ar3;
		
//		int res[] = new int[Math.min(ar1.length, ar2.length)];
//		int resInd = 0;
//		for (int i = 0; i < ar1.length; i++) {
//			if (search(ar2, ar1[i]) >= 0) {
//				res[resInd++] = ar1[i];
//			}
//		}
//		return Arrays.copyOf(res, resInd);
	}
	/**
	 * Assumption: no repeated numbers in each array, but
	 * numbers in first array may be repeated in the second
	 * @param ar1 - first array
	 * @param ar2 - second array
	 * @return array containing numbers of first array that are not repeated
	 * 	in the second
	 */
	public static int[] difference (int ar1[], int ar2[]) {
		HashSet<Integer> tmp = new HashSet<>();
		HashSet<Integer> res = new HashSet<>();
		for (Integer num : ar2) {
			if(tmp.add(num)) {
			}
		}
		int count = 0;
		for (Integer num : ar1) {
			if(tmp.contains(num) == false) {
				res.add(num);
				count++;
			}
		}
		Iterator<Integer> it = res.iterator();
		int[] ar3 = new int[count];
		int i = 0;
			while(it.hasNext()) {
				ar3[i] = it.next();
				i++;
			}
			sort(ar3);
		return ar3;
		
//		int[] res = new int[ar1.length];
//		int resInd = 0;
//		for (int i = 0; i < ar1.length; i++) {
//			if (search(ar2, ar1[i]) < 0) {
//				res[resInd++] = ar1[i];
//			}
//			
//		}
//		return Arrays.copyOf(res, resInd);
	}
	
	
	
	
	
	
	
	
}
