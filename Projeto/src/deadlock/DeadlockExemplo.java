package deadlock;

public class DeadlockExemplo {
    static final Object LOCK_A = new Object();
    static final Object LOCK_B = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("T1 pegou A");
                sleep(50);
                System.out.println("T1 tentando pegar B");
                synchronized (LOCK_B) {
                    System.out.println("T1 pegou B");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("T2 pegou B");
                sleep(50);
                System.out.println("T2 tentando pegar A");
                synchronized (LOCK_A) {
                    System.out.println("T2 pegou A");
                }
            }
        });

        t1.start();
        t2.start();
        //execuçao dos codigos t1 e t2
    }

    //metodo sleep(ele vai dar uma espera no terminal) -> DeadlockCorrigido esta importando isso
    //coloquei so por convenção pra deixar mais legal
    static void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) {

        }
    }
}
