package SearchEnginePackage;

/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
   Updated by Richard Peck 05/10/12
*/

import java.io.*;
import java.util.Scanner;

class HTMLList {
    String str;
    HTMLList next;
    URLList urlList;
    
    HTMLList (String s, HTMLList n) {
        str = s;
        next = n;
    }
}

class URLList {
	String url;
	URLList next;
	
	URLList(String _url, URLList n)
	{
		url = _url;
		next = n;
	}
}
class Searcher3 {
    
    public static long time;
	
    public static boolean exists (HTMLList l, String word) {
        
        long startTime = System.currentTimeMillis();
        while (l != null) {
            if (l.str.equals (word)) {
                
                long endTime = System.currentTimeMillis();
                time = endTime - startTime;
                return true;
            }
            l = l.next;
        }
        
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;
        return false;
    }

    public static HTMLList readHtmlList (String filename) throws IOException {
        
        long startTime = System.currentTimeMillis();
        String name;
        HTMLList start, current, tmp;
        String lastURL = "";
        // Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));

        name = infile.readLine(); //Read the first line
        start = new HTMLList (name, null);
        current = start;
        name = infile.readLine(); // Read the next line
        
        if(isPage(name)) {
            lastURL = getURL(name);
        }
        
        while(name != null)
        {
        	 if(isPage(name)) {
                lastURL = getURL(name);
            }
        	 else
        	 {
        		 if(exists(start, name)) //Have we found this name before, if so we add the last url to this name
        		 {
        			 HTMLList existingEntry = find(start, name);
        		
        			 URLList duplicateURLEntry = find(existingEntry.urlList, lastURL); //check for this url already exisiting with this name
        			 if(duplicateURLEntry == null)
        			 {
        				 URLList urlList = new URLList(lastURL, null);
        				 urlList.next = existingEntry.urlList;
            			 existingEntry.urlList = urlList; //swap our pointer to the new starting element in the list
        			 }
        		
        		 }
        		 else //Add a new entry with a new urllist
        		 {
        			 tmp = new HTMLList(name, null);
        			 tmp.urlList = new URLList(lastURL, null);
        			 current.next = tmp;
        			 current = tmp;
        		 }
        			 
        	 }
        	
        	name = infile.readLine();
        }
       
        infile.close(); // Close the file
        
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;
        return start;
    }
    
    public static boolean isPage(String line)
    {
    	if(line.length() < 6) {
            return false;
        }
    	
    	if(line.substring(0, 6).equals("*PAGE:")) {
            return true;
        }
    	else {
            return false;
        }
    }
    
    public static String getURL(String pageLine)
    {
    	if(pageLine.length() < 6) {
            return "";
        }
    	return pageLine.substring(6);
    }
    
    public static HTMLList find(HTMLList l, String name)
    {
    	 while (l != null) {
             if (l.str.equals (name)) {
                 return l;
             }
             l = l.next;
         }
    	 return null;
    }
    
    public static URLList find(URLList l, String url)
    {
	   	 while (l != null) {
	         if (l.url.equals (url)) {
	             return l;
	         }
	         l = l.next;
	     }
		 return null;
    }
    public static void printURLs(HTMLList l)
	{
    	System.out.println("URLs linked to "+ l.str);
    	
    	URLList urlList = l.urlList;
    	int i = 0;
		while(urlList != null)
		{
			System.out.println(urlList.url);
			urlList = urlList.next;
			i++;
		}
		System.out.println("There were "+ i + " URLs attached.Search time:" + Searcher3.time + " milliseconds");		
		
	}
}

public class SearchCmd3 {

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
            if (!file.equals(SearchCmd2.searchFile.SMALL.toString()) & !file.equals(SearchCmd2.searchFile.MEDIUM.toString()) & !file.equals(SearchCmd2.searchFile.LARGE.toString()))
            {
               throw new Exception("Input must be: " + SearchCmd2.searchFile.SMALL.toString() + " / " + SearchCmd2.searchFile.MEDIUM.toString() + " / " + SearchCmd2.searchFile.LARGE.toString());
            }
         }
         catch (Exception ex)
         {
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
        HTMLList l = Searcher3.readHtmlList (fileFormat.file());
         System.out.println("Datafile loaded in " + Searcher3.time / 1000F + " seconds.");

        // Ask for a word to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in));

        System.out.println ("Hit return to exit.");
        boolean quit = false;
        HTMLList currentEntry = null;
        while (!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if (name == null || name.length() == 0) {
                quit = true;
            } else if ((currentEntry = Searcher3.find (l, name)) != null) {
                System.out.println ("The word \""+name+"\" has been found.");
                Searcher3.printURLs(currentEntry);
            } else {
                System.out.println ("The word \""+name+"\" has NOT been found.Search time:" + Searcher3.time + " milliseconds");
            }
        }
    }
}
