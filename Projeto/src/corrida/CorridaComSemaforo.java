package corrida;

import java.util.concurrent.*;

public class CorridaComSemaforo {
    static int count = 0;
    static final Semaphore sem = new Semaphore(1, true); //nao pode mudar

    public static void main(String[] args) throws Exception {

        System.out.println("iniciando execução...");

        //parte pra configurar o experimento da corrida das threads
        int T = 8, M = 250000; //250k incrementos
        //T = numero de threads que vao ser criadas no pool
        // M =  numero de vezes cada thread vai executar o loop e incrementar

        ExecutorService pool = Executors.newFixedThreadPool(T);

        //vai definir o trabalho que cada thread vai fazer
        //happens before
        Runnable r = () -> {
            for (int i = 0; i < M; i++) {
                try {
                    sem.acquire();
                    count++;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    sem.release();
                }
            }
        };

        long t0 = System.nanoTime(); //mede tempo
        for (int i = 0; i < T; i++) pool.submit(r); //inicia a thread

        pool.shutdown();//manda o pool encerrar quando acabar
        boolean terminou = pool.awaitTermination(2, TimeUnit.MINUTES);

        long t1 = System.nanoTime();//tempo final(threads terminarem)

        if (!terminou) {
            System.out.println("as threads não terminaram a tempo!");
        }

        double tempoSegundos = (t1 - t0) / 1000000000.0;//aqui eh float(nao mudar), se nao vai dar numero inteiro

        System.out.printf("Esperado=%d, Obtido=%d, Tempo=%.3fs%n", T * M, count, tempoSegundos);
    }
}
