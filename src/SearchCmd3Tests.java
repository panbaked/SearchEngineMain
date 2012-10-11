import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

public class SearchCmd3Tests {
	
	@Test public void isAddGivenPageLineReturnsTrue()
	{	
		Assert.assertEquals(true, Searcher3.isPage("*PAGE:http://testpage.com"));
	}
	@Test public void isAddGivenNonPageLineReturnsFalse()
	{
		Assert.assertEquals(false, Searcher3.isPage("jibberish"));
	}
	@Test public void getURLGivenPageLineReturnsURL()
	{
		Assert.assertEquals("http://testpage.com", Searcher3.getURL("*PAGE:http://testpage.com"));
	}
	
	@Test public void readHTMLListPerformance()
	{
		try {
			Searcher3.readHtmlList("medium.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
