import java.io.IOException;

import org.junit.Test;


public class SimpleMapTests {
	
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
