package bindings;

import delight.nashornsandbox.NashornSandbox;
import delight.nashornsandbox.NashornSandboxes;
import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptException;
import java.io.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BindingsExecutionTest {
    private File resourcesDirectory = new File("src/test/resources/bindings");

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
                        .collect(Collectors.toList()));

    }

    @Before
    public void beforeEach() {
        sandbox = NashornSandboxes.create();
        sandbox.allowExitFunctions(false);
        bindings = new DelightedBindings(sandbox.createBindings());
    }

    @Test
    public void executeFunction() throws ScriptException, NotScriptedException {
        sandbox.eval(functionTest, bindings);
        assertEquals(3.0, bindings.execute("singleLevel", 1, 2));
    }

    @Test
    public void DisabledExit() throws ScriptException, NotScriptedException {
        sandbox.eval(functionTest, bindings);
        assertEquals(3.0, bindings.execute("singleLevel", 1, 2));
    }
    @Test
    public void DisabledExitCalled() throws ScriptException, NotScriptedException {
        sandbox.eval(functionTest, bindings);
        try{
            bindings.execute("callsExit");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        //assertEquals(3.0, bindings.execute("singleLevel", 1, 2));
    }


}
