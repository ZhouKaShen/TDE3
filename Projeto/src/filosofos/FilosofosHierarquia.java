package filosofos;

import java.util.concurrent.Semaphore;

public class FilosofosHierarquia {

    static class Filosofo extends Thread {
        private int id;
        private Semaphore menor, maior;

        public Filosofo(int id, Semaphore left, Semaphore right, int leftId, int rightId) {
            this.id = id;
            if (leftId < rightId) {
                menor = left;
                maior = right;
            } else {
                menor = right;
                maior = left;
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("filosofo " + id + " pensando...");
                    Thread.sleep(200);

                    System.out.println("filosofo " + id + " com fome");
                    menor.acquire();
                    System.out.println("filosofo " + id + " pegou garfo menor");
                    maior.acquire();
                    System.out.println("filosofo " + id + " pegou garfo maior e esta comendo");

                    Thread.sleep(200);

                    maior.release();
                    menor.release();
                    System.out.println("filosofo " + id + " largou os garfos");
                }
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args) {
        int N = 5;
        Semaphore[] garfos = new Semaphore[N];

        for (int i = 0; i < N; i++)
            garfos[i] = new Semaphore(1);

        for (int i = 0; i < N; i++) {
            int leftId = i;
            int rightId = (i + 1) % N;
            new Filosofo(i, garfos[leftId], garfos[rightId], leftId, rightId).start();
        }
    }
}
