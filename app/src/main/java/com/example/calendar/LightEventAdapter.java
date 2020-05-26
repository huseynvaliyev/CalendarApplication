package com.example.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LightEventAdapter extends RecyclerView.Adapter<LightEventAdapter.MyViewHolder>  {

    ArrayList<Events> mEventList;
    LayoutInflater inflater;



    public LightEventAdapter(Context context, ArrayList<Events> events) {
        inflater = LayoutInflater.from(context);
        this.mEventList = events;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_event_light_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Events selectedEvent = mEventList.get(position);
        holder.setData(selectedEvent, position);

    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventName;
        TextView eventDate;
        TextView startTime;
        TextView endTime;
        TextView address;
        ImageButton deleteEvent;


        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.eventName);
            eventDate = (TextView) itemView.findViewById(R.id.eventDate);
            startTime = (TextView) itemView.findViewById(R.id.startTime);
            endTime = (TextView) itemView.findViewById(R.id.endTime);
            address = (TextView) itemView.findViewById(R.id.address);
            deleteEvent = (ImageButton) itemView.findViewById(R.id.deleteEvent);
            deleteEvent.setOnClickListener(this);
        }

        public void setData(Events selectedEvent, int position) {
            this.eventName.setText("Event Name:"+mEventList.get(position).getEventName());
            this.eventDate.setText("Event Date:"+mEventList.get(position).getTime());
            this.startTime.setText("Start Time:"+mEventList.get(position).getStartTime());
            this.endTime.setText("End Time:"+mEventList.get(position).getEndTime());
            this.address.setText("Address:"+mEventList.get(position).getAddress());
        }

        @Override
        public void onClick(View v) {
            if (v == deleteEvent) {
                deleteEvent(getLayoutPosition());
            }
        }

        private void deleteEvent(int position) {
            Events deletedEvent = mEventList.get(position);
            final EventSource database = new EventSource(inflater.getContext());
            database.open();
            database.deleteEvent(deletedEvent);
            mEventList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mEventList.size());
        }
    }
}
