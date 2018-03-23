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
        addedScript += "var quit=function(){};var exit=function(){};";
        return this;
    }

    DelightedBindingFactory removeWrite() {
        addedScript += "var readFully=function(){};var readLine=function(){};";
        return this;
    }

    DelightedBindingFactory removeLoad() {
        addedScript += "var load=function(){};var loadWithNewGlobal=function(){};";
        return this;
    }

    DelightedBindingFactory removePrint() {
        addedScript += "var print=function(){};var echo = function(){};";
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
