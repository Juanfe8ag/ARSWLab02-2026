/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.io.IOException;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;
    public final ControlLock lock =  new ControlLock();

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, lock);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, lock);
    }
    
    public static Control newControl() {
        return new Control();
    }

    public void countPrimes() {
        int totalPrimes = 0;
        for(int i = 0;i < NTHREADS;i++){
            totalPrimes += pft[i].getSize();
        }
        System.out.println("El número de primos encontrados es de " + totalPrimes);
        System.out.println("Presiona ENTER para continuar la busqueda.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean areAlive(){
        for (int i = 0;i < NTHREADS;i++){
            if (pft[i].isAlive()){
                return true;
            }
        }
        return false;
    }

    @Override
    public void run() {
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        try{
            while (areAlive()) {
                Thread.sleep(TMILISECONDS);

                synchronized (lock) {
                    lock.pause();
                }

                countPrimes();

                synchronized (lock) {
                    lock.resume();
                    lock.notifyAll();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
