/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_server;

import java.util.ArrayList;


/**Andi
 *
 * @author santen-suru
 */
public class User {
    // User-Password list
   	private String hasil[][];
    private ArrayList<Pair<String,String>> _userlist = new ArrayList<Pair<String,String>>();
	
    
    User() {

    	hasil = Database.getAll();
    	for( String[] x : hasil){
    		if(x[0]!=null){
        		_userlist.add(new Pair(x[0],x[1]));    			
    		}
    	}
    }
    public ArrayList<Pair<String,String>> getUserList() {
    	return _userlist;
    }
}
