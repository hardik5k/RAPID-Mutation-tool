package MutationTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SetDoIsignalDo {
    private static Scanner sc = new Scanner(System.in);
    public static ArrayList<String> opr = new ArrayList<String>(Arrays.asList("Change digital output", "change value", "change interrupt var", "move statement"));
        
    public static void main(ArrayList<String> program){
        int ln;
        do {
            System.out.println("Please enter line number to mutate. Enter -1 to go back.");
            ln = sc.nextInt();
            if (ln == -1){
                return;
            }
            
            else if (ln >= 1 && ln <= program.size() && (program.get(ln - 1).contains("SETDO") || program.get(ln - 1).contains("ISIGNALDO"))){
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
                        System.out.println("Enter new value");
                        sc.nextLine();
                        split_line[2] = sc.nextLine();
                        break;
                    case 3:
                        if (program.get(ln - 1).contains("ISIGNALDO")){
                            System.out.println("Enter new value");
                            sc.nextLine();
                            split_line[3] = sc.nextLine();
                        }
                        else {
                            System.out.println("Not valid for this line");
                        }
                        break;
                    case 4:
                        System.out.println("Enter new line number");
                        sc.nextLine();
                        int new_ln = sc.nextInt();
                        Util.moveLine(program, ln - 1, new_ln - 1);
                        ln = new_ln;
                        break;
                
                    default:
                        System.out.println("Invalid option chosen");
                        break;
                }
                String new_line = split_line[0] + " " + split_line[1] + ", " + split_line[2] + (split_line.length == 4 ? ", " + split_line[3]: "" ) +";";
                Util.mutate_line(program, ln, new_line);
            }
            else {
                System.out.println("Invalid input");
                return;
            }

        } while (ln != -1);
    }
}
