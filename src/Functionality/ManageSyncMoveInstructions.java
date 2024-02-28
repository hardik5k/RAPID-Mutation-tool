package Functionality;
import Functionality.ManageWaitInstructions;
import Main.*;


public class ManageSyncMoveInstructions {
    public static void mainfunction(Parser p, String lbl, Integer lineNumber){
        String[] tokens1 = lbl.split(" |,|;|:=");
        String instruction = tokens1[0].replaceAll("\\s", "");
        // System.out.println(tokens[0]+tokens[1]+tokens[2]);
        WaitInstruction w = new WaitInstruction();
        w.setInstructionName(instruction);
        w.setLineNumber(lineNumber);


        switch (instruction) {
            case "SyncMoveOn"  :
            {
                if(Parser.sync_bool1.equals(false)){
                    // System.out.println("1 is " + tokens1[1] + " 2 is " + tokens1[2]);
                    
                    Parser.temp_block.clearSync_stack();
                    // System.out.println(Parser.temp_block.getSync_stack());

                    Ident_Task_name temp_ident_task_name_on = new Ident_Task_name(tokens1[1],tokens1[2]);
                    Parser.temp_block.setName_on(temp_ident_task_name_on);
                    // System.out.println(Parser.temp_block.getName_on().get_task_name());

                    Parser.sync_bool1 = true;
                    Parser.sync_bool2 = false;
                }
                else if(Parser.sync_bool1.equals(true))
                {
                    System.out.println("Error !! No Nesting of SyncMove instructs allowed. ");
                    Parser.syncMoveboolFlag = false;
                    return;
                }
                break;
            }
            case "SyncMoveOff" :
            {
                if(Parser.sync_bool2.equals(false)){
                    // System.out.println("1 is " + tokens1[1]);
                    Ident_Task_name temp_ident_task_name_off = new Ident_Task_name(tokens1[1]);
                    Parser.temp_block.setName_off(temp_ident_task_name_off);

                    Parser.sync_bool2=true;
                    Parser.sync_bool1 = false;
                    
                    // To check whether temp block stores right value
                    // System.out.println("qwerty                                "+Parser.temp_block.getName_on().get_task_name());

                if(Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_task_name())
                   && Parser.sync_bool2.equals(true) && Parser.sync_bool1.equals(false))
                {
                    switch(p.taskName)
                    {
                        case "TASK1": {
                            if(!Parser.syncident_VAR_list1.contains(Parser.temp_block.getName_on().get_ident_name())
                                || !Parser.syncident_VAR_list1.contains(Parser.temp_block.getName_off().get_ident_name()))
                            {
                                System.out.println("      Error !! VAR syncident name not defined .");break;
                            }

                            if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){

                                Sync_move_block tem_temp_block = new Sync_move_block();
                                tem_temp_block.setName_on(Parser.temp_block.getName_on());
                                tem_temp_block.setName_off(Parser.temp_block.getName_off());
                                tem_temp_block.setSync_stack(Parser.temp_block.getSync_stack());


                                Parser.syncmap_rob1.addLast(tem_temp_block);
                                // System.out.println(Parser.syncmap_rob1);
                                Ident_Task_name temp_id_tsk = new Ident_Task_name("","");
                                Ident_Task_name temp_id_tsk2 = new Ident_Task_name("","");
                                // System.out.println(Parser.syncmap_rob1.peekFirst().getName_on().get_task_name());

                                // if(Parser.syncmap_rob2.size() == 2)
                                // System.out.println(Parser.syncmap_rob2.get(1).getName_on().get_task_name());

                                Parser.temp_block.setName_on(temp_id_tsk); 
                                Parser.temp_block.setName_off(temp_id_tsk2);

                                // Parser.temp_block.clearSync_stack();                                
                            }
                            else{
                                System.out.println("Error ! Ident name already exists , no duplication allowed .");
                                Parser.syncMoveboolFlag1 = false;
                                Parser.syncMoveFlag1Line = p.lineNumber;
                            }
                            break;
                        }
                        
                        
                        case "TASK2":{
                            if(!Parser.syncident_VAR_list2.contains(Parser.temp_block.getName_on().get_ident_name())
                                || !Parser.syncident_VAR_list2.contains(Parser.temp_block.getName_off().get_ident_name()))
                            {
                                System.out.println("      Error !! VAR syncident name not defined .");break;
                            }
                            if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){

                                Sync_move_block tem_temp_block = new Sync_move_block();
                                tem_temp_block.setName_on(Parser.temp_block.getName_on());
                                tem_temp_block.setName_off(Parser.temp_block.getName_off());
                                tem_temp_block.setSync_stack(Parser.temp_block.getSync_stack());

                                // To check whether the temp_block stack and stack entered in Linked list is same
                                // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                // System.out.println(Parser.temp_block.getSync_stack());
                                // System.out.println(tem_temp_block.getSync_stack());
                                // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");



                                Parser.syncmap_rob2.addLast(tem_temp_block);

                                // To see the stack of robmap2
                                // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                // for(int y = 0; y < Parser.syncmap_rob2.size(); y++) {
                                //     System.out.println(Parser.syncmap_rob2.get(y).getSync_stack());
                                // }
                                // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


                                // System.out.println(Parser.syncmap_rob2);
                                Ident_Task_name temp_id_tsk = new Ident_Task_name("","");
                                Ident_Task_name temp_id_tsk2 = new Ident_Task_name("","");
                                // System.out.println(Parser.syncmap_rob2.peekFirst().getName_on().get_task_name());
                                // if(Parser.syncmap_rob2.size() == 2)
                                // System.out.println(Parser.syncmap_rob2.get(1).getName_on().get_task_name());
                                Parser.temp_block.setName_on(temp_id_tsk); 
                                Parser.temp_block.setName_off(temp_id_tsk2);
                                // Parser.temp_block.clearSync_stack();

                                // System.out.println( "     *********************   ");

                                // break;
                            }
                            else{
                                System.out.println("Error ! Ident name already exists , no duplication allowed .");
                                Parser.syncMoveboolFlag1 = false;
                                Parser.syncMoveFlag1Line = p.lineNumber;
                            }
                            break;
                        }
                        case "TASK3": {
                            if(!Parser.syncident_VAR_list3.contains(Parser.temp_block.getName_on().get_ident_name())
                                || !Parser.syncident_VAR_list3.contains(Parser.temp_block.getName_off().get_ident_name()))
                            {
                                System.out.println("      Error !! VAR syncident name not defined .");break;
                            }
                            if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){

                                Sync_move_block tem_temp_block = new Sync_move_block();
                                tem_temp_block.setName_on(Parser.temp_block.getName_on());
                                tem_temp_block.setName_off(Parser.temp_block.getName_off());
                                tem_temp_block.setSync_stack(Parser.temp_block.getSync_stack());


                                Parser.syncmap_rob3.addLast(tem_temp_block);
                                // System.out.println(Parser.syncmap_rob3);
                                Ident_Task_name temp_id_tsk = new Ident_Task_name("","");
                                Ident_Task_name temp_id_tsk2 = new Ident_Task_name("","");
                                // System.out.println(Parser.syncmap_rob3.peekFirst().getName_on().get_task_name());

                                Parser.temp_block.setName_on(temp_id_tsk); 
                                Parser.temp_block.setName_off(temp_id_tsk2);

                                // break;
                            }
                            else{
                                System.out.println("Error ! Ident name already exists , no duplication allowed .");
                                Parser.syncMoveboolFlag1 = false;
                                Parser.syncMoveFlag1Line = p.lineNumber;
                            }
                            break;
                        }
                        case "TASK4": {
                            if(!Parser.syncident_VAR_list4.contains(Parser.temp_block.getName_on().get_ident_name())
                                || !Parser.syncident_VAR_list4.contains(Parser.temp_block.getName_off().get_ident_name()))
                            {
                                System.out.println("      Error !! VAR syncident name not defined .");break;
                            }
                            if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){

                                Sync_move_block tem_temp_block = new Sync_move_block();
                                tem_temp_block.setName_on(Parser.temp_block.getName_on());
                                tem_temp_block.setName_off(Parser.temp_block.getName_off());
                                tem_temp_block.setSync_stack(Parser.temp_block.getSync_stack());


                                Parser.syncmap_rob4.addLast(tem_temp_block);
                                // System.out.println(Parser.syncmap_rob4);
                                Ident_Task_name temp_id_tsk = new Ident_Task_name("","");
                                Ident_Task_name temp_id_tsk2 = new Ident_Task_name("","");
                                // System.out.println(Parser.syncmap_rob4.peekFirst().getName_on().get_task_name());

                                Parser.temp_block.setName_on(temp_id_tsk); 
                                Parser.temp_block.setName_off(temp_id_tsk2);

                                // break;
                            }
                            else{
                                System.out.println("Error ! Ident name already exists , no duplication allowed .");
                                Parser.syncMoveboolFlag1 = false;
                                Parser.syncMoveFlag1Line = p.lineNumber;
                            }
                            break;
                        }
                    }   
                }
       
                }
                    else if(Parser.sync_bool2.equals(true)) {
                        System.out.println("Error !! No Nesting of SyncMove instructs allowed. ");
                        Parser.syncMoveboolFlag = false;
                        Parser.syncMoveFlag2Line = p.lineNumber;
                        return;
                    }
                    break;
                }
            case "MoveL": {
                // System.out.println(tokens1[2]);
                Parser.temp_block.addSync_stack(tokens1[2]);
                break;
            }
            case "MoveC": {
                // System.out.println(tokens1[2]);
                Parser.temp_block.addSync_stack(tokens1[2]);
                break;
            }
            case "MoveJ": {
                // System.out.println(tokens1[2]);
                Parser.temp_block.addSync_stack(tokens1[2]);
                break;
            }
            case "MoveAbsJ": {
                // System.out.println(tokens1[2]);
                Parser.temp_block.addSync_stack(tokens1[2]);
                break;
            }
    }

        // if(Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_task_name()) && 
        // Parser.sync_bool2.equals(true) && Parser.sync_bool1.equals(false)
        // ){
        //     switch(p.taskName)
        //     {
        //         case "TASK1": {
        //             if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){
        //                 // Parser.hmap_rob1.put(temp_ident_name,temp_task_name);
        //                 Parser.syncmap_rob1.put(Parser.temp_block.getName_on().get_ident_name(),Parser.temp_block);                   
        //             }
        //             else{
        //                 System.out.println("Error ! Ident name already exists , no duplication allowed .");
        //             }
        //             break;
        //         }      
        //         case "TASK2":{
        //             if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){
        //                 Parser.syncmap_rob2.put(Parser.temp_block.getName_on().get_ident_name(),Parser.temp_block);
        //                 // break;
        //             }
        //             else{
        //                 System.out.println("Error ! Ident name already exists , no duplication allowed .");
        //             }
        //             break;
        //         }
        //         case "TASK3": {
        //             if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){
        //                 Parser.syncmap_rob3.put(Parser.temp_block.getName_on().get_ident_name(),Parser.temp_block);
        //                 // break;
        //             }
        //             else{
        //                 System.out.println("Error ! Ident name already exists , no duplication allowed .");
        //             }
        //             break;
        //         }
        //         case "TASK4": {
        //             if(!Parser.globalTasklist.containsKey(Parser.temp_block.getName_on().get_ident_name())){
        //                 Parser.syncmap_rob4.put(Parser.temp_block.getName_on().get_ident_name(),Parser.temp_block);
        //                 // break;
        //             }
        //             else{
        //                 System.out.println("Error ! Ident name already exists , no duplication allowed .");
        //             }
        //             break;
        //         }
        //         }   
        // }    
        // if(instruction.equals("SyncMoveOn") && Parser.sync_bool1.equals(false)){
        //     System.out.println("1 is " + tokens1[1] + " 2 is " + tokens1[2]);
        //     Ident_Task_name temp_ident_task_name_on = new Ident_Task_name(tokens1[1],tokens1[2]);
        //     temp_block.setName_on(temp_ident_task_name_on);
        //     Parser.sync_bool1 = true;
        //     Parser.sync_bool2 = false;
        // }
        // if(instruction.equals("SyncMoveOff") && Parser.sync_bool2.equals(false)){
        //     System.out.println("1 is " + tokens1[1]);
        //     Ident_Task_name temp_ident_task_name_off = new Ident_Task_name(tokens1[1]);
        //     temp_block.setName_on(temp_ident_task_name_off);
        //     Parser.sync_bool2=true;
        //     Parser.sync_bool1 = false;
        // }  
    }
    
}
