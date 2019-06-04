package com.example.bangla_dictionary_rafi_kamal.db;

import java.util.List;

import android.os.AsyncTask;
import com.example.bangla_dictionary_rafi_kamal.adapter.WordListAdapter;
import com.example.bangla_dictionary_rafi_kamal.model.Word;


public class DataLoader extends AsyncTask<String, Void, List<Word>> {
	
	private DictionaryDB dictionaryDB;
	private WordListAdapter adapter;
	
	public DataLoader(DictionaryDB dictionaryDB, WordListAdapter adapter) {
		this.dictionaryDB = dictionaryDB;
		this.adapter = adapter;
	}

	@Override
	protected List<Word> doInBackground(String... params) {
		return dictionaryDB.getWords(params[0]);
	}
	
	@Override
	protected void onPostExecute(List<Word> result) {
		adapter.updateEntries(result);
	}
}
