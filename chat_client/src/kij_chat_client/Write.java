/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author santen-suru
 */
public class Write implements Runnable {
    
	private Scanner chat;
        private PrintWriter out;
        boolean keepGoing = true;
        ArrayList<String> log;
	
	public Write(Scanner chat, PrintWriter out, ArrayList<String> log)
	{
		this.chat = chat;
                this.out = out;
                this.log = log;
	}
	
	@Override
	public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
	{
		try
		{
			while (keepGoing)//WHILE THE PROGRAM IS RUNNING
			{						
				String input = chat.nextLine();	//SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
                if (input.contains("login")) {
//                    String[] tes = input.split(" ");
//                    tes[2]=simple_MD5.MD5(tes[2]);
//                    List<String> list = Arrays.asList(tes);
//                    input = String.join(" ", list);
                    
                    String[] tes = input.split(" ");
                    String username=tes[1];
                    String password=simple_MD5.MD5(tes[2]);
                    
                    byte[] key = password.getBytes();
                    RC4 rc4 = new RC4(key);
                    String cipherText = rc4.encrypt(username);
                    
                    input=tes[0] + " " + cipherText;
                    System.out.println(input);
                }

				out.println(input);//SEND IT TO THE SERVER
				out.flush();//FLUSH THE STREAM
                                
                                if (input.contains("logout")) {
                                    if (log.contains("true"))
                                        keepGoing = false;
                                    
                                }
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
		} 
	}

}
