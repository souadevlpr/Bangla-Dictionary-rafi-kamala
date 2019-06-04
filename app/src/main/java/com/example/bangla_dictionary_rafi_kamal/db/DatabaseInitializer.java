package com.example.bangla_dictionary_rafi_kamal.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.bangla_dictionary_rafi_kamal.util.FileHelper;


public class DatabaseInitializer extends SQLiteOpenHelper {


    private static String DB_DIR = "/data/data/com.example.bangla_dictionary_rafi_kamal/databases/";
    private static String DB_NAME = "dictionary";
    private static String DB_PATH = DB_DIR + DB_NAME;
    private static String OLD_DB_PATH = DB_DIR + "old_" + DB_NAME;
    private static final int DB_VERSION = 51;

    private final Context myContext;

    private boolean createDatabase = false;
    private boolean upgradeDatabase = false;
    
    private static final String TAG = DatabaseInitializer.class.getName();

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     * 
     * @param context
     */
    public DatabaseInitializer(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
        myContext = context;
        // Get the path of the database that is based on the context.
        DB_PATH = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
    }

    /**
     * Upgrade the database in internal storage if it exists but is not current. 
     * Create a new empty database in internal storage if it does not exist.
     */
    public void initializeDataBase() {
        /*
         * Creates or updates the database in internal storage if it is needed
         * before opening the database. In all cases opening the database copies
         * the database in internal storage to the cache.
         */
        getWritableDatabase();

        if (createDatabase || upgradeDatabase) {
            /*
             * If the database is created by the copy method, then the creation
             * code needs to go here. This method consists of copying the new
             * database from assets into internal storage and then caching it.
             */
            try {
                /*
                 * Write over the empty data that was created in internal
                 * storage with the one in assets and then cache it.
                 */
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        } else if (upgradeDatabase) {
            /*
             * If the database is upgraded by the copy and reload method, then
             * the upgrade code needs to go here. This method consists of
             * renaming the old database in internal storage, create an empty
             * new database in internal storage, copying the database from
             * assets to the new database in internal storage, caching the new
             * database from internal storage, loading the data from the old
             * database into the new database in the cache and then deleting the
             * old database from internal storage.
             */
            try {
                FileHelper.copyFile(DB_PATH, OLD_DB_PATH);
                copyDataBase();
                SQLiteDatabase old_db = SQLiteDatabase.openDatabase(OLD_DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
                SQLiteDatabase new_db = SQLiteDatabase.openDatabase(DB_PATH,null, SQLiteDatabase.OPEN_READWRITE);
                /*
                 * Add code to load data into the new database from the old
                 * database and then delete the old database from internal
                 * storage after all data has been transferred.
                 */
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    

    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        close();

        InputStream myInput = myContext.getAssets().open(DB_NAME);

        /*
         * Open the empty db in interal storage as the output stream.
         */
        OutputStream myOutput = new FileOutputStream(DB_PATH);

        /*
         * Copy over the empty db in internal storage with the database in the
         * assets folder.
         */
        FileHelper.copyFile(myInput, myOutput);
        /*
         * Access the copied database so SQLiteHelper will cache it and mark it
         * as created.
         */
        getWritableDatabase().close();
        Log.d(TAG, "Copying done");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        createDatabase = true;
        Log.d(TAG, "Creating new database..");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    	
        upgradeDatabase = true;


    	
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


}