import java.net.*;
import java.io.*;

import tcpclient.TCPClient;

public class ConcHTTPAsk {
	
		
    public static void main( String[] args) throws IOException {
		
		
        // Your code here
		
		ServerSocket testsocket = new ServerSocket(Integer.parseInt(args[0]));
		
		
		
		
		
		while(true) { 
			
			Socket testconnection = testsocket.accept();
			MyRunnable multireq = new MyRunnable(testconnection);
			
			new Thread(multireq).start();
			
		}	
			
  }
	
	
	
	
	
	
	
}

