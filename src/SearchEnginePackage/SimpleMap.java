package SearchEnginePackage;

import java.util.Arrays;


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

public class SimpleMap
{
	int defaultCapacity = 2056;
	
	Bucket[] mappings = new Bucket[defaultCapacity];

	public URLList get(String s)
	{
            int i = getHash(s);
            return mappings[i] != null ? mappings[i].value : null;
	}
	
	public void put(String s, int key, URLList value)
	{
            boolean insert = true;
           
            int i = getHash(s);
            if(mappings[i] == null)
            {
                mappings[i] = new Bucket(s, key, value);
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