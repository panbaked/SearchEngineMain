package SearchEnginePackage;

import SearchEnginePackage.Searcher3;
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

    @Test public void existsFindExistingElement()
    {
        HTMLList2 n0 = new HTMLList2("*PAGE:n0", null);
        HTMLList2 n1 = new HTMLList2("n1", null);
        HTMLList2 n2 = new HTMLList2("n2", null);
        
        n0.next = n1;
        n1.next = n2;
        
        int expected = 1;
        int result = Searcher2.exists(n0, "n1");
        
        Assert.assertEquals(expected, result);
    }
    
    @Test public void existsNonExistantElementReturnsNull()
    {
        HTMLList2 n0 = new HTMLList2("*PAGE:n0", null);
        HTMLList2 n1 = new HTMLList2("n1", null);
        HTMLList2 n2 = new HTMLList2("n2", null);
        
        n0.next = n1;
        n1.next = n2;
        
        int expected = 0;
        int result = Searcher2.exists(n0, "nonesense");
        
        Assert.assertEquals(expected, result);
    }
}
