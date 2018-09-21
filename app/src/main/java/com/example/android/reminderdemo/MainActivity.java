package com.example.android.reminderdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    //fields
    private List<Reminder> mReminders;
    private ArrayAdapter mAdapter;
    private ListView mListView;
    private EditText mNewReminderText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize
        mListView = findViewById(R.id.listView_main);
        mNewReminderText = findViewById(R.id.editText_main);
        mReminders = new ArrayList<>();
        //mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mReminders);
        //mListView.setAdapter(mAdapter);

        //Could add RecyclerView here:
        //mRecyclerView = (RecyclerView) findViewById()

        UpdateUI();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = mNewReminderText.getText().toString();
                Reminder newReminder = new Reminder(text);
                //Check if text was entered
                if (!(TextUtils.isEmpty(text))) {
                    mReminders.add(newReminder);
                    //refresh
                    //replaced "mAdapter.notifyDataSetChanged()"; with
                    UpdateUI();
                    //clear contents
                    mNewReminderText.setText("");
                    mListView.setSelection(mReminders.size()-1);

                } else {
                    Snackbar.make(view, R.string.SnackbarReminderHint, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mReminders.remove(position);
                //replaced "mAdapter.notifyDataSetChanged();" with
                UpdateUI();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void UpdateUI() {
        /* not needed if you make sure to have created the Adapter in the first place.
        * Then this routines contains only notifyDataSetChanged statement
        * so no need for a separate routine*/

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mReminders);
            mListView.setAdapter(mAdapter);
            //mRecyclerView.setAdapter(mRecyclerView);
        } else {
            mAdapter.notifyDataSetChanged();
            //mReceyvlerView.notifyDataSetChanged();

        }
    }
}
