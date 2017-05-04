package ru.spbau.mit.kravtsun;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryLeakLimitTest {
    private static final int BIG_ARRAY_SIZE = 30000000;
    private static final int SMALL_ARRAY_SIZE = 200000;
    private static final int INTEGER_SIZE = 4;
    private static final int INTEGERS_FOR_MEGABYTE = 1024 * 1024 / INTEGER_SIZE;
    private static final int BIG_ARRAY_MEGABYTES = BIG_ARRAY_SIZE / INTEGERS_FOR_MEGABYTE;
    private int[] smallArray;
    private int[] bigArray;

    @Rule
    public MemoryLeakLimit memoryLeakLimit = new MemoryLeakLimit();

    @Test
    public void smallTest() {
        memoryLeakLimit.limit(1);
        initSmall();
    }

    @Test
    public void bigTest() {
        assertTrue(BIG_ARRAY_MEGABYTES > 0);
        memoryLeakLimit.limit(BIG_ARRAY_MEGABYTES + 1);
        initBig();
    }

    // Expected to fail - didn't find a way to check with ExpectedException.
    @Test
    public void bigFail() {
        memoryLeakLimit.limit(BIG_ARRAY_MEGABYTES - 1);
        initBig();
    }

    // Expected to fail - didn't find a way to check with ExpectedException.
    @Test
    public void smallFail() {
        memoryLeakLimit.limit(0);
        initSmall();
    }

    private void initSmall() {
        smallArray = new int[SMALL_ARRAY_SIZE];
    }

    private void initBig() {
        bigArray = new int[BIG_ARRAY_SIZE];
    }
}
