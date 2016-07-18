package com.collar.named.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Frank on 7/10/16.
 */
public enum Attribute {

    Unknow(0, "未知"),
    Gold(1, "金"),
    Wood(2, "木"),
    Water(3, "水"),
    Fire(4, "火"),
    Earth(5, "土");

    private int id;
    private String name;
    private static Map<String, Attribute> nameMap = new HashMap<String, Attribute>(10);
    private static Map<Integer, Attribute> idMap = new HashMap<Integer, Attribute>(10);

    static {
        for (Attribute attribute : Attribute.values()) {
            nameMap.put(attribute.name, attribute);
            idMap.put(attribute.id, attribute);
        }
    }

    Attribute (int id, String name){
        this.id = id;
        this.name = name;
    }

    public static Attribute getAttributeByName (String name) {
        if (nameMap.get(name) == null) {
            return Unknow;
        }
        return nameMap.get(name);
    }

    public static Attribute getAttributeById (int id) {
        if (idMap.get(id) == null) {
            return Unknow;
        }
        return idMap.get(id);
    }


    public int getId() {
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
}
