package Functionality;
import Main.Variable;
import Main.Parser;

import java.util.*;
import java.io.*;


public class ManageWaitUntil {
    private static Integer lineNumber = 0;
    private static ArrayList<Parser> parsedTasks = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedVariables = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedRobTargetVariables = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("/home/opt1musenpai/Darpan/Meenakshi mam summer project/task analyzer/Rapid-program-parser/src/InputRules/rule3.txt"); // path to source rapid file
        BufferedReader br = new BufferedReader(fr);
        String lbl = ""; String lbl2 = ""; String lbl2last = "";

        ArrayList<String> globalVariables = new ArrayList<>();
        ArrayList<String> localVariables = new ArrayList<>();

        ArrayList<String> boolVariables = new ArrayList<>();
        String usedBoolVariable = "";
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
                //boolVariable = line[2];
                boolVariables.add(line[2]);
            }

            if(lbl.startsWith("PROC MAIN()") && taskCount==1) 
            {
                ArrayList<String> track = new ArrayList<>(localVariables);
                while((lbl2 = br.readLine()) != null){ 
                    lbl2 = lbl2.replaceFirst("^\\s*", "");                    
                    String[] line = lbl2.split("\\s");

                    if(track.size()==localVariables.size())
                    {
                        String temp = line[0];
                        for(int i=0;i<boolVariables.size();i++)
                        {
                            if(temp.equals(boolVariables.get(i))){
                                usedBoolVariable = temp;
                                break;
                            }
                        }
                        if(temp!="" && line.length>2 && line[2].equals("TRUE"))
                        {
                            task1 = true;
                        }
                        if(track.size()>0) track.remove(0);
                    }
                    else if(line[0].equals("ENDPROC"))
                    {
                        break;
                    }
                    else{
                        if(track.size()>0) track.remove(0);
                    }
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
                    //String temp = "WaitUntil TestAndSet("+boolVariable+");";

                    if(track.size()==localVariables.size())
                    {
                        String temp = line[0];
                        if(temp!="" && line.length>1 && line[0].equals("WaitUntil") && line[1].equals(usedBoolVariable))
                        {
                            task2 = true;
                        }
                        if(track.size()>0) track.remove(0);
                    }
                    else if(line[0].equals("ENDPROC"))
                    {
                        break;
                    }
                    else{
                        if(track.size()>0) track.remove(0);
                    }
                }
            }
        }
        System.out.println("localVariables Vars : " + localVariables);
        System.out.println("usedBoolVariable "+ usedBoolVariable); 
        System.out.println("task1 "+task1);
        System.out.println("task2 "+task2);
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#3 : Checked WaitUntil  instruction !!");
        System.out.println("                             *****************                                       ");
        //System.out.println("start "+start);
        //System.out.println("Shared RobTarget Vars: " + sharedRobTargetVariables);
        //System.out.println(c3());
    }
}
