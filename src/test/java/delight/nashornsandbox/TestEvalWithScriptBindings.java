package delight.nashornsandbox;

import delight.nashornsandbox.exceptions.ScriptCPUAbuseException;
import org.junit.jupiter.api.Test;


import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("all")
public class TestEvalWithScriptBindings {
  @Test
  public void test() throws ScriptCPUAbuseException, ScriptException {
    final NashornSandbox sandbox = NashornSandboxes.create();
    Bindings binding1 = sandbox.createBindings();
    binding1.put("y", 2);

    Bindings binding2 = sandbox.createBindings();
    binding2.put("y", 4);
    
    final Object res1 = sandbox.eval("function cal() {var x = y + 1; return x;} cal();", binding1);
    assertEquals(3.0, res1);
    
    final Object res2 = sandbox.eval("function cal() {var x = y + 1; return x;} cal();", binding2);
    assertEquals(5.0, res2);
    
  }
  
  @Test
  public void testWithCPUAndMemory() throws ScriptCPUAbuseException, ScriptException {
    final NashornSandbox sandbox = NashornSandboxes.create();
    sandbox.setMaxCPUTime(100);
    sandbox.setMaxMemory(1000 * 1024);
    sandbox.setExecutor(Executors.newSingleThreadExecutor());
    Bindings binding1 = sandbox.createBindings();
    binding1.put("y", 2);

    Bindings binding2 = sandbox.createBindings();
    binding2.put("y", 4);

    final Object res1 = sandbox.eval("function cal() {var x = y + 1; return x;} cal();", binding1);
    assertEquals(3.0, res1);

    final Object res2 = sandbox.eval("function cal() {var x = y + 1; return x;} cal();", binding2);
    assertEquals(5.0, res2);
    
  }
}
