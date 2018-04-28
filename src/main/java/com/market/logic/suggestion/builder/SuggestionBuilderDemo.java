package com.market.logic.suggestion.builder;

import java.util.List;

public class SuggestionBuilderDemo {

	public static void main(String[] args) {
		String[] stopWords = { "girl", "is", "a", "can", "the" };
		String[] tokenStream = { "The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like",
				"chewing", "gum", "." };

		SuggestionsBuilder sb = new SuggestionsBuilder();

		List<String> suggestions = sb.generateSuggestions(tokenStream, stopWords);
		sb.printAllSuggestions(suggestions);
	}

}
