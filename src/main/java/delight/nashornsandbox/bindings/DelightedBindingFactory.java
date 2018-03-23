package delight.nashornsandbox.bindings;

import delight.nashornsandbox.internal.JsSanitizer;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class DelightedBindingFactory {
    private final JsSanitizer sanitizer;
    private final ScriptEngine scriptEngine;
    private String addedScript = "";

    DelightedBindingFactory(boolean allowNoBraces, int maxPreparedStatements) {
        scriptEngine = new NashornScriptEngineFactory().getScriptEngine();
        sanitizer = new JsSanitizer(scriptEngine, maxPreparedStatements, allowNoBraces);
    }

    DelightedBindingFactory removeExit() {
        addedScript += "function quit(){};function exit(){};";
        return this;
    }

    DelightedBindingFactory removeWrite() {
        addedScript += "function readFully(){};function readLine(){};";
        return this;
    }

    DelightedBindingFactory removeLoad() {
        addedScript += "function load(){};function loadWithNewGlobal(){};";
        return this;
    }

    DelightedBindingFactory removePrint() {
        addedScript += "function print(){};function echo(){};";
        return this;
    }

    DelightedBindings build(String script) throws ScriptException {
        String securedJs = sanitizer.secureJs(script);
        securedJs = addedScript + securedJs;
        DelightedBindings bindings = new DelightedBindings(securedJs, scriptEngine.createBindings());
        scriptEngine.eval(securedJs, bindings);
        return bindings;
    }
}
