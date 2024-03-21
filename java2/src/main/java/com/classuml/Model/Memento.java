package com.classuml.Model;
import java.util.ArrayDeque;
import java.util.Deque;

public class Memento 
{
    private ArrayDeque<UMLDiagram> statesUndo = new ArrayDeque<UMLDiagram>();
    private ArrayDeque<UMLDiagram> statesRedo = new ArrayDeque<UMLDiagram>();
    public Memento()
    {
    }
    public Memento(ArrayDeque<UMLDiagram> statesUndo2, ArrayDeque<UMLDiagram> statesRedo2)
    {
        statesUndo.clear();
        statesRedo.clear();
        for(UMLDiagram umlUndo: statesUndo2)
        {
            UMLDiagram undoList = new UMLDiagram(umlUndo);
            this.statesUndo.add(undoList);
        }
        for(UMLDiagram umlRedo: statesRedo2)
        {
            UMLDiagram redoList = new UMLDiagram(umlRedo);
            this.statesRedo.add(redoList);
        }
        
    }
    public ArrayDeque<UMLDiagram> getUndo()
    {
        return statesUndo;
    }
    public ArrayDeque<UMLDiagram> getRedo()
    {
        return statesRedo;
    } 
    public boolean saveState(UMLDiagram curState)
    {
        System.out.println("Saved!");
        if(curState != null)
        {
            statesUndo.push(curState);
            return true;
        }  
        return false;
    }
    public UMLDiagram undoState()
    {
        System.out.println("Here!");
        if(statesUndo.isEmpty())
            return null;
        System.out.println(statesUndo.toString());
        //statesUndo.pop();
        UMLDiagram state2 = new UMLDiagram(statesUndo.pop());
        System.out.println("Divider!!!");
        System.out.println(statesUndo.toString());
        statesRedo.push(state2);
        return state2;
    }
    public UMLDiagram redo()
    {
        System.out.println("Redo called!");
        if(statesRedo.isEmpty())
            return null;
        UMLDiagram state3 = new UMLDiagram(statesRedo.pop());
        return state3;
    }
    public void clearStates()
    {
        statesUndo.clear();
        statesRedo.clear();
    }
    
}
