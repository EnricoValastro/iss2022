package unibo.wenvUsage22.myWalker;

public class V3GuardEndOfWork {
	
	protected static int vn ;
 
	public static void setValue( int n ) {
		vn = n;
	}
	
	public static boolean checkValue(   ) {
		return vn == 4 ;
	}
 	public boolean eval( ) {
 		return checkValue();
 		
	}
}
