package com.classuml.Model;
import java.util.ArrayDeque;

public class Memento 
{
    private ArrayDeque<UMLDiagram> statesUndo = new ArrayDeque<UMLDiagram>();
    private ArrayDeque<UMLDiagram> statesRedo = new ArrayDeque<UMLDiagram>();
    public Memento()
    {
    }
    //Deep copy constructor.
    public Memento(ArrayDeque<UMLDiagram> statesUndo2, ArrayDeque<UMLDiagram> statesRedo2)
    {
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
    //Returns undo deque.
    public ArrayDeque<UMLDiagram> getUndo()
    {
        return statesUndo;
    }
    //Returns redo deque.
    public ArrayDeque<UMLDiagram> getRedo()
    {
        return statesRedo;
    } 
    //Saves the passed in state.
    public boolean saveState(UMLDiagram curState)
    {

            statesUndo.push(curState);
            return true;
    }
    //Returns undo diagram.
    public UMLDiagram undoState()
    {
        if(statesUndo.isEmpty())
            return null;
        UMLDiagram state2 = new UMLDiagram(statesUndo.pop());
        return state2;
    }
    //Returns redo diagram.
    public UMLDiagram redo()
    {
        if(statesRedo.isEmpty())
            return null;
        UMLDiagram state3 = new UMLDiagram(statesRedo.pop());
        statesUndo.push(state3);
        return state3;
    }
    //Pops top of undo, used if command called failed but state was saved.
    public void popUndo()
    {
        statesUndo.pop();
    }
    //Pushes passed in diagram onto statesRedo.
    public void pushRedo(UMLDiagram curState)
    {
        statesRedo.push(curState);
    }
    //Clears redo when new command is issues after undoing.
    public void clearRedo()
    {
        statesRedo.clear();
    }
    
}
