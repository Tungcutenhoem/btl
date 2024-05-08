package Model;

import java.util.ArrayList;
import java.util.List;

public class APIList {
    private ArrayList<String>list = new ArrayList<>();
    public APIList()
    {

    }
    public APIList(ArrayList list)
    {
        this.list = list;
    }

    public ArrayList<String> getListAPI() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    public void AddAPIString(String API)
    {
        list.add(API);
    }
}
