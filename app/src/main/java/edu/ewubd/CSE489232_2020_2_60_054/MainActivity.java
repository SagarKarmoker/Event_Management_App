package edu.ewubd.CSE489232_2020_2_60_054;
//Sagar Karmoker
//ID: 2020-2-60-054
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    Button createBtn, historyBtn, exitBtn;
    private ListView eventList;
    private ArrayList<Event> events;
    private CustomEventAdapter adapter; //dynamically add the data

    EventDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // runtime call the abstract method
        //modify after that
        setContentView(R.layout.main_activity); // resource.layout_directory.activityxml_file_name
        eventList = findViewById(R.id.eventList);
        events = new ArrayList<>();

        db = new EventDB(this);

        createBtn = findViewById(R.id.createBtn);
        historyBtn = findViewById(R.id.historyBtn);
        exitBtn = findViewById(R.id.exitBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateEventActivity.class);
                startActivity(i);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadEvents(); //loading from database
    }

    private void loadEvents(){
        events.clear();
        String q = "SELECT * FROM events";
        Cursor cur = db.selectEvents(q);
        if(cur != null){
            if(cur.getCount() > 0){ // number of rows
                while (cur.moveToNext()){
                    String ID = cur.getString(0);
                    String name = cur.getString(1);
                    String place = cur.getString(2);
                    String type = cur.getString(3);
                    long _date = cur.getLong(4);
                    int _capacity = cur.getInt(5);
                    double _budget = cur.getDouble(6);
                    String email = cur.getString(7);
                    String phone = cur.getString(8);
                    String desc = cur.getString(9);

                    Event event = new Event(ID, name, place, type, String.valueOf(_date), String.valueOf(_capacity), String.valueOf(_budget), email, phone, desc);
                    System.out.println(event.toString());
                    events.add(event);
                }
            }
            cur.close();
        }
        db.close();

        adapter = new CustomEventAdapter(this, events); // render
        eventList.setAdapter(adapter); // setting in listview using adapter
        // handle the click on an event-list item
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int
            position, long id) {
                // String item = (String) parent.getItemAtPosition(position);
                System.out.println(position);
                Intent i = new Intent(MainActivity.this, CreateEventActivity.class);
                i.putExtra("EventID", events.get(position).id);
                i.putExtra("EventName", events.get(position).name);
                i.putExtra("EventPlace", events.get(position).place);
                i.putExtra("EventType", events.get(position).eventType);
                i.putExtra("EventDateTime", events.get(position).datetime);
                i.putExtra("EventCapacity", events.get(position).capacity);
                i.putExtra("EventBudget", events.get(position).budget);
                i.putExtra("EventEmail", events.get(position).email);
                i.putExtra("EventPhone", events.get(position).phone);
                i.putExtra("EventDesc", events.get(position).description);
                //todo finish it
                startActivity(i);
            }
        });

        //todo 1. click create event 2. longpress edit or delete

        // handle the long-click on an event-list item
        eventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Do you want to delete event - " + events.get(position).name + " ?";
                System.out.println(message);
                showDialog(message, "Delete Event", events.get(position)); // key can be id
                return true;
            }
        });
    }


    //todo showdiaglog
    private void showDialog(String message, String title, Event event){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println(event.id);
                db.deleteEvent(event.id);
                adapter.notifyDataSetChanged();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}