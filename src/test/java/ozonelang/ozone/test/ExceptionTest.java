package ozonelang.ozone.test;

import org.junit.Test;
import static ozonelang.ozone.core.AST.exception.OzoneException.raiseEx;
import ozonelang.ozone.core.AST.exception.OzoneException;
import ozonelang.ozone.core.AST.Location;
import ozonelang.ozone.core.AST.exception.StackTrace;

public class ExceptionTest {
    @Test
    public void testException() {
        StackTrace s = new StackTrace(new Location("<test>", "oh boi", 1L));
        for (int i = 0; i < 10; i++)
            s.addLocation(new Location("<test>", "oh no", (long) i));
        OzoneException e = new OzoneException("TestException", s, true);
        raiseEx(e);
    }
}
