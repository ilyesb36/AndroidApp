package com.example.myapplication.classes;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSqLite extends SQLiteOpenHelper {
    private static final String TABLE_PRODUITS = "table_produits";
    private static final String COL_ID = "ID";
    private static final String COL_TITRE = "Titre";
    private static final String COL_QUANTITY = "Quantity";
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PRODUITS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITRE + " TEXT NOT NULL, "
            + COL_QUANTITY + " INT);";

    public MaBaseSqLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On écrit ici ce qu’il faut faire lors d’une mise à jour de l’application. Par exemple supprimer la table et recommencer
        db.execSQL("DROP TABLE " + TABLE_PRODUITS + ";");
        onCreate(db);
    }
}