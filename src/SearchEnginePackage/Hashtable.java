package SearchEnginePackage;

class Bucket {
    final String key;
    final int hashKey;
    URLList value;

    public Bucket(String s, int k, URLList v) {
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
            long endTime = System.nanoTime(); // Stop timer
            searchTime = endTime - startTime; // Set timer variable
            
            return mappings[i].value;
        }
        return null;
    }

    public void put(String s, URLList value){
        int i = getHash(s);
        if(mappings[i] == null) {
            mappings[i] = new Bucket(s, i, value);
        } else {
            value.next = mappings[i].value;
            mappings[i].value = value;
        }	
    }

    public int getHash(String s) {
        return Math.abs(s.hashCode() % defaultCapacity); // Using modulo to find the hash key
    }
}