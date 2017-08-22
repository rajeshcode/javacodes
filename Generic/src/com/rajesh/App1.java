package com.rajesh;

import java.util.ArrayList;
import java.util.List;

/* THis Program is to demonstrate the APP class to APP1
  with generic type T , So that we can use that class to print any datatypes
 */
class Store1<T> {
private T item;

public void setStoreItem(T item){
        this.item=item;
        }

public T getItem(){
        return this.item;
        }

@Override
public String toString(){
        //return super.toString(); It will print the address of the Store object
        return this.item.toString();
        }

}

public class App1 {
    public static void main(String[] args) {
        //Raw tye AVOID IT!!
        /*Store store = new Store();
        store.setStoreItem("Hello Generic data"); // It will accesspt string
        System.out.println(store);

        store.setStoreItem(5);
        Integer item  = (Integer) store.getItem(); // Type Casting done
        System.out.println(store); */

        Store1<String> stringStore = new Store1<>() ; // Diamond Operatior
        // In background in above store class it will Replace all T with String
         //and also " Class Store1<T> will be come class Store1"

        stringStore.setStoreItem(" Hello Well come");

         String item = stringStore.getItem();
         System.out.println(item);

        Store1<Double> stringStore1 = new Store1<>() ;
        stringStore1.setStoreItem(35.0);
        double item1 = stringStore1.getItem();
        System.out.println(item1);

        // ArrayList is a Generic type
        Store1 s = new Store1();
        List<String> l = new ArrayList<>();












    }

}
