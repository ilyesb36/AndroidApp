package com.example.myapplication.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProduitsBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "produits.db";
    private static final String TABLE_PRODUIT = "table_produits";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TITRE = "Titre";
    private static final int NUM_COL_TITRE = 1;
    private static final String COL_QUANTITY = "Quantity";
    private static final int NUM_COL_QUANTITY = 2;
    private SQLiteDatabase bdd;
    private MaBaseSqLite maBaseSQLite;
    public ProduitsBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBaseSqLite(context, NOM_BDD, null, VERSION_BDD);
    }
    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }
    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }
    public SQLiteDatabase getBDD(){
        return bdd;
    }
    public long insertProduit(Produit produit){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé
        values.put(COL_TITRE, produit.getTitre());
        values.put(COL_QUANTITY, produit.getQuantity());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PRODUIT, null, values);
    }
    public int updateProduit(String titre, Produit produit){
        //La mise à jour d'un livre
        //on précise quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_TITRE, produit.getTitre());
        values.put(COL_QUANTITY, produit.getQuantity());
        return bdd.update(TABLE_PRODUIT, values, COL_TITRE + " LIKE \"" + titre +"\"", null);
    }
    public int ajouterProduit(String titre){
        ContentValues values = new ContentValues();
        values.put(COL_QUANTITY, getQuantityWithTitle(titre)+1);
        return bdd.update(TABLE_PRODUIT, values, COL_TITRE + " LIKE \"" + titre +"\"", null);
    }
    public int decrementerProduit(String titre,int quantity){
        ContentValues values = new ContentValues();
        values.put(COL_QUANTITY, getQuantityWithTitle(titre)-quantity);
        return bdd.update(TABLE_PRODUIT, values, COL_TITRE + " LIKE \"" + titre +"\"", null);
    }
    public int removeProduitWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_PRODUIT, COL_ID + " = " +id, null);
    }
    public Produit getProduitWithTitre(String titre){
        //On récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PRODUIT, new String[] {COL_ID, COL_TITRE, COL_QUANTITY},
                COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToProduit(c);
    }
    public int getQuantityWithTitle(String titre){
        Cursor c = bdd.query(TABLE_PRODUIT, new String[] {COL_ID, COL_TITRE, COL_QUANTITY},
                COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToQuantity(c);
    }
    //Cette méthode permet de convertir un cursor en un livre
    private Produit cursorToProduit(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;
        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Produit produit = new Produit();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        produit.setId(c.getInt(NUM_COL_ID));
        produit.setTitre(c.getString(NUM_COL_TITRE));
        produit.setQuantity(c.getInt(NUM_COL_QUANTITY));
        //On ferme le cursor
        c.close();
        //On retourne le livre
        return produit;
    }

    private int cursorToQuantity(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return 0;
        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre

        int quantity = c.getInt(NUM_COL_QUANTITY);
        //On ferme le cursor
        c.close();
        //On retourne le livre
        return quantity;
    }
}