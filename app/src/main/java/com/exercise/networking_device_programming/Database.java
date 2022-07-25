package com.exercise.networking_device_programming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {
    private static final String dbName = "bookstoredb";
    private static final int dbv = 1;


    public Database(Context context) {
        super(context, dbName, null, dbv);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE books (" +
                       "ID_BOOK INTEGER PRIMARY KEY AUTOINCREMENT," +
                       "BOOK_CATEGORY VARCHAR(120)," +
                       "BOOK_TITLE VARCHAR(70)," +
                       "BOOK_PRICE VARCHAR(3)," +
                       "BOOK_AUTHOR VARCHAR(120));";

        sqLiteDatabase.execSQL(query);

        ArrayList<String> books = new ArrayList<>();
        books.add("The Hobbit");
        books.add("The Fellowship of the Ring");
        books.add("The Two Towers");
        books.add("The Return of the King");

        ContentValues tmp = new ContentValues();
        // insert a few books in db.
        for (String book: books
             ) {
            tmp.put("BOOK_TITLE", book);
            tmp.put("BOOK_CATEGORY", "Fantasy");
            tmp.put("BOOK_PRICE", "90");
            tmp.put("BOOK_AUTHOR", "Tolkien");

            sqLiteDatabase.insert(
                    "books",
                    null,
                    tmp);
            tmp.clear();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books");
        onCreate(sqLiteDatabase);
    }

    public HashMap<String, String> getBookDetails(String book) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> result;
        Cursor cursor;

        String[] columns = {
                "BOOK_TITLE", "BOOK_CATEGORY" ,"BOOK_PRICE", "BOOK_AUTHOR"
        };

        if (db.isOpen()) {
            cursor = db.query(
                    "books",
                    columns,
                    "BOOK_TITLE='"+book+"'",
                    null,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();

            result = new HashMap<>();
            result.put("title", cursor.getString(0));
            result.put("category", cursor.getString(1));
            result.put("price", cursor.getString(2));
            result.put("author", cursor.getString(3));

            db.close();
            return result;
        }

        return null;
    }

    public ArrayList<HashMap<String, String>> getSimilarBooks(String book, String cat) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> result;
        HashMap<String, String> firstBook;
        HashMap<String, String> secondBook;
        Cursor cursor;

        String[] columns = {
                "BOOK_TITLE", "BOOK_CATEGORY" ,"BOOK_PRICE", "BOOK_AUTHOR"
        };

        if (db.isOpen()) {
            cursor = db.query(
                    "books",
                    columns,
                    "BOOK_CATEGORY='"+cat+"' and BOOK_TITLE != '"+book+"'",
                    null,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();

            result     = new ArrayList<>();
            firstBook  = new HashMap<>();
            secondBook = new HashMap<>();

            firstBook.put("title", cursor.getString(0));
            firstBook.put("category", cursor.getString(1));
            firstBook.put("price", cursor.getString(2));
            firstBook.put("author", cursor.getString(3));

            cursor.moveToNext();
            secondBook.put("title", cursor.getString(0));
            secondBook.put("category", cursor.getString(1));
            secondBook.put("price", cursor.getString(2));
            secondBook.put("author", cursor.getString(3));

            result.add(firstBook);
            result.add(secondBook);

            db.close();
            return result;
        }
        return null;

    }
}
