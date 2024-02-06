import java.util.ArrayList;
import java.util.Scanner;

import Main.ConditionalStatements;
import Main.Parser;

public class ConditionalReplacement {
    
    private static Scanner sc = new Scanner(System.in);

        
    public static void main(ArrayList<String> program, Parser p){
        int ln;
        do {
        
            System.out.println("Please enter line number to mutate. Enter -1 to go back.");

            ln = sc.nextInt();
            if (ln == -1){
                return;
            }
            else if (ln >= 1 && ln <= program.size()){
                for (ConditionalStatements c : p.cs){
                    if (program.get(ln - 1).contains(c.getCondition())){
                        System.out.println("Enter the new condition");
                        sc.nextLine();
                        String new_cond = sc.nextLine();

                        Util.mutate(program, ln, c.getCondition(), " " + new_cond + " ");
            }
        }
            }
            else {
                System.out.println("Invalid input");
                return;
            }

        } while (ln != -1);
    }

}

