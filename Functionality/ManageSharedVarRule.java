package Functionality;
import Main.Variable;
import Main.Parser;

import java.util.*;
import java.io.*;

public class ManageSharedVarRule {
    private static Integer lineNumber = 0;
    private static ArrayList<Parser> parsedTasks = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedVariables = new ArrayList<>();
    public static ArrayList<ArrayList<String>> sharedRobTargetVariables = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        
        FileReader fr = new FileReader("/home/opt1musenpai/Darpan/Meenakshi mam summer project/task analyzer/Rapid-program-parser/src/InputRules/rule6.txt"); // path to source rapid file
        BufferedReader br = new BufferedReader(fr);
        String lbl = ""; String lbl2 = ""; String lbl2last = "";

        ArrayList<String> globalVariables = new ArrayList<>();
        ArrayList<String> localVariables = new ArrayList<>();

        int taskCount = 0, mainCount = 0;
        boolean task1 = false, task2 = false, start = false;
        String stringVariable = "";
        String methodName = "";
        boolean checkOne = false;
        boolean checkTwo = false;

        while ((lbl = br.readLine()) != null)  {
            lineNumber++;
            lbl = lbl.replaceFirst("^\\s*", "");
            
            if (lbl.startsWith("BEGINTASK")) {
                taskCount++;
            }

            if(lbl.startsWith("CONST")) {
                String[] line = lbl.split("\\s");
                if(!line[2].equals("Initial_point")) 
                    {
                        localVariables.add(line[2]);
                    }
            }

            if(lbl.startsWith("PERS string")) {
                String[] line = lbl.split("\\s");
                stringVariable = line[2];
            }

            if(lbl.startsWith("PROC MAIN()") && taskCount==1) 
            {
                ArrayList<String> track = new ArrayList<>(localVariables);
                while((lbl2 = br.readLine()) != null){ 
                    lbl2 = lbl2.replaceFirst("^\\s*", "");                    
                    String[] line = lbl2.split("\\s");
                    String[] line2 = lbl2.split(":=");
                    String tempVar = ""; String tempMeth="";
                    if(line2.length>1 && line2[0].equals(stringVariable))
                    {
                        tempVar = line2[0];
                        tempMeth = line2[1];

                        if(tempMeth.length()>2) 
                        {
                            tempMeth = tempMeth.substring(1,tempMeth.length()-2);
                        }

                        methodName = tempMeth;
                    }

                    if(line[0].equals("ENDPROC"))
                    {
                        if(lbl2last.startsWith(stringVariable))
                        {
                            task1 = true;
                        }
                        break;
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

                    if(line[0].equals(methodName)){
                        checkOne = true;
                        //break;
                    }
                    if(line[0].equals("ENDPROC"))
                    {
                        //break;
                    }
                    if(lbl2.startsWith("PROC "+methodName))
                    {
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
                            checkTwo = true;
                        }
                        break;
                    }                    
                }
            }

        }
        //System.out.println("checkOne "+checkOne);
        //System.out.println("checkTwo "+checkTwo);
        if(checkOne==true && checkTwo==true) task2 = true;
        System.out.println("localVariables Vars : " + localVariables);
        System.out.println("stringVariable "+ stringVariable); 
        System.out.println("method name "+ methodName);
        System.out.println("task1 "+task1);
        System.out.println("task2 "+task2);
        System.out.println("                             *****************                                       ");
        System.out.println("             Rule#6 : Checked PERS - shared variables rule !!");
        System.out.println("                             *****************                                       ");
        //System.out.println("start "+start);
        //System.out.println("Shared RobTarget Vars: " + sharedRobTargetVariables);
        //System.out.println(c3());
    }
    
}
