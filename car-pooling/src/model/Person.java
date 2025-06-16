package model;

import utils.IdGenerator;

public class Person {
    private final String personId;
    private final String name;
    public Person(String name) {
        this.personId = IdGenerator.nextId("P");
        this.name     = name;
    }
    public String getPersonId() { return personId; }
    public String getName()     { return name; }
}