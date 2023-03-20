package com.example.work;

public class idiom {
    private String name;
    private String spell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public idiom(String name, String spell) {
        this.name = name;
        this.spell = spell;
    }

    public idiom() {
    }

    @Override
    public String toString() {
        return "idiom{" +
                "name='" + name + '\'' +
                ", spell='" + spell + '\'' +
                '}';
    }
}
