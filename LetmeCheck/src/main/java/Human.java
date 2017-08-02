/**
 * Created by rajec on 8/1/17.
 */
public class Human {
    String name ;
    int age;
    int heightInInches;
    String eyeColor;

    //Constructor , is for how the bjects should be created
    /*public void Human(){

        age=35;
        eyeColor= "black";
        heightInInches=71;
        name = "Rajesh Chandramohan";

    }*/

    // Right click and click Generate constuctor


    public Human(String name, int age, int heightInInches, String eyeColor) {
        this.name = name;
        this.age = age;
        this.heightInInches = heightInInches;
        this.eyeColor = eyeColor;
    }


    public void Speak() {
        System.out.println("Hello My name is "+ name);
        System.out.println("i am "+ heightInInches + " inch tall");
        System.out.println("My Eye color is " + eyeColor );

    }


     public  void eat(){
        System.out.println("eating");

     }

     public void walk(){
         System.out.println("Walking");
     }

     public  void  work(){
         System.out.println("working");
     }


}
