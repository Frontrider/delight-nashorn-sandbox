package delight.nashornsandbox.bindings;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Bindings;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DelightedBindings implements Bindings {

    private final String script;

    private final Bindings parentBindings;

    DelightedBindings(String script, Bindings parentBindings) {
        this.script = script;
        this.parentBindings = parentBindings;
    }

    @Override
    public Object put(String name, Object value) {
        return parentBindings.put(name, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> toMerge) {
        parentBindings.putAll(toMerge);
    }

    @Override
    public void clear() {
        parentBindings.clear();
    }

    @Override
    public Set<String> keySet() {
        return parentBindings.keySet();
    }

    @Override
    public Collection<Object> values() {
        return parentBindings.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return parentBindings.entrySet();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return parentBindings.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        parentBindings.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        parentBindings.replaceAll(function);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return parentBindings.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return parentBindings.remove(key, value);
    }

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        return parentBindings.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(String key, Object value) {
        return parentBindings.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return parentBindings.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return parentBindings.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return parentBindings.compute(key, remappingFunction);
    }

    @Override
    public Object merge(String key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return parentBindings.merge(key, value, remappingFunction);
    }

    @Override
    public int size() {
        return parentBindings.size();
    }

    @Override
    public boolean isEmpty() {
        return parentBindings.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return parentBindings.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return parentBindings.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return parentBindings.get(key);
    }

    @Override
    public Object remove(Object key) {
        return parentBindings.remove(key);
    }

    public Object execute(String path, Object... args) throws NotScriptedException,RuntimeException{
        ScriptObjectMirror global = (ScriptObjectMirror) parentBindings.get("nashorn.global");
        Object fun;
        String[] splitPath = null;
        if (path.contains(".")) {
            splitPath = path.split("\\.");
            fun = global.get(splitPath[0]);
        } else
            fun = global.get(path);
        if (fun instanceof ScriptObjectMirror) {
            if (splitPath == null) {
              return ((ScriptObjectMirror) fun).call(fun, args);
            } else {
                ScriptObjectMirror mirror =getMember(splitPath,path,global);
                return mirror.call(fun,args);
            }
        } else {
            throw new NotScriptedException(path + " is not scripted!");
        }
    }

    private ScriptObjectMirror getMember(String[] path, String originalPath,ScriptObjectMirror parent) throws NotScriptedException{
        Object obj = parent.get(path[0]);
        if (obj instanceof ScriptObjectMirror) {
            if (path.length == 1) {
                return (ScriptObjectMirror) obj;
            } else {
                String[] newPath = new String[path.length - 1];
                System.arraycopy(path, 1, newPath, 0, path.length - 1);
                return getMember(newPath, originalPath, (ScriptObjectMirror) obj);
            }
        } else
            throw new NotScriptedException(path[0] + " on path " + originalPath + " is nor scripted!");
    }

    public String getScript() {
        return script;
    }
}
