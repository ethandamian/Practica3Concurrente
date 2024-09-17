public class CounterNaive {
    private int count = 0;

    public synchronized int increment() {
        return this.count++;
    }

    public int getValue() {
        return this.count;
    }

}