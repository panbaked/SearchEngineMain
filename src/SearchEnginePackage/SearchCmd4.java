package SearchEnginePackage;

/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
   Updated by Richard Peck 05/10/12
*/

import java.io.*;
import java.util.Scanner;

class HTMLHash {
	String str;
	int hash;
	URLList urlList;
	
	HTMLHash(String s)
	{
		str = s;
		hash = s.hashCode();
	}
}
class Searcher4 {
    
    public static long time;
	
    public static boolean exists (Hashtable hashMap, String word) {
        
        long startTime = System.currentTimeMillis();
    	int hash = word.hashCode();
    	if(hashMap.get(word) != null) {
            
            long endTime = System.currentTimeMillis();
            time = endTime - startTime;
            return true;
        }
        
         long endTime = System.currentTimeMillis();
         time = endTime - startTime;    	
    	return false;    		
    }
    public static Hashtable readHashMap(String filename) throws IOException {
        
        long startTime = System.currentTimeMillis();
    	String line, lastURL = "";
    	Hashtable hashMap = new Hashtable();
    	BufferedReader infile = new BufferedReader(new FileReader(filename));
    	
    	line = infile.readLine();
    	
    	while(line != null)
    	{
            if(isPage(line)) {
                lastURL = getURL(line);
            }
            else
            {
                if(exists(hashMap, line))
                {
                    URLList duplicateURLEntry = find(hashMap.get(line), lastURL); //check for this url already exisiting with this name
                    if(duplicateURLEntry == null) //if it is not a duplicate we update the hashMap
                    {
                            hashMap.put(line, line.hashCode(), new URLList(lastURL, null));
                    }
                }
                else
                {
                    hashMap.put(line, line.hashCode(), new URLList(lastURL, null));
                }
            }
            line = infile.readLine();

    	}
    	infile.close();
        long endTime = System.currentTimeMillis();
        time = endTime - startTime;
    	return hashMap;
    }
    public static HTMLHash[] expand(HTMLHash[] toExpand, int size)
    {
    	HTMLHash[] tmp = new HTMLHash[size];
    	System.arraycopy(toExpand, 0, tmp, 0, toExpand.length);
    	return tmp;
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
    public static void printURLs(URLList urlList)
	{    	
    	int i = 0;
		while(urlList != null)
		{
			System.out.println(urlList.url);
			urlList = urlList.next;
			i++;
		}
		System.out.println("There were "+ i + " URLs attached.Search time:" + Searcher4.time +"\"milliseconds");		
		
	}
}

public class SearchCmd4 {

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
        Hashtable hashMap = Searcher4.readHashMap(fileFormat.file());
        System.out.println("Datafile loaded in " + Searcher4.time / 1000F + " seconds.");

        // Ask for a word to search
        BufferedReader inuser =
            new BufferedReader (new InputStreamReader (System.in));

        System.out.println ("Hit return to exit.");
        boolean quit = false;
        URLList currentEntry = null;
        while (!quit) {
            System.out.print ("Search for: ");
            name = inuser.readLine(); // Read a line from the terminal
            if (name == null || name.length() == 0) {
                quit = true;
            } else if ((currentEntry = hashMap.get(name)) != null) {
                System.out.println ("The word \""+name+"\" has been found.");
                System.out.println("URLs linked to "+ name);
                Searcher4.printURLs(currentEntry);
            } else {
                System.out.println ("The word \""+name+"\" has NOT been found.Search time:" + Searcher4.time +"\"milliseconds");
            }
        }
    }
}
