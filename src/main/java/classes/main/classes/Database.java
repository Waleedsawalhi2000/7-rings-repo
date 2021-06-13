package classes.main.classes;

import classes.main.classes.Vlan;

import java.util.List;

public class Database {
   final List<Vlan> list;

    public Database(List<Vlan> list) {
        this.list=list;
    }
    public List<Vlan> getDatabase(){
        return list;
    }
}
