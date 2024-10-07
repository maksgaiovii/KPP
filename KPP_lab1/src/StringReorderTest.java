import org.junit.*;

import static org.junit.Assert.assertEquals;
import java.util.*;

public class StringReorderTest {

    @Test
    public void testReorderStrings() {
        StringReorder stringReorder = new StringReorder();

        List<String> input = Arrays.asList("abc", "dce", "123");
        String result = stringReorder.reorderStrings(input);

        assertEquals("(a,b,c,d,c,e,1,2,3)", result);
    }
}
