package MutationTool;
import java.util.ArrayList;
import java.util.Scanner;

public class AssignmentMutation {
    
    private static Scanner sc = new Scanner(System.in);

        
    public static void main(ArrayList<String> program){
        int ln;
        do {
        
            System.out.println("Please enter line number to mutate. Enter -1 to go back.");

            ln = sc.nextInt();
            if (ln == -1){
                return;
            }
            else if (ln >= 1 && ln <= program.size() && program.get(ln - 1).contains(":=")){
                String new_line = change_assignment(program.get(ln -1));
                Util.mutate_line(program, ln, new_line);
            }
            else {
                System.out.println("Invalid input");
                return;
            }

        } while (ln != -1);
    }

    private static String change_assignment(String line){
        
            String[] split_line = line.split(":=");
            String new_val = "";
            System.out.println("Enter new value");
            sc.nextLine();
            new_val = sc.nextLine();
            return split_line[0] + ":= " +  new_val;
        
        
    }

}

