package src;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class driver {

    static Scanner keyboard = new Scanner(System.in);
    

    public static void main(String[] args) 
    {    
        String f;

        System.out.print("Enter file : ");
        f = keyboard.nextLine();
        try
        {
            Matriks.bacaFileMatriks(f);
            System.out.println();
        }
        catch(FileNotFoundException e)
        {
            keyboard.close();
            System.out.println("There is no such file");
            return;
        }
        System.out.println();
    }
}
