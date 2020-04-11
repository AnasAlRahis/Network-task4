import java.net.*;
import java.io.*;
import tcpclient.TCPClient;


public class MyRunnable implements Runnable { 
		Socket socket = null;
		public MyRunnable (Socket socket) { 
			this.socket = socket;
		}
		public void run() {
			
			String badrequest = "HTTP/1.1 400 BAD REQUEST\r\n\r\n";
			String unknownserver = "HTTP/1.1 404 NOT FOUND\r\n\r\n";			
			StringBuilder input_string = new StringBuilder();
			try {
			BufferedReader bufferinput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String teststring = bufferinput.readLine();
			
			
			if(teststring.contains("GET") && teststring.contains("ask?") && teststring.contains("HTTP/1.1")) {
				String[] split_string = splitting(teststring);
				if(split_string[0].equals("") || split_string[1].equals("")) {
					socket.getOutputStream().write(badrequest.getBytes());
					bufferinput.close();
					socket.close();
				}
				else {
					try {
						String result = TCPClient.askServer(split_string[0],Integer.parseInt(split_string[1]),split_string[2]);
					
						input_string.append("HTTP/1.1 200 OK\r\n");
						input_string.append("\r\n");
						input_string.append("I will fuck you up, bitch");
					
						socket.getOutputStream().write(input_string.toString().getBytes());
						bufferinput.close();
						socket.close();
					} catch (IOException x){
						socket.getOutputStream().write(unknownserver.getBytes());
						bufferinput.close();
						socket.close();
					}
				}
			}
			else {
				socket.getOutputStream().write(badrequest.getBytes());
				bufferinput.close();
				socket.close();
			}
		} catch(IOException x){
			System.err.println(x);
		}
		}
		public static String[] splitting(String input) { 
		String[] hostportparam = new String[3];
		for(int x = 0; x < hostportparam.length; x++)
				hostportparam[x] = "";
		String[] spltd_string = input.split("[&=? ]");
		for(int i = 0; i<spltd_string.length;i++) {
			if(spltd_string[i].equals("hostname"))
				hostportparam[0] = spltd_string[i+1];
			if(spltd_string[i].equals("port"))
				hostportparam[1] = spltd_string[i+1];
			if(spltd_string[i].equals("string"))
				hostportparam[2] = spltd_string[i+1];
		}
		return hostportparam;
		
	}
	}