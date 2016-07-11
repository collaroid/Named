package com.collar.named.entity;

/**
 * Created by Frank on 7/10/16.
 */
public enum Attribute {

    Gold(1, "金"),
    Wood(2, "木"),
    Water(3, "水"),
    Fire(4, "火"),
    Earth(5, "土");

    private int id;
    private String name;

    Attribute (int id, String name){
        this.id = id;
        this.name = name;
    }
}
