package SearchEnginePackage;

class Bucket {
    final String key;
    final int hashKey;
    URLList value;

    public Bucket(String s, int k, URLList v)
    {
        key = s;
        hashKey = k;
        value = v;
    }
}

public class Hashtable
{
    int defaultCapacity = 2056;
    Bucket[] mappings = new Bucket[defaultCapacity];
    public long searchTime;
    
    public URLList get(String s)
    {
        long startTime = System.nanoTime();
        int i = getHash(s);

        if(i < 0 || i >= mappings.length)
        {
            return null;
        }
        if(mappings[i] != null && mappings[i].key.equalsIgnoreCase(s))
        {
            long endTime = System.nanoTime();
            searchTime = endTime - startTime;
            
            return mappings[i].value;
        }

        return null;
    }

    public void put(String s, URLList value)
    {
        int i = getHash(s);
        if(mappings[i] == null)
        {
            mappings[i] = new Bucket(s, i, value);
        }
        else
        {
            value.next = mappings[i].value;
            mappings[i].value = value;
        }	
    }

    public int getHash(String s)
    {
        return Math.abs(s.hashCode() % defaultCapacity);
    }
}