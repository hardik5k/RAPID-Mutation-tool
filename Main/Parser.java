package Main;


import Functionality.*;

import java.io.*;
import java.util.*;


public class Parser {

    public int x = 0;

    // public static String input = "D:\\Rapid-program-parser\\src\\inputFiles\\";

    public HashMap<String, Variable> varMap = new HashMap<String, Variable>();
    public HashMap<String, RobTarget> robTargetMap = new HashMap<>();

    public Stack<ConditionalStatements> stk = new Stack<>();
    public ArrayList<ConditionalStatements> cs = new ArrayList<ConditionalStatements>();

    public Boolean syncMoveOn = false;
    public Boolean syncMoveOff = false;
    public static Boolean syncMoveboolFlag = true;
    public static Boolean waitSyncboolFlag1 = true;public static Integer waitSyncFlag1Line;
    public static Boolean waitSyncboolFlag2 = true;public static Integer waitSyncFlag2Line;

    public static Boolean syncMoveboolFlag1 = true;public static Integer syncMoveFlag1Line;
    public static Boolean syncMoveboolFlag2 = true;public static Integer syncMoveFlag2Line;

    public static Boolean sync_bool1 = false,sync_bool2= false;

    public static Boolean flag_task1 = false,flag_task2 = false,flag_task3 = false,flag_task4 = false;

    public static LinkedList<String> syncident_VAR_list1 = new LinkedList<String>();
    public static LinkedList<String> syncident_VAR_list2 = new LinkedList<String>();
    public static LinkedList<String> syncident_VAR_list3 = new LinkedList<String>();
    public static LinkedList<String> syncident_VAR_list4 = new LinkedList<String>();

    public static LinkedList<String> PERS_VAR_list1 = new LinkedList<String>();
    public static LinkedList<String> PERS_VAR_list2 = new LinkedList<String>();
    public static LinkedList<String> PERS_VAR_list3 = new LinkedList<String>();
    public static LinkedList<String> PERS_VAR_list4 = new LinkedList<String>();


    public static LinkedList<String> syncident_waitSyncList = new LinkedList<String>();

    public ArrayList<MoveInstruction> moveInstructionsList = new ArrayList<>();
    public ArrayList<SyncInstruction> syncInstructionsList = new ArrayList<>();
    public ArrayList<WaitInstruction> waitInstructionsList = new ArrayList<>();

    public static final LinkedHashMap<String, Bool_bool> globalTasklist = new LinkedHashMap<String, Bool_bool> ();
    public static final LinkedHashMap<String, Bool_list> globalTasklist2 = new LinkedHashMap<String, Bool_list> ();

    public static LinkedHashMap<String, String> hmap_rob1 = new LinkedHashMap<String, String> ();
    public static LinkedHashMap<String, String> hmap_rob2 = new LinkedHashMap<String, String> ();
    public static LinkedHashMap<String, String> hmap_rob3 = new LinkedHashMap<String, String> ();
    public static LinkedHashMap<String, String> hmap_rob4 = new LinkedHashMap<String, String> ();
    public static Sync_move_block temp_block = new Sync_move_block();

    public static LinkedList<Sync_move_block> syncmap_rob1 = new LinkedList<Sync_move_block>();
    public static LinkedList<Sync_move_block> syncmap_rob2 = new LinkedList<Sync_move_block>();
    public static LinkedList<Sync_move_block> syncmap_rob3 = new LinkedList<Sync_move_block>();
    public static LinkedList<Sync_move_block> syncmap_rob4 = new LinkedList<Sync_move_block>();


    public HashMap<String, Procedure> procMap = new HashMap<>();
    public Integer lineNumber;

    public String taskName, foregroundTaskName;

    public String currentProcName = "";

    public void processTask(BufferedReader br, Integer startLineNumber, Integer taskNumber, ArrayList<String> program) throws IOException {

//        FileReader fr = new FileReader("input.txt"); // path to source rapid file

//        BufferedReader br = new BufferedReader(fr);
        String lbl = "";
        lineNumber = startLineNumber;
        

        while ((lbl = br.readLine()) != null)    //read rapid file line by line
        {
            lbl = lbl.replaceFirst("^\\s*", "");
            program.add(lbl);
//            System.out.println(lbl);
            if (lbl.startsWith("ENDTASK")) {
                break;
            }
            lineNumber++;
            // take necessary action depending on type of statement
            process(lbl);
        }
        if(taskName == "TASK1") flag_task1 = true;
        if(taskName == "TASK2") flag_task2 = true;
        if(taskName == "TASK3") flag_task3 = true;
        if(taskName == "TASK4") flag_task4 = true;

        for(Map.Entry<String,Procedure> p : procMap.entrySet())
        {
//            System.out.println(p.getValue().getReadRobtargetvars());
            p.getValue().setReadVars(new ArrayList<>(new LinkedHashSet<>(p.getValue().getReadVars())));
            p.getValue().setWriteVars(new ArrayList<>(new LinkedHashSet<>(p.getValue().getWriteVars())));
            p.getValue().setReadRobtargetvars(new ArrayList<>(new LinkedHashSet<>(p.getValue().getReadRobtargetvars())));
            p.getValue().setWriteRobtargetvars(new ArrayList<>(new LinkedHashSet<>(p.getValue().getWriteRobtargetvars())));

            for(String v : p.getValue().getReadVars())
            {
                for(Integer u : p.getValue().getUsedLocations())
                {
                    varMap.get(v).addReadLocation(u);
                }
            }
            for(String v : p.getValue().getWriteVars())
            {
                for(Integer u : p.getValue().getUsedLocations())
                {
                    varMap.get(v).addWriteLocation(u);
                }
            }
            for(String r : p.getValue().getReadRobtargetvars())
            {
                for(Integer u : p.getValue().getUsedLocations())
                {
                    robTargetMap.get(r).addReadLocation(u);
                }
            }
            for(String r : p.getValue().getWriteRobtargetvars())
            {
                for(Integer u : p.getValue().getUsedLocations())
                {
                    robTargetMap.get(r).addWriteLocation(u);
                }
            }
        }

        for (String v : varMap.keySet() ) {
            Collections.sort(varMap.get(v).getReadLocations());
            Collections.sort(varMap.get(v).getWriteLocations());
        }
        for (String r : robTargetMap.keySet()){
            Collections.sort(robTargetMap.get(r).getReadLocations());
            Collections.sort(robTargetMap.get(r).getWriteLocations());
        }

        FileOutputStream fos = new FileOutputStream("parser-output-task-" + taskNumber + ".csv", false);

        PrintWriter pw = new PrintWriter(fos);

        for (Map.Entry<String, Variable> entry : varMap.entrySet()) {
            pw.println(entry.getKey() + "->" + entry.getValue());
        }

        for (Map.Entry<String, RobTarget> entry : robTargetMap.entrySet()) {
            pw.println(entry.getKey() + "->" + entry.getValue());
        }

        for (ConditionalStatements c : cs) {
            pw.println(c);
        }

        for (MoveInstruction mv : moveInstructionsList) {
            pw.println(mv);
        }

        for (SyncInstruction s : syncInstructionsList) {
            pw.println(s);
        }

        for (WaitInstruction w : waitInstructionsList) {
            pw.println(w);
        }

        for (Map.Entry<String,Procedure> entry : procMap.entrySet()){
            pw.println(entry.getKey() + "->" + entry.getValue());
        }

        pw.close();
        fos.close();

    }

    public static void check_wait_sync_at_end()
    {
        if(Parser.waitSyncboolFlag1.equals(false)){
            System.out.println("");
            System.out.println("Error !! Ident name already exists , no duplication allowed . "+ "Line number : "+ Parser.waitSyncFlag1Line);
            System.out.println("");
            System.out.println("                             *****************                                       ");
            System.out.println("             Rule#1(a) : Checked WaitSync Task instructions !!");
            System.out.println("                             *****************                                       ");
            return;
        }
        if(Parser.waitSyncboolFlag2.equals(false)){
            System.out.println("");
            System.out.println("Error !! Task name does not exist in PERS tasks list . " + "Line number : "+ Parser.waitSyncFlag2Line);
            System.out.println("");
            System.out.println("                             *****************                                       ");
            System.out.println("             Rule#1(a) : Checked WaitSync Task instructions !!");
            System.out.println("                             *****************                                       ");
            return;
        }

        Bool_list hashmap_tops_boolean_list = new Bool_list(false,false,false,false);

        System.out.println("");
        System.out.println(" The Hashmap for 4 Robots are as follows :");
        System.out.println("ROB1 :"+Parser.hmap_rob1);
        System.out.println("ROB2 :"+Parser.hmap_rob2);
        System.out.println("ROB3 :"+Parser.hmap_rob3);
        System.out.println("ROB4 :"+Parser.hmap_rob4);

        for (Map.Entry<String,Bool_list> mapElement :
            Parser.globalTasklist2.entrySet()) {
 
            String key = mapElement.getKey();
 
            // Finding the value
            // using getValue() method
            Boolean value1 = (mapElement.getValue()).rob1;
            
            Parser.check_hmap_top();

            // Printing the key-value pairs
            // System.out.println(key + " : " + value1);
        }

        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#1(a) : Checked WaitSync Task instructions !!");
        System.out.println("                             *****************                                       ");


    }
    
/* This function checks the Top elements of hashmap of the 4 robots 
 * linkedhashmap_task1 = a   linkedhashmap = b   linkedhashmap_task3 = c   linkedhashmap_task4=d
*/
    public static void check_hmap_top()
    {
        String a=Parser.getFirst(Parser.hmap_rob1),b=Parser.getFirst(Parser.hmap_rob2),c=Parser.getFirst(Parser.hmap_rob3),d=Parser.getFirst(Parser.hmap_rob4);
        String[] str = {a,b,c,d};
        Stack <String> stamck = new Stack<String>();
        String temp_ident = new String();
        Boolean flag_check = false;


        for(int j = 0; j < str.length; j++){
            System.out.println("");
            System.out.println( " Hashmap_rob1 TOP = " +a + 
                                " *** Hashmap_rob2 TOP = "+b +
                                " *** Hashmap_rob3 TOP = " + c + 
                                " *** Hashmap_rob4 TOP = " + d);
            // System.out.println("                   #####"+str[j]);
            if(Parser.globalTasklist2.containsKey(str[j])){
                Bool_list temp_bool = new Bool_list(false,false,false,false);
                for(int i=0;i<str.length; i++){
                    if(!str[j].equals(null) )
                    {
                        if(str[j].equals(a)){
                            temp_bool.rob1 = true;
                        }
                        if(str[j].equals(b)){
                            temp_bool.rob2 = true;
    
                        if(str[j].equals(c)){
                            temp_bool.rob3 = true;
                        }
                        if(str[j].equals(d)){
                            temp_bool.rob4 = true;
                        }
                    }

                System.out.println("Temp boolean  ROB-1 match            |     " + temp_bool.rob1 + " "+Parser.globalTasklist2.get(a).rob1 );
                System.out.println("Temp boolean  ROB-2 match            |     " + temp_bool.rob2 + " "+Parser.globalTasklist2.get(a).rob2 );
                System.out.println("Temp boolean  ROB-3 match            |     " + temp_bool.rob3 + " "+Parser.globalTasklist2.get(a).rob3 );
                System.out.println("Temp boolean  ROB-4 match            |     " + temp_bool.rob4 + " "+Parser.globalTasklist2.get(a).rob4 );
                System.out.println("");
                
                if(temp_bool.rob1 == Parser.globalTasklist2.get(a).rob1 && 
                temp_bool.rob2 == Parser.globalTasklist2.get(a).rob2 &&
                temp_bool.rob3 == Parser.globalTasklist2.get(a).rob3 &&
                temp_bool.rob4 == Parser.globalTasklist2.get(a).rob4
                ){
                    if(temp_bool.rob1 == true) {
                        stamck.push(Parser.getFirstKey(hmap_rob1));
                        // flag_for_ident_same = true;
                    }
                    if(temp_bool.rob2 == true) {
                        stamck.push(Parser.getFirstKey(hmap_rob2));
                        // flag_for_ident_same = true;
                    }
                    if(temp_bool.rob3 == true) {
                        stamck.push(Parser.getFirstKey(hmap_rob3));
                        // flag_for_ident_same = true;
                    }
                    if(temp_bool.rob4 == true) {
                        stamck.push(Parser.getFirstKey(hmap_rob4));
                        // flag_for_ident_same = true;
                    }

                    String t1 = new String();
                    temp_ident = stamck.pop();
                    Integer op = 0;
                    while(stamck != null) {
                        ++op;
                        t1=stamck.pop();
                        if(!t1.equals(temp_ident)){
                            System.out.println("Errrrror ! ident values not same ");
                            return;
                            
                        }
                        if(stamck.empty()) break;
                    }
                    
                    
                    System.out.println("Eurekaaaaaa !!! Wait Sync Task matched successfully .");
                    Parser.syncident_waitSyncList.add(temp_ident);
                    // System.out.println(Parser.syncident_waitSyncList);
                    System.out.println("                       ---------------------                                   ");
                    System.out.println("");

                    // for(int k = 0; k < str.length; k++)
                    {
                        if(temp_bool.rob1){
                            Object o = hmap_rob1.remove(Parser.getFirstKey(hmap_rob1));
                        }
                        if(temp_bool.rob2){
                            Object o = hmap_rob2.remove(Parser.getFirstKey(hmap_rob2));
                        }
                        if(temp_bool.rob3){
                            Object o = hmap_rob3.remove(Parser.getFirstKey(hmap_rob3));
                        }
                        if(temp_bool.rob4){
                            Object o = hmap_rob4.remove(Parser.getFirstKey(hmap_rob4));
                        }
                    }
                    
                    System.out.println("ROB1 :"+Parser.hmap_rob1);
                    System.out.println("ROB2 :"+Parser.hmap_rob2);
                    System.out.println("ROB3 :"+Parser.hmap_rob3);
                    System.out.println("ROB4 :"+Parser.hmap_rob4);
                    flag_check =true;
                    break;
                }
       
                }   
            }
            if( hmap_rob1.isEmpty()
                && hmap_rob2.isEmpty() && hmap_rob3.isEmpty()
                && hmap_rob4.isEmpty()){
                    break;
                }
            
        }
    }

    if(!flag_check)System.out.println("Errrrrrrrrrrrrrrrrrror in task");
    
    }

    // A function to get the taskname of first element of a hashmap .
    public static String getFirst(LinkedHashMap<String, String> lhm)
    {   
        String[] arrayKeys = lhm.keySet().toArray( new String[ lhm.size() ] );
        if(arrayKeys.length > 0 )
        {
        
            String temp = lhm.get(arrayKeys[0]);
            return temp;

        }
    return null;
    }

    // Funtion to getFirstKey of a Hashmap
    public static String getFirstKey(LinkedHashMap<String, String> lhm)
    {   
        String[] arrayKeys = lhm.keySet().toArray( new String[ lhm.size() ] );
        if(arrayKeys.length > 0 )
        {
            String temp = (arrayKeys[0]);
            return temp;

        }
        return "";
    }

    public void process(String lbl) throws IOException {

        if (lbl.startsWith("PROC") || lbl.startsWith("ENDPROC")) {
//            System.out.println("before: " + currentProcName);
            ManageProc.mainfunction(this, lbl, lineNumber);
//            System.out.println("after: " + currentProcName);
        }

        else if (lbl.startsWith("VAR") || lbl.startsWith("CONST") || lbl.startsWith("PERS")) {
            ManageVariable.mainfunction(this, lbl, lineNumber);
        }

        // Start Conditional Statements
        else if (lbl.startsWith("FOR") || lbl.startsWith("WHILE") || lbl.startsWith("IF")) {
            ManageConditionalStatements.mainfunction(this, lbl, lineNumber);
            x++;
        }

        // End Conditional Statements
        else if (lbl.startsWith("ENDFOR") || lbl.startsWith("ENDWHILE") || lbl.startsWith("ENDIF")) {
            stk.peek().addExit(lineNumber);
            x--;
            if (x == 0) {
                ConditionalStatements c = stk.pop();
                cs.add(c);
            }
        }

        else if (lbl.startsWith("ELSEIF") || lbl.startsWith("ELSE")) {
            ManageConditionalStatements.mainfunction(this, lbl, lineNumber);
        }



        // Move statements
        else if (lbl.startsWith("MoveL") || lbl.startsWith("MoveC") || lbl.startsWith("MoveJ") || lbl.startsWith("MoveAbsJ")) {
            ManageMove.mainfunction(this, lbl, lineNumber);
            if(Parser.sync_bool2.equals(false) && Parser.sync_bool1.equals(true))
                ManageSyncMoveInstructions.mainfunction(this, lbl, lineNumber);
        }

        else if (lbl.startsWith("CONNECT") || lbl.startsWith("SetDO") || lbl.startsWith("ISignalDO") || lbl.startsWith("ISignalDI") || lbl.startsWith("WaitDO") || lbl.startsWith("WaitDI")) {
            ManageSyncConstructs.mainfunction(this, lbl, lineNumber);
        }

        else if (lbl.startsWith("SyncMoveOn") || lbl.startsWith("SyncMoveOff") || lbl.startsWith("WaitSyncTask") || lbl.startsWith("WaitTime") || lbl.startsWith("WaitUntil") || lbl.startsWith("WaitTestAndSet")) {
            if(lbl.startsWith("WaitSyncTask")){
                System.out.println(lbl+" "+lineNumber);
                ManageWaitSyncTaskInstructions.mainfunction(this, lbl, lineNumber);
            }
            if(lbl.startsWith("SyncMoveOn")){
                System.out.println(lbl+" "+lineNumber);
                ManageSyncMoveInstructions.mainfunction(this, lbl, lineNumber);
            }
            if(lbl.startsWith("SyncMoveOff")){
                System.out.println(lbl+" "+lineNumber);
                ManageSyncMoveInstructions.mainfunction(this, lbl, lineNumber);
            }
            ManageWaitInstructions.mainfunction(this, lbl, lineNumber);
        }

        //Function usage in a statement
        else if (!lbl.contains(":=") && !lbl.startsWith("MODULE") && !lbl.startsWith("ENDMODULE")) {
            ManageProc.mainfunction(this, lbl, lineNumber);
        }

        // Initialization statements
        else {

            String[] words = lbl.split(" |[.x]|[.y]|[.z]|[=]|:|;|>|<");
            String leftSide = words[0].replaceAll("\\s", "");

            if (robTargetMap.containsKey(leftSide)) {
                //robTarget constant initialization
                if (lbl.contains("[")) {
                    robTargetMap.get(leftSide).assignValue(lbl);
                } else {
                    robTargetMap.get(leftSide).extractRobTargetOperands(this, lbl, lineNumber);
                }
                if(!currentProcName.equals("") && !currentProcName.equals("MAIN"))
                {
                    procMap.get(currentProcName).addWriteRobTargetVar(leftSide);
                }
                else{
                    robTargetMap.get(leftSide).addWriteLocation(lineNumber);
                }
            } else if (varMap.containsKey(leftSide)) {
                varMap.get(leftSide).extractVarOperands(this, lbl, lineNumber);
//                System.out.println("Variable name : " + leftSide);
                if(!currentProcName.equals("") && !currentProcName.equals("MAIN"))
                {
                    procMap.get(currentProcName).addWriteVars(leftSide);
                }
                else{
                    varMap.get(leftSide).addWriteLocation(lineNumber);
                }
            }

        }
//        System.out.println(varMap.entrySet());
    }


public static void check_sync_move_at_end()
{
    if(Parser.syncMoveboolFlag1.equals(false)){
        System.out.println("");
        System.out.println("Error !! Ident name already exists , no duplication allowed . "+ "Line number : "+ Parser.syncMoveFlag1Line);
        System.out.println("");
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#1(b) : Checked SyncMoveOn - SyncMoveOff Task instruction !!");
        System.out.println("                             *****************                                       ");
        System.out.println("");

        return;
    }
    
    if(Parser.syncMoveboolFlag.equals(false)){
        System.out.println("");
        System.out.println("Error !! No Nesting of SyncMove instructs allowed. "+"Line number : "+ Parser.syncMoveFlag2Line);
        System.out.println("");
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#1(b) : Checked SyncMoveOn - SyncMoveOff Task instruction !!");
        System.out.println("                             *****************                                       ");
        System.out.println("");
        return;
    }

    Bool_list hashmap_tops_boolean_list = new Bool_list(false,false,false,false);
    System.out.println("");
    System.out.println(" The Linked List for 4 Robots are as follows :");
    System.out.println("ROB1 :"+Parser.syncmap_rob1);
    System.out.println("ROB2 :"+Parser.syncmap_rob2);
    System.out.println("ROB3 :"+Parser.syncmap_rob3);
    System.out.println("ROB4 :"+Parser.syncmap_rob4);


    for (Map.Entry<String,Bool_list> mapElement :
        Parser.globalTasklist2.entrySet()) {

        String key = mapElement.getKey();

        // Finding the value
        // using getValue() method
        Boolean value1 = (mapElement.getValue()).rob1;
        
        
        Parser.check_sync_hmap_top();


        // System.out.println(key + " : " + value1);
    }

    System.out.println("                             *****************                                       ");
    System.out.println("             Rule#1(b) : Checked SyncMoveOn - SyncMoveOff Task instruction !!");
    System.out.println("                             *****************                                       ");
    System.out.println("");

}

public static void check_sync_hmap_top()
    {
        // String a=Parser.getFirst_sync_intruct_taskname(Parser.syncmap_rob1),b=Parser.getFirst_sync_intruct_taskname(Parser.syncmap_rob2),c=Parser.getFirst_sync_intruct_taskname(Parser.syncmap_rob3),d=Parser.getFirst_sync_intruct_taskname(Parser.syncmap_rob4);
        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // System.out.println(Parser.syncmap_rob2.getFirst().getSync_stack());
        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        String a = new String();
        String b = new String();

        String c = new String();

        String d = new String();

        clear_the_string(a);
        clear_the_string(b);
        clear_the_string(c);
        clear_the_string(d);
        
        // System.out.println(a + b + c+d);

        if(!Parser.syncmap_rob1.isEmpty())
            a= Parser.syncmap_rob1.get(0).getName_on().get_task_name();
        if(!Parser.syncmap_rob2.isEmpty())
            b = Parser.syncmap_rob2.get(0).getName_on().get_task_name();
        if(!Parser.syncmap_rob3.isEmpty())
            c = Parser.syncmap_rob3.get(0).getName_on().get_task_name();
        if(!Parser.syncmap_rob4.isEmpty())
            d = Parser.syncmap_rob4.get(0).getName_on().get_task_name(); 

        // display_next_top();

        String[] str = {a,b,c,d};
        Stack <String> stamck = new Stack<String>();
        Stack<Stack<String>> stamck_MOVE_commands = new Stack<Stack<String>>();
        String temp_ident = new String();
        Stack<String> temp_stack = new Stack<String>();
        // System.out.println( " ** ");
        // System.out.println(a + b + c+d);
        Boolean flag_check = false;

        Stack <String> s1 = new Stack<String>();
        Stack <String> s2 = new Stack<String>();
        Stack <String> s3 = new Stack<String>();
        Stack <String> s4 = new Stack<String>();
        clear_stack(s1);
        clear_stack(s2);
        clear_stack(s3);
        clear_stack(s4);



        for(int j = 0; j < str.length; j++)
        {
            // System.out.println(a + b + c+d);
            if(!Parser.syncmap_rob1.isEmpty())
                a= Parser.syncmap_rob1.get(0).getName_on().get_task_name();
            if(!Parser.syncmap_rob2.isEmpty())
                b = Parser.syncmap_rob2.get(0).getName_on().get_task_name();
            if(!Parser.syncmap_rob3.isEmpty())
                c = Parser.syncmap_rob3.get(0).getName_on().get_task_name();
            if(!Parser.syncmap_rob4.isEmpty())
                d = Parser.syncmap_rob4.get(0).getName_on().get_task_name(); 

            if(!Parser.syncmap_rob1.isEmpty())
            s1= Parser.syncmap_rob1.get(0).getSync_stack();
            if(!Parser.syncmap_rob2.isEmpty())
                s2 = Parser.syncmap_rob2.get(0).getSync_stack();
            if(!Parser.syncmap_rob3.isEmpty())
                s3 = Parser.syncmap_rob3.get(0).getSync_stack();
            if(!Parser.syncmap_rob4.isEmpty())
                s4 = Parser.syncmap_rob4.get(0).getSync_stack();

            System.out.println("");
            System.out.println(" Syncmap_rob1 TOP = "+a + " *** Syncmap_rob2 TOP = "+b + " *** Syncmap_rob3 TOP = " + c + " *** Syncmap_rob4 TOP = " + d);
            System.out.println("");
            // System.out.println("                   #####"+str[j]);
            if(Parser.globalTasklist2.containsKey(str[j])){

                Bool_list temp_bool = new Bool_list(false,false,false,false);
                for(int i=0;i<str.length; i++){
                    if(!str[j].equals(null) )
                    {
                        if(str[j].equals(a)){
                            temp_bool.rob1 = true;
                        }
                        if(str[j].equals(b)){
                            temp_bool.rob2 = true;
                        // }                        System.out.println(temp_bool.rob2);
    
                        if(str[j].equals(c)){
                            temp_bool.rob3 = true;
                        }
                        if(str[j].equals(d)){
                            temp_bool.rob4 = true;
                        }
                    }

                System.out.println("Temp boolean  ROB-1 match            |     " + temp_bool.rob1 +" "+ Parser.globalTasklist2.get(str[j]).rob1 );
                System.out.println("Temp boolean  ROB-2 match            |     " + temp_bool.rob2 +" "+ Parser.globalTasklist2.get(str[j]).rob2 );
                System.out.println("Temp boolean  ROB-3 match            |     " + temp_bool.rob3 +" "+ Parser.globalTasklist2.get(str[j]).rob3 );
                System.out.println("Temp boolean  ROB-4 match            |     " + temp_bool.rob4 +" "+ Parser.globalTasklist2.get(str[j]).rob4 );
                System.out.println("");

                // System.out.println(str[j]);

                
                if(temp_bool.rob1 == Parser.globalTasklist2.get(str[j]).rob1 && 
                temp_bool.rob2 == Parser.globalTasklist2.get(str[j]).rob2 &&
                temp_bool.rob3 == Parser.globalTasklist2.get(str[j]).rob3 &&
                temp_bool.rob4 == Parser.globalTasklist2.get(str[j]).rob4
                ){
                    if(temp_bool.rob1 == true 
                    ) {
                        if(Parser.syncident_waitSyncList.contains(Parser.syncmap_rob1.getFirst().getName_on().get_ident_name())
                        || Parser.syncident_waitSyncList.contains(Parser.syncmap_rob1.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !! No duplication of ident values with WaitSyncTask allowed .");
                        }
                        stamck.push(Parser.syncmap_rob1.getFirst().getName_on().get_ident_name());

                        if(Parser.syncmap_rob1.getFirst().getName_on().get_ident_name().equals(Parser.syncmap_rob1.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !!!  Same IDent values of SyncMove On and SyncMove Off");
                            return ;
                        }
                        stamck_MOVE_commands.push(s1);

                    }
                    if(temp_bool.rob2 == true 
                    ) {
                        if(Parser.syncident_waitSyncList.contains(Parser.syncmap_rob2.getFirst().getName_on().get_ident_name())
                        || Parser.syncident_waitSyncList.contains(Parser.syncmap_rob2.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !! No duplication of ident values with WaitSyncTask allowed .");
                        }
                        stamck.push(Parser.syncmap_rob2.getFirst().getName_on().get_ident_name());
                        if(Parser.syncmap_rob2.getFirst().getName_on().get_ident_name().equals(Parser.syncmap_rob2.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !!!  Same IDent values of SyncMove On and SyncMove Off");
                            return ;
                        }                        stamck_MOVE_commands.push(s2);
                        // System.out.println(Parser.syncmap_rob2.getFirst().getName_on().get_ident_name());

                    }
                    if(temp_bool.rob3 == true){
                        if(Parser.syncident_waitSyncList.contains(Parser.syncmap_rob3.getFirst().getName_on().get_ident_name())
                        || Parser.syncident_waitSyncList.contains(Parser.syncmap_rob3.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !! No duplication of ident values with WaitSyncTask allowed .");
                        }

                        stamck.push(Parser.syncmap_rob3.getFirst().getName_on().get_ident_name());
                        if(Parser.syncmap_rob3.getFirst().getName_on().get_ident_name().equals(Parser.syncmap_rob3.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !!!  Same IDent values of SyncMove On and SyncMove Off");
                            return ;
                        }                        stamck_MOVE_commands.push(s3);
                        // System.out.println(Parser.syncmap_rob3.getFirst().getName_on().get_ident_name());

                    }
                    if(temp_bool.rob4 == true ){
                        if(Parser.syncident_waitSyncList.contains(Parser.syncmap_rob4.getFirst().getName_on().get_ident_name())
                        || Parser.syncident_waitSyncList.contains(Parser.syncmap_rob4.getFirst().getName_off().get_ident_name()))
                        {
                            System.out.println("Errrorrr !! No duplication of ident values with WaitSyncTask allowed .");
                        }

                        stamck.push(Parser.syncmap_rob4.getFirst().getName_on().get_ident_name());
                        if(Parser.syncmap_rob4.getFirst().getName_on().get_ident_name().equals(Parser.syncmap_rob4.getFirst().getName_off().get_ident_name())){
                            System.out.println("Errrorrr !!!  Same IDent values of SyncMove On and SyncMove Off");
                            return ;
                        }
                        stamck_MOVE_commands.push(s4);
                        // System.out.println(Parser.syncmap_rob4.getFirst().getName_on().get_ident_name());
                    }
                    System.out.println(stamck);

                    String t1 = new String();
                    temp_ident = stamck.pop();
                    // System.out.println("temp ident is "+ temp_ident);

                    Integer op = 0;
                    while(stamck != null) {
                        ++op;
                        // System.out.println(op);
                        t1=stamck.pop();
                        // System.out.println(temp_ident);
                        // System.out.println("(("+t1+"))");
                        if(!t1.equals(temp_ident)){
                            System.out.println("Errrrror ! ident values not same ");
                            return;
                            
                        }
                        if(stamck.empty()) break;
                    }
                    
                    System.out.println("");
                    System.out.println("The Stacks of top of Linked List for 4 Robots are as follows :");
                    
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if(!Parser.syncmap_rob1.isEmpty())System.out.println("ROB1 : "+s1);
                    if(!Parser.syncmap_rob2.isEmpty())            System.out.println("ROB2 : "+s2);
                    if(!Parser.syncmap_rob3.isEmpty())            System.out.println("ROB3 : "+s3);
                    if(!Parser.syncmap_rob4.isEmpty())           System.out.println("ROB4 : "+s4);
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");




                    Stack<String> t2 = new Stack<String>();
                    
                    temp_stack = stamck_MOVE_commands.pop();
                    // System.out.println("temp ident is "+ temp_ident);

                    Integer op2 = 0;
                    while(stamck_MOVE_commands != null) {
                        ++op2;
                        // System.out.println(op2);
                        t2=stamck_MOVE_commands.pop();
                        // System.out.println(temp_stack);
                        // System.out.println("(("+t2+"))");
                        if(!t2.equals(temp_stack)){
                            System.out.println("Errrrror ! STACK values NOT same ");
                            return;
                            
                        }
                        if(stamck_MOVE_commands.empty()) break;
                    }
                    
                    System.out.println("");
                    System.out.println("Eurekaaaaaa !!! Sync Move block matched successfully .");
                    System.out.println("                       ---------------------                                   ");
                    System.out.println("");
                    {
                        if(temp_bool.rob1){
                            // Object o = syncmap_rob1.remove(Parser.getFirstKey_of_sync_instructs(syncmap_rob1));
                            Object o =Parser.syncmap_rob1.removeFirst();
                        }
                        if(temp_bool.rob2){
                            // Object o = syncmap_rob2.remove(Parser.getFirstKey_of_sync_instructs(syncmap_rob2));
                            Object o =Parser.syncmap_rob2.removeFirst();
                        }
                        if(temp_bool.rob3){
                            // Object o = syncmap_rob3.remove(Parser.getFirstKey_of_sync_instructs(syncmap_rob3));
                            Object o= Parser.syncmap_rob3.removeFirst();
                        }
                        if(temp_bool.rob4){
                            // Object o = syncmap_rob4.remove(Parser.getFirstKey_of_sync_instructs(syncmap_rob4));
                            Object o= Parser.syncmap_rob4.removeFirst();
                        }
                    }
                    System.out.println("ROB1 :"+Parser.syncmap_rob1);
                    System.out.println("ROB2 :"+Parser.syncmap_rob2);
                    System.out.println("ROB3 :"+Parser.syncmap_rob3);
                    System.out.println("ROB4 :"+Parser.syncmap_rob4);
                    // display_next_top();

                    flag_check =true;
                    break;
                }
                
                    
                }  
            }
            if( Parser.syncmap_rob1.isEmpty()
                && Parser.syncmap_rob4.isEmpty() && Parser.syncmap_rob2.isEmpty()
                && Parser.syncmap_rob3.isEmpty()){
                    break;
                }
            
        }
    }

        if(!flag_check)System.out.println("Errrrrrrrrrrrrrrrrrror in task");
    
        
        // System.out.println("@@@@@@@@@@"+Parser.globalTasklist2.containsKey(a)+ " Value is " + Parser.globalTasklist2.get(a));
    }
   
public static void display_next_top(){
        System.out.println("                        NEXT TOP      ");
        if(!Parser.syncmap_rob1.isEmpty()) 
        System.out.println(Parser.syncmap_rob1.getFirst().getName_on().get_ident_name()+Parser.syncmap_rob1.get(0).getName_on().get_task_name());
        if(!Parser.syncmap_rob2.isEmpty()) 
        System.out.println(Parser.syncmap_rob2.getFirst().getName_on().get_ident_name()+Parser.syncmap_rob2.get(0).getName_on().get_task_name());
        if(!Parser.syncmap_rob3.isEmpty()) 
        System.out.println(Parser.syncmap_rob3.getFirst().getName_on().get_ident_name()+Parser.syncmap_rob3.get(0).getName_on().get_task_name());
        if(!Parser.syncmap_rob4.isEmpty()) 
        System.out.println(Parser.syncmap_rob4.getFirst().getName_on().get_ident_name()+Parser.syncmap_rob4.get(0).getName_on().get_task_name());
    }

    public static String clear_the_string(String str) {
        str = "";
        return str;
    }

    public static Stack<String> clear_stack(Stack<String> stack) {
        stack=null;
        return stack;
    }


public static void check_PERS_common(){
    for (Map.Entry<String, Bool_bool> mapElement :
             Parser.globalTasklist.entrySet()) {
 
            // Integer key = mapElement.getKey();
 
            // Finding the value
            // using getValue() method
            // String value = mapElement.getValue();
            if(mapElement.getValue().get_rob1().equals(true)){
                if(!Parser.PERS_VAR_list1.contains(mapElement.getKey())){
                    System.out.println("Error !! common PERS variable not present for "+ mapElement.getKey() + " of "+ "ROB_1");
                }
                //     Parser.PERS_VAR_list1.remove(mapElement.getKey());
                // }else Parser.PERS_VAR_list1.remove(mapElement.getKey());
                
            }
            if(mapElement.getValue().get_rob2().equals(true)){
                if(!Parser.PERS_VAR_list2.contains(mapElement.getKey())){
                    System.out.println("Error !! common PERS variable not present for "+ mapElement.getKey()+ " of "+ "ROB_2");}
                //     Parser.PERS_VAR_list2.remove(mapElement.getKey());
                // }else Parser.PERS_VAR_list2.remove(mapElement.getKey());
                
            }
            if(mapElement.getValue().get_rob3().equals(true)){
                if(!Parser.PERS_VAR_list3.contains(mapElement.getKey())){
                    System.out.println("Error !! common PERS variable not present for "+ mapElement.getKey()+ " of "+ "ROB_3");
                }
                    // Parser.PERS_VAR_list3.remove(mapElement.getKey());
                // }else Parser.PERS_VAR_list3.remove(mapElement.getKey());
                
            }
            if(mapElement.getValue().get_rob4().equals(true)){
                if(!Parser.PERS_VAR_list4.contains(mapElement.getKey())){
                    System.out.println("Error !! common PERS variable not present for "+ mapElement.getKey()+ " of "+ "ROB_4");}
                    // Parser.PERS_VAR_list4.remove(mapElement.getKey());
                // }else Parser.PERS_VAR_list4.remove(mapElement.getKey());
                
            }

        }
        if(Parser.flag_task1 && Parser.flag_task2 && Parser.flag_task3 && Parser.flag_task4){
            if(!Parser.PERS_VAR_list1.isEmpty() || 
            !Parser.PERS_VAR_list2.isEmpty() ||
            !Parser.PERS_VAR_list3.isEmpty() ||
            !Parser.PERS_VAR_list4.isEmpty() 
            ){
                System.out.println("Error !! Common PERS not present in one of the robots .");
            }
        }
}

}

// public static String getFirst_sync_intruct_taskname(LinkedHashMap<String, Sync_move_block> lhm)
// {   
//         System.out.println(lhm);
//     String[] arrayKeys = lhm.keySet().toArray( new String[ lhm.size() ] );
//         if(arrayKeys.length > 0 )
//         {
//             System.out.println(lhm.get(arrayKeys[0]).getName_on().get_task_name()
//                 );
//             Sync_move_block temp_temp = new Sync_move_block();
//             temp_temp = lhm.get(arrayKeys[0]);

//             // System.out.println(" jhgufytfyfdyfdygfytgg                "+Parser.syncmap_rob2.get(arrayKeys[0]).getName_on().get_task_name() +"              "+arrayKeys[0]);
//             System.out.println(" jhgufytfyfdyfdygfytgg                "+ Parser.syncmap_rob2.getFirst().getName_on().get_task_name()  +"              " + Parser.syncmap_rob2.getFirst().getName_on().get_ident_name());
//             System.out.println(temp_temp);
//             System.out.println(temp_temp.getName_on().get_ident_name()+"       "+ temp_temp.getName_on().get_task_name());
//             String temp = lhm.get(arrayKeys[0]).getName_on().get_task_name();
//             System.out.println(temp+" @#@#$%@%$%@%");
//             return temp;
//         }
//         // if(!lhm.isEmpty())
//         // return lhm.
//         return null;
// }

// public static String getFirstKey_of_sync_instructs(LinkedHashMap<String, Sync_move_block> lhm)
//     {   
//         String[] arrayKeys = lhm.keySet().toArray( new String[ lhm.size() ] );
//         if(arrayKeys.length > 0 )
//         {
//             String temp = (arrayKeys[0]);
//             // System.out.println(temp);
//             return temp;
//         }
//     return "";
// }
// }