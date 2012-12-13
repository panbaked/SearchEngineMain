package SearchEnginePackage;

class Bucket {
    final String key;
    final int hashKey;
    HTMLList value;

    public Bucket(String s, int k, HTMLList v) {
        key = s;
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
        if(mappings[i] != null && mappings[i].key.equalsIgnoreCase(s)) {
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
        if(mappings[i] == null) {
            HTMLList newListEntry = new HTMLList(s, null);
            newListEntry.urlList = value;
            mappings[i] = new Bucket(s, i, newListEntry);
            
        } else {
            HTMLList current = mappings[i].value;
            while(current != null) {
                if(current.str.equalsIgnoreCase(s)) {
                    value.next = current.urlList;
                    current.urlList = value;
                }
                
                current = current.next;
            }
        }	
    }

    public int getHash(String s) {
        return Math.abs(s.hashCode() % defaultCapacity); // Using modulo to find the hash key
    }
}