public final class ExamEvaluator {
    private ExamEvaluator() {}

    public static ExamResult evaluate(int t1, int t2, int t3) {
        validate(t1);
        validate(t2);
        validate(t3);

        if (t1 == 0 || t2 == 0 || t3 == 0) {
            return new ExamResult(0, Entitlement.NONE);
        }

        int sum = t1 + t2 + t3;
        Entitlement e = (sum > 4) ? Entitlement.FULL : Entitlement.PARTIAL;
        return new ExamResult(sum, e);
    }

    private static void validate(int g) {
        if (g < 0 || g > 2) throw new IllegalArgumentException("Grade must be 0,1,2: " + g);
    }
}
