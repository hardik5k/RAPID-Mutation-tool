package Main;

public class Bool_bool {
    Boolean rob1, rob2,rob3,rob4;
    String insertAtTask;

    public Bool_bool(Boolean rob1, Boolean rob2, Boolean rob3, Boolean rob4)
    {
        this.rob1 = rob1;
        this.rob2 = rob2;
        this.rob3 = rob3;
        this.rob4 = rob4;
    }
    public Bool_bool(String s)
    {
        this.insertAtTask = s;
    }

    public void set_rob1(Boolean bool1)
    {
        rob1 = bool1;
    }
    public void set_rob2(Boolean bool2)
    {
        rob2 = bool2;
    }
    public void set_rob3(Boolean bool3)
    {
        rob3 = bool3;
    }
    public void set_rob4(Boolean bool4)
    {
        rob4 = bool4;
    }
    public Boolean get_rob1(){
        return this.rob1;
    }
    public Boolean get_rob2(){
        return this.rob2;
    }
    public Boolean get_rob3(){
        return this.rob3;
    }
    public Boolean get_rob4(){
        return this.rob4;
    }
    public void set_insertedAtthistask(String insert)
    {
        this.insertAtTask = insert;
    }
    public String get_insertedAtthistask() 
    {
        return this.insertAtTask;
    }

}
