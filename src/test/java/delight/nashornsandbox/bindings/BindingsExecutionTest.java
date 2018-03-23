package delight.nashornsandbox.bindings;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptException;
import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BindingsExecutionTest {
    private File resourcesDirectory = new File("src/test/resources/delight.nashornsandbox.bindings");
    final String clearexit = "\\s+quit\\s*\\(\\)\\s*;|\\s+exit\\s*\\(\\)\\s*;";
    private DelightedBindings bindings;
    private NashornSandbox sandbox;
    private String functionTest;

    public BindingsExecutionTest() throws FileNotFoundException {
        File test1 = new File(resourcesDirectory.getAbsolutePath() + "/delightedBindingExecutionTest.js");
        functionTest = String.join("\n",
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(test1)))
                        .lines()
                        .collect(Collectors.toList()))
                .replaceAll(clearexit, "");
    }

    @Before
    public void beforeEach() throws ScriptException {
        sandbox = NashornSandboxes.create();
        sandbox.allowExitFunctions(false);
        sandbox.allowNoBraces(false);
        sandbox.allowGlobalsObjects(false);
        bindings = new DelightedBindings(sandbox.createBindings());
        sandbox.eval(functionTest, bindings);
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
