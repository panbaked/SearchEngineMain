package SearchEnginePackage;
import java.io.IOException;
import junit.framework.Assert;
import org.junit.Test;


public class SearchCmd2Tests {
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
    
    @Test public void readHTMLListPerformance()
    {
        try 
        {
            Searcher2.readHtmlList("medium.txt");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
