/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

/*import java.net.Socket;*/
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Scanner;
import sun.misc.BASE64Decoder;

/**
 *
 * @author santen-suru
 */
public class Read implements Runnable {
        
        private Scanner in;//MAKE SOCKET INSTANCE VARIABLE
        String input;
        boolean keepGoing = true;
        BASE64Decoder decoder = new BASE64Decoder();
        ArrayList<String> log;
	
	public Read(Scanner in, ArrayList<String> log)
	{
		this.in = in;
                this.log = log;
	}
    
        @Override
	public void run()//INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
	{
		try
		{
			while (keepGoing)//WHILE THE PROGRAM IS RUNNING
			{						
				if(this.in.hasNext()) {
                                                                   //IF THE SERVER SENT US SOMETHING
                                        input = this.in.nextLine();
                                        System.out.println(input);//PRINT IT OUT
                                        if (input.split(" ")[0].toLowerCase().equals("success")) {
                                            if (input.split(" ")[1].toLowerCase().equals("logout")) {
                                                keepGoing = false;
                                            } else if (input.split(" ")[1].toLowerCase().equals("login")) {
                                                
                                                byte[] key = Main.password.getBytes();
                                                RC4 rc4 = new RC4(key);
                                                String decrypted = rc4.decrypt(input.split(" ")[2]);
                                                
                                                byte[] sigBytes1 = decoder.decodeBuffer(decrypted.split("\n\r\n\r")[0]);
                                                byte[] sigBytes2 = decoder.decodeBuffer(decrypted.split("\n\r\n\r")[1]);
                                                
                                                
                                                KeyFactory keyFact = KeyFactory.getInstance("RSA");
                                                
                                                // Convert the public key bytes into a PublicKey object
                                                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes1);
                                                Main.SPuKey = keyFact.generatePublic(x509KeySpec);
                                                System.out.println(Main.SPuKey);
                                                
                                                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(sigBytes2);
                                                Main.CPrKey = keyFact.generatePrivate(pkcs8KeySpec);
                                                System.out.println(Main.CPrKey);
                                                
                                                log.clear();
                                                log.add("true");
                                            }
                                        }
                                        
                                }
                                
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();//MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
		} 
	}
}
