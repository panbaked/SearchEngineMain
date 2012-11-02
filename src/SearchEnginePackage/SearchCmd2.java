package SearchEnginePackage;

/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
*/

import java.io.*;

class HTMLList2 {
    String str;
    HTMLList2 next;

    HTMLList2 (String s, HTMLList2 n) {
        str = s;
        next = n;
    }
}

class Searcher2 {

    public static int exists (HTMLList2 l, String word) {
    	int foundWords = 0;
        String lastURL = "";
        while (l != null) 
        {
            if(l.str.charAt(0) == '*')
            {
                lastURL = l.str.substring(6); //remove *PAGE:
            } 
            
            if (l.str.equals (word)) 
            {
            	System.out.println ("The word has been found in: " + lastURL);
            	foundWords++;
            }
            l = l.next;
        }
        return foundWords;
    }

    public static HTMLList2 readHtmlList (String filename) throws IOException {
        String name;
        HTMLList2 start, current, tmp;

        // Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));

        name = infile.readLine(); //Read the first line
        start = new HTMLList2(name, null);
        current = start;
        name = infile.readLine(); // Read the next line
        while (name != null) {    // Exit if there is none
            tmp = new HTMLList2(name,null);
            current.next = tmp;
            current = tmp;            // Update the linked list
            name = infile.readLine(); // Read the next line
        }
        infile.close(); // Close the file

        return start;
    }
}

public class SearchCmd2 {

    public static void main (String[] args) throws IOException {
        String name;

        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println("Usage: java SearchCmd <datafile>");
            System.exit(1);
        }

        // Read the file and create the linked list
        HTMLList2 l = Searcher2.readHtmlList (args[0]);

        // Ask for a word to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in));

        System.out.println ("Hit return to exit.");
        boolean quit = false;
        while (!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if (name == null || name.length() == 0) {
                quit = true;
            } 
            else
            {
            	int instancesFound = Searcher2.exists(l, name);
            	System.out.println("There were "+instancesFound+" instances found.");
            }
        }
    }
}
