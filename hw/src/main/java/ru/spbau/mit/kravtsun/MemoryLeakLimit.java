package ru.spbau.mit.kravtsun;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static java.lang.Runtime.getRuntime;

/**
 * MemoryLeakLimit basically just mimics ExternalResource behavior, but
 * sadly, they had no option for throwing exceptions at after()
 * @see <a href="https://github.com/junit-team/junit4/pull/1421">this pull request</a>
 */
public class MemoryLeakLimit implements TestRule {
    private long memoryLimit = Long.MAX_VALUE;
    private long memoryBefore;

    public void limit(long mb) {
        final long bytesInMegabyte = 1 << 20;
        memoryLimit = mb * bytesInMegabyte;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    private void before() {
        memoryBefore = getCurrentMemory();
    }

    private void after() throws Exception {
        long memoryAfter = getCurrentMemory();
        final long memoryChange = memoryAfter - memoryBefore;

        if (memoryLimit < memoryChange) {
            throw new Exception(errorMessage(memoryChange));
        }
    }

    private static long getCurrentMemory() {
        final Runtime curRuntime = getRuntime();
        curRuntime.gc();
        return curRuntime.maxMemory() - curRuntime.freeMemory();
    }

    private String errorMessage(long memoryChange) {
        return "Memory limit exceeded: (expected)" +
                memoryLimit +
                " bytes  <  (occupied)" +
                memoryChange +
                " bytes.";
    }
}
