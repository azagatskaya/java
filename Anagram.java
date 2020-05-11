package telran.text;

import java.util.HashMap;

public class Anagram {
	public static boolean isAnagram(String word, String anagram) {
		if (!isLengthSame(word, anagram)) {
			return false;
		}
		String wordLetters[] = getLetters(word);
		String anagramLetters[] = getLetters(anagram);
		HashMap<String, Integer> letterCountsWord = getLettersCounts(wordLetters);
		HashMap<String, Integer> letterCountsAnagram = getLettersCounts(anagramLetters);
	  return compareMaps(letterCountsWord, letterCountsAnagram);
	}
	
	private static boolean compareMaps(HashMap<String, Integer> letterCountsWord, 
			HashMap<String, Integer> letterCountsAnagram) {
	    return letterCountsWord.entrySet().containsAll(letterCountsAnagram.entrySet()) 
	    		&& letterCountsAnagram.entrySet().containsAll(letterCountsWord.entrySet());
	  }

	private static String[] getLetters(String word) {
		return word.split("");
	}

	private static HashMap<String, Integer> getLettersCounts(String[] letters) {
		HashMap<String, Integer> mapCounts = new HashMap<>();
		for (String letter : letters) {
			Integer count = mapCounts.getOrDefault(letter, 0);
			mapCounts.put(letter, count + 1);
		}
		return mapCounts;
	}

	private static boolean isLengthSame(String word, String anagram) {
		return word.length() == anagram.length() ? true : false;

	}
}
