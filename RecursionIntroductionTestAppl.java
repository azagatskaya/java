
public class RecursionIntroductionTestAppl {

	public static void main(String[] args) {
		// System.out.println(factorial(6));
		System.out.println(pow(5, 3)); //возведение в степень
		//int ar[] = {10,5,20,15};
		//System.out.println(sum(ar));
		//System.out.println(square(2));
	}

	private static int sum(int i, int[] ar) {
		if(i == ar.length) {
			return 0;
		}
		return ar[i] + sum(i + 1, ar);
	}
	private static long pow(int a, int b) {
		if (b == 0) {
			return 1; 
		} else if (b == 1) {
			return a;
		}
		return pow(a, b - 1, a); 
	}
	private static int pow(int a, int b, int c) {
		int k = c;
		int sum = sumForPow(a,k); 
		if (b == 1) {
			return sum;
		}
		return pow(sum, b - 1, a); 
	}
	private static int sumForPow(int a, int k) {
		if (a == 1) {
			return k;
		}
		return k + sumForPow(a - 1, k);
	}
	
//	private static int pow(int a, int b) {
//		if (b == 0) {
//			return 1;
//		}
//		return a * pow(a, b - 1);
//	}
//	

	private static long factorial(int a) {
		if (a == 0) {
			return 1;
		}
		return a * factorial(a - 1);
	}
	private static int sum(int[]ar) {
		return sum(0, ar);
	}
	private static long square(int x) {
		if(x == 1) {
			return 1;
		}
		return x + square(x-1) + (x-1);
	}
	//1) в индекслинкед лист написать паблик функцию и тест в общий тест
	// реверс линкедлиста используя рекурсию
	// индексет лист тестс 
	//2) write method long pow(int, int)
	//no cycles, no operator *, just + -
	//3) write long square(int x)
	//1. No cycles, 2. just + - operators, 3. No additional functions
}
