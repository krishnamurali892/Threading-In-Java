import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerByBlockingQueue {

    public static void main(String[] args){
        int value = 0;
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        Thread producer = new Thread(new ProducerBlockingQueue(queue, value));
        Thread consumer = new Thread(new ConsumerBlockingQueue(queue, value));
        producer.start();
        consumer.start();
    }
}

class ProducerBlockingQueue implements Runnable{

    int value;
    BlockingQueue<Integer> queue;

    public ProducerBlockingQueue(BlockingQueue<Integer> queue, int value){
        this.value = value;
        this.queue = queue;

    }
    @Override
    public void run() {
        while (true){
            try {
                value = value +1;
                queue.put(value);
                System.out.println("Produced "+value);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


class ConsumerBlockingQueue implements Runnable{

    int value;
    BlockingQueue<Integer> queue;

    public ConsumerBlockingQueue(BlockingQueue<Integer> queue, int value){
        this.value = value;
        this.queue = queue;

    }
    @Override
    public void run() {
        while (true){
            try {
                int value = queue.take();
                System.out.println("Consumed "+value);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
