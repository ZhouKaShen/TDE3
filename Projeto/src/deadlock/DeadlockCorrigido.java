package deadlock;

import static deadlock.DeadlockExemplo.sleep;//importaçao do metodo no DeadlockExemplo

public class DeadlockCorrigido {
    static final Object LOCK_A = new Object();
    static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> usarEmOrdem());
        Thread t2 = new Thread(() -> usarEmOrdem());

        t1.start();
        t2.start();
        //execução do codigo
    }

    static void usarEmOrdem() {
        synchronized (LOCK_A) {
            System.out.println(Thread.currentThread().getName() + " pegou a");
            sleep(50);

            synchronized (LOCK_B) {
                System.out.println(Thread.currentThread().getName() + " pegou b");
            }
        }
    }
}
