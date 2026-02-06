package co.eci.snake.concurrency;

public class SnakeControl {

    private volatile boolean paused = false;
    private final Object lock = new Object();

    public void pause() {
        synchronized (lock) {
            paused = true;
        }
    }

    public void resume() {
        synchronized (lock) {
            paused = false;
            lock.notifyAll();
        }
    }

    public void awaitIfPaused() throws InterruptedException {
        synchronized (lock) {
            while (paused) {
                lock.wait();
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }
}
