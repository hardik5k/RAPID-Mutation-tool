import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WaitSyncTask {
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<String> opr = new ArrayList<String>(Arrays.asList("change identity point", "change task"));
        
    public static void main(ArrayList<String> program){
        int ln;
        do {
            System.out.println("Please enter line number to mutate. Enter -1 to go back.");
            ln = sc.nextInt();
            if (ln == -1){
                return;
            }
            
            else if (ln >= 1 && ln <= program.size() && (program.get(ln - 1).contains("WaitSyncTask") || program.get(ln - 1).contains("SyncMoveOn"))){
                String[] split_line = program.get(ln - 1).replaceAll("[;,]", " ").trim().split("\\s+");
                int option;
                option = Util.getOption("Choose from one of the following", opr, sc);
                switch (option) {
                    case 1:
                        System.out.println("Enter new variable");
                        sc.nextLine();
                        split_line[1] = sc.nextLine();
                        break;
                    case 2:
                        System.out.println("Enter new task");
                        sc.nextLine();
                        split_line[2] = sc.nextLine();
                        break;
                
                    default:
                        System.out.println("Invalid option chosen");
                        break;
                }
                String new_line = split_line[0] + " " + split_line[1] + ", " + split_line[2] + ";";
                Util.mutate_line(program, ln, new_line);
            }
            else {
                System.out.println("Invalid input");
                return;
            }

        } while (ln != -1);
    }
}
