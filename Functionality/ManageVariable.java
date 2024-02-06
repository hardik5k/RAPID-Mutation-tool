package Functionality;

import Main.Bool_list;
import Main.Bool_bool;
import Main.Parser;
import Main.RobTarget;
import Main.Variable;
import Main.WaitInstruction;
import java.util.*;

import java.io.IOException;



public class ManageVariable {
    public static void mainfunction(Parser p,String lbl, Integer lineNumber) throws IOException {
        if (lbl.contains("robtarget")) {
            RobTarget r = new RobTarget();
            r.setDataType("robtarget");
            r.setDeclaredLoc(lineNumber);
            if (lbl.startsWith("PERS")) {
                r.setGlobal();
            }
            String robvar = r.extractVariable(lbl);
            //assigning value during declaration
            if (lbl.contains(":=")) {
                //const value assignment
                if (lbl.contains("[")) {
                    r.assignValue(lbl);
                }
                else    //assignment using existing variables
                {
                    r.extractRobTargetOperands(p,lbl, lineNumber);
                }
            }
            r.setName(robvar);
            if(!p.currentProcName.equals("") && !p.currentProcName.equals("MAIN"))
            {
                p.procMap.get(p.currentProcName).addWriteRobTargetVar(robvar);
            }
            else{
                r.addWriteLocation(lineNumber);
            }
            p.robTargetMap.put(robvar, r);
//            System.out.println(r);
        }
        else {
            Variable v = new Variable();
            v.setDeclaredLoc(lineNumber);

            if (lbl.startsWith("PERS")) {
                v.setGlobal();

                // tasks types lists have to be global
                if (lbl.contains("tasks")) {
                    String taskvar = manageTasks(p,lbl);
                    System.out.println();
                    System.out.println("PERS task added: " + taskvar);
                    System.out.println("--------");
                    v.setDataType("tasks");
                    v.setName(taskvar);
                    if(!p.currentProcName.equals("") && !p.currentProcName.equals("MAIN"))
                    {
                        p.procMap.get(p.currentProcName).addWriteVars(taskvar);
                    }
                    else{
                        v.addWriteLocation(lineNumber);
                    }
                    p.varMap.put(taskvar, v);
                    // System.out.println("*******************");
                    // // System.out.println(p.varMap);
                    // // for (String key: p.varMap.keySet()){  
                    // //     System.out.println(key+ " = " + p.varMap.get(key).getName());
                    // // }
                    // p.varMap.entrySet().forEach(entry->{
                    //     System.out.println(entry.getKey() + " = " + entry.getValue());  
                    // });
                    // System.out.println("*******************");
                    return;
                }
            }

            // num
            if (lbl.contains("num")) {
                v.setDataType("num");
            }

            // bool
            else if (lbl.contains("bool")) {
                v.setDataType("bool");
            }

            // string
            else if (lbl.contains("string")) {
                v.setDataType("string");
            }

            //syncident
            else if (lbl.contains("syncident")) {
                v.setDataType("syncident");
                String temp = lbl;
                String[] tokens4 = temp.split("syncident |;");
                // System.out.println(p.taskName);
                switch(p.taskName){
                    case "TASK1" :{
                        if(
                            !Parser.syncident_VAR_list1.contains(tokens4[1]))
                        Parser.syncident_VAR_list1.add(tokens4[1]);
                        break;
                    }
                    case "TASK2" :{
                        if(
                            // Parser.flag_task1 &&
                            !Parser.syncident_VAR_list2.contains(tokens4[1]))
                        Parser.syncident_VAR_list2.add(tokens4[1]);
                        break;
                    }
                    case "TASK3" :{
                        if(
                            // Parser.flag_task2 &&
                            !Parser.syncident_VAR_list3.contains(tokens4[1]))
                        Parser.syncident_VAR_list3.add(tokens4[1]);
                        break;
                    }
                    case "TASK4" :{
                        if(
                            // Parser.flag_task3 &&
                            !Parser.syncident_VAR_list4.contains(tokens4[1]))
                        {
                        Parser.syncident_VAR_list4.add(tokens4[1]);
                        // System.out.println("               "+tokens4[1]);
                        // System.out.println("hgggggggggggggggggggggggggg");
                        }
                        break;
                    }
                }
                // System.out.println();
                // System.out.println("."+lbl);
                // System.out.println(tokens4[1]+".");
            }
            

            String var = v.extractVariable(lbl);
            v.extractVarOperands(p,lbl, lineNumber);
            v.setName(var);
            if(!p.currentProcName.equals("") && !p.currentProcName.equals("MAIN"))
            {
                p.procMap.get(p.currentProcName).addWriteVars(var);
            }
            else{
                v.addWriteLocation(lineNumber);
            }
            p.varMap.put(var, v);
//            System.out.println(v);
        }
    }


    private static String manageTasks(Parser p,String lbl) {
        String[] tokens = lbl.split(":=|;");
        String[] left = tokens[0].split(" ");

        // System.out.println(tokens[1]);
        // String rob_tokens = tokens[1].replace("],[", "");
        // System.out.println(rob_tokens);
        // String[] right = tokens[1].split
        

        String var = "";
        for (int i = 2; i < left.length; i++) {
            var += left[i];
        }
        String[] words = var.split("[{]|[}]");
        // System.out.println("*************"+words[0]);

        Bool_bool tempBoolist = new Bool_bool(false,false,false,false);
        Bool_list tempBoolist2 = new Bool_list(false,false,false,false);
        if(tokens[1].contains("T_ROB1"))
        {
            tempBoolist.set_rob1(true);
            tempBoolist2.set_rob1(true);
        }
        if(tokens[1].contains("T_ROB2"))
        {
            tempBoolist.set_rob2(true);
            tempBoolist2.set_rob2(true);
        }
        if(tokens[1].contains("T_ROB3"))
        {
            tempBoolist.set_rob3(true);
            tempBoolist2.set_rob3(true);
        }
        if(tokens[1].contains("T_ROB4"))
        {
            tempBoolist.set_rob4(true);
            tempBoolist2.set_rob4(true);
        }

        String tempo =words[0];
        if(p.taskName.equals("TASK1")){
            Parser.PERS_VAR_list1.add(tempo);
        }
        if(p.taskName.equals("TASK2")){
            Parser.PERS_VAR_list2.add(tempo);
        }
        if(p.taskName.equals("TASK3")){
            Parser.PERS_VAR_list3.add(tempo);
        }
        if(p.taskName.equals("TASK4")){
            Parser.PERS_VAR_list4.add(tempo);
        }

        if((!Parser.globalTasklist.containsKey(tempo) ))
        // if(!Parser.globalTasklist.containsKey(tempo) && p.taskName =="TASK1")
        {
            // tempo.insertedat(p.taskName);
            tempBoolist.set_insertedAtthistask(p.taskName);
            Parser.globalTasklist.put(tempo,tempBoolist);
            Parser.globalTasklist2.put(tempo,tempBoolist2);
            // System.out.println("     Task added to global task list      ");
            
        }
        else if(((Parser.flag_task1 == false && p.taskName !="TASK1")||
            (Parser.flag_task2 == false && p.taskName !="TASK2")||
            (Parser.flag_task3 == false && p.taskName !="TASK3")|| 
            (Parser.flag_task4 == false && p.taskName !="TASK4")) 
            && 
            (p.taskName == (Parser.globalTasklist.get(tempo).get_insertedAtthistask())))
        // ((Parser.flag_task1 == true && (p.taskName=="Task2" || p.taskName=="Task3" || p.taskName=="Task4")) ||
        // (Parser.flag_task1 == true && Parser.flag_task2 == true && (p.taskName=="Task3" || p.taskName=="Task4")) ||
        // (Parser.flag_task1 == true && Parser.flag_task2 == true && Parser.flag_task3 == true && (p.taskName=="Task4"))
        // ))
        {
            System.out.println("Error ! The Rob_task already contains that task "+ tempo);
        }  
        else if (Parser.globalTasklist.containsKey(tempo) &&  (!Parser.globalTasklist.get(tempo).get_rob1().equals(tempBoolist.get_rob1())
            || !Parser.globalTasklist.get(tempo).get_rob2().equals(tempBoolist.get_rob2())
            || !Parser.globalTasklist.get(tempo).get_rob3().equals(tempBoolist.get_rob3())
            || !Parser.globalTasklist.get(tempo).get_rob4().equals(tempBoolist.get_rob4())
        )){
            // System.out.println(" temp bool list "+tempBoolist);
            System.out.println("Error ! The PERS variables mismatch . ");
            System.out.println("(The processing will be done according to the PERS variable of "+Parser.globalTasklist.get(tempo).get_insertedAtthistask()+
            ", where it was inserted in the global task list .)");
            return "";
        }

        return words[0];
    }
}