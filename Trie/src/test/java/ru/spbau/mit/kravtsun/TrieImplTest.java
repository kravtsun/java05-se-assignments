package ru.spbau.mit.kravtsun;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class TrieImplTest {
    private final static int MAX_STRING_SIZE = 100;
    private final static int TESTS_COUNT = 10000;

    private final static Random randomizer;
    private final static String random_symbols;

    static {
        randomizer = new Random(1123834521);

        StringBuilder sb = new StringBuilder();
        for (char c = 'a'; c <= 'z'; ++c) {
            sb.append(c);
        }

        for (char c = 'A'; c <= 'Z'; ++c) {
            sb.append(c);
        }

        random_symbols = sb.toString();
    }

    private static String randomString() {
        int size = 1 + randomizer.nextInt(MAX_STRING_SIZE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int nextCharPosition = randomizer.nextInt(random_symbols.length());
            char nextSymbol = random_symbols.charAt(nextCharPosition);
            sb.append(nextSymbol);
        }
        return sb.toString();
    }

    @Test
    public void testSimple() {
        TrieImpl TrieImpl = new TrieImpl();

        assertTrue(TrieImpl.add("abc"));
        assertTrue(TrieImpl.contains("abc"));
        assertEquals(1, TrieImpl.size());
        assertEquals(1, TrieImpl.howManyStartsWithPrefix("abc"));
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
            assertTrue(trie.size() == alreadyAdded.size());
            if (alreadyAdded.contains(newString)) {
                assertFalse(trie.add(newString));
                assertTrue(trie.size() == alreadyAdded.size());
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
            assertTrue(alreadyAdded.contains(s) == trie.contains(s));
        }
    }

    @Test
    public void trieSpecificCases() {
        TrieImpl trie = new TrieImpl();
        String newString = randomString();
        assertTrue(trie.size() == 0);
        assertTrue(trie.add(newString));
        assertTrue(trie.size() == 1);
        assertFalse(trie.add(newString));
        assertTrue(trie.size() == 1);
        String prefix = newString.substring(0, newString.length()-1);
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
        assertTrue(trie.size() == 1);
        assertFalse(trie.add(newString));
        assertTrue(trie.size() == 1);
        String suffix = newString.substring(1), prefix = newString.substring(0, newString.length()-1);
        assertFalse(trie.contains(suffix));
        assertFalse(trie.contains(prefix));
        assert(trie.size() == 1);
        assertFalse(trie.remove(suffix));
        assertFalse(trie.remove(prefix));
        assertTrue(trie.remove(newString));
        assertTrue(trie.size() == 0);
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
        String prefix = newString.substring(0, newString.length()-1);

        int prefixTestCount = randomizer.nextInt(10);
        for (int i = 10; i < prefixTestCount; ++i) {
            String checkPrefix = prefix.substring(0, randomizer.nextInt(prefix.length()));
            assertTrue(trie.howManyStartsWithPrefix(checkPrefix) == 1);
        }

        assert(trie.size() == 1);
        assertFalse(trie.remove(suffix));
        assertFalse(trie.remove(prefix));
        assertTrue(trie.remove(newString));
        assertTrue(trie.size() == 0);
    }
}