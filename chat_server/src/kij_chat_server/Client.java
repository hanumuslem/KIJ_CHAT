package kij_chat_server;

import java.io.PrintWriter;
import java.net.Socket;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Scanner;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** original ->http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/
 * 
 * @author santen-suru
 */


public class Client implements Runnable{

	private Socket socket;//SOCKET INSTANCE VARIABLE
        private String username;
        private boolean login = false;
        
        private ArrayList<Pair<Socket,String>> _loginlist;
        private ArrayList<Pair<String,String>> _userlist;
        private ArrayList<Pair<String,String>> _grouplist;
	
	public Client(Socket s, ArrayList<Pair<Socket,String>> _loginlist, ArrayList<Pair<String,String>> _userlist, ArrayList<Pair<String,String>> _grouplist)
	{
		socket = s;//INSTANTIATE THE SOCKET)
                this._loginlist = _loginlist;
                this._userlist = _userlist;
                this._grouplist = _grouplist;
	}
	   

	
	@Override
	public void run() //(IMPLEMENTED FROM THE RUNNABLE INTERFACE)
	{
		try //HAVE TO HAVE THIS FOR THE in AND out VARIABLES
		{
			Scanner in = new Scanner(socket.getInputStream());//GET THE SOCKETS INPUT STREAM (THE STREAM THAT YOU WILL GET WHAT THEY TYPE FROM)
			PrintWriter out = new PrintWriter(socket.getOutputStream());//GET THE SOCKETS OUTPUT STREAM (THE STREAM YOU WILL SEND INFORMATION TO THEM FROM)
			
			while (true)//WHILE THE PROGRAM IS RUNNING
			{		
				if (in.hasNext())
				{
					
					String input = in.nextLine();//IF THERE IS INPUT THEN MAKE A NEW VARIABLE input AND READ WHAT THEY TYPED
//					System.out.println("Client Said: " + input);//PRINT IT OUT TO THE SCREEN
//					out.println("You Said: " + input);//RESEND IT TO THE CLIENT
//					out.flush();//FLUSH THE STREAM
                                        
                                        // param LOGIN <userName> <pass>
                                        if (input.split(" ")[0].toLowerCase().equals("login") == true) {
                                            System.out.println(input);
                                            String[] vals = input.split(" ");
                                            boolean flag = false;
                                            for (Pair<String,String> x : this._userlist){
                                                byte[] key = x.getSecond().getBytes();
                                                RC4 rc4 = new RC4(key);
                                                String decrypted = rc4.decrypt(vals[1]);
                                                if(x.getFirst().equals(decrypted)==true){
                                                    
                                                    flag=true;
                                                }
                                            }
//                                            if (this._userlist.contains(new Pair(vals[1], vals[2])) == true) {
                                            if (flag == true) {
                                                if (this.login == false) {
                                                    this._loginlist.add(new Pair(this.socket, vals[1]));
                                                    this.username = vals[1];
                                                    this.login = true;
                                                    
                                                    String algorithm = "RSA";
                                                    KeyPair keyPair = KeyPairGenerator.getInstance(algorithm).generateKeyPair();
                                                    Key publicKey = keyPair.getPublic();
                                                    Key privatKey = keyPair.getPrivate();
                                                    

                                                    BASE64Encoder encoder = new BASE64Encoder();
                                                    
                                                    byte[] array1 = publicKey.getEncoded();
                                                    String Pukey = encoder.encode(array1);

                                                    byte[] array2 = privatKey.getEncoded();
                                                    String Prkey = encoder.encode(array2);
                                                    
/*                                                    //public to string
                                                    byte[] array = publicKey.getEncoded();
                                                    BASE64Encoder encoder = new BASE64Encoder();
                                                    String Pkey = encoder.encode(array);
                                                    System.out.println(Pkey);
                                                    
                                                  //Convert PublicKeyString to Byte Stream
                                                    BASE64Decoder decoder = new BASE64Decoder();
                                                    byte[] sigBytes2 = decoder.decodeBuffer(Pkey);
                                                    // Convert the public key bytes into a PublicKey object
                                                    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes2);
                                                    KeyFactory keyFact = KeyFactory.getInstance("RSA");
                                                    Key pubKey2 = keyFact.generatePublic(x509KeySpec);
                                                    System.out.println(pubKey2);
*/                                                    
//                                                    //public to string
//                                                    byte[] array = privatKey.getEncoded();
//                                                    BASE64Encoder encoder = new BASE64Encoder();
//                                                    String Pkey = encoder.encode(array);
//                                                    System.out.println(Pkey);
//                                                    
//                                                  //Convert PublicKeyString to Byte Stream
//                                                    BASE64Decoder decoder = new BASE64Decoder();
//                                                    byte[] sigBytes2 = decoder.decodeBuffer(Pkey);
//                                                    // Convert the public key bytes into a PublicKey object
//                                                    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(sigBytes2);
//                                                    KeyFactory keyFact = KeyFactory.getInstance("RSA");
//                                                    Key privKey2 = keyFact.generatePrivate(pkcs8KeySpec);
//                                                    System.out.println(privKey2);

                                                    
                                                    Database.updatePublic(this.username,Pukey);
                                                    
                                                    System.out.println("Users count: " + this._loginlist.size());
                                                    out.println("SUCCESS login");
                                                    
                                                    out.flush();
                                                } else {
                                                    out.println("FAIL login");
                                                    out.flush();
                                                }
                                            } else {
                                                out.println("FAIL login");
                                                out.flush();
                                            }
                                        }
                                        
                                        // param LOGOUT
                                        if (input.split(" ")[0].toLowerCase().equals("logout") == true) {
                                            String[] vals = input.split(" ");
                                            
                                            if (this._loginlist.contains(new Pair(this.socket, this.username)) == true) {
                                                this._loginlist.remove(new Pair(this.socket, this.username));
                                                System.out.println(this._loginlist.size());
                                                out.println("SUCCESS logout");
                                                out.flush();
                                                this.socket.close();
                                                break;
                                            } else {
                                                out.println("FAIL logout");
                                                out.flush();
                                            }
                                        }
                                        
                                        // param PM <userName dst> <message>
                                        if (input.split(" ")[0].toLowerCase().equals("pm") == true) {
                                            String[] vals = input.split(" ");
                                            
                                            boolean exist = false;
                                            
                                            for(Pair<Socket, String> cur : _loginlist) {
                                                if (cur.getSecond().equals(vals[1])) {
                                                    PrintWriter outDest = new PrintWriter(cur.getFirst().getOutputStream());
                                                    String messageOut = "";
                                                    for (int j = 2; j<vals.length; j++) {
                                                        messageOut += vals[j] + " ";
                                                    }
                                                    System.out.println(this.username + " to " + vals[1] + " : " + messageOut);
                                                    outDest.println(this.username + ": " + messageOut);
                                                    outDest.flush();
                                                    exist = true;
                                                }
                                            }
                                            
                                            if (exist == false) {
                                                System.out.println("pm to " + vals[1] + " by " + this.username + " failed.");
                                                out.println("FAIL pm");
                                                out.flush();
                                            }
                                        }
                                        
                                        // param CG <groupName>
                                        if (input.split(" ")[0].toLowerCase().equals("cg") == true) {
                                            String[] vals = input.split(" ");
                                            
                                            boolean exist = false;
                                            
                                            for(Pair<String, String> selGroup : _grouplist) {
                                                if (selGroup.getFirst().equals(vals[1])) {
                                                    exist = true;
                                                }
                                            }
                                            
                                            if(exist == false) {
                                                Group group = new Group();
                                                int total = group.updateGroup(vals[1], this.username, _grouplist);
                                                System.out.println("total group: " + total);
                                                System.out.println("cg " + vals[1] + " by " + this.username + " successed.");
                                                out.println("SUCCESS cg");
                                                out.flush();
                                            } else {
                                                System.out.println("cg " + vals[1] + " by " + this.username + " failed.");
                                                out.println("FAIL cg");
                                                out.flush();
                                            }
                                        }
                                        
                                        // param GM <groupName> <message>
                                        if (input.split(" ")[0].toLowerCase().equals("gm") == true) {
                                            String[] vals = input.split(" ");
                                            
                                            boolean exist = false;
                                            
                                            for(Pair<String, String> selGroup : _grouplist) {
                                                if (selGroup.getSecond().equals(this.username)) {
                                                    exist = true;
                                                }
                                            }
                                            
                                            if (exist == true) {
                                                for(Pair<String, String> selGroup : _grouplist) {
                                                    if (selGroup.getFirst().equals(vals[1])) {
                                                        for(Pair<Socket, String> cur : _loginlist) {
                                                            if (cur.getSecond().equals(selGroup.getSecond()) && !cur.getFirst().equals(socket)) {
                                                                PrintWriter outDest = new PrintWriter(cur.getFirst().getOutputStream());
                                                                String messageOut = "";
                                                                for (int j = 2; j<vals.length; j++) {
                                                                    messageOut += vals[j] + " ";
                                                                }
                                                                System.out.println(this.username + " to " + vals[1] + " group: " + messageOut);
                                                                outDest.println(this.username + " @ " + vals[1] + " group: " + messageOut);
                                                                outDest.flush();
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                System.out.println("gm to " + vals[1] + " by " + this.username + " failed.");
                                                out.println("FAIL gm");
                                                out.flush();
                                            }
                                        }
                                        
                                        // param BM <message>
                                        if (input.split(" ")[0].toLowerCase().equals("bm") == true) {
                                            String[] vals = input.split(" ");
                                            
                                            for(Pair<Socket, String> cur : _loginlist) {
                                                if (!cur.getFirst().equals(socket)) {
                                                    PrintWriter outDest = new PrintWriter(cur.getFirst().getOutputStream());
                                                    String messageOut = "";
                                                    for (int j = 1; j<vals.length; j++) {
                                                        messageOut += vals[j] + " ";
                                                    }
                                                    System.out.println(this.username + " to alls: " + messageOut);
                                                    outDest.println(this.username + " <BROADCAST>: " + messageOut);
                                                    outDest.flush();
                                                }
                                            }
                                        }
				}
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();//MOST LIKELY THERE WONT BE AN ERROR BUT ITS GOOD TO CATCH
		}	
	}

}


