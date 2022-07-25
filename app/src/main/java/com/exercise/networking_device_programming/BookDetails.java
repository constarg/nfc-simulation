package com.exercise.networking_device_programming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NdefRecord;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        // Read
        NdefRecord record = NFCSimulation.read_tag();
        String tagValue;
        HashMap<String, String> fromDB;
        ArrayList<HashMap<String, String>> similarBooks;
        Database db;

        // book.
        TextView book_title_t;
        TextView book_cat_t;
        TextView book_price_t;
        TextView book_author_t;

        // first similar book.
        TextView book_f_title_t;
        TextView book_f_cat_t;
        TextView book_f_price_t;
        TextView book_f_author_t;

        // second similar book.
        TextView book_s_title_t;
        TextView book_s_cat_t;
        TextView book_s_price_t;
        TextView book_s_author_t;

        // if no tag has been found.
        if (record == null) {
            startActivity(new Intent(
                    BookDetails.this,
                    Failed.class
            ));
        } else {
            // extract the value of the tag.
            try {
                tagValue = NFCSimulation.extract_tag_value(record);
                // Details from db.
                db = new Database(BookDetails.this);
                fromDB = db.getBookDetails(tagValue);

                // Show the book.
                book_title_t  = (TextView) findViewById(R.id.book_title);
                book_cat_t    = (TextView) findViewById(R.id.book_cat);
                book_price_t  = (TextView) findViewById(R.id.book_price);
                book_author_t = (TextView) findViewById(R.id.book_author);

                book_title_t.append(" "+fromDB.get("title"));
                book_cat_t.append(" "+fromDB.get("category"));
                book_price_t.append(" "+fromDB.get("price"));
                book_author_t.append(" "+fromDB.get("author"));

                similarBooks    = db.getSimilarBooks(fromDB.get("title"), fromDB.get("category"));
                book_f_title_t  = (TextView) findViewById(R.id.book_title_first);
                book_f_cat_t    = (TextView) findViewById(R.id.book_cat_first);
                book_f_price_t  = (TextView) findViewById(R.id.book_price_first);
                book_f_author_t = (TextView) findViewById(R.id.book_author_first);

                book_f_title_t.append(" "+similarBooks.get(0).get("title"));
                book_f_cat_t.append(" "+similarBooks.get(0).get("category"));
                book_f_price_t.append(" "+similarBooks.get(0).get("price"));
                book_f_author_t.append(" "+similarBooks.get(0).get("author"));

                book_s_title_t  = (TextView) findViewById(R.id.book_title_second);
                book_s_cat_t    = (TextView) findViewById(R.id.book_cat_second);
                book_s_price_t  = (TextView) findViewById(R.id.book_price_second);
                book_s_author_t = (TextView) findViewById(R.id.book_author_second);

                book_s_title_t.append(" "+similarBooks.get(1).get("title"));
                book_s_cat_t.append(" "+similarBooks.get(1).get("category"));
                book_s_price_t.append(" "+similarBooks.get(1).get("price"));
                book_s_author_t.append(" "+similarBooks.get(1).get("author"));


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }
}