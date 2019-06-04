package com.example.bangla_dictionary_rafi_kamal.activity;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.bangla_dictionary_rafi_kamal.R;
import com.example.bangla_dictionary_rafi_kamal.adapter.WordListAdapter;
import com.example.bangla_dictionary_rafi_kamal.db.DatabaseInitializer;
import com.example.bangla_dictionary_rafi_kamal.db.DictionaryDB;
import com.example.bangla_dictionary_rafi_kamal.model.Word;

public class BookMarkedWordsActivity extends ListActivity {
	
	private DictionaryDB dictionaryDB;
	private WordListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarked);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		DatabaseInitializer initializer = new DatabaseInitializer(getBaseContext());
        dictionaryDB = new DictionaryDB(initializer);
        
        adapter = new WordListAdapter(this, dictionaryDB);
		setListAdapter(adapter);
		
		List<Word> bookmarkedWords = dictionaryDB.getBookmarkedWords();
		adapter.updateEntries(bookmarkedWords);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
