package model;

// User.java

public class User {
    private final String id, name;
    public User(String id, String name) {
        if (id == null|| name == null) throw new IllegalArgumentException();
        this.id = id; this.name = name;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    @Override public boolean equals(Object o){
        return o instanceof User && ((User)o).id.equals(id);
    }
    @Override public int hashCode(){ return id.hashCode(); }
    @Override public String toString(){ return name+"("+id+")"; }
}
