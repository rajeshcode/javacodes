package LockObjects;




/**
 * Codes with minor comments are from
 * <a href="http://www.caveofprogramming.com/youtube/">
 * <em>http://www.caveofprogramming.com/youtube/</em>
 * </a>
 * <br>
 * Created by rajec on 26/02/16.
 */



public class App {

    public static void main(String[] args) {
        System.out.println("Synchronized Objects: ");
        Worker worker = new Worker();
        worker.main();
        System.out.println("Synchronized Methods: ");
        //workerMethodsSynchronized worker2 = new WorkerMethodsSynchronized();
        //worker2.main();
    }
}

