package Functionality;

import Functionality.ManageWaitInstructions;
import Main.*;

public class ManageWaitSyncTaskInstructions {
    public static void mainfunction(Parser p, String lbl, Integer lineNumber){
        String[] tokens1 = lbl.split(" |,|;");
        String instruction = tokens1[0].replaceAll("\\s", "");
        WaitInstruction w = new WaitInstruction();
        w.setInstructionName(instruction);
        w.setLineNumber(lineNumber);
        
        String temp_ident_name = tokens1[1], temp_task_name = tokens1[2];

        if(Parser.globalTasklist.containsKey(temp_task_name)){
            switch(p.taskName)
            {
                case "TASK1": {
                    // if(!Parser.globalTasklist.containsKey(temp_ident_name)){
                        if(!Parser.syncident_VAR_list1.contains(temp_ident_name)){
                            System.out.println("      Error !! VAR syncident name not defined .");break;
                        }
                        if(Parser.globalTasklist.containsKey(temp_task_name) && !Parser.hmap_rob1.containsKey(temp_ident_name)){
                        Parser.hmap_rob1.put(temp_ident_name,temp_task_name);
                        
                    }
                    else{
                        System.out.println("Error ! Ident name already exists , no duplication allowed .");
                        Parser.waitSyncboolFlag1 = false;
                        Parser.waitSyncFlag1Line = p.lineNumber;
                    }
                    break;
                }
                case "TASK2":{
                    if(!Parser.syncident_VAR_list2.contains(temp_ident_name)){
                        System.out.println("      Error !! VAR syncident name not defined .");break;
                    }
                    if(Parser.globalTasklist.containsKey(temp_task_name) && !Parser.hmap_rob2.containsKey(temp_ident_name)){
                        Parser.hmap_rob2.put(temp_ident_name,temp_task_name);
                        // break;
                    }
                    else{
                        System.out.println("      Error ! Ident name already exists , no duplication allowed .");
                        Parser.waitSyncboolFlag1 = false;
                        Parser.waitSyncFlag1Line = p.lineNumber;
                    }
                    break;
                }
                case "TASK3": {
                    if(!Parser.syncident_VAR_list3.contains(temp_ident_name)){
                        System.out.println("      Error !! VAR syncident name not defined .");break;
                    }
                    if(Parser.globalTasklist.containsKey(temp_task_name) && !Parser.hmap_rob3.containsKey(temp_ident_name)){
                        Parser.hmap_rob3.put(temp_ident_name,temp_task_name);
                        // break;
                    }
                    else{
                        System.out.println("Error ! Ident name already exists , no duplication allowed .");
                        Parser.waitSyncboolFlag1 = false;
                        Parser.waitSyncFlag1Line = p.lineNumber;
                    }
                    break;
                }
                case "TASK4": {
                    if(!Parser.syncident_VAR_list4.contains(temp_ident_name)){
                        System.out.println("      Error !! VAR syncident name not defined .");break;
                    }
                    if(Parser.globalTasklist.containsKey(temp_task_name) && !Parser.hmap_rob4.containsKey(temp_ident_name)){
                        Parser.hmap_rob4.put(temp_ident_name,temp_task_name);
                        // break;
                    }
                    else{
                        System.out.println("Error ! Ident name already exists , no duplication allowed .");
                        Parser.waitSyncboolFlag1 = false;
                        Parser.waitSyncFlag1Line = p.lineNumber;
                    }
                    break;
                }
                }
        }
        else if(!Parser.globalTasklist.containsKey(temp_task_name))
        {
            System.out.println("Error ! Task name does not exist in global PERS tasks list .");
            Parser.waitSyncboolFlag2 = false;
            Parser.waitSyncFlag2Line = p.lineNumber;
            return;
        }
    }

}
