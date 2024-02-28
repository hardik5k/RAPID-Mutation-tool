package MutationTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Main.Parser;

public class Mutation {

    static ArrayList<String> unitLevelMutations = new ArrayList<String>(Arrays.asList("Operator Replacement", "Replace Conditionals", "Mutate Assignment Statements", "RAPID Specific mutation", "Go back"));
    static ArrayList<String> taskLevelMutations = new ArrayList<String>(Arrays.asList("Wait Until/Test and Set", "Set/Wait Do", "Set Do Isignal Do", "WaitSyncTask/SyncMoveOn", "Go back"));
    private static Scanner sc = new Scanner(System.in);
    public static void UnitLevel(ArrayList<String> program, Parser p){

        
        int option;
        do {
            option = Util.getOption("Please choose from one of the following categories", unitLevelMutations, sc);

        switch(option){
            case 1 :
                OperatorReplacement.main(program);
                break;
            case 2 :
                ConditionalReplacement.main(program, p);
                break;
            case 3 :
                AssignmentMutation.main(program);
                break;
            case 4 :
                Rapid.main(program);
                break;
            case 5 :
                break;
            default:
                System.out.println("Invalid option chosen");

        }
    } while (option != 5);


    }

    public static void TaskLevel(ArrayList<String> program, Parser p){

        
        int option;
        do {
            option = Util.getOption("Please choose from one of the following categories", taskLevelMutations, sc);

        switch(option){
            case 1 :
                WaitUntil.main(program);
                break;
            case 2 :
                SetDoWaitDo.main(program);
                break;
            case 3 :
                SetDoIsignalDo.main(program);
                break;
            case 4 :
                WaitSyncTask.main(program);
                break;
            case 5 :
                break;
            default:
                System.out.println("Invalid option chosen");

        }
    } while (option != 5);


    }

}
