/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_server;

import java.util.ArrayList;

/**
 *
 * @author santen-suru
 */
public class Group {
    // Group-User list
    private String hasil[][];
    private ArrayList<Pair<String,String>> _grouplist = new ArrayList<Pair<String,String>>();
    
    Group() {
       hasil = Database.getAllGroup();
    	for( String[] x : hasil){
    		if(x[0]!=null){
        		_grouplist.add(new Pair(x[0],x[1]));   
                        System.out.println(x[0] + x[1]);
    		}
    	}
    }
    
    Group(ArrayList<Pair<String,String>> _grouplist) {
        this._grouplist.clear();
        for (int i = 0; i<_grouplist.size(); i++) {
            this._grouplist.add(new Pair(_grouplist.get(i).getFirst(), _grouplist.get(i).getSecond()));
        }
    }
    
    public ArrayList<Pair<String,String>> getGroupList() {
        return _grouplist;
    }
    
    public int updateGroup(String groupName, String user, ArrayList<Pair<String,String>> _grouplist) {
        _grouplist.add(new Pair(groupName, user));
        Database.insertGroup(groupName, user);
        return this.countGroup();
    }
    
    private int countGroup() {
        ArrayList<String> listGroup = new ArrayList<String>();
        int count = 0;
        for (Pair<String, String> selGroup : _grouplist) {
            if (listGroup.contains(selGroup.getFirst()) == false) {
                count++;
                listGroup.add(selGroup.getFirst());
            }
        }
        
        return count;
    }
}
