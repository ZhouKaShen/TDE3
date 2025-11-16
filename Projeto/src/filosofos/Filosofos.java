package filosofos;

import java.util.concurrent.Semaphore; //classe semapohre serve pra controla
                                        //qantas threads pode acessa um recurso ao mesmo tempo

public class Filosofos {

    static class Filosofo extends Thread {
        private int id;
        private Semaphore esquerda, direita; //mesma coisa que separar os atributos, so resumi pra deixar mais facil

        public Filosofo(int id, Semaphore esquerda, Semaphore direita) {
            this.id = id;
            this.esquerda = esquerda;
            this.direita = direita;
        }

        //ele vai sobrescrever aq, entao fica atento
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("filosofo " + id + " pensando...");
                    Thread.sleep(200);

                    System.out.println("filosofo " + id + " com fome, tentando pegar garfo esquerdo");
                    esquerda.acquire(); //se dar nisso vai dar deadlock
                    System.out.println("filosofo " + id + " pegou esquerda, tentando pegar direita");

                    direita.acquire();
                    System.out.println("filosofo " + id + " comendo...");

                    Thread.sleep(200);

                    direita.release();
                    esquerda.release();
                    System.out.println("filosofo " + id + " largou garfos");
                }
            } catch (Exception e) {}
        }
    }


    public static void main(String[] args) {
        int N = 5;
        //defini os filosofos aq(entao 5 garfos) -> 1 filosofo = 1 garfo
        Semaphore[] garfos = new Semaphore[N]; //guarda 5 garfos

        for (int i = 0; i < N; i++)
            garfos[i] = new Semaphore(1); //semaphore(permits: quantidade permitida de garfo)
                                                //quantidade permitida de garfo

        for (int i = 0; i < N; i++) {
            Semaphore esquerda = garfos[i]; //nao tem volta circular

            //na variavel direita ela soma primeiro(i+1) e depois faz o resto com o N que eh o numero de garfos
            Semaphore direita = garfos[(i + 1) % N]; //tem volta circular

            new Filosofo(i, esquerda, direita).start(); //inicia a thread do filosofo
        }
    }
}