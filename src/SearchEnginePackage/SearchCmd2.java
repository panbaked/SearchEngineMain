package SearchEnginePackage;

/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
*/

import java.io.*;
import java.util.Scanner;

class HTMLList2 {
    String str;
    HTMLList2 next;

    HTMLList2 (String s, HTMLList2 n) {
        str = s;
        next = n;
    }
}

class Searcher2 {
    
    public static long time;

    public static int exists (HTMLList2 l, String word) {
        
        long startTime = System.currentTimeMillis();
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
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;
        return foundWords;
    }

    public static HTMLList2 readHtmlList (String filename) throws IOException {
        
        long startTime = System.currentTimeMillis();
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
         long endTime = System.currentTimeMillis();
         time = endTime - startTime;
         return start;
    }
}

public class SearchCmd2 {
    
     public static enum searchFile
   {

      SMALL("small.txt"),
      MEDIUM("medium.txt"),
      LARGE("large.txt");
      private final String path;

      searchFile(String file)
      {
         this.path = file;
      }

      public String file()
      {
         return this.path;
      }
   }


    public static void main (String[] args) throws IOException {
        
      String name;
      
      //prompt the user which file to use
      
      Scanner readIn = new Scanner(System.in);
      String file = null;

      while (file == null)
      {
         try
         {
            System.out.println("Please type which file you use for searching e.g. : small, medium, large");
            file = readIn.next().toUpperCase();
            if (!file.equals(searchFile.SMALL.toString()) & !file.equals(searchFile.MEDIUM.toString()) & !file.equals(searchFile.LARGE.toString()))
            {
               throw new Exception("Input must be: " + searchFile.SMALL.toString() + " / " + searchFile.MEDIUM.toString() + " / " + searchFile.LARGE.toString());
            }
         }
         catch (Exception ex)
         {
            file = null;
            System.out.println("Invalid input: " + ex.getMessage());
         }
      }

      searchFile fileFormat = null;
      if (file.equals(searchFile.SMALL.toString())) {
            fileFormat = searchFile.SMALL;
        }
      if (file.equals(searchFile.MEDIUM.toString())) {
            fileFormat = searchFile.MEDIUM;
        }
      if (file.equals(searchFile.LARGE.toString())) {
            fileFormat = searchFile.LARGE;
        }

      // Read the file and create the linked list
      HTMLList2 l = Searcher2.readHtmlList(fileFormat.file());
      System.out.println("Datafile loaded in " + Searcher2.time / 1000F + " seconds.");
       

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
            	System.out.println("There were "+instancesFound+" instances found.Search time:" + Searcher2.time + " miliseconds");
            }
        }
    }
}
