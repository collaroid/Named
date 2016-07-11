package com.collar.named.entity;

/**
 * Created by Frank on 7/10/16.
 */
public class Character {

    private int strokes;
    private Attribute attribute;
    private String value;
    private String url;
    private String pingying;

    public int getStrokes() {
        return strokes;
    }

    public void setStrokes(int strokes) {
        this.strokes = strokes;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPingying() {
        return pingying;
    }

    public void setPingying(String pingying) {
        this.pingying = pingying;
    }
}
