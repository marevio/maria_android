package gr.ihu.ct.week4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import gr.ihu.ct.week4.classes.DataStore;

public class ListActivity extends AppCompatActivity {
    TextView textViewInfo;
    ListView listViewBooks;

    private void findViews(){
        textViewInfo = (TextView)findViewById(R.id.textViewInfo);
        listViewBooks = (ListView)findViewById(R.id.listViewBooks);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        String filterAuthor = intent.getStringExtra("AUTHOR");
        String filterTitle = intent.getStringExtra("TITLE");
        int filterGenreId = intent.getIntExtra("GENREID", 0);


        findViews();
        //show user filters for information
        String message = String.format("Author: %s\nTitle: %s\nGenreId: %d", filterAuthor, filterTitle, filterGenreId);
        textViewInfo.setText(message);
        //show all genres on our list



        DataStore.LoadBooks(filterAuthor, filterTitle, filterGenreId);


        //COMPLEX OBJECT BINDING
        ListAdapter booksAdapter = new SimpleAdapter(
                this,
                DataStore.Books,
                R.layout.list_item,
                new String[] {DataStore.KEY_TITLE, DataStore.KEY_AUTHOR, DataStore.KEY_GENRENAME},
                new int[] {R.id.book_item_title, R.id.book_item_author, R.id.book_item_genre}
        );
        listViewBooks.setAdapter(booksAdapter);

        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailsIntent = new Intent(ListActivity.this, DetailsActivity.class);
                detailsIntent.putExtra(DataStore.KEY_POSITION, position);
                startActivity(detailsIntent);
            }
        });

    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}