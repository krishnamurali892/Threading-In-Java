import java.util.ArrayList;

public class ProducerConsumerProblem {

    public static void main(String[] args){
        ArrayList<Integer> sharedList = new ArrayList<Integer>();
        int i = 0;
        Thread producer = new Thread(new Producer(sharedList, i));
        Thread consumer = new Thread(new Consumer(sharedList));
        producer.start();
        consumer.start();
    }
}

class Producer implements Runnable{
    ArrayList<Integer> sharedList;
    int i;

    public Producer(ArrayList<Integer> sharedList, int i){
        this.sharedList = sharedList;
        this.i = i;
    }

    @Override
    public void run() {
        while (true){
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public  void produce() throws InterruptedException {
        synchronized (sharedList){
            if (sharedList != null && sharedList.size() == 0) {
                System.out.println("producing.....");
                while (sharedList.size() < 6) {
                    i = i+1;
                    System.out.println("Produced " + i);
                    sharedList.add(i);
                    Thread.sleep(1000);
                }
            }
            sharedList.notify();
            sharedList.wait();
        }
    }
}

class Consumer implements Runnable{
    ArrayList<Integer> sharedList;

    public Consumer(ArrayList<Integer> sharedList){
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        while (true){
            try {
                while (true) {
                    consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public  void consume() throws InterruptedException {
        synchronized (sharedList){
            if (sharedList != null && sharedList.size() == 6) {
                System.out.println("consuming.....");
                int i = sharedList.size() - 1;
                while (i > -1) {
                    System.out.println("consumed "+ sharedList.remove(i));
                    Thread.sleep(1000);
                    i--;
                }
            }
            sharedList.notify();
            sharedList.wait();
        }
    }

}