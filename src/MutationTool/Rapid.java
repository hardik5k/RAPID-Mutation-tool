package MutationTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rapid {
    private static Scanner sc = new Scanner(System.in);
    public static void main(ArrayList<String> program){
        System.out.println("Choose line number");
        int ln = sc.nextInt();

        if (ln < 1 || ln > program.size() + 1) {
            
            System.out.println("Invalid Line number chosen" + ln);
            return;
        }

        if (program.get(ln - 1).contains("Move")){
            handleMove(program, ln);
        }   
        else if (program.get(ln - 1).contains("robtarget")){
            handleRobTarget(program, ln);
        }
        else {
            System.out.println("invalid input. No rapid datastructure found.");
            return;
        }
       
    }

    private static void handleMove(ArrayList<String> program, int ln){
        String[] move_split = split_move_statement(program.get(ln - 1));

        ArrayList<String> possible_mutations = new ArrayList<String>(Arrays.asList("To point", "Speed", "zone", "Tool", "Coordinate system"));
        int option = Util.getOption("Select what to mutate", possible_mutations, sc);
        

        if (option >= 1 && option <= possible_mutations.size()){
            String old = move_split[option];
            if (option == possible_mutations.size()){
                old = move_split[option].split(":=")[1];
            }
            System.out.println("Enter the new value:");
            sc.nextLine();
            String rep = sc.nextLine();

            Util.mutate(program, ln, old, rep);
        }

        

    }

    private static String generate_robtarget(String[] rob_split){
        String new_rob_target = "[";
            int[] rob_target_lengths = {3, 4, 4, 6};
            int i = 0;
            for (int j = 0; j < 4; j++){
                if (j != 0) new_rob_target += ", ";
                new_rob_target += "[ " + rob_split[i++];
                int x = rob_target_lengths[j];
                while (--x > 0){
                    new_rob_target += ", " + rob_split[i++];
                }
                new_rob_target += " ]";

            }
        return new_rob_target + "];";
    }

    private static void handleRobTarget(ArrayList<String> program, int ln){
        String[] line_split = program.get(ln - 1).split(":=");
        String[] rob_split = line_split[1].replaceAll("[\\[;\\]]", " ").trim().replaceAll("\\s+", "").split(",");

        ArrayList<String> possible_mutations = new ArrayList<String>(Arrays.asList("X", "Y", "Z", "Q1", "Q2", "Q3", "Q4"));
        int option = Util.getOption("Select what to mutate", possible_mutations, sc);
        

        if (option >= 1 && option <= possible_mutations.size()){

            System.out.println("Enter the new value:");
            sc.nextLine();
            String rep = sc.nextLine();

            rob_split[option - 1] = rep;

            String new_line = line_split[0] + " := " + generate_robtarget(rob_split);

            Util.mutate_line(program, ln, new_line);
        }
        else {
            System.out.println("Invalid input");
        }

        

    }

    private static String[] split_move_statement(String input){
        Pattern pattern = Pattern.compile("\\w+\\([^)]*\\)|\\w+");
        Matcher matcher = pattern.matcher(input);
        List<String> parts = new ArrayList<>();
        while (matcher.find()) {
            parts.add(matcher.group());
        }
        if (parts.size() > 5){
            parts.set(5, "\\" + parts.get(5) + ":=" + parts.get(6));
            parts.remove(parts.size() - 1);
        }

        return parts.toArray(new String[0]);
    }
}
