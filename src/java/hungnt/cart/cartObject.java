/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public class cartObject implements Serializable{
    private Map<String,Integer> items;

    public cartObject() {
    }

    public Map<String, Integer> getItems() {
        return items;
    }
    
    public void add(String title){
        if(items == null){
            this.items = new HashMap<>();
        }
        int quantity = 1;
        if(items.containsKey(title)){
           quantity = items.get(title) + 1; 
        }
        this.items.put(title, quantity);
    }
    public void remove(String title){
        //Check cart's items is exsited
        if(this.items == null){
            return;
        }
        //Check if product is in cart
        if(this.items.containsKey(title)){
            this.items.remove(title);
            //if cart's items is empty , remove the cart
            if(this.items.isEmpty()){
                this.items = null;
            }
        }
    }
}
