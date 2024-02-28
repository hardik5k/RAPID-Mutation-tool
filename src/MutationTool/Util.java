package MutationTool;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {
    private static int i = 0;
    public static int getOption(String caption, ArrayList<String> arr, Scanner sc){
        System.out.println(caption);
        int i = 1;
        for (String item : arr) {
            System.out.println(i++ +
                             ". " + item);
        }

        int option = sc.nextInt();

        return option;           
    }

    public static void moveLine(ArrayList<String> code, int x, int y) {
        if (x < 0 || y < 0 || x >= code.size() || y >= code.size() || x == y) {
            System.out.println("Invalid indices. Unable to move lines.");
            return;
        }
        ArrayList<String> mutatedProgram = new ArrayList<String>();
        for (String s : code){
            mutatedProgram.add(s);
        }

        String lineToMove = mutatedProgram.remove(x);
        // Add the line at index y
        mutatedProgram.add(y, lineToMove);

        write_mutation(mutatedProgram);

    }

    public static ArrayList<String> mutate(ArrayList<String>program, int linenumber, String originalOperator, String newOperator){
        ArrayList<String> mutatedProgram = new ArrayList<String>();
        for (String s : program){
            mutatedProgram.add(s);
        }

        String line = mutatedProgram.get(linenumber - 1);
        mutatedProgram.set(linenumber - 1, line.replace(originalOperator, newOperator));

        write_mutation(mutatedProgram);
        return mutatedProgram;
    }

    public static ArrayList<String> mutate_line(ArrayList<String>program, int linenumber, String new_line){
        ArrayList<String> mutatedProgram = new ArrayList<String>();
        for (String s : program){
            mutatedProgram.add(s);
        }

        mutatedProgram.set(linenumber - 1, new_line);

        write_mutation(mutatedProgram);
        return mutatedProgram;
    }



    public static void write_mutation(ArrayList<String>mutated_program) {
        FileWriter writer = null;
        try {
            String program_name = MTool.inputProgram;
            if (program_name.contains("/")){
                String[] split_path = program_name.split("/");
                program_name = split_path[split_path.length - 1];

            }
            writer = new FileWriter("mutant_" + i++ + "_" + program_name);
            for(String str: mutated_program) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
            System.out.println("Mutation successfully generated.");
            for (String x : mutated_program){
                System.out.println(x);
            }
        } catch(IOException err){
            System.out.println(err);
        }


        
    }
}
