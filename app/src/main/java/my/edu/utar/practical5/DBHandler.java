package my.edu.utar.practical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "petTracker.db";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pets (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE, type_id INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS pets");
    }
    public void addPet(PetDAO pet){
        SQLiteDatabase db = this.getWritableDatabase(); // Get writable database
        ContentValues values = new ContentValues();
        values.put("name", pet.getPetName());  // Store pet name
        values.put("type_id", pet.getPetType()); // Store pet type

        // Insert the pet into the database
        db.insert("pets", null, values);

        // Close database connection
        db.close();
    }

    public List<PetDAO> getAllPet() {
        List<PetDAO> petList = new ArrayList<>();
        String selectQuery = "SELECT * FROM pets";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PetDAO pet = new PetDAO();
                pet.setId(cursor.getInt(0));
                pet.setPetName(cursor.getString(1));     //name
                pet.setPetType(cursor.getString(2));     //type_id
                petList.add(pet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return petList;
    }

    public int updatePet(PetDAO pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", pet.getPetName());  // Update pet name

        // Correct usage of db.update()
        int rowsUpdated = db.update("pets", values, "name=?", new String[]{pet.getPetName()});

        db.close(); // Close database connection
        return rowsUpdated; // Return number of rows updated
    }

    public void deletePet(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("pets", "name="+name, null);
        db.close();
    }
}