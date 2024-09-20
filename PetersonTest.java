package unam.fc.concurrent.practica3;

import java.util.concurrent.*;

public class PetersonTest {
    private static final int TASK_COUNT = 400;
    private static int counter = 0; // Se cambio el counter (atomic) que teniamos a un integer
    private static final DoublePeterson lock = new DoublePeterson();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Crear un arreglo para almacenar los resultados de cada hilo
        Future<Integer>[] futures = new Future[4];

        Callable<Integer> task = () -> {
            int me = (int) (Thread.currentThread().getId() % 4) + 1; // Asignar ID de hilo entre 1 y 4
            ThreadID.set(me);
            int localCount = 0;
            for (int i = 0; i < TASK_COUNT / 4; i++) {
                lock.lock();
                try {
                    counter++; // Incrementar el contador compartido dentro de la sección crítica
                    localCount++;
                    System.out.println("Thread " + me + " incremented counter to " + counter);
                } finally {
                    lock.unlock();
                }
            }
            return localCount; // Devolver el número de incrementos realizados por el hilo
        };

        // Ejecutar las tareas
        for (int i = 0; i < 4; i++) {
            futures[i] = executor.submit(task);
        }

        int[] incrementsPerThread = new int[4];
        for (int i = 0; i < 4; i++) {
            incrementsPerThread[i] = futures[i].get();
        }

        executor.shutdown();

        // Imprimir los resultados
        System.out.println("Total increments: " + counter);
        for (int i = 0; i < 4; i++) {
            System.out.println("Thread " + (i + 1) + " made " + incrementsPerThread[i] + " increments.");
        }
    }
}
