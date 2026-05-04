package com.example.exams;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExamEvaluatorOptTest {


    @ParameterizedTest(name = "optimized rule {index}: ({0},{1},{2}) -> {3}, {4}")
    @CsvSource({
            "0,1,1,0,NONE",
            "1,1,1,3,PARTIAL",
            "1,1,2,4,PARTIAL",
            "1,2,1,4,PARTIAL",
            "1,2,2,5,FULL",
            "2,1,1,4,PARTIAL",
            "2,1,2,5,FULL",
            "2,2,1,5,FULL",
            "2,2,2,6,FULL"
    })
    void nonZeroCombinationsRemainExplicitRules(int a, int b, int c, int total, Entitlement entitlement) {
        assertEquals(new ExamResult(total, entitlement), ExamEvaluator.evaluate(a, b, c));
    }
}
