package delight.nashornsandbox.bindings;

import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptException;
import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BindingsExecutionTest {
    private DelightedBindings bindings;
    private String functionTest;

    public BindingsExecutionTest() throws FileNotFoundException {
        File resourcesDirectory = new File("src/test/resources/delight/nashornsandbox/bindings");
        File test1 = new File(resourcesDirectory.getAbsolutePath() + "/delightedBindingExecutionTest.js");
        functionTest = String.join("\n",
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(test1)))
                        .lines()
                        .collect(Collectors.toList()));
    }

    @Before
    public void beforeEach() throws ScriptException {
        bindings = new DelightedBindingFactory(false,100)
                .removeExit()
                .build(functionTest);
    }

    @Test
    public void executeFunction() throws NotScriptedException {
        assertEquals(3.0, bindings.execute("singleLevel", 1, 2));
    }

    @Test
    public void RemovedExit() throws NotScriptedException {
        assertEquals(3.0, bindings.execute("singleLevel", 1, 2));
    }

    @Test
    public void RemovedExitCalled() throws  NotScriptedException {
        bindings.execute("callsExit");
    }


}
