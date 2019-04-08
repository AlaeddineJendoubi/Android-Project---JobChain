package esprit.tn.DataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import esprit.tn.Entities.Favoris;

import java.util.ArrayList;

public class DbController extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DbController(Context context) {
        //num de version se sont les nombres des lancement de l'application
        super(context, "Mat.db", null, 1);
    }

    //1ére methode appelée lors de l'installation de l'apk : la création des tbles se fait ici
// appelé une seule fois
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FAVORIS(ID STRING PRIMARY KEY ,IDUSER TEXT,IMG INTEGER ,NOM TEXT , PRENOM TEXT, REGION TEXT,SPECIALITE TEXT ,DESCRIPTION TEXT  UNIQUE, NBFOIS TEXT )");
    }

    //appelé à partir de la deuxiéme fois
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FAVORIS");
        onCreate(db);
    }

    public void insert_contact(String id,String idUser, int img, String nom, String prenom, String spec, String reg, String desc) {
        ContentValues content = new ContentValues();
        content.put("ID", id);
        content.put("IDUSER", idUser);
        content.put("IMG", img);
        content.put("NOM", nom);
        content.put("PRENOM", prenom);
        content.put("REGION", reg);
        content.put("SPECIALITE", spec);
        content.put("DESCRIPTION", desc);

        this.getWritableDatabase().insertOrThrow("FAVORIS", "", content);

    }


    public ArrayList<Favoris> affichertout(String id) {
        ArrayList<Favoris> Fav = new ArrayList<>();
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from FAVORIS where IDUSER ='" + id + "'" , null);

        if (cursor.moveToNext()) {


            do {
                int im = cursor.getInt(1);
                String imm = cursor.getString(2);
                String nm = cursor.getString(3);
                String mm = cursor.getString(4);
                String mm1 = cursor.getString(5);
                String mm2 = cursor.getString(6);
                //String mm2 = cursor.getString(6);
 Fav.add(new Favoris(im,imm, nm, mm, mm1));
 } while (cursor.moveToNext());
        }
        return Fav;
    }

    public void deletefav(String id)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete("FAVORIS","ID = ? ", {id});
        this.getWritableDatabase().delete("FAVORIS", "IDUSER='" + id + "'", null);
    }


}