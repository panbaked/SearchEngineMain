/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
   Updated by Richard Peck 05/10/12
*/

import java.io.*;

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
	
    public static boolean exists (HTMLList l, String word) {
        while (l != null) {
            if (l.str.equals (word)) {
                return true;
            }
            l = l.next;
        }
        return false;
    }

    public static HTMLList readHtmlList (String filename) throws IOException {
        String name;
        HTMLList start, current, tmp;
        String lastURL = "";
        // Open the file given as argument
        BufferedReader infile = new BufferedReader(new FileReader(filename));

        name = infile.readLine(); //Read the first line
        start = new HTMLList (name, null);
        current = start;
        name = infile.readLine(); // Read the next line
        
        if(isPage(name))
        	lastURL = getURL(name);
        
        while(name != null)
        {
        	 if(isPage(name))
             	lastURL = getURL(name);
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

        return start;
    }
    
    public static boolean isPage(String line)
    {
    	if(line.length() < 6)
    		return false;
    	
    	if(line.substring(0, 6).equals("*PAGE:"))
    		return true;
    	else
    		return false;
    }
    
    public static String getURL(String pageLine)
    {
    	if(pageLine.length() < 6)
    		return "";
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
		System.out.println("There were "+ i + " URLs attached.");		
		
	}
}

public class SearchCmd3 {

    public static void main (String[] args) throws IOException {
        String name;

        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println("Usage: java SearchCmd <datafile>");
            System.exit(1);
        }
       
        // Read the file and create the linked list
        HTMLList l = Searcher3.readHtmlList (args[0]);

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
                System.out.println ("The word \""+name+"\" has NOT been found.");
            }
        }
    }
}
