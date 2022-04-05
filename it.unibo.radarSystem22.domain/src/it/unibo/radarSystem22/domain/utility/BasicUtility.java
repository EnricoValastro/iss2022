package it.unibo.radarSystem22.domain.utility;

public class BasicUtility {
	
	//Delay func
	public static void delay(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//About threads func
	public static void threadsDetails(String msg)   { 
		String tname    = Thread.currentThread().getName();
		String nThreads = ""+Thread.activeCount() ;
		System.out.println( msg + " curthread=T n=N".replace("T", tname).replace("N", nThreads));
	}
		
}
