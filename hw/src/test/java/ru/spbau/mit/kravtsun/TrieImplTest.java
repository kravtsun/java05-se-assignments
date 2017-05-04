package ru.spbau.mit.kravtsun;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class TrieImplTest {
    private static final int MAX_STRING_SIZE = 100;
    private static final int TESTS_COUNT = 10000;

    private static final Random RANDOMIZER;
    private static final String RANDOM_SYMBOLS;

    static {
        RANDOMIZER = new Random(1123834521);

        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; ++c) {
            sb.append(c);
        }

        for (char c = 'A'; c <= 'Z'; ++c) {
            sb.append(c);
        }

        RANDOM_SYMBOLS = sb.toString();
    }

    private static String randomString() {
        int size = 1 + RANDOMIZER.nextInt(MAX_STRING_SIZE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int nextCharPosition = RANDOMIZER.nextInt(RANDOM_SYMBOLS.length());
            char nextSymbol = RANDOM_SYMBOLS.charAt(nextCharPosition);
            sb.append(nextSymbol);
        }
        return sb.toString();
    }

    @Test
    public void testSimple() {
        TrieImpl trieImpl = new TrieImpl();

        assertTrue(trieImpl.add("abc"));
        assertTrue(trieImpl.contains("abc"));
        assertEquals(1, trieImpl.size());
        assertEquals(1, trieImpl.howManyStartsWithPrefix("abc"));
    }
//
//    public static TrieImpl instance() {
//        try {
//            return (TrieImpl) Class.forName("TrieImpl").newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        throw new IllegalStateException("Error while class loading");
//    }

    @Test
    public void myTest() {
        TrieImpl trie = new TrieImpl();

        ArrayList<String> alreadyAdded = new ArrayList<>();
        for (int i = 0; i < TESTS_COUNT; i++) {
            String newString = randomString();
            assertEquals(trie.size(), alreadyAdded.size());
            if (alreadyAdded.contains(newString)) {
                assertFalse(trie.add(newString));
                assertEquals(trie.size(), alreadyAdded.size());
            } else {
                assertTrue(trie.add(newString));
                alreadyAdded.add(newString);
            }
        }

        for (String s : alreadyAdded) {
            assertFalse(trie.add(s));
            assertTrue(trie.contains(s));
        }

        for (int i = 0; i < TESTS_COUNT; i++) {
            String s = randomString();
            assertEquals(alreadyAdded.contains(s), trie.contains(s));
        }
    }

    @Test
    public void trieSpecificCases() {
        TrieImpl trie = new TrieImpl();
        String newString = randomString();
        assertEquals(trie.size(), 0);
        assertTrue(trie.add(newString));
        assertEquals(trie.size(), 1);
        assertFalse(trie.add(newString));
        assertEquals(trie.size(), 1);
        String prefix = newString.substring(0, newString.length() - 1);
        assertTrue(trie.add(prefix));
        assertTrue(trie.remove(newString));
        assertTrue(trie.contains(prefix) && !trie.contains(newString));
    }

    @Test
    public void typicalCase1() {
        TrieImpl trie = new TrieImpl();
        String newString = randomString();
        assertTrue(trie.add(newString));
        assertTrue(trie.contains(newString));
        assertEquals(trie.size(), 1);
        assertFalse(trie.add(newString));
        assertEquals(trie.size(), 1);
        String suffix = newString.substring(1);
        String prefix = newString.substring(0, newString.length() - 1);
        assertFalse(trie.contains(suffix));
        assertFalse(trie.contains(prefix));
        assertEquals(trie.size(), 1);
        assertFalse(trie.remove(suffix));
        assertFalse(trie.remove(prefix));
        assertTrue(trie.remove(newString));
        assertEquals(trie.size(), 0);
        assertFalse(trie.contains(suffix));
        assertFalse(trie.contains(prefix));
        assertFalse(trie.contains(newString));
        assertFalse(trie.remove(newString));
        assertFalse(trie.contains(suffix));
        assertFalse(trie.contains(prefix));
        assertFalse(trie.contains(newString));
    }

    @Test
    public void typicalCase2() {
        TrieImpl trie = new TrieImpl();
        String newString = randomString();
        assertTrue(trie.add(newString));
        assertFalse(trie.add(newString));

        String suffix = newString.substring(1);
        String prefix = newString.substring(0, newString.length() - 1);

        int prefixTestCount = RANDOMIZER.nextInt(10);
        for (int i = 10; i < prefixTestCount; ++i) {
            String checkPrefix = prefix.substring(0, RANDOMIZER.nextInt(prefix.length()));
            assertEquals(trie.howManyStartsWithPrefix(checkPrefix), 1);
        }

        assertEquals(trie.size(), 1);
        assertFalse(trie.remove(suffix));
        assertFalse(trie.remove(prefix));
        assertTrue(trie.remove(newString));
        assertEquals(trie.size(), 0);
    }
}
