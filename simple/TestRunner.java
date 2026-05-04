public class TestRunner {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        runAll();
        System.out.println("\nSummary: passed=" + passed + " failed=" + failed);
        if (failed > 0) System.exit(1);
    }

    private static void runAll() {
        collapsedDecisionTableCases();
    }

    private static void assertEquals(Object exp, Object act, String name) {
        if ((exp == null && act == null) || (exp != null && exp.equals(act))) {
            pass(name);
        } else {
            fail(name + " expected=" + exp + " got=" + act);
        }
    }

    private static void pass(String name) { passed++; System.out.println("PASS: " + name); }
    private static void fail(String msg) { failed++; System.out.println("FAIL: " + msg); }

    private static void collapsedDecisionTableCases() {
        assertResult(0, 1, 1, 0, Entitlement.NONE, "collapsed rule 1");
        assertResult(1, 0, 1, 0, Entitlement.NONE, "collapsed rule 2");
        assertResult(1, 1, 0, 0, Entitlement.NONE, "collapsed rule 3");
        assertResult(1, 1, 1, 3, Entitlement.PARTIAL, "collapsed rule 4");
        assertResult(1, 1, 2, 4, Entitlement.PARTIAL, "collapsed rule 5");
        assertResult(1, 2, 1, 4, Entitlement.PARTIAL, "collapsed rule 6");
        assertResult(1, 2, 2, 5, Entitlement.FULL, "collapsed rule 7");
        assertResult(2, 1, 1, 4, Entitlement.PARTIAL, "collapsed rule 8");
        assertResult(2, 1, 2, 5, Entitlement.FULL, "collapsed rule 9");
        assertResult(2, 2, 1, 5, Entitlement.FULL, "collapsed rule 10");
        assertResult(2, 2, 2, 6, Entitlement.FULL, "collapsed rule 11");
    }

    private static void assertResult(int t1, int t2, int t3, int total,
                                     Entitlement entitlement, String name) {
        ExamResult actual = ExamEvaluator.evaluate(t1, t2, t3);
        assertEquals(total, actual.getTotal(), name + " total");
        assertEquals(entitlement, actual.getEntitlement(), name + " entitlement");
    }
}
