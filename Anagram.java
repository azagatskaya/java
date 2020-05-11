package telran.text;

import java.util.Arrays;

public class Anagram {
	public static boolean isAnagram(String word, String anagram) {
		if (!isLengthSame(word, anagram)) {
			return false;
		}

		String[] wordLetters = getLetters(word);
		String[] anagramLetters = getLetters(anagram);
		Arrays.sort(wordLetters);
		Arrays.sort(anagramLetters);
		Boolean res = Arrays.equals(wordLetters, anagramLetters);
		return res;
	}

	private static String[] getLetters(String word) {
		return word.split("");
	}

	private static boolean isLengthSame(String word, String anagram) {
		return word.length() == anagram.length() ? true : false;

	}
}
