package com.classuml.Model;
import java.util.ArrayDeque;
import java.util.Deque;

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
        state = null;
        if(statesUndo.isEmpty())
            return state;
        state = statesUndo.pop();
        statesRedo.add(state);
        return state;
    }
    public UMLDiagram redo()
    {
        state = null;
        if(statesRedo.isEmpty())
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
