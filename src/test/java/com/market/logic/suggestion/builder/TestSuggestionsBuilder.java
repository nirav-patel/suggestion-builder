package com.market.logic.suggestion.builder;

import static org.junit.Assert.assertArrayEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.TestCase;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class TestSuggestionsBuilder extends TestCase {

	SuggestionsBuilder suggestionsBuilder = null;

	@Before
	public void setUp() throws Exception {
		suggestionsBuilder = new SuggestionsBuilder();
	}

	public static final Object[][] getParameters() {
		return new Object[][] { 
			{ getStopWord1(), getTokenStream1(), 3, getExpectedOutputTest1() },
			{ getStopWord1(), getTokenStream1(), 2, getExpectedOutputTest2() },
			{ getStopWord1(), getNullTokenStream(), 2, getExpectedOutputTest3() },
			{ getStopWord2(), getTokenStream2(), 3, getExpectedOutputTest4() } };
	}

	@Parameters(method = "getParameters")
	@Test
	public void test(String[] stopTokens, String[] tokenStream, Integer maxCombinedToken, String[] expected)
			throws Exception {
		// ACT
		setFinalStaticField(SuggestionsBuilder.class, "MAX_COMBINED_TOKENS", maxCombinedToken);
		// ACTION
		List<String> suggestions = suggestionsBuilder.generateSuggestions(tokenStream, stopTokens);
		// ASSERT
		assertArrayEquals(expected, suggestions.toArray());
	}

	private static String[] getStopWord1() {
		String[] stopWords = { "is", "a", "can", "the" };
		return stopWords;
	}

	private static String[] getStopWord2() {
		String[] stopWords = { "the", "is", "a", "day" };
		return stopWords;
	}

	private static String[] getTokenStream1() {
		String[] tokenStream = { "The", "beautiful", "girl", "from", "the", "farmers", "market", ".", "I", "like",
				"chewing", "gum", "." };
		return tokenStream;
	}

	private static String[] getTokenStream2() {
		String[] tokenStream = { "Today", "is", "a", "beautiful", "day", ".", "It", "is", "a", "Sunday", "." };
		return tokenStream;
	}

	private static String[] getNullTokenStream() {
		return null;
	}

	private static String[] getExpectedOutputTest1() {
		String[] expected = { "beautiful", "beautiful girl", "beautiful girl from", "girl", "girl from", "from",
				"farmers", "farmers market", "market", "like", "like chewing", "like chewing gum", "chewing",
				"chewing gum", "gum" };
		return expected;
	}

	private static String[] getExpectedOutputTest2() {
		String[] expected = { "beautiful", "beautiful girl", "girl", "girl from", "from", "farmers", "farmers market",
				"market", "like", "like chewing", "chewing", "chewing gum", "gum" };
		return expected;
	}

	private static String[] getExpectedOutputTest3() {
		return new String[0];
	}

	private static String[] getExpectedOutputTest4() {
		String[] expected = { "Today", "beautiful", "It", "Sunday" };
		return expected;
	}

	private static void setFinalStaticField(Class<?> clazz, String fieldName, Object value)
			throws ReflectiveOperationException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, value);
	}

}
