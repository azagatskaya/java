package telran.util.memory;

import java.math.BigInteger;

public class MemoryService {
	public static int getAvailableMemoryBlockSize() {
		long size = Integer.MAX_VALUE;
		long middleSize = 0;
		long leftSize = size;
		long rightSize = 0;
		
		while (true) {
			try {
				byte[] ar = new byte[(int) size];
				if(middleSize != 0 && leftSize > rightSize) {
					ar = null;
					rightSize = middleSize;
					middleSize = (leftSize + rightSize) / 2;
					size = middleSize;
					ar = new byte[(int) size];
				}
				return (int) size;
			} catch (OutOfMemoryError e) {
				if (middleSize != 0 && leftSize > rightSize) {
					
					leftSize = middleSize;
					middleSize = (leftSize + rightSize) / 2;
					size = middleSize;

				} else if (middleSize == 0) {
					middleSize = (leftSize + rightSize) / 2;
					size = middleSize;
				}
			}
//			try {
//				byte[] ar = new byte[size];
//				return size;
//			} catch (OutOfMemoryError e) {
//				size = size/2;
//			} 
		}
	}
}
