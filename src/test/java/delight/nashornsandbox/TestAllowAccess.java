package delight.nashornsandbox;

import java.io.File;

import javax.script.ScriptException;

import delight.nashornsandbox.exceptions.ScriptCPUAbuseException;
import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
public class TestAllowAccess {
  @Test
  public void test_file() throws ScriptCPUAbuseException, ScriptException {
    final NashornSandbox sandbox = NashornSandboxes.create();
    sandbox.allow(File.class);
    sandbox.eval("var File = Java.type('java.io.File'); File;");
  }
}
