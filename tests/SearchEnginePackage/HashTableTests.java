package SearchEnginePackage;

import java.io.IOException;

import org.junit.Test;


public class HashTableTests {
	
    @Test public void readHTMLListPerformance()
    {
        try {
            Searcher4.readHashMap("medium.txt");
        } catch (IOException e) {
                // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
