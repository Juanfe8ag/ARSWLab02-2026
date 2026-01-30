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

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1);
    }
    
    public static Control newControl() {
        return new Control();
    }

    public void stopThreads() throws IOException {
        int totalPrimes = 0;
        for(int i = 0;i < NTHREADS;i++ ) {
            try {
                pft[i].wait();
            }catch(InterruptedException e){
                e.getStackTrace();
            }
        }

        for(int i = 0;i < NTHREADS;i++){
            totalPrimes += pft[i].getSize();
        }
        System.out.println("El número de primos encontrados es de " + totalPrimes);
        System.out.println("Presiona ENTER para continuar la busqueda.");
        System.in.read();
    }
    @Override
    public void run() {
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }

        try {
            stopThreads();
        }catch(IOException e){
            e.getStackTrace();
        }
    }
    
}
