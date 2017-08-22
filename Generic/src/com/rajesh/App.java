package com.rajesh;

 class Store {
     private Object item;

     public void setStoreItem(Object item) {
         this.item = item;
     }

     public Object getItem(){
         return this.item;
     }

     @Override
     public String toString() {
         //return super.toString(); It will print the address of the Store object
         return this.item.toString();
     }
 }

public class App {
    public static void main(String[] args) {
        Store store = new Store();
        store.setStoreItem("Hello Generic data"); // It will accesspt string
        System.out.println(store);

        store.setStoreItem(5);
        Integer item  = (Integer) store.getItem(); // Type Casting done
        System.out.println(store);




    }

}
