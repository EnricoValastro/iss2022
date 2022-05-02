package it.unibo.radarSystem22_s4.comm.utils;

public class BasicUtils {


    public static void showSystemInfo(){

        System.out.println(
                "COMPUTER | memory="+ Runtime.getRuntime().totalMemory() +
                        " num of processors=" +  Runtime.getRuntime().availableProcessors());
        System.out.println(
                "AT START | num of threads="+ Thread.activeCount() +" currentThread=" + Thread.currentThread() );
    }


    public static void delay(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitTheUser() {
        try {
            System.out.println("Please hit to restart ");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void aboutThreads(String msg)   {
        String tname    = Thread.currentThread().getName();
        String nThreads = ""+Thread.activeCount() ;
        System.out.println( msg + " curthread=T n=N".replace("T", tname).replace("N", nThreads));
    }


}
