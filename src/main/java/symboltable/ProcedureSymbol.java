package symboltable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProcedureSymbol extends Symbol implements Scope {
    public Map<String, Symbol> arguments = new LinkedHashMap<>();
    Scope enclosingScope;
    public ProcedureSymbol next = null;

    public ProcedureSymbol(String name, Type retType, Scope enclosingScope) {
        super(name, retType);
        this.enclosingScope = enclosingScope;
    }

    @Override
    public String getScopeName() {
        return name;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public void define(Symbol sym) {
        arguments.put(sym.name, sym);
        sym.scope = this;
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = arguments.get(name);
        if (s != null) return s;
        // if not here, check any enclosing scope
        if (getEnclosingScope() != null) {
            return getEnclosingScope().resolve(name);
        }
        return null; // not found
    }

    // 👇 会用到么？
    @Override
    public Symbol resolveWithin(String name) {
        return arguments.get(name);
    }

    public String toString() {
        String[] temp = super.toString().split(":");
        return "proc " + temp[0] + " " + arguments.values() + ":" + temp[1];
    }
}
