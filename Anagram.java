package telran.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

	private static String[] makeArrayOfLetters(String word) {
		String[] lettersList = new String[word.length()];
		for (int i = 0; i < word.length(); i++) {
			char letterChar = word.charAt(i);
			String letter = String.valueOf(letterChar);
			lettersList[i] = letter;
		}
		return lettersList;
		}
	private static boolean isLengthSame(String word, String anagram) {
		return word.length() == anagram.length() ? true : false;

	}
}
