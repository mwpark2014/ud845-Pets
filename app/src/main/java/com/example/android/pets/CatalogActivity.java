/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Loader;
import android.content.CursorLoader;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.data.PetContract.PetEntry;


/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private ListView mPetListView;
    private static final int PET_LOADER = 0;
    PetCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mPetListView = (ListView) findViewById(R.id.list_view_pet);
        View emptyView = findViewById(R.id.empty_view);
        mPetListView.setEmptyView(emptyView);

        //set up item click listener
        mPetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);
                intent.setData(currentPetUri);
                startActivity(intent);
            }
        });

        mCursorAdapter = new PetCursorAdapter(this, null);
        mPetListView.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(PET_LOADER, null, this);
    }

    protected void onStart() {
        super.onStart();
    }

//    /**
//     * Temporary helper method to display information in the onscreen TextView about the state of
//     * the pets database.
//     */
//    private void displayDatabaseInfo() {
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//
//        String[] projection = new String[] {PetEntry._ID,
//                PetEntry.COLUMN_NAME, PetEntry.COLUMN_BREED,
//                PetEntry.COLUMN_GENDER, PetEntry.COLUMN_WEIGHT};
////        String selection = PetEntry.COLUMN_GENDER + "?";
////        String[] selectionArgs = new String[] {String.valueOf(PetEntry.GENDER_FEMALE)};
////        String sortOrder = PetEntry.COLUMN_NAME + " DESC";
//        // Perform this raw SQL query "SELECT * FROM pets"
//        // to get a Cursor that contains all rows from the pets table.
//
//        Cursor cursor = getContentResolver().query(PetEntry.CONTENT_URI, projection, null, null, null);
//        if(cursor == null)
//            return;
//        try {
//            // Display the number of rows in the Cursor (which reflects the number of rows in the
//            // pets table in the database).
//            PetCursorAdapter adapter = new PetCursorAdapter(this, cursor);
//            mPetListView.setAdapter(adapter);
//        }
//        catch(IllegalArgumentException e) {
//            Toast.makeText(this, e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);

        return true;
    }

    private void insertPet() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.

        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_NAME, "Toto");
        values.put(PetEntry.COLUMN_BREED, "Terrier");
        values.put(PetEntry.COLUMN_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_WEIGHT, "Toto");

        Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);
        if(newUri == null) {
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, getString(R.string.editor_insert_pet_success),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePets() {
        getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                // Do nothing for now
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                //delete all pets in database
                deletePets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_NAME,
                PetEntry.COLUMN_BREED};

        return new CursorLoader(this,
                PetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
