package SearchEnginePackage;

/* SearchCmd1.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

class HTMLlist {
    String str;
    HTMLlist next;

    HTMLlist (String s, HTMLlist n) {
        str = s;
        next = n;
    }
}

class Searcher1 {
    public static long time; // Variable for timer

    public static boolean exists (HTMLlist l, String word) throws IOException {
        long startTime = System.currentTimeMillis(); // Start timer
        while (l != null) {
            if (l.str.equals (word)) {
                long endTime = System.currentTimeMillis(); // Stop timer
                time = endTime - startTime; // Set timer variable
                return true;
            }
            l = l.next;
        }
        long endTime = System.currentTimeMillis(); // Stop timer
        time = endTime - startTime; // Set timer variable
        return false;
    }

    public static HTMLlist readHtmlList (String filename) throws IOException {
        long startTime = System.currentTimeMillis(); // Start timer
        String name;
        HTMLlist start, current, tmp;

        // Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));
        
        name = infile.readLine(); //Read the first line
        start = new HTMLlist (name, null);
        current = start;
        name = infile.readLine(); // Read the next line
        while (name != null) {    // Exit if there is none
            tmp = new HTMLlist(name,null);
            current.next = tmp;
            current = tmp;            // Update the linked list
            name = infile.readLine(); // Read the next line
        }
        infile.close(); // Close the file
        long endTime = System.currentTimeMillis(); // Stop timer
        time = endTime - startTime; // Set timer variable
        return start;
    }
}

public class SearchCmd1 {
    
    public static void main (String[] args) throws IOException {
        String name;
        String file = "large.txt"; // Define what file to search
        
        if (file == null) { // Check that a filename has been given as argument
            System.out.println("Usage: java SearchCmd <datafile>");
            System.exit(1);
        }

        HTMLlist l = Searcher1.readHtmlList (file); // Read the file and create the linked list

        BufferedReader inuser = 
            new BufferedReader (new InputStreamReader (System.in)); // Ask for a word to search
        System.out.println("Datafile loaded in " + Searcher1.time / 1000F + " seconds."); // Print time used to build the list
        //MyLog.logToFile(Searcher1.time / 1000F); // Log times to file
        
        System.out.println ("Hit return to exit.");
        boolean quit = false;
        while (!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if (name == null || name.length() == 0) {
                quit = true;
            } else if (Searcher1.exists (l, name)) {
                System.out.println ("The word " + name + " has been found.");
            } else {
                System.out.println ("The word \"" + name + " has NOT been found.");
            }
            System.out.println("Search time: " + Searcher1.time + " miliseconds");
        }
    }
}