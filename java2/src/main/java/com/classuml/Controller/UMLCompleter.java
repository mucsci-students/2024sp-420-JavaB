package com.classuml.Controller;

import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UMLCompleter implements Completer {
    private final Completer delegate;

    public UMLCompleter() {
        this.delegate = new StringsCompleter(getCommandsArr());
    }

    public UMLCompleter(ArrayList<String> getCommands){
        this.delegate = new StringsCompleter(getCommands);
    }

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        return delegate.complete(buffer, cursor, candidates);
    }

    private Set<String> getCommands() {
        // Return a set of all the commands supported by the UML CLI
        return Set.of("menu", "addclass", "deleteclass", "renameclass", "addparameter", "deleteparameter", "renameparameter",
                "replaceparams", "addfield", "renamefield", "deletefield", "addmethod", "deletemethod", "renamemethod",
                "addrelationship", "deleterelationship", "changetype", "listclasses", "listclass", "listrelationships",
                "snapshot", "save", "load", "help", "exit", "undo", "redo");
    }

    private ArrayList<String> getCommandsArr(){
        ArrayList<String> arr = new ArrayList<>();
        arr.addAll(getCommands());
        return arr;

    }
}