/* SearchCmd.java
   Written by Rune Hansen
   Updated by Alexandre Buisse <abui@itu.dk>
   Updated by Richard Peck 05/10/12
*/

import java.io.*;

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
	
    public static boolean exists (SimpleMap hashMap, String word) {
    	int hash = word.hashCode();
    	if(hashMap.get(word) != null)
    		return true;
    	
    	return false;    		
    }
    public static SimpleMap readHashMap(String filename) throws IOException {
    	String line, lastURL = "";
    	SimpleMap hashMap = new SimpleMap();
    	BufferedReader infile = new BufferedReader(new FileReader(filename));
    	
    	line = infile.readLine();
    	
    	while(line != null)
    	{
            if(isPage(line))
                    lastURL = getURL(line);
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
		System.out.println("There were "+ i + " URLs attached.");		
		
	}
}

public class SearchCmd4 {

    public static void main (String[] args) throws IOException {
        String name;

        // Check that a filename has been given as argument
        if (args.length != 1) {
            System.out.println("Usage: java SearchCmd <datafile>");
            System.exit(1);
        }
       
        // Read the file and create the linked list
        SimpleMap hashMap = Searcher4.readHashMap(args[0]);

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
                System.out.println ("The word \""+name+"\" has NOT been found.");
            }
        }
    }
}
