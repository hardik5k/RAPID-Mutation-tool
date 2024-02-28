package Main;

public class Ident_Task_name {
    String ident_name, task_name;
    public Ident_Task_name(String ident_name, String task_name)
    {
        this.ident_name = ident_name;
        this.task_name = task_name;
    }
    public Ident_Task_name(String ident_name)
    {
        this.ident_name = ident_name;
        // this.task_name = task_name;
    }
    public String get_ident_name() {
        return this.ident_name;
    }
    public String get_task_name() {
        return this.task_name;
    }
    
}

// class Bool_list{
//     Boolean rob1, rob2,rob3,rob4;

//     public Bool_list(Boolean rob1, Boolean rob2, Boolean rob3, Boolean rob4)
//     {
//         this.rob1 = rob1;
//         this.rob2 = rob2;
//         this.rob3 = rob3;
//         this.rob4 = rob4;
//     }

//     // @Override
//     // public String toString()
//     // {
//     //     return 
//     // }

// }