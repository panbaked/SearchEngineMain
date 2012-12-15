package SearchEnginePackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Searcher4 {
    public static long time;
	
    public static boolean exists(Hashtable hashMap, String word) {    
    	if(hashMap.get(word) != null) {
            return true;
        }
    	return false;    		
    }
    
    public static Hashtable readHashMap(String filename) throws IOException {
        long startTime = System.currentTimeMillis(); // Start timer
    	String line, lastURL = "";
    	Hashtable hashMap = new Hashtable();
    	
        // Open the file given as argument
        BufferedReader infile = 
                new BufferedReader(new FileReader(filename)); 

    	line = infile.readLine();
    	
        while(line != null) {
            if(isPage(line)) {
                lastURL = getURL(line);
            } else {
                if(exists(hashMap, line)) {
                    //check for this url already exisiting with this name
                    URLList duplicateURLEntry = find(hashMap.get(line), lastURL); 
                    //if it is not a duplicate we update the hashMap
                    if(duplicateURLEntry == null) { 
                        hashMap.put(line, new URLList(lastURL, null));
                    }    
                } else {
                    hashMap.put(line, new URLList(lastURL, null));
                }
            }
            line = infile.readLine();
    	}
        
    	infile.close();
        long endTime = System.currentTimeMillis(); // Stop timer
        time = endTime - startTime; // Update variable
    	return hashMap;
    }
  
    public static boolean isPage(String line) { // check for page
    	if(line.length() < 6) {
            return false;
        }
    	
    	if(line.substring(0, 6).equals("*PAGE:")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String getURL(String pageLine) { // get an url
    	if(pageLine.length() < 6) {
            return "";
        }
    	return pageLine.substring(6);
    }
    
    public static URLList find(URLList l, String url) { // find an url
        URLList current = l;
        while(current != null) {
            if(current.url.equalsIgnoreCase (url)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    
    // print all urls that belongs to that word 
    public static void printURLs(URLList urlList) { 
    	int i = 0;
        
        while(urlList != null) {
            System.out.println(urlList.url);
            urlList = urlList.next;
            i++;
        }
       
        System.out.println("There were " + i + " URLs attached.");	
    }
}

public class SearchCmd4 {

    public static void main(String[] args) throws IOException {
        String name;
        
        // prompt the user which file to use
        BufferedReader readIn =
            new BufferedReader (new InputStreamReader (System.in)); 
      
        String file = null;

        while(file == null) {
            try {
                System.out.print("Please type which file you use for searching"); 
                System.out.println("e.g. : small, medium, large");
                System.out.print("Type: ");
                file = readIn.readLine().toUpperCase();
                
                if(!file.equals(SearchCmd2.searchFile.SMALL.toString()) 
                   & !file.equals(SearchCmd2.searchFile.MEDIUM.toString()) 
                   & !file.equals(SearchCmd2.searchFile.LARGE.toString())) {
                    
                    throw new Exception("Input must be: " 
                            + SearchCmd2.searchFile.SMALL.toString() 
                            + " / " + SearchCmd2.searchFile.MEDIUM.toString() 
                            + " / " + SearchCmd2.searchFile.LARGE.toString());
                }
            } catch(Exception ex) {
                file = null;
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }

        SearchCmd2.searchFile fileFormat = null;
        
        if(file.equals(SearchCmd2.searchFile.SMALL.toString())) {
                fileFormat = SearchCmd2.searchFile.SMALL;
        }
        
        if(file.equals(SearchCmd2.searchFile.MEDIUM.toString())) {
                fileFormat = SearchCmd2.searchFile.MEDIUM;
        }
        
        if(file.equals(SearchCmd2.searchFile.LARGE.toString())) {
                fileFormat = SearchCmd2.searchFile.LARGE;
        }
        
        // Read the file and create the linked list
        Hashtable hashMap = Searcher4.readHashMap(fileFormat.file()); 
        
        System.out.println("Datafile loaded in " + Searcher4.time / 1000F + " seconds.");
        //MyLog.logToFile(Searcher4.time / 1000F,"Log4-L.txt"); // Log times to file
        
        // Ask for a word to search
        BufferedReader inuser =
            new BufferedReader(new InputStreamReader (System.in)); 

        System.out.println("Hit return to exit.");
        boolean quit = false;
        URLList currentEntry = null;
        
        while(!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            
            if(name == null || name.length() == 0) {
                quit = true;
            } else if((currentEntry = hashMap.get(name)) != null) {
                System.out.println ("The word " + name + " has been found.");               
                System.out.println("URLs linked to " + name);
                Searcher4.printURLs(currentEntry);
                System.out.println("Search time: " + hashMap.searchTime/1000 
                        + " miliseconds\n");
            } else {
                System.out.println ("The word " + name + " has NOT been found."
                        + "\nSearch time: " + hashMap.searchTime + " milliseconds\n");
            }
        }
    }
}
