package com.example.exams;

import java.util.Objects;

public final class ExamResult {
    private final int total;
    private final Entitlement entitlement;

    public ExamResult(int total, Entitlement entitlement) {
        this.total = total;
        this.entitlement = Objects.requireNonNull(entitlement);
    }

    public int getTotal() {
        return total;
    }

    public Entitlement getEntitlement() {
        return entitlement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamResult that = (ExamResult) o;
        return total == that.total && entitlement == that.entitlement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, entitlement);
    }

    @Override
    public String toString() {
        return "ExamResult{" + "total=" + total + ", entitlement=" + entitlement + '}';
    }
}
