package models;

public class GFG {
    //public static int i=0;

    // static block
    static {
        System.out.println("Loading class!");
    }

    {
        System.out.println("Initializer block called!");
    }
}
