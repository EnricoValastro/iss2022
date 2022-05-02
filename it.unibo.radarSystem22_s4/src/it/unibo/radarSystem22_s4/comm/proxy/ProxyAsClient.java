package it.unibo.radarSystem22_s4.comm.proxy;

import it.unibo.radarSystem22_s4.comm.ProtocolType;
import it.unibo.radarSystem22_s4.comm.interfaces.Interaction2021;
import it.unibo.radarSystem22_s4.comm.tcp.TcpClientSupport;

public class ProxyAsClient {

    private Interaction2021 conn;
    protected String name ;
    protected ProtocolType protocol ;

    public ProxyAsClient( String name, String host, String entry, ProtocolType protocol ) {
        try {
            System.out.println(name+"   |   CREATING entry= "+entry+" protocol=" + protocol);
            this.name     = name;
            this.protocol = protocol;
            setConnection(host,  entry,  protocol);
            System.out.println(name+"   |   CREATED entry= "+entry+" conn=" + conn);
        } catch (Exception e) {
            System.out.println(name+"   |   ERROR " + e.getMessage());
        }
    }

    protected void setConnection(String host, String entry, ProtocolType protocol) throws Exception {
        switch( protocol ) {
            case tcp : {
                int port = Integer.parseInt(entry);
                //TcpClientSupport try to create TcpConnection for 10 = num of attempts
                conn = TcpClientSupport.connect(host,  port, 10);
                System.out.println(name + "   |   setConnection "  + conn);
                break;
            }
            case udp : {
                int port = Integer.parseInt(entry);
                //conn = UdpClientSupport.connect(host,  port );
                break;
            }
            case coap : {
                //conn = new CoapConnection( host,  entry );
                break;
            }
            case mqtt : {
                /*
                La connessione col Broker viene stabilita in fase di configurazione
                La entry è quella definita per ricevere risposte;
                System.out.println(name+"   |   ProxyAsClient connect MQTT entry=" + entry );
                conn = MqttConnection.getSupport();
                 */
                break;
            }
            default :{
                System.out.println(name + "   |   Protocol unknown");
            }
        }
    }

    public void sendCommandOnConnection( String cmd )  {
        System.out.println( name+"   |   sendCommandOnConnection " + cmd + " conn=" + conn);
        try {
            conn.forward(cmd);
        } catch (Exception e) {
            System.out.println( name+"   |   sendCommandOnConnection ERROR=" + e.getMessage()  );
        }
    }

    public String sendRequestOnConnection( String request )  {
        System.out.println(name+"   |   sendRequestOnConnection request=" + request + " conn=" + conn);
        try {
            String answer = conn.request(request);
            return  answer  ;
            //return CommUtils.getContent( answer );

        } catch (Exception e) {
            System.out.println( name+"   |   sendRequestOnConnection ERROR=" + e.getMessage()  );
            return null;
        }
    }
    public Interaction2021 getConn() {
        return conn;
    }

    public void close() {
        try {
            conn.close();
            System.out.println(name + "   |   CLOSED " + conn   );
        } catch (Exception e) {
            System.out.println(name+ "   |   sendRequestOnConnection ERROR=" + e.getMessage()  );
        }
    }


}
