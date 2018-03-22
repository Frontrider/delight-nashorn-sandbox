package bindings;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class NestedBindingsExecutionTest {
    private final String classTest;
    private File resourcesDirectory = new File("src/test/resources/bindings");

    private DelightedBindings bindings;
    private NashornSandbox sandbox;

    public NestedBindingsExecutionTest() throws FileNotFoundException {

        File test2 = new File(resourcesDirectory.getAbsolutePath() + "/delightedBindingNestedExecutionTest.js");
        classTest = String.join("\n",
                new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(test2)))
                        .lines()
                        .collect(Collectors.toList()));
    }
    @Before
    public void beforeEach() {
        sandbox = NashornSandboxes.create();
        bindings = new DelightedBindings(sandbox.createBindings());
    }

    @Test
    public void executeNested() throws ScriptException, NotScriptedException {
        sandbox.eval(classTest,bindings);
        assertEquals(12.0,bindings.execute("TestedNested.nested.final"));
    }

    public void disabledFunctions()
    {

    }

}