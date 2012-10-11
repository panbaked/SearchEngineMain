import java.io.IOException;

import org.junit.Test;


public class SearchCmd2Tests {
	@Test public void readHTMLListPerformance()
	{
		try {
			Searcher2.readHtmlList("medium.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
