package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	
	private List<Integer> primes;

    public ControlLock lock;
	
	public PrimeFinderThread(int a, int b, ControlLock lock) {
		super();
                this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
        this.lock = lock;
	}

        @Override
	public void run(){
        for (int i= a;i < b;i++){
            synchronized (lock) {
                while (lock.isPaused()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            if (isPrime(i)){
                primes.add(i);
                System.out.println(i);
            }
        }
	}
	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

    public int getSize() { return primes.size(); }
	
}
