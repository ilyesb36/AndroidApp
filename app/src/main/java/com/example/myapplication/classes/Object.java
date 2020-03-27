package com.example.myapplication.classes;

public class Object {

    private int id;
    private String titre;
    private int quantity;

    public Object() {
    }

    public Object(String titre, int quantity) {
        this.titre = titre;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", titre='" + titre + '\'' +
                ", quantity=" + quantity ;
    }
}
