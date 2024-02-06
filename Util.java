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

        String lineToMove = code.remove(x);

        // Add the line at index y
        code.add(y, lineToMove);
    }

    public static ArrayList<String> mutate(ArrayList<String>program, int linenumber, String originalOperator, String newOperator){
        ArrayList<String> mutatedProgram = new ArrayList<String>();
        for (String s : program){
            mutatedProgram.add(s);
        }

        String line = mutatedProgram.get(linenumber - 1);
        mutatedProgram.set(linenumber - 1, line.replace(originalOperator, newOperator));

        for (String x : mutatedProgram){
            System.out.println(x);
        }
        write_mutation(mutatedProgram);
        return mutatedProgram;
    }

    public static ArrayList<String> mutate_line(ArrayList<String>program, int linenumber, String new_line){
        ArrayList<String> mutatedProgram = new ArrayList<String>();
        for (String s : program){
            mutatedProgram.add(s);
        }

        mutatedProgram.set(linenumber - 1, new_line);

        for (String x : mutatedProgram){
            System.out.println(x);
        }
        write_mutation(mutatedProgram);
        return mutatedProgram;
    }



    public static void write_mutation(ArrayList<String>mutated_program) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("mutant_" + i++ + "_" + MTool.inputProgram);
            for(String str: mutated_program) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch(IOException err)
        {System.out.println(err);}
        
    }
}
