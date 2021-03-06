package SearchEnginePackage;

import java.io.*;


class HTMLList3 {
    String str;
    HTMLList3 next;
    URLList urlList;
    
    HTMLList3 (String s, HTMLList3 n) {
        str = s;
        next = n;
    }
}

class URLList {
    String url;
    URLList next;

    URLList(String _url, URLList n) {
        url = _url;
        next = n;
    }
}

class Searcher3 { 
    public static long time; // Variable for timer
    
    public static boolean exists(HTMLList3 l, String word) {    
        
        while (l != null) {
            if (l.str.equals(word)) {
                return true;
            }
            l = l.next;
        }
        return false;
    }

    public static HTMLList3 readHtmlList(String filename) throws IOException {    
        long startTime = System.currentTimeMillis(); // Start timer
        String name;
        HTMLList3 start, current, tmp;
        String lastURL = "";
        
        // Open the file given as argument
        BufferedReader infile = 
                new BufferedReader(new FileReader(filename)); 

        name = infile.readLine(); // Read the first line
        start = new HTMLList3 (name, null);
        current = start;
        name = infile.readLine(); // Read the next line
        
        if(isPage(name)) {
            lastURL = getURL(name);
        }
        
        while(name != null) {
            if(isPage(name)) {
                lastURL = getURL(name);
            } else {
                // Have we found this name before, if so we add the last url to this name
        	if(exists(start, name)) { 
                    HTMLList3 existingEntry = find(start, name);
                    // Check for this url already exisiting with this name
                    URLList duplicateURLEntry = find(existingEntry.urlList, lastURL); 
                    
                    if(duplicateURLEntry == null) {
                        URLList urlList = new URLList(lastURL, null);
                        urlList.next = existingEntry.urlList;
                        // Swap our pointer to the new starting element in the list
                        existingEntry.urlList = urlList; 
                    }
                    
                } else { // Add a new entry with a new urllist
                    tmp = new HTMLList3(name, null);
                    tmp.urlList = new URLList(lastURL, null);
                    current.next = tmp;
                    current = tmp;
                }			 
            }
            name = infile.readLine();
        }
        
        infile.close(); // Close the file  
        long endTime = System.currentTimeMillis(); // Stop timer
        time = endTime - startTime; // Update variable
        return start;
    }
    
    public static boolean isPage(String line) { // Check if it is a page
    	if(line.length() < 6) {
            return false;
        }
    	
    	if(line.substring(0, 6).equals("*PAGE:")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String getURL(String pageLine) { // Get url
    	if(pageLine.length() < 6) {
            return "";
        }
    	return pageLine.substring(6);
    }
    
    public static HTMLList3 find(HTMLList3 l, String name) { // Find a word
        long startTime = System.nanoTime(); // Start timer
        
        while (l != null) {
            if (l.str.equals(name)) {
                long endTime = System.nanoTime(); // Stop timer
                time = endTime - startTime; // Update variable
                return l;
            }
            l = l.next;
        }
        long endTime = System.nanoTime(); // Stop timer
        time = endTime - startTime; // Update variable
        return null;
    }
    
    public static URLList find(URLList l, String url) { // Find a url
        while (l != null) {
            if (l.url.equals(url)) {
                return l;
            }
            l = l.next;
        }
        return null;
    }
    
    public static void printURLs(HTMLList3 l) {
    	System.out.println("URLs linked to " + l.str);
    	URLList urlList = l.urlList;
    	int i = 0;
        
        while(urlList != null) {
            System.out.println(urlList.url);
            urlList = urlList.next;
            i++;
        }
        
        System.out.println("There were " + i + " URLs attached.");
    }
}

public class SearchCmd3 {
   
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

    public static void main (String[] args) throws IOException {
        String name;
        
        //prompt the user which file to use
        BufferedReader readIn =
            new BufferedReader (new InputStreamReader (System.in)); 
        
        String file = null;

        while (file == null) {
            try {
                System.out.print("Please type which file you use for searching "); 
                System.out.println("e.g. : small, medium, large");
                System.out.print("Type: ");
                file = readIn.readLine().toUpperCase();
                if (!file.equals(SearchCmd2.searchFile.SMALL.toString()) 
                        & !file.equals(SearchCmd2.searchFile.MEDIUM.toString()) 
                        & !file.equals(SearchCmd2.searchFile.LARGE.toString())) {
                    
                    throw new Exception("Input must be: " 
                            + SearchCmd2.searchFile.SMALL.toString() 
                            + " / " + SearchCmd2.searchFile.MEDIUM.toString() 
                            + " / " + SearchCmd2.searchFile.LARGE.toString());
                }
            } catch (Exception ex) {
                file = null;
                System.out.println("Invalid input: " + ex.getMessage());
            }
        }

        SearchCmd2.searchFile fileFormat = null;
        if (file.equals(SearchCmd2.searchFile.SMALL.toString())) {
            fileFormat = SearchCmd2.searchFile.SMALL;
        }
        if (file.equals(SearchCmd2.searchFile.MEDIUM.toString())) {
            fileFormat = SearchCmd2.searchFile.MEDIUM;
        }
        if (file.equals(SearchCmd2.searchFile.LARGE.toString())) {
            fileFormat = SearchCmd2.searchFile.LARGE;
        }
        
        // Read the file and create the linked list
        HTMLList3 l = Searcher3.readHtmlList (fileFormat.file()); 
        System.out.println("Datafile loaded in " + Searcher3.time / 1000F + " seconds.");
        //MyLog.logToFile(Searcher3.time / 1000F,"Log3.txt"); // Log times to file
        System.exit(0);
        
        // Ask for a word to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in)); 

        System.out.println("Hit return to exit.");
        boolean quit = false;
        HTMLList3 currentEntry = null;
        
        while (!quit) {
            System.out.print("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal

            if (name == null || name.length() == 0) {
                quit = true;
            } else if ((currentEntry = Searcher3.find(l, name)) != null) {
                System.out.println("The word " + name + " has been found.");
                Searcher3.printURLs(currentEntry);
                System.out.println("Search time: " + Searcher3.time/1000 + " miliseconds");
            } else {
                System.out.println("The word " + name 
                        + " has NOT been found.\nSearch time: " 
                        + Searcher3.time + " milliseconds");
            }
        }

    }
}
