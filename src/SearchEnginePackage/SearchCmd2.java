package SearchEnginePackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

class HTMLList2 {
    String str;
    HTMLList2 next;

    HTMLList2(String s, HTMLList2 n) {
        str = s;
        next = n;
    }
}

class Searcher2 {
    public static long time; // Variable for timer
    
    public static int exists(HTMLList2 l, String word) { 
        long startTime = System.currentTimeMillis(); // Start timer
       
        int foundWords = 0;
        String lastURL = ""; // Variable to hold lastest url 
       
        while (l != null) {
            if(l.str.charAt(0) == '*') {
                lastURL = l.str.substring(6); //remove *PAGE: and update url variable
            } 
            
            if (l.str.equals (word)) {
            	System.out.println ("The word has been found in: " + lastURL);
            	foundWords++;
            }
            l = l.next;
        }
        
        long endTime = System.currentTimeMillis(); // Stop timer
        time = endTime - startTime; // Set timer variable
        return foundWords; // Return words
    }

    public static HTMLList2 readHtmlList(String filename) throws IOException {
        long startTime = System.currentTimeMillis(); // Start timer
        String name;
        HTMLList2 start, current, tmp;

        BufferedReader infile = new BufferedReader(new FileReader(filename)); // Open the file given as argument

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
        long endTime = System.currentTimeMillis(); // Stop timer
        time = endTime - startTime; // Set timer variable
        return start;
    }
}

public class SearchCmd2 {
    
    public static enum searchFile {
        SMALL("small.txt"),
        MEDIUM("medium.txt"),
        LARGE("large.txt");
        private final String path;

        searchFile(String file) {
            this.path = file;
        }

        public String file() {
            return this.path;
        }
    }


    public static void main(String[] args) throws IOException {
        String name;
        
        BufferedReader readIn =
            new BufferedReader (new InputStreamReader (System.in)); //prompt the user which file to use

        String file = null;

        while (file == null) {
            try {
                System.out.println("Please type which file you use for searching e.g. : small, medium, large");
                System.out.print("Type: ");
                file = readIn.readLine().toUpperCase();
                if (!file.equals(searchFile.SMALL.toString()) & !file.equals(searchFile.MEDIUM.toString()) & !file.equals(searchFile.LARGE.toString())) {
                    throw new Exception("Input must be: " + searchFile.SMALL.toString() + " / " + searchFile.MEDIUM.toString() + " / " + searchFile.LARGE.toString());
                }
            } catch (Exception ex) {
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
 
        HTMLList2 l = Searcher2.readHtmlList(fileFormat.file()); // Read the file and create the linked list
        System.out.println("Datafile loaded in " + Searcher2.time / 1000F + " seconds."); // Print time used to build the list
       
        
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in)); // Ask for a word to search

        System.out.println ("Hit return to exit.");
        boolean quit = false;
        
        while (!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if (name == null || name.length() == 0) {
                quit = true;
            } else {
            	int instancesFound = Searcher2.exists(l, name);
            	System.out.println("There were " + instancesFound + " instances found.\nSearch time:" + Searcher2.time + " miliseconds");
            }
        }
    }
}
