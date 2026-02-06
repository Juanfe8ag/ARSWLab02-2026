package edu.eci.arsw.primefinder;

public class ControlLock {
    public boolean paused = false;
    public Object lock = new Object();

    public void pause(){
        paused = true;
    }

    public void resume(){
        paused = false;
    }

    public boolean isPaused(){
        return paused;
    }
}
