package com.example.simplelife.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simplelife.R;
import com.example.simplelife.entities.Events;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GridAdapterPlan extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;

    public GridAdapterPlan(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events) {
        super(context, R.layout.fragment_single_cell_plan);

        this.dates=dates;
        this.currentDate=currentDate;
        this.events=events;
        inflater = LayoutInflater.from(context);
    }

//    public GridAdapterPlan(@NonNull Context context, int resource) {
//        super(context, R.layout.fragment_single_cell_plan);
//
//        this.dates=dates;
//        this.currentDate=currentDate;
//        this.events=events;
//        inflater = LayoutInflater.from(context);
//    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);
        int DayNo = dateCalendar.get(Calendar.DAY_OF_MONTH); //lay so ngay cua thang
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1; // chon thang
        int displayYear = dateCalendar.get(Calendar.YEAR); // chon nam
        int currentMonth = currentDate.get(Calendar.MONTH)+1; // lay thang hien tai
        int currentYear = currentDate.get(Calendar.YEAR);//lay nam hien tai

        //Gan thoi gian cho TextView
        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.fragment_single_cell_plan, parent,false);
        }


        if(displayMonth==currentMonth && displayYear==currentYear){
            view.setBackgroundColor(getContext().getResources().getColor(R.color.black));
            view.setBackground(getContext().getResources().getDrawable(R.drawable.background_single_cell_plan));
        }
        else {
            view.setBackgroundColor(Color.parseColor("#292929"));
        }

        TextView Day_Number = view.findViewById(R.id.calendar_day);
        TextView EventNumber = view.findViewById(R.id.events_id);
        Day_Number.setText(String.valueOf(DayNo));
        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();

        Log.d("Day Number", ""+Day_Number);
        Log.d("Event Number", ""+EventNumber);

        for(int i = 0; i < events.size(); i++){
            eventCalendar.setTime(ConvertStringToDate(events.get(i).getDATE()));

            if(DayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH)+1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){

                // show ra list plan
                arrayList.add(events.get(i).getEVENT());
                //show ra só lượng plan có trong ngày
                EventNumber.setText(arrayList.size()+" SK");
                EventNumber.setBackgroundColor(getContext().getResources().getColor(R.color.reds));

            }
        }

        return view;
    }

    //
    private Date ConvertStringToDate(String eventDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;

        try {
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }


    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
