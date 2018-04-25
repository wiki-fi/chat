package com.fomina.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    // Properties ---------------------------------------------------------------------------------

    private int id;
    private String name;

    // Constructor --------------------------------------------------------------------------------

    public User(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public User(String name){ this.name = name; }

    // Getters & Setters --------------------------------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Object overrides ---------------------------------------------------------------------------

    /**
     * Returns the String representation of this User.
     * Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName());
    }
}
