package com.market.logic.suggestion.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Nirav Patel
 *
 */
public class SuggestionsBuilder {
	
	private static final Integer MAX_COMBINED_TOKENS = 4;
	private static final String SPACE = " ";

	public List<String> generateSuggestions(String[] tokenStream, String[] stopWords) {
		
		List<String> list = new ArrayList<>();
		//Validate Input Parameters
		if (tokenStream != null && tokenStream.length != 0 && stopWords != null) {
			
			List<String> stopWordList = convertToSortedList(stopWords);
			int nextIndex = 0;
			
			for (int i = 0; i < tokenStream.length; i++) {
				String token = tokenStream[i].trim();
				nextIndex = i;
				int tokenCount = 0;
				StringBuilder sb = new StringBuilder();
				// For each token, prepare suggestion strings until stopToken or single character found
				// or max token limit has reached   
				while (canProceed(stopWordList, token, tokenCount)) {
					if (tokenCount > 0) {
						sb.append(SPACE);
					}
					sb = sb.append(token);
					list.add(sb.toString());
					nextIndex++;
					tokenCount++;
					token = tokenStream[nextIndex].trim();
				}
			}
		}
		return list;
	}

	private List<String> convertToSortedList(String[] stopWords) {
		return Stream.of(stopWords).map(String::toLowerCase).sorted().collect(Collectors.toList());
	}

	private boolean canProceed(List<String> stopWordList, String token, int count) {
		return isValidTokenCountLimit(count) && isMultiCharacterToken(token) && nonStopWord(stopWordList, token);
	}

	private boolean isValidTokenCountLimit(int count) {
		return count < MAX_COMBINED_TOKENS;
	}

	private boolean isMultiCharacterToken(String token) {
		return token.length() > 1;
	}

	private boolean nonStopWord(List<String> stopWordList, String token) {
		return Collections.binarySearch(stopWordList, token.toLowerCase()) < 0;
	}

	public void printAllSuggestions(List<String> suggestionList) {
		for (String suggestion : suggestionList) {
			System.out.println(suggestion);
		}
	}
}
