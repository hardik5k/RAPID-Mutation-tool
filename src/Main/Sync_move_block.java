package Main;
import java.util.*;

public class Sync_move_block {
    private Ident_Task_name name_on , name_off;
    // LinkedHashMap<int>;
    private Stack<String> sync_stack = new Stack<String> ();

    public Sync_move_block(Ident_Task_name nameon, Ident_Task_name nameoff,Stack<String> sync_stack) {
        this.name_on = nameon;
        this.name_off = nameoff;
        this.sync_stack = sync_stack;
    }
    public Sync_move_block(){

    }

    public Ident_Task_name getName_on() {
        // System.out.println("sdfgsd");
        return this.name_on;
    }
    
    public void setName_on(Ident_Task_name name_on) {
        this.name_on = name_on;
    }
    public Ident_Task_name getName_off() {
       
        return this.name_off;
    }
    public void setName_off(Ident_Task_name name_off) {
        this.name_off = name_off;
    }
    public Stack<String> getSync_stack() {
        return this.sync_stack;
    }
    public void setSync_stack(Stack<String> sync_stack) {
        // this.sync_stack = sync_stack;
        for(int i = 0; i < sync_stack.size(); i++){
            this.sync_stack.push(sync_stack.get(i));
        }
    }
    public void addSync_stack(String num) {
        sync_stack.add(num);
    }
    public void clearSync_stack() {
        sync_stack.clear();
    }
}
