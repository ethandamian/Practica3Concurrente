package unam.fc.concurrent.practica3;

public class ThreadID {
    private static final ThreadLocal<Integer> threadID = new ThreadLocal<>();

    public static int get() {
        return threadID.get();
    }

    public static void set(int id) {
        threadID.set(id);
    }
}
