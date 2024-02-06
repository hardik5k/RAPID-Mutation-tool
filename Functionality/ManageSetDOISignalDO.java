package Functionality;

import Main.Variable;
import Main.Parser;

import java.util.*;
import java.io.*;

public class ManageSetDOISignalDO {
    private static Integer lineNumber = 0;
    private static ArrayList<Parser> parsedTasks = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedVariables = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedRobTargetVariables = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("/home/opt1musenpai/Darpan/Meenakshi mam summer project/task analyzer/Rapid-program-parser/src/InputRules/rule4.txt"); // path to source rapid file
        BufferedReader br = new BufferedReader(fr);
        String lbl = ""; String lbl2 = ""; String lbl2last = "";

        ArrayList<String> globalVariables = new ArrayList<>();
        ArrayList<String> localVariables = new ArrayList<>();

        int taskCount = 0, mainCount = 0;
        boolean task1 = false, task2 = false, start = false;
        String setDOVariable = "";

        while ((lbl = br.readLine()) != null)  {
            lineNumber++;
            lbl = lbl.replaceFirst("^\\s*", "");
            
            if (lbl.startsWith("BEGINTASK")) {
                taskCount++;
            }

            if(lbl.startsWith("CONST")) {
                String[] line = lbl.split("\\s");
                localVariables.add(line[2]);
            }


            if(lbl.startsWith("PROC MAIN()") && taskCount==1) 
            {
                ArrayList<String> track = new ArrayList<>(localVariables);
                while((lbl2 = br.readLine()) != null){ 
                    lbl2 = lbl2.replaceFirst("^\\s*", "");                    
                    String[] line = lbl2.split("\\s");
                    String temp = line[0];

                    if(temp.equals("ENDPROC")){
                        String[] line2 = lbl2last.split("\\s");
                        String first = line2[0];
                        if(first.equals("SETDO"))
                        {
                            task1 = true;
                            if(line2.length>=1){
                                setDOVariable = line2[1];
                            }
                            break;
                        }
                        else{
                            break;
                        }
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
                    String temp = line[0];

                    if(track.size()==localVariables.size())
                    {
                        if(temp!="" && line.length>1 && line[0].equals("ISignalDO") && line[1].equals(setDOVariable))
                        {
                            task2 = true;
                            break;
                        }
                        if(!temp.equals("CONNECT"))
                        {
                            track.remove(0);
                        }
                    }
                    else if(line[0].equals("ENDPROC"))
                    {
                        break;
                    }
                    else{
                        if(track.size()>0 && temp!="CONNECT") track.remove(0);
                    }
                }
            }
        }
        System.out.println("localVariables Vars : " + localVariables);
        System.out.println("setDOVariable "+ setDOVariable); 
        System.out.println("task1 "+task1);
        System.out.println("task2 "+task2);
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#4 : Checked SetDO - ISignalDO task instruction !!");
        System.out.println("                             *****************                                       ");
        //System.out.println("start "+start);
        //System.out.println("Shared RobTarget Vars: " + sharedRobTargetVariables);
        //System.out.println(c3());
    }
    
}
