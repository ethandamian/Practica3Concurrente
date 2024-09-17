public class BakeryImpl {
    private final int THREADS = 4;
    private boolean[] flag;
    private int[] label;

    private volatile int id;

    public BakeryImpl(int id) {
        this.id = id;
        this.flag = new boolean[THREADS];
        this.label = new int[THREADS];
    }

    public int maxLabel() {
        int max = 0;
        for (int i : label) {
            if (i > max) {
                max = i;
            }
        }

        return max;
    }

    public void lock() {
        flag[id] = true;
        label[id] = maxLabel() + 1;
        flag[id] = false;

        for (int i = 0; i < THREADS; i++) {
            if (i == id) {
                continue;
            }

            while (flag[i]) {
                // wait
            }

            // wait until all threads have a label
            while (label[i] != 0 && (label[i] < label[id] || (label[i] == label[id] && i < id))) {
                // wait
            }
        }
    }

    public void unlock() {
        label[id] = 0;
    }
}
