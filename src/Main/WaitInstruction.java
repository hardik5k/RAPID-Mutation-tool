package Main;

import java.util.*;

public class WaitInstruction {
    private String instructionName;
    private ArrayList<Variable> vars = new ArrayList<Variable>();
    private Integer lineNumber;
    private Float waitTime;
    private String condition;  

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public void addVariable(Variable v) {
        vars.add(v);
    }

    public void removeVariable(Variable v) {
        vars.remove(v);
    }

    public ArrayList<Variable> getVars() {
        return vars;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Float getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Float waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return "WaitInstruction{" +
                "instructionName='" + instructionName + '\'' +
                ", vars=" + vars +
                ", lineNumber=" + lineNumber +
                ", waitTime=" + waitTime +
                ", condition='" + condition + '\'' +
                '}';
    }
}

class TaskList{
    String name;
    Bool_list blist;

    public TaskList(String name,Bool_list blist)
    {
        this.name = name;
        this.blist = blist;
    }

    public static Boolean compareBoolList(Bool_list boolean_list1, Bool_list boolean_list2) 
    {
        Boolean result = false;
        if(boolean_list1.equals(boolean_list2))
        {
            result = true;
            
        }
        return result;
    }
}

// class Ident_Task_name{
//     String ident_name, task_name;
//     public Ident_Task_name(String ident_name, String task_name)
//     {
//         this.ident_name = ident_name;
//         this.task_name = task_name;
//     }

// }

// class Bool_list{
//     Boolean rob1, rob2,rob3,rob4;

//     public Bool_list(Boolean rob1, Boolean rob2, Boolean rob3, Boolean rob4)
//     {
//         this.rob1 = rob1;
//         this.rob2 = rob2;
//         this.rob3 = rob3;
//         this.rob4 = rob4;
//     }

//     // @Override
//     // public String toString()
//     // {
//     //     return 
//     // }

// }
