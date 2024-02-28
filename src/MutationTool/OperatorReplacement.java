package MutationTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class OperatorReplacement {
    
    private static ArrayList<String> rel_opr = new ArrayList<String>(Arrays.asList("<", ">", "=", ">=", "<=", "<>"));
    private static ArrayList<String> arth_opr = new ArrayList<String>(Arrays.asList("+", "-", "/", "*"));
    private static ArrayList<String> log_opr = new ArrayList<String>(Arrays.asList("&&", "||", "not"));
    private static ArrayList<String> opr_cat = new ArrayList<String>(Arrays.asList("ROR", "AOR", "LOR", "RETURN"));

    private static Scanner sc = new Scanner(System.in);

        
    public static void main(ArrayList<String> program){
        int opr_cat_chosen;
        do {
        
            opr_cat_chosen = Util.getOption("Please choose from one of the following categories of operators", opr_cat, sc);


            switch(opr_cat_chosen){
                case 1 :
                    mutate_operator(rel_opr, program);
                    break;
                case 2 :
                    mutate_operator(arth_opr, program);
                    break;
                case 3 :
                    mutate_operator(log_opr, program);
                    break;
                case 4 :
                    return;
                default:
                    System.out.println("Invalid option chosen");
                    break;
            }

        } while (opr_cat_chosen != 4);
    }

    private static void mutate_operator(ArrayList<String> ops, ArrayList<String> program){
        int ln;
        System.out.println("Please enter the line number:" );
        ln = sc.nextInt();

        if (ln < 1 || ln > program.size() + 1) {
            System.out.println("Invalid Line number chosen");
            return;
        }

        ArrayList<String> availableOperators = new ArrayList<>();

        for (String x : ops){
            if (program.get(ln - 1).contains(x)){
                availableOperators.add(x);
            }
        }

        if (availableOperators.size() == 0 ){
            System.out.println("No valid operator found");
            return;
        }
        
            int option = Util.getOption("Choose from one of the following possible operators to mutate", availableOperators, sc);
            if (option == -1){
                System.out.println("Invalid option choosen");
                return;
            }
            System.out.println("Enter the new operator");
            sc.nextLine();
            String changedOperator = sc.nextLine();



            if (ops.contains(changedOperator)) {
                Util.mutate(program, ln, availableOperators.get(option - 1), changedOperator);
            }
            else {
                System.out.println("Invalid input");
                return;
            }
        }


    

}

