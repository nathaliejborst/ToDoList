package com.example.nathalie.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Adapter toDoAdapter;
    private ListView listView;
    Button button, urgent, important, normal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.LV);
        button = (Button) findViewById(R.id.button);
        urgent = (Button) findViewById(R.id.button1);
        important= (Button) findViewById(R.id.button2);
        normal = (Button) findViewById(R.id.button3);

        ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();

        toDoAdapter = new Adapter(this, cursor) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                super.bindView(view, context, cursor);
            }
        };

        listView.setAdapter(toDoAdapter);

        button.setOnClickListener(new GoButtonClickListener());
        urgent.setOnClickListener(new ChangeColor());
        important.setOnClickListener(new ChangeColor());
        normal.setOnClickListener(new ChangeColor());

        // Delete item from list
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
//                ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
                TextView textView = (TextView) findViewById(R.id.toDoItem);
                String itemText = textView.getText().toString();
                ALERT(id, itemText);
                return false;
            }
        });


        // Checks or unchecks item on list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
                db.update(id);
                UpdateData();

            }
    });
    }

    private void UpdateData() {
        ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();
        toDoAdapter.swapCursor(cursor);
    }

    private class GoButtonClickListener implements View.OnClickListener {
                @Override
                public void onClick(View view) {
                    ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
                    EditText editText = (EditText) findViewById(R.id.editText);
                    String item = String.valueOf(editText.getText());

                    toDoAdapter.textColor(item);

                    if(item.length() == 0) {
                        Toast.makeText(MainActivity.this, "Please fill in a TO DO-list item", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.insert(item);
                        editText.setText("");
                        UpdateData();

                        urgent.setVisibility(View.VISIBLE);
                        important.setVisibility(View.VISIBLE);
                        normal.setVisibility(View.VISIBLE);

                    }
        }
    }

    public void ALERT (long id, String itemText){

        final long idInput = id;


        new AlertDialog.Builder(MainActivity.this)
                .setTitle("You sure?")
                .setMessage("Delete item?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ToDoDatabase db = ToDoDatabase.getInstance(getApplicationContext());
                        db.delete(idInput);
                        UpdateData();
                        Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_LONG).show();

                        //the user wants to leave - so dismiss the dialog and exit
                        dialog.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // the user is not sure? So you can exit or just stay
                dialog.dismiss();
            }
        }).show();

    }

    private class ChangeColor implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            switch(view.getId()) {
                case R.id.button1:
                    String colorUrg = "#8e1d21";
                    toDoAdapter.textColor(colorUrg);
                    urgent.setVisibility(View.INVISIBLE);
                    important.setVisibility(View.INVISIBLE);
                    normal.setVisibility(View.INVISIBLE);
                    break;
                case R.id.button2:
                    String colorImp = "#1c8e7b";
                    toDoAdapter.textColor(colorImp);
                    urgent.setVisibility(View.INVISIBLE);
                    important.setVisibility(View.INVISIBLE);
                    normal.setVisibility(View.INVISIBLE);
                    break;
                case R.id.button3:
                    String colorNor = "#404746";
                    toDoAdapter.textColor(colorNor);
                    urgent.setVisibility(View.INVISIBLE);
                    important.setVisibility(View.INVISIBLE);
                    normal.setVisibility(View.INVISIBLE);
                    break;
            }

            }
        }
}
