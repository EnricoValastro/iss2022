package it.unibo.comm2022.interfaces;

public interface IApplMsgHandler {
	 public String getName();
	 public  void elaborate( String message, Interaction conn );
	 public void sendMsgToClient(String message, Interaction conn);
	 public void sendAnswerToClient(String message,Interaction conn);
}
