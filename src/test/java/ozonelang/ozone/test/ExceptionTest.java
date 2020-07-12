package ozonelang.ozone.test;

import org.junit.Test;
import static ozonelang.ozone.core.runtime.exception.OzoneException.raiseEx;
import ozonelang.ozone.core.runtime.exception.OzoneException;
import ozonelang.ozone.core.AST.Context;
import ozonelang.ozone.core.runtime.exception.StackTrace;

public class ExceptionTest {
    @Test
    public void testException() {
        StackTrace s = new StackTrace(new Context("<test>", "oh boi", 1L));
        for (int i = 0; i < 10; i++)
            s.addLocation(new Context("<test>", "oh no", (long) i));
        OzoneException e = new OzoneException("this is a test exception", "TestException", s, true);
        raiseEx(e);
    }
}
