/**
 * Created by rajec on 8/1/17.
 */
public class Earth {

    public static void  main(String args[]){
        //Variable
        Human Rajesh;

        /*Rajesh = new Human();
        Rajesh.age=35;
        Rajesh.eyeColor= "black";
        Rajesh.heightInInches=71;
        Rajesh.name = "Rajesh Chandramohan"; */

         Rajesh = new Human("Rajesh Chandramohan" , 35 , 71, "Black");



        Rajesh.Speak();

        /*Human Natasha = new Human();
        Natasha.age=1;
        Natasha.eyeColor="Black";
        Natasha.name="Natasha Rajesh";
        Natasha.heightInInches = 16; */
        Human Natasha = new Human( "Natasha Rajesh", 1 , 16 , "Black");


        Natasha.Speak();



    }
}
