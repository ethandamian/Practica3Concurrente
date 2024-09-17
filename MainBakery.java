import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainBakery {
    private static final int THREADS = 4;
    private static volatile CounterNaive counter = new CounterNaive();
    static int[] threadTaskCount = new int[THREADS];

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        BakeryImpl[] bakery = new BakeryImpl[THREADS];
        for (int i = 0; i < THREADS; i++) {
            bakery[i] = new BakeryImpl(i);
        }

        for (int i = 0; i < 400; i++) {
            int threadId = i % THREADS;
            executor.execute(() -> {
                bakery[threadId].lock();
                try {

                    counter.increment();
                    synchronized (threadTaskCount) {
                        threadTaskCount[threadId]++; // Incrementar el contador del hilo
                    }
                    System.out.println("Thread " + threadId + " - " + counter.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    bakery[threadId].unlock();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }

        // Imprimir el número de veces que cada hilo ha incrementado el contador
        for (int i = 0; i < THREADS; i++) {
            System.out.println("El hilo " + i + " incrementó el contador " +
                    threadTaskCount[i] + " veces.");
        }

        System.out.println("Valor final del contador: " + counter.getValue());
    }

}
