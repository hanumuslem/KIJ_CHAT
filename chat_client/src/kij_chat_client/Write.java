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
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

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
                   

                    String[] tes = input.split(" ");
                    Main.username=tes[1];
                    Main.password=simple_MD5.MD5(tes[2]);
                    
                    byte[] key = Main.password.getBytes();
                    RC4 rc4 = new RC4(key);
                    String cipherText = rc4.encrypt(Main.username);
                    
                    input=tes[0] + " " + cipherText;
                    //System.out.println(input);
                }
                if (input.contains("pm")) {
                     Cipher cipher = Cipher.getInstance("RSA");   
                     
                    String[] tes = input.split(" ");
                    String username_source=tes[1];
                    String message="";
                    for (int j = 2; j<tes.length; j++) {
                        if(j==tes.length-1){
                        message += tes[j];
                        }else{
                        message += tes[j] + " ";                        
                        }
                    }
                    
                    
                    byte[] message_ = message.getBytes();                    
                    byte[] key = Main.password.getBytes();
                    
                    //rsa
                    cipher.init(Cipher.ENCRYPT_MODE, Main.CPrKey);
                    byte[] cipherText_ = cipher.doFinal(message_);
                    //System.out.println("cipher: " + new String(cipherText_));
                    
                    String message__ = Base64.encodeBase64String(cipherText_);
                    //System.out.println(message);
                    //System.out.println(message__);
                    
                    RC4 rc4 = new RC4(key);
                    String cipherText = rc4.encrypt(message__+" "+username_source);
                    
                    input=tes[0] + " " + cipherText;
                    //System.out.println(input);
                }if(input.contains("cg")){
                    
                    String[] tes = input.split(" ");
                    
                    String group_name = tes[1];
                    byte[] key = Main.password.getBytes();
                    //enkripsi
                    RC4 rc4 = new RC4(key);
                    String cipherText = rc4.encrypt(group_name);
                    input=tes[0] + " " + cipherText;
                                    
                }if(input.contains("bm")){
                     Cipher cipher = Cipher.getInstance("RSA");   
                     
                    String[] tes = input.split(" ");
                    //String username_source=tes[1];
                    String message="";
                    for (int j = 1; j<tes.length; j++) {
                        if(j==tes.length-1){
                        message += tes[j];
                        }else{
                        message += tes[j] + " ";                        
                        }
                    }
                    
                    
                    byte[] message_ = message.getBytes();                    
                    //byte[] key = Main.password.getBytes();
                    
                    //rsa
                    cipher.init(Cipher.ENCRYPT_MODE, Main.CPrKey);
                    byte[] cipherText_ = cipher.doFinal(message_);
                    //System.out.println("cipher: " + new String(cipherText_));
                    
                    String message__ = Base64.encodeBase64String(cipherText_);
                    
                    
                    input=tes[0] + " " + message__;
                    //System.out.println(">>>>>>>>> " + cipherText_ + " <<<<<<<<<<");
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
