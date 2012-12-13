package SearchEnginePackage;

class Bucket {
    final int hashKey;
    HTMLList value;

    public Bucket(int k, HTMLList v) {
        hashKey = k;
        value = v;
    }
}

public class Hashtable {
    int defaultCapacity = 2056; // Set table size
    Bucket[] mappings = new Bucket[defaultCapacity];
    public long searchTime; // Timer variable
    
    public URLList get(String s) {
        long startTime = System.nanoTime(); // Start timer
        int i = getHash(s);

        if(i < 0 || i >= mappings.length) {
            return null;
        }
        if(mappings[i] != null) {
            HTMLList current = mappings[i].value;
            while(current != null) {
                if(current.str.equalsIgnoreCase(s)) {
                    long endTime = System.nanoTime(); // Stop timer
                    searchTime = endTime - startTime; // Set timer variable
            
                    return current.urlList;
                }
                current = current.next;
            }
           
        }
        return null;
    }

    public void put(String s, URLList value){
        int i = getHash(s);
        if(s.equals("graduate"))
            System.out.println("BEEP");
        if(mappings[i] == null) {
            HTMLList newListEntry = new HTMLList(s, null);
            newListEntry.urlList = value;
            mappings[i] = new Bucket(i, newListEntry);
        } else {
            HTMLList current = mappings[i].value;
            boolean existingInsert = false;
            while(current != null) {
                if(current.str.equals(s)) {
                    value.next = current.urlList;
                    current.urlList = value;
                    existingInsert = true;
                    break;
                }
                
                current = current.next;
            }
            
            if(!existingInsert){
                 HTMLList newListEntry = new HTMLList(s, mappings[i].value);
                 newListEntry.urlList = value;
                 mappings[i].value = newListEntry;
            }
                
        }	
    }

    public int getHash(String s) {
        return Math.abs(s.hashCode() % defaultCapacity); // Using modulo to find the hash key
    }
}