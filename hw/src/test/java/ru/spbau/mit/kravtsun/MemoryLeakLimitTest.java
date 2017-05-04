package ru.spbau.mit.kravtsun;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.*;

public class MemoryLeakLimitTest {
    private static final int BIG_ARRAY_SIZE = 30000000;
    private static final int SMALL_ARRAY_SIZE = 100000;
    private static final int INTEGER_SIZE = 4;
    private static final int INTEGERS_FOR_MEGABYTE = 1024 * 1024 / INTEGER_SIZE;
    private static final int BIG_ARRAY_MEGABYTES = BIG_ARRAY_SIZE / INTEGERS_FOR_MEGABYTE;
    private int[] smallArray;
    private int[] bigArray;

    @Rule
    public MemoryLeakLimit memoryLeakLimit = new MemoryLeakLimit();

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    @After
    public void useArrays() {
        useSmall();
        useBig();
    }

    // Expected to fail - didn't find a way to check with ExpectedException.
    @Test(expected = Exception.class)
    public void bigFail() {
        memoryLeakLimit.limit(BIG_ARRAY_MEGABYTES - 1);
        initBig();
        exception.expect(Exception.class);
    }

    // Expected to fail - didn't find a way to check with ExpectedException.
    @Test(expected = Exception.class)
    public void smallFail() {
        memoryLeakLimit.limit(0);
        initSmall();
        exception.expect(Exception.class);
    }

    private void initSmall() {
        smallArray = new int[SMALL_ARRAY_SIZE];
    }

    private void initBig() {
        bigArray = new int[BIG_ARRAY_SIZE];
    }

    private void useSmall() {
        if (!Objects.isNull(smallArray)) {
            smallArray[0] = 1;
            smallArray[SMALL_ARRAY_SIZE-1] = -1;
        }
    }

    private void useBig() {
        if (!Objects.isNull(bigArray)) {
            Random randomizer = new Random();
            final int N = randomizer.nextInt(100);
            for (int i = 0; i < N; ++i) {
                int nextIndex = randomizer.nextInt(BIG_ARRAY_SIZE);
                bigArray[nextIndex] = i;
            }
        }
    }
}