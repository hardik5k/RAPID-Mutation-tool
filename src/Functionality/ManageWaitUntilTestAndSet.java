package Functionality;
import Main.Variable;
import Main.Parser;

import java.util.*;
import java.io.*;

public class ManageWaitUntilTestAndSet {
    private static Integer lineNumber = 0;
    private static ArrayList<Parser> parsedTasks = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedVariables = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedRobTargetVariables = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        FileReader fr = new FileReader("flex loader.txt");

        FileReader fr = new FileReader("/home/opt1musenpai/Darpan/Meenakshi mam summer project/task analyzer/Rapid-program-parser/src/InputRules/rule2.txt"); // path to source rapid file
        BufferedReader br = new BufferedReader(fr);
        String lbl = ""; String lbl2 = ""; String lbl2last = "";

        ArrayList<String> globalVariables = new ArrayList<>();
        ArrayList<String> localVariables = new ArrayList<>();

        String boolVariable = "";
        int taskCount = 0, mainCount = 0;
        boolean task1 = false, task2 = false, start = false;

        while ((lbl = br.readLine()) != null)  {
            lineNumber++;
            lbl = lbl.replaceFirst("^\\s*", "");
            //System.out.println("lbl "+lbl);
            
            if (lbl.startsWith("BEGINTASK")) {
                taskCount++;
            }

            //System.out.println("taskCount "+taskCount);
            if(lbl.startsWith("CONST")) {
                String[] line = lbl.split("\\s");
                localVariables.add(line[2]);
            }
            if(lbl.startsWith("PERS bool")) {
                String[] line = lbl.split("\\s");
                boolVariable = line[2];
            }

            if(lbl.startsWith("PROC MAIN()") && taskCount==1) 
            {
                ArrayList<String> track = new ArrayList<>(localVariables);
                while((lbl2 = br.readLine()) != null){ 
                    lbl2 = lbl2.replaceFirst("^\\s*", "");                    
                    String[] line = lbl2.split("\\s");
                    String temp = "WaitUntil TestAndSet("+boolVariable+");";

                    if(lbl2.equals(temp)){
                        if(track.size() == localVariables.size()){
                            start = true;
                            
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(line[0].equals("ENDPROC"))
                    {
                        if(lbl2last.equals(boolVariable+":=FALSE;") && start==true)
                        {
                            task1 = true;
                        }
                        break;
                    }
                    else{
                        if(track.size()>0) track.remove(0);
                    }
                    
                    lbl2last = lbl2;
                }
            }
            start = false;
            lbl2last = "";

            if(lbl.startsWith("PROC MAIN()") && taskCount==2) 
            {
                ArrayList<String> track = new ArrayList<>(localVariables);
                while((lbl2 = br.readLine()) != null){ 
                    lbl2 = lbl2.replaceFirst("^\\s*", "");                    
                    String[] line = lbl2.split("\\s");
                    String temp = "WaitUntil TestAndSet("+boolVariable+");";

                    if(lbl2.equals(temp)){
                        if(track.size() == localVariables.size()){
                            start = true;
                            
                        }
                        else
                        {
                            break;
                        }
                    }
                    else if(line[0].equals("ENDPROC"))
                    {
                        if(lbl2last.equals(boolVariable+":=FALSE;") && start==true)
                        {
                            task2 = true;
                        }
                        break;
                    }
                    else{
                        if(track.size()>0) track.remove(0);
                    }
                    
                    lbl2last = lbl2;
                }
            }
        }
        System.out.println("localVariables Vars : " + localVariables);
        System.out.println("boolVariable "+ boolVariable); 
        System.out.println("task1 "+task1);
        System.out.println("task2 "+task2);
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#2 : Checked WaitUntil TestAndSet instruction !!");
        System.out.println("                             *****************                                       ");

        //System.out.println("start "+start);
        //System.out.println("Shared RobTarget Vars: " + sharedRobTargetVariables);
        //System.out.println(c3());
    }

    private static void createGlobalVarTable(Parser p) {
        ArrayList<String> globalVars = new ArrayList<>();
        ArrayList<String> globalRobTargetVars = new ArrayList<>();
        for (String v : p.varMap.keySet()) {
            if (p.varMap.get(v).isGlobal()) {
                globalVars.add(v);
            }
        }
        for (String r : p.robTargetMap.keySet()) {
            if (p.robTargetMap.get(r).isGlobal()) {
                globalRobTargetVars.add(r);
            }
        }
        sharedVariables.add(globalVars);
        sharedRobTargetVariables.add(globalRobTargetVars);
    }

    private static boolean c3() {
        for (int m = 0; m < parsedTasks.size() - 1; m++) {
            Parser p1 = parsedTasks.get(m);
            for (int n = m + 1; n < parsedTasks.size(); n++) {
                Parser p2 = parsedTasks.get(n);

                // not enough waitSyncTask combinations present to synchronize the 2 tasks
                if (p1.waitInstructionsList.size() != p2.waitInstructionsList.size()) {
                    System.out.println("Unequal Wait instructions in the 2 tasks");
                    return false;
                }

                for (int i = 0; i < p1.waitInstructionsList.size(); i++) {
                    ArrayList<String> accessedVars1 = new ArrayList<>();
                    if (i > 0) {
                        int l1 = p1.waitInstructionsList.get(i - 1).getLineNumber();
                        int l2 = p1.waitInstructionsList.get(i).getLineNumber();

                        for (String s : sharedVariables.get(m)) {
                            ArrayList<Integer> readLocs = p1.varMap.get(s).getReadLocations();

                            if (readLocs != null && !readLocs.isEmpty()) {
                                int firstReadLoc = readLocs.get(0);
                                int index = 1;
                                if (firstReadLoc < l2) {
                                    int readLoc = firstReadLoc;
                                    while (index < readLocs.size() && readLoc <= l1) {
                                        readLoc = readLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < readLocs.size() && readLoc < l2) {
                                        accessedVars1.add(s);
                                    }
                                }
                            }
                            ArrayList<Integer> writeLocs = p1.varMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int firstWriteLoc = writeLocs.get(0);
                                int index = 1;
                                if (firstWriteLoc < l2) {
                                    int writeLoc = firstWriteLoc;
                                    while (index < writeLocs.size() && writeLoc <= l1) {
                                        writeLoc = writeLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < writeLocs.size() && writeLoc < l2) {
                                        accessedVars1.add(s);
                                    }
                                }
                            }
                        }
                        for (String s : sharedRobTargetVariables.get(m)) {
                            ArrayList<Integer> readLocs = p1.robTargetMap.get(s).getReadLocations();

                            if (readLocs != null && !readLocs.isEmpty()) {
                                int firstReadLoc = readLocs.get(0);
                                int index = 1;
                                if (firstReadLoc < l2) {
                                    int readLoc = firstReadLoc;
                                    while (index < readLocs.size() && readLoc <= l1) {
                                        readLoc = readLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < readLocs.size() && readLoc < l2) {
                                        accessedVars1.add(s);
                                    }
                                }
                            }
                            ArrayList<Integer> writeLocs = p1.robTargetMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int firstWriteLoc = writeLocs.get(0);
                                int index = 1;
                                if (firstWriteLoc < l2) {
                                    int writeLoc = firstWriteLoc;
                                    while (index < writeLocs.size() && writeLoc <= l1) {
                                        writeLoc = writeLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < writeLocs.size() && writeLoc < l2) {
                                        accessedVars1.add(s);
                                    }
                                }
                            }
                        }
                    } else {
                        int l1 = p1.waitInstructionsList.get(i).getLineNumber();
                        for (String s : sharedVariables.get(m)) {
                            ArrayList<Integer> readLocs = p1.varMap.get(s).getReadLocations();
                            if (readLocs != null && !readLocs.isEmpty()) {
                                int firstReadLoc = readLocs.get(0);
                                if (firstReadLoc < l1) {
                                    accessedVars1.add(s);
                                }
                            }

                            ArrayList<Integer> writeLocs = p1.varMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int firstWriteLoc = writeLocs.get(0);
                                if (firstWriteLoc < l1) {
                                    accessedVars1.add(s);
                                }
                            }
                        }

                        for (String s : sharedRobTargetVariables.get(m)) {
                            ArrayList<Integer> readLocs = p1.robTargetMap.get(s).getReadLocations();
                            if (readLocs != null && !readLocs.isEmpty()) {
                                int firstReadLoc = readLocs.get(0);
                                if (firstReadLoc < l1) {
                                    accessedVars1.add(s);
                                }
                            }

                            ArrayList<Integer> writeLocs = p1.robTargetMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int firstWriteLoc = writeLocs.get(0);
                                if (firstWriteLoc < l1) {
                                    accessedVars1.add(s);
                                }
                            }
                        }

                    }
                    System.out.println("Accessed Vars1 :" + accessedVars1);

                    ArrayList<String> accessedVars2 = new ArrayList<>();
                    if (i < p2.waitInstructionsList.size() - 1) {
                        int l1 = p2.waitInstructionsList.get(i).getLineNumber();
                        int l2 = p2.waitInstructionsList.get(i + 1).getLineNumber();

                        for (String s : sharedVariables.get(n)) {
                            ArrayList<Integer> readLocs = p2.varMap.get(s).getReadLocations();

                            if (readLocs != null && !readLocs.isEmpty()) {
                                int firstReadLoc = readLocs.get(0);
                                int index = 1;
                                if (firstReadLoc < l2) {
                                    int readLoc = firstReadLoc;
                                    while (index < readLocs.size() && readLoc <= l1) {
                                        readLoc = readLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < readLocs.size() && readLoc < l2) {
                                        accessedVars2.add(s);
                                    }
                                }
                            }
                            ArrayList<Integer> writeLocs = p2.varMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int firstWriteLoc = writeLocs.get(0);
                                int index = 1;
                                if (firstWriteLoc < l2) {
                                    int writeLoc = firstWriteLoc;
                                    while (index < writeLocs.size() && writeLoc <= l1) {
                                        writeLoc = writeLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < writeLocs.size() && writeLoc < l2) {
                                        accessedVars2.add(s);
                                    }
                                }
                            }
                        }
                        for (String s : sharedRobTargetVariables.get(n)) {
                            ArrayList<Integer> readLocs = p2.robTargetMap.get(s).getReadLocations();

                            if (readLocs != null && !readLocs.isEmpty()) {
                                int firstReadLoc = readLocs.get(0);
                                int index = 1;
                                if (firstReadLoc < l2) {
                                    int readLoc = firstReadLoc;
                                    while (index < readLocs.size() && readLoc <= l1) {
                                        readLoc = readLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < readLocs.size() && readLoc < l2) {
                                        accessedVars2.add(s);
                                    }
                                }
                            }
                            ArrayList<Integer> writeLocs = p2.robTargetMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int firstWriteLoc = writeLocs.get(0);
                                int index = 1;
                                if (firstWriteLoc < l2) {
                                    int writeLoc = firstWriteLoc;
                                    while (index < writeLocs.size() && writeLoc <= l1) {
                                        writeLoc = writeLocs.get(index);
                                        index += 1;
                                    }
                                    if (index < writeLocs.size() && writeLoc < l2) {
                                        accessedVars2.add(s);
                                    }
                                }
                            }
                        }
                    } else {
                        int l1 = p2.waitInstructionsList.get(i).getLineNumber();
                        for (String s : sharedVariables.get(n)) {
                            ArrayList<Integer> readLocs = p2.varMap.get(s).getReadLocations();
                            if (readLocs != null && !readLocs.isEmpty()) {
                                int LastReadLoc = readLocs.get(readLocs.size() - 1);
                                if (LastReadLoc > l1) {
                                    accessedVars2.add(s);
                                }
                            }

                            ArrayList<Integer> writeLocs = p2.varMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int LastWriteLoc = writeLocs.get(writeLocs.size() - 1);
                                if (LastWriteLoc > l1) {
                                    accessedVars2.add(s);
                                }
                            }
                        }

                        for (String s : sharedRobTargetVariables.get(n)) {
                            ArrayList<Integer> readLocs = p2.robTargetMap.get(s).getReadLocations();
                            if (readLocs != null && !readLocs.isEmpty()) {
                                int LastReadLoc = readLocs.get(readLocs.size() - 1);
                                if (LastReadLoc > l1) {
                                    accessedVars2.add(s);
                                }
                            }

                            ArrayList<Integer> writeLocs = p1.robTargetMap.get(s).getWriteLocations();
                            if (writeLocs != null && !writeLocs.isEmpty()) {
                                int LastWriteLoc = writeLocs.get(writeLocs.size() - 1);
                                if (LastWriteLoc > l1) {
                                    accessedVars2.add(s);
                                }
                            }
                        }

                    }
                    System.out.println("Accessed Vars2 :" + accessedVars2);
                    ArrayList<String> args1 = new ArrayList<>();
                    ArrayList<String> args2 = new ArrayList<>();

                    for (Variable v : p1.waitInstructionsList.get(i).getVars()) {
                        args1.add(v.getName());
                    }
                    for (Variable v : p2.waitInstructionsList.get(i).getVars()) {
                        args2.add(v.getName());
                    }

                    System.out.println(args1);
                    System.out.println(args2);

                    if (!args1.equals(args2) || !accessedVars1.equals(accessedVars2)) {
                        return false;
                    }
                    System.out.println("---------------------------------New Wait Instruction----------------------------------------");
                }
                System.out.println("------------------------New Pair of Tasks with p1 and other task-------------------------------------------------");
            }
            System.out.println("-------------------------------------p1 is changing now------------------------------------");
        }
        return true;
    }
}
