package delight.nashornsandbox;

import delight.nashornsandbox.exceptions.ScriptCPUAbuseException;
import org.junit.jupiter.api.Test;

import javax.script.ScriptException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("all")
public class TestIfElse {
  @Test
  public void testIfElse() throws ScriptCPUAbuseException, ScriptException {
    final NashornSandbox sandbox = NashornSandboxes.create();
    sandbox.setMaxCPUTime(500);
    sandbox.setExecutor(Executors.newSingleThreadExecutor());
    final StringBuilder _builder = new StringBuilder();
    _builder.append("if (true)\n");
    _builder.append("var x=1;\n");
    _builder.append("else\n");
    _builder.append("var y=2;\t\t\n");
    sandbox.eval(_builder.toString());
    assertEquals(Integer.valueOf(1), sandbox.eval("x"));
    final StringBuilder _builder_1 = new StringBuilder();
    _builder_1.append("for (var i=0;i<10;i++) {\n");
    _builder_1.append("    ");
    _builder_1.append("if (false)\n");
    _builder_1.append("    \t");
    _builder_1.append("var x=3;\n");
    _builder_1.append("    ");
    _builder_1.append("else\n");
    _builder_1.append("    \t");
    _builder_1.append("var y=4;\t\n");
    _builder_1.append("}\n");
    sandbox.eval(_builder_1.toString());
    assertEquals(Integer.valueOf(4), sandbox.eval("y"));
    sandbox.getExecutor().shutdown();
  }
}
