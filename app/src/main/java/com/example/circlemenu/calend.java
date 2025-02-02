package com.mds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class calend extends AppCompatActivity {
    private CalendarView calendarView;
    private mySQLiteDBHandler dbHandler;
    private EditText editText;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendlayout);

        calendarView=(CalendarView) findViewById(R.id.calendarView);
        editText=findViewById(R.id.editText);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfMonth);
                ReadDatabase(view);

            }
        });
        try {
            dbHandler = new mySQLiteDBHandler(this, "Calendar database",null,1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("Create Table EventCalendar(Date TEXT,Event TEXT)");
            ContentValues contentValues= new ContentValues();
            for(int i=1971;i<=2050;i++)
            {
                contentValues.put("Date",i+"1225");
                contentValues.put("Event","Christmas");
                sqLiteDatabase.insert("EventCalendar",null,contentValues);
            }
            for(int i=1971;i<=2050;i++)
            {
                contentValues.put("Date",i+"38");
                contentValues.put("Event","Mother's Day");
                sqLiteDatabase.insert("EventCalendar",null,contentValues);
            }
            for(int i=1971;i<=2050;i++)
            {
                contentValues.put("Date",i+"31");
                contentValues.put("Event","Martisor");
                sqLiteDatabase.insert("EventCalendar",null,contentValues);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void InsertDatabase(View view){
      ContentValues contentValues= new ContentValues();
      contentValues.put("Date",selectedDate);
      contentValues.put("Event",editText.getText().toString());
      sqLiteDatabase.insert("EventCalendar",null,contentValues);
    }

    public void ReadDatabase(View view){
        String query = "Select Event from EventCalendar where Date ="+selectedDate;
        try{
            Cursor cursor=sqLiteDatabase.rawQuery(query,null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(1));
        }
        catch (Exception e){
            e.printStackTrace();
            editText.setText("");
        }
    }
}