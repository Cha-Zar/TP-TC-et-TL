package com.example.exams;

public final class ExamEvaluator {
    private ExamEvaluator() {}

    /**
     * Evaluate three exam grades and return the total and entitlement.
     * Grades must be 0, 1 or 2.
     */
    public static ExamResult evaluate(int test1, int test2, int test3) {
        validateGrade(test1);
        validateGrade(test2);
        validateGrade(test3);

        if (test1 == 0 || test2 == 0 || test3 == 0) {
            return new ExamResult(0, Entitlement.NONE);
        }

        int sum = test1 + test2 + test3;
        Entitlement ent = (sum > 4) ? Entitlement.FULL : Entitlement.PARTIAL;
        return new ExamResult(sum, ent);
    }

    private static void validateGrade(int g) {
        if (g < 0 || g > 2) {
            throw new IllegalArgumentException("Grade must be 0, 1, or 2: " + g);
        }
    }
}
