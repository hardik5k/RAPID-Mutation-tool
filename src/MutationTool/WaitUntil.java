package MutationTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WaitUntil {
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<String> opr = new ArrayList<String>(Arrays.asList("change variable", "toggle Test and Set", "move statement"));
        
    public static void main(ArrayList<String> program){
        int ln;
        do {
            System.out.println("Please enter line number to mutate. Enter -1 to go back.");
            ln = sc.nextInt();
            if (ln == -1){
                return;
            }
            
            else if (ln >= 1 && ln <= program.size() && program.get(ln - 1).contains("WaitUntil")){
                if (program.get(ln - 1).contains("TestAndSet")){
                    String new_line = handle_wait_untill_test(program, ln);
                    if (new_line != ""){
                        Util.mutate_line(program, ln, new_line);
                    }
                    
                }
                else{
                    String new_line = handle_wait_untill(program, ln);
                    if (new_line != ""){
                        Util.mutate_line(program, ln, new_line);
                    }
                }
            }
            else {
                System.out.println("Invalid input");
                return;
            }

        } while (ln != -1);
    }

    public static String handle_wait_untill(ArrayList<String> program, int ln){
        String[] split_line = program.get(ln - 1).replaceAll("[;]", " ").trim().split("\\s+");
                int option;
                option = Util.getOption("Choose from one of the following", opr, sc);
                switch (option) {
                    case 1:
                        System.out.println("Enter new variable");
                        sc.nextLine();
                        split_line[1] = sc.nextLine();
                        break;
                    case 2:
                        split_line[1] = "TestAndSet(" + split_line[1] + ")";
                        break;
                    case 3:
                        System.out.println("Enter new line number");
                        sc.nextLine();
                        int new_ln = sc.nextInt();
                        Util.moveLine(program, ln - 1, new_ln - 1);
                        return "";
                
                    default:
                        System.out.println("Invalid option chosen");
                        break;
                }
                return split_line[0] + " " + split_line[1] + ";";
                
    }

    public static String handle_wait_untill_test(ArrayList<String> program, int ln){
        String[] split_line = program.get(ln - 1).replaceAll("[();]", " ").trim().split("\\s+");
                int option;
                option = Util.getOption("Choose from one of the following", opr, sc);
                switch (option) {
                    case 1:
                        System.out.println("Enter new variable");
                        sc.nextLine();
                        split_line[2] = sc.nextLine();
                        break;
                    case 2:
                        return split_line[0] + " " + split_line[2] + ";";
                    case 3:
                        System.out.println("Enter new line number");
                        sc.nextLine();
                        int new_ln = sc.nextInt();
                        Util.moveLine(program, ln - 1, new_ln - 1);
                        return "";
                
                    default:
                        System.out.println("Invalid option chosen");
                        break;
                }
                return split_line[0] + " " + split_line[1] + "(" + split_line[2] + ");";
                
    }
}
