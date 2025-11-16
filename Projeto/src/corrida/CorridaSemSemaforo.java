package corrida;

import java.util.concurrent.*;

public class CorridaSemSemaforo {
    static int count = 0; //vai inicar com zero primeiro

    public static void main(String[] args) throws Exception {
        int T = 8, M = 250000;
        ExecutorService pool = Executors.newFixedThreadPool(T);

        Runnable r = () -> {
            for (int i = 0; i < M; i++)
                count++; //condiçao de corrida -> contando
        };

        long t0 = System.nanoTime();
        for (int i = 0; i < T; i++) pool.submit(r);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        long t1 = System.nanoTime();


        double tempoSegundos = (t1 - t0) / 1000000000.0;//aqui eh float(nao mudar), se nao vai dar numero inteiro

        System.out.printf("Esperado=%d, Obtido=%d, Tempo=%.3fs%n", T * M, count, tempoSegundos);

    }
}
