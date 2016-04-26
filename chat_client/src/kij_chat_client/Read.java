/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_client;

/*import java.net.Socket;*/
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Scanner;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
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
                                        //System.out.println(input);//PRINT IT OUT
                                        if (input.split(" ")[0].toLowerCase().equals("success")) {
                                            if (input.split(" ")[1].toLowerCase().equals("logout")) {
                                                keepGoing = false;
                                            } else if (input.split(" ")[1].toLowerCase().equals("login")) {
                                                
                                                byte[] key = Main.password.getBytes();
                                                RC4 rc4 = new RC4(key);
                                                String decrypted = rc4.decrypt(input.split(" ")[2]);
                                                //System.out.println(decrypted);
                                                byte[] sigBytes2 = decoder.decodeBuffer(decrypted);
                                                
                                                
                                                KeyFactory keyFact = KeyFactory.getInstance("RSA");
                                                                                                
                                                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(sigBytes2);
                                                Main.CPrKey = keyFact.generatePrivate(pkcs8KeySpec);
                                                //System.out.println(Main.CPrKey);
                                                System.out.println(input);
                                                log.clear();
                                                log.add("true");
                                            }else if (input.split(" ")[1].toLowerCase().equals("pm")) {
                                                
                                                Cipher cipher = Cipher.getInstance("RSA");
                                                byte[] key = Main.password.getBytes();
                                                RC4 rc4 = new RC4(key);
                                                String decrypted = rc4.decrypt(input.split(" ")[2]);
                                                String[] temp = decrypted.split(" ");
                                                //System.out.println(decrypted);
                                                
                                                //spukey
                                                String username = temp[0];
                                                byte[] message= Base64.decodeBase64(temp[1]);
                                                byte[] sigBytes1 = decoder.decodeBuffer(temp[2]);
                                                
                                                KeyFactory keyFact = KeyFactory.getInstance("RSA");
                                                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes1);
                                                Key SPuKey = keyFact.generatePublic(x509KeySpec);
                                                //System.out.println(SPuKey);
                                                
                                                cipher.init(Cipher.DECRYPT_MODE, SPuKey);
                                                byte[] plainText = cipher.doFinal(message);
                                                String message_ = new String(plainText);
                                                //System.out.println(Main.CPrKey);
                                                System.out.println(username + " : "+message_);
                                                
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
