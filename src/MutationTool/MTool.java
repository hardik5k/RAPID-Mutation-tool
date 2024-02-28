package MutationTool;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import Main.Parser;


public class MTool {

    private static ArrayList<String>program = new ArrayList<String>();   
    public static String inputProgram; 

    public static void main(String[] args) throws IOException{

        Scanner sc = new Scanner(System.in);
        Parser p = new Parser();
        inputProgram = args[0];
        Integer lineNumber = 0;
        
        FileReader fr = new FileReader(inputProgram); 
        BufferedReader br = new BufferedReader(fr);
        String lbl = "";
        while ((lbl = br.readLine()) != null) {
            lineNumber++;
            lbl = lbl.replaceFirst("^\\s*", "");
            program.add(lbl);
            if (lbl.startsWith("BEGINTASK")) {
                String[] tokens = lbl.replaceAll("\\s", "").split("<|>|,");
                // p = new Parser();
                p.taskName = tokens[1];
                p.foregroundTaskName = tokens[2];
                p.processTask(br, lineNumber, 0, program);
                lineNumber = p.lineNumber + 1;               
        }    
        
    }
    int option = -1;
        do {
            System.out.println("Please choose from one of the following mutation tasks:");
            System.out.println("1. Unit level");
            System.out.println("2. Task level");
            System.out.println("3. Exit");

            option = sc.nextInt();

        switch(option){
            case 1 :
                Mutation.UnitLevel(program, p);
                break;
            case 2 :
                Mutation.TaskLevel(program, p);
                break;
            case 3 :
                System.out.println("Thank you for using this tool");
                break;
            default:
                System.out.println("Invalid option chosen");

        }
    } while (option != 3);
    fr.close();
    sc.close();
}
        
}
