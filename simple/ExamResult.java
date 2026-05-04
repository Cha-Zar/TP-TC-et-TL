public final class ExamResult {
    private final int total;
    private final Entitlement entitlement;

    public ExamResult(int total, Entitlement entitlement) {
        this.total = total;
        this.entitlement = entitlement;
    }

    public int getTotal() { return total; }
    public Entitlement getEntitlement() { return entitlement; }

    @Override
    public String toString() {
        return "ExamResult(total=" + total + ", entitlement=" + entitlement + ")";
    }
}
