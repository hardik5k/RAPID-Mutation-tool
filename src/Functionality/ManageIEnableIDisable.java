package Functionality;

import Main.Variable;
import Main.Parser;

import java.util.*;
import java.io.*;

public class ManageIEnableIDisable {
    private static Integer lineNumber = 0;
    private static ArrayList<Parser> parsedTasks = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedVariables = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedRobTargetVariables = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("/home/opt1musenpai/Darpan/Meenakshi mam summer project/task analyzer/Rapid-program-parser/src/InputRules/rule7.txt"); // path to source rapid file
        BufferedReader br = new BufferedReader(fr);
        String lbl = ""; String lbl2 = ""; String lbl2last = "";

        ArrayList<String> globalVariables = new ArrayList<>();
        ArrayList<String> localVariables = new ArrayList<>();

        int taskCount = 0, mainCount = 0;
        boolean task = false, start = false;
        boolean checkEnable = false;
        boolean checkDisable = false;
        boolean checkTrap = false;
        boolean checkVars = false;

        while ((lbl = br.readLine()) != null)  {
            lineNumber++;
            lbl = lbl.replaceFirst("^\\s*", "");
            
            if (lbl.startsWith("BEGINTASK")) {
                taskCount++;
            }

            if(lbl.startsWith("CONST") || lbl.startsWith("PERS")) {
                String[] line = lbl.split("\\s");
                if(!line[2].equals("Initial_point") && !localVariables.contains(line[2])) 
                    {
                        localVariables.add(line[2]);
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
                    String temp = line[0];

                    if(lbl2.startsWith("IEnable"))
                    {
                        checkEnable = true;
                    }
                    if(lbl2.startsWith("IDisable"))
                    {
                        checkDisable = true;
                    }

                    if(lbl2.startsWith("TRAP"))
                    {
                        checkTrap = true;
                        while((lbl2 = br.readLine()) != null){ 
                            lbl2 = lbl2.replaceFirst("^\\s*", "");                    
                            String[] line3 = lbl2.split("\\.");
   
                            if(track.contains(line3[0]))
                            {
                                track.remove(line3[0]);
                            }
                            if(lbl2.startsWith("ENDPROC")){
                                break;
                            }
                        }

                        if(track.size()==0)
                        {
                            checkVars = true;
                        }
                        break;
                    }                    
                }
            }

        }
        //System.out.println("checkOne "+checkOne);
        //System.out.println("checkTwo "+checkTwo);
        if(checkEnable==true && checkDisable==true && checkTrap==true && checkVars==true) task = true;
        System.out.println("sharedVariables Vars : " + localVariables);
        System.out.println("task "+task);
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#7 : Checked IEnable - IDisable block rule !!");
        System.out.println("                             *****************                                       ");
    }
    
}
