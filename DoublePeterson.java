package unam.fc.concurrent.practica3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

class DoublePeterson implements Lock {
    private final Peterson[] locks = new Peterson[4];

    public DoublePeterson() {
        for (int i = 0; i < 4; i++) {
            locks[i] = new Peterson(); // Inicializa 4 candados Peterson
        }
    }

    @Override
    public void lock() {
        int me = ThreadID.get(); // Obtener el ID del hilo
        if (me == 1 || me == 2) {
            locks[0].lock(); // Los hilos 1 y 2 toman lock 0
            locks[1].lock(); // Y luego lock 1
        } else if (me == 3 || me == 4) {
            locks[2].lock();
            locks[3].lock();
        }
    }

    @Override
    public void unlock() {
        int me = ThreadID.get(); // Obtener el ID del hilo
        if (me == 1 || me == 2) {
            locks[1].unlock();
            locks[0].unlock();
        } else if (me == 3 || me == 4) {
            locks[3].unlock();
            locks[2].unlock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'lockInterruptibly'");
    }

    @Override
    public boolean tryLock() {
        throw new UnsupportedOperationException("Unimplemented method 'tryLock'");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'tryLock'");
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("Unimplemented method 'newCondition'");
    }
}
