package com.classuml.Model;
import com.classuml.Controller.UMLCli;
import com.classuml.Model.UMLDiagram;
import com.classuml.View.UMLGui;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memento 
{
    Deque<UMLDiagram> statesUndo = new ArrayDeque<UMLDiagram>();
    Deque<UMLDiagram> statesRedo = new ArrayDeque<UMLDiagram>();
    UMLDiagram state;
    public boolean saveState(UMLDiagram curState)
    {
        if(curState != null)
        {
            statesUndo.add(curState);
            return true;
        }
        return false;
    }
    public UMLDiagram undoState()
    {
        state.equals(null);
        if(statesUndo.isEmpty())
            return state;
        state = statesUndo.pop();
        statesRedo.add(state);
        return state;
    }
    public UMLDiagram redo()
    {
        state.equals(null);
        if(statesRedo.isEmpty)
            return state;
        state = statesRedo.pop();
        return state;
    }
    public void clearStates()
    {
        statesUndo.clear();
        statesRedo.clear();
    }
    
}
