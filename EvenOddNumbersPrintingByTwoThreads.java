public class EvenOddNumbersPrintingByTwoThreads {
    public static void main(String[] args) {
        Lock lock = new Lock();
        Thread evenThread = new Thread(new EvenThread(lock));
        Thread oddThread = new Thread(new OddThread(lock));
        evenThread.start();
        oddThread.start();
    }
}
    class Lock {
        int num = 0;
    }

    class EvenThread implements Runnable{
        Lock lock;

        public EvenThread(Lock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                try {
                    printEven();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void printEven() throws InterruptedException {
            // note lock is not working on Wrapper objects ex: Integer Long check why
            synchronized (lock) {
                if (lock.num % 2 == 0) {
                    System.out.println("EvenThread " + lock.num);
                    lock.num++;
                    Thread.sleep(1000);
                }
                lock.notify();
                lock.wait();
            }
        }
    }
    
    class OddThread implements Runnable{
        Lock lock;

        public OddThread(Lock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                try {
                    printOdd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void printOdd() throws InterruptedException {
            synchronized (lock) {
                if (lock.num % 2 != 0) {
                    System.out.println("OddThread " + lock.num);
                    lock.num++;
                    Thread.sleep(1000);
                }
                lock.notify();
                lock.wait();
            }
        }
    }
