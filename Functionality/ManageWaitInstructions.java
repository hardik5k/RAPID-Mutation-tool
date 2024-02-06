package Functionality;

import Main.Parser;
import Main.Variable;
import Main.WaitInstruction;

public class ManageWaitInstructions {

    public static void mainfunction(Parser p, String lbl, Integer lineNumber) {
        String[] tokens = lbl.split(" |;|,|=|AND|OR|[(]|[)]");
        String instruction = tokens[0].replaceAll("\\s", "");
        
        WaitInstruction w = new WaitInstruction();
        w.setInstructionName(instruction);
        w.setLineNumber(lineNumber);

        switch (instruction) {
            case "WaitSyncTask":
            {
                process1(p, w, lineNumber, tokens, instruction);
                break;
            }
            case "SyncMoveOn":{
                process1(p, w, lineNumber, tokens, instruction);
                break;
            }
            case "WaitTime": {
                for (int i = 1; i < tokens.length; i++) {
                    if (!tokens[i].equals("")) {
                        Float fval = Float.parseFloat(tokens[i]);
                        w.setWaitTime(fval);
                        break;
                    }
                }
            }
            break;
            case "WaitTestAndSet":{
                process2(p, w, lineNumber, tokens, instruction);
                break;
            }
            case "SyncMoveOff":{
                process2(p, w, lineNumber, tokens, instruction);
                break;
            }
            case "WaitUntil": {
                int count = 0;
                for (int i = 1; i < tokens.length; i++) {
                    if (!tokens[i].equals("")) {
                        if (count % 2 == 0) {
                            if (p.varMap.containsKey(tokens[i])) {
                                Variable v = p.varMap.get(tokens[i]);
                                if(!p.currentProcName.equals("") && !p.currentProcName.equals("MAIN"))
                                {
                                    p.procMap.get(p.currentProcName).addReadVar(tokens[i]);
                                }
                                else{
                                    v.addReadLocation(lineNumber);
                                }
                                w.addVariable(p.varMap.get(tokens[i]));
                            }
                        }
                        count++;
                    }
                }
            }
        }
        p.waitInstructionsList.add(w);
    }

    private static void process1(Parser p, WaitInstruction w, Integer lineNumber, String[] tokens, String instruction) {
        if (instruction.equals("SyncMoveOn")) {
            p.syncMoveOn = true;
            p.syncMoveOff = false;
        }
        int count = 0;
        for (int i = 1; i < tokens.length; i++) {
            if (!tokens[i].equals("")) {
                if (count < 2) {
                    if (p.varMap.containsKey(tokens[i])) {
                        if(!p.currentProcName.equals("") && !p.currentProcName.equals("MAIN")){
                            p.procMap.get(p.currentProcName).addReadVar(tokens[i]);
                        }
                        else
                        {
                            p.varMap.get(tokens[i]).addReadLocation(lineNumber);
                            // System.out.println(lineNumber);
                        }

                        w.addVariable(p.varMap.get(tokens[i]));
                        // System.out.println(p.varMap.get(tokens[i]));
                    }
                }
                count++;
            }
        }
    }

    private static void process2(Parser p, WaitInstruction w, Integer lineNumber, String[] tokens, String instruction) {
        if (instruction.equals("SyncMoveOff")) {
            p.syncMoveOn = false;
            p.syncMoveOff = true;
        }

        for (int i = 1; i < tokens.length; i++) {
            if (!tokens[i].equals("")) {
                if (p.varMap.containsKey(tokens[i])) {
                    Variable v = p.varMap.get(tokens[i]);
                    if(!p.currentProcName.equals("") && !p.currentProcName.equals("MAIN"))
                    {
                        p.procMap.get(p.currentProcName).addReadVar(tokens[i]);
                    }
                    else{
                        v.addReadLocation(lineNumber);
                    }
                    w.addVariable(p.varMap.get(tokens[i]));
                    break;
                }
            }
        }
    }
}






/*  The given below is an algo for comparing the hashmaps
 by taking Different cases that which all Robots have the
  same WaitSyncTask  */


// public static void check_wait_sync_at_end()
// {
//     Bool_list hashmap_tops_boolean_list = new Bool_list(false,false,false,false);
//     for (Map.Entry<String,Bool_list> mapElement :
//         Parser.globalTasklist2.entrySet()) {
//         String key = mapElement.getKey();
//         // Finding the value
//         // using getValue() method
//         Boolean value1 = (mapElement.getValue()).rob1;
//         System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//         for(int i=1;i<12;i++){
//             if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob3) == Parser.getFirst(Parser.hmap_rob4) 
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 1");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 2");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) != Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) == Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 3");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob4) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 4");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) == Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 5");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             Parser.getFirst(Parser.hmap_rob3) != Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 // hashmap_tops_boolean_list.rob3 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 6 ");
//                 }
//             }
//             // else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob2) && 
//             // Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             // Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             // Parser.getFirst(Parser.hmap_rob3) != Parser.getFirst(Parser.hmap_rob4)
//             // ){
//             //     hashmap_tops_boolean_list.rob1 =true;
//             //     hashmap_tops_boolean_list.rob2 =true;
//             //     // hashmap_tops_boolean_list.rob1 =true;
//             //     // hashmap_tops_boolean_list.rob1 =true;
//             //     if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//             //     {
//             //         if(hashmap_tops_boolean_list.rob1 == true){
//             //             Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//             //         }
//             //         if(hashmap_tops_boolean_list.rob2 == true){
//             //             Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//             //         }
//             //         if(hashmap_tops_boolean_list.rob3 == true){
//             //             Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//             //         }
//             //         if(hashmap_tops_boolean_list.rob4 == true){
//             //             Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//             //         }
//             //         System.out.println("Tested OKAY !");
//             //         // continue;
//             //     }
//             //     else{
//             //         System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 7 ");
//             //     }
//             // }
//             else if(Parser.getFirst(Parser.hmap_rob1) != Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             Parser.getFirst(Parser.hmap_rob1) != Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 8");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) != Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob1) != Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             Parser.getFirst(Parser.hmap_rob3) == Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 9");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob4) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             Parser.getFirst(Parser.hmap_rob3) != Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 10");
//                 }
//             }
//             else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             Parser.getFirst(Parser.hmap_rob3) != Parser.getFirst(Parser.hmap_rob4)
//             ){
//                 hashmap_tops_boolean_list.rob1 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob3 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 11 ");
//                 }
//             }
//             // else if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob3) && 
//             // Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             // Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob4) &&
//             // Parser.getFirst(Parser.hmap_rob3) != Parser.getFirst(Parser.hmap_rob4)
//             // ){
//             //     hashmap_tops_boolean_list.rob1 =true;
//             //     // hashmap_tops_boolean_list.rob1 =true;
//             //     hashmap_tops_boolean_list.rob3 =true;
//             //     // hashmap_tops_boolean_list.rob1 =true;
//             //     if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//             //     {
//             //         if(hashmap_tops_boolean_list.rob1 == true){
//             //             Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//             //         }
//             //         if(hashmap_tops_boolean_list.rob2 == true){
//             //             Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//             //         }
//             //         if(hashmap_tops_boolean_list.rob3 == true){
//             //             Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//             //         }
//             //         if(hashmap_tops_boolean_list.rob4 == true){
//             //             Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//             //         }
//             //         System.out.println("Tested OKAY !");
//             //         // continue;
//             //     }
//             //     else{
//             //         System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 12");
//             //     }
//             // }
//             else if(Parser.getFirst(Parser.hmap_rob4) == Parser.getFirst(Parser.hmap_rob2) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob3) && 
//             Parser.getFirst(Parser.hmap_rob2) != Parser.getFirst(Parser.hmap_rob1) &&
//             Parser.getFirst(Parser.hmap_rob3) != Parser.getFirst(Parser.hmap_rob1)
//             ){
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob2 =true;
//                 // hashmap_tops_boolean_list.rob1 =true;
//                 hashmap_tops_boolean_list.rob4 =true;
//                 if(Parser.globalTasklist2.containsValue(hashmap_tops_boolean_list))
//                 {
//                     if(hashmap_tops_boolean_list.rob1 == true){
//                         Parser.hmap_rob1.remove(Parser.getFirstKey(Parser.hmap_rob1));
//                     }
//                     if(hashmap_tops_boolean_list.rob2 == true){
//                         Parser.hmap_rob2.remove(Parser.getFirstKey(Parser.hmap_rob2));
//                     }
//                     if(hashmap_tops_boolean_list.rob3 == true){
//                         Parser.hmap_rob3.remove(Parser.getFirstKey(Parser.hmap_rob3));
//                     }
//                     if(hashmap_tops_boolean_list.rob4 == true){
//                         Parser.hmap_rob4.remove(Parser.getFirstKey(Parser.hmap_rob4));
//                     }
//                     System.out.println("Tested OKAY !");
//                     // continue;
//                 }
//                 else{
//                     System.out.println("Error !! The sequence of WaitSync Tasks is wrong . 13");
//                 }
//             }
//             hashmap_tops_boolean_list.rob1 =false;
//                 hashmap_tops_boolean_list.rob1 =false;
//                 hashmap_tops_boolean_list.rob1 =false;
//                 hashmap_tops_boolean_list.rob1 =false;
//         }
//         // if(((mapElement.getValue().rob1 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob1)) ||
//         // (mapElement.getValue().rob2 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob2)) ||
//         // (mapElement.getValue().rob3 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob3)) ||
//         // (mapElement.getValue().rob4 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob4)) )
//         // &&( mapElement.getKey() != )
//         // )
//         // {
//         //     // if(Parser.getFirst(Parser.hmap_rob1) == mapElement.getKey()){
//         //     //     // hashmap_tops_boolean_list.rob1 = true;
//         //     //     continue;
//         //     // }
//         //     // else {
//         //     //     hashmap_tops_boolean_list.rob1 = true;
//         //     // }
//         //     // continue;
//         // }
//         // Printing the key-value pairs
//         System.out.println(key + " : " + value1);
//     }
//     // for(int i=1;i<12;i++){
//     //     if(Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob2) && 
//     //     Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob3) && 
//     //     Parser.getFirst(Parser.hmap_rob1) == Parser.getFirst(Parser.hmap_rob4) && 
//     //     Parser.getFirst(Parser.hmap_rob1)!= null)
//     //     {
//     //     }
//     //     System.out.println(Parser.getFirst(Parser.hmap_rob1));
//     // }
//     // Parser.getFirst(Parser.hmap_rob1);
//     // for (Map.Entry<String,Bool_list> mapElement :
//     //     Parser.globalTasklist.entrySet()) {
//     //     String key = mapElement.getKey();
//     //     // Finding the value
//     //     // using getValue() method
//     //     Boolean value1 = (mapElement.getValue()).rob1;
//     //     System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//     //     if(((mapElement.getValue().rob1 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob1)) ||
//     //     (mapElement.getValue().rob2 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob2)) ||
//     //     (mapElement.getValue().rob3 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob3)) ||
//     //     (mapElement.getValue().rob4 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob4)) )
//     //     &&( mapElement.getKey() != )
//     //     )
//     //     {
//     //         // if(Parser.getFirst(Parser.hmap_rob1) == mapElement.getKey()){
//     //         //     // hashmap_tops_boolean_list.rob1 = true;
//     //         //     continue;
//     //         // }
//     //         // else {
//     //         //     hashmap_tops_boolean_list.rob1 = true;
//     //         // }
//     //         continue;
//     //     }
//     //     else if(((mapElement.getValue().rob1 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob1)) ||
//     //     (mapElement.getValue().rob2 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob2)) ||
//     //     (mapElement.getValue().rob3 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob3)) ||
//     //     (mapElement.getValue().rob4 == false && mapElement.getKey() == Parser.getFirst(Parser.hmap_rob4)) )
//     //     && ((mapElement.getValue().rob))
//     //     )
//     //     {
//     //     }
//     //     // Printing the key-value pairs
//     //     System.out.println(key + " : " + value1);
//     // }   
//     System.out.println(Parser.hmap_rob1);
//     System.out.println(Parser.hmap_rob2);
//     System.out.println(Parser.hmap_rob3);
//     System.out.println(Parser.hmap_rob4);
// }
// public static String getFirst(LinkedHashMap<String, String> lhm)
// {   
//     String[] arrayKeys = lhm.keySet().toArray( new String[ lhm.size() ] );
//     if(arrayKeys.length > 0 )
//     {
//         String temp = lhm.get(arrayKeys[0]);
//         return temp;
//     //first key is at 0 index
//     // System.out.println("First key: " + arrayKeys[0]);   
//     // //last keys is at array length - 1 index
//     // System.out.println("Last key: " + arrayKeys[ arrayKeys.length - 1 ]);  
//     // //get first value using the first key
//     // System.out.println( "First value: " + lhm.get(arrayKeys[0]) );   
//     // //get last value using the last key
//     // System.out.println( "Last value: " + lhm.get(arrayKeys[ arrayKeys.length - 1 ]) );
//     }
// return "";
// }
// // getFirstKey(Parser.hmap_rob1)
// public static String getFirstKey(LinkedHashMap<String, String> lhm)
// {   
//     String[] arrayKeys = lhm.keySet().toArray( new String[ lhm.size() ] );
//     if(arrayKeys.length > 0 )
//     {   
//         String temp = (arrayKeys[0]);
//         return temp;
//     //first key is at 0 index
//     // System.out.println("First key: " + arrayKeys[0]);   
//     // //last keys is at array length - 1 index
//     // System.out.println("Last key: " + arrayKeys[ arrayKeys.length - 1 ]);  
//     // //get first value using the first key
//     // System.out.println( "First value: " + lhm.get(arrayKeys[0]) );  
//     // //get last value using the last key
//     // System.out.println( "Last value: " + lhm.get(arrayKeys[ arrayKeys.length - 1 ]) );
//     }
// return "";
// }
