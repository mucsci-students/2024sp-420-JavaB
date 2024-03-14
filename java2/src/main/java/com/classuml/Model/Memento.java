package com.classuml.Model;

import com.classuml.Controller.UMLCli;
import com.classuml.View.UMLGui;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memento 
{
    Deque<UMLDiagram> states = new ArrayDeque<UMLDiagram>();
    public boolean saveState(UMLDiagram curState)
    {
        if(curState != null)
        {
            states.add(curState);
            return true;
        }
        return false;
    }
    public UMLDiagram redoState()
    {
        return states.pop();
    }
    public void clearStates()
    {
        states.clear();
    }
    
}
