package com.rajesh;

import java.util.ArrayList;
import java.util.List;

//Class HashTable <T , E>
// How to Use Multiple TypeParmeters

class Hashtable<K , V>{

    private  K key;
    private V value;

    // Constructor
    public Hashtable(K key, V value){
        this.key= key;
        this.value=value;

    }

    @Override
    public String toString(){
        return key.toString()+ " - " + value.toString();

    }
}


public class App2 {
    public static void main(String[] args) {


        //Use Multiple Parameter with single Parameter defined in Class Store1
        Hashtable<String , Integer> hashtable = new Hashtable<>("Hello world:", 55);
        System.out.println(hashtable);
        









    }

}
