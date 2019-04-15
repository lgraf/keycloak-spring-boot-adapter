package org.lgraf.mailing;

public class Mailing {
    private final Long id;

    private final String name;


    Mailing( Long id, String name ) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }
}
