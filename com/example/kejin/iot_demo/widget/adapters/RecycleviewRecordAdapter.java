package com.example.kejin.iot_demo.widget.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kejin.iot_demo.R;
import com.example.kejin.iot_demo.data_class.DataRecord;

import java.util.List;

/**
 * Created by kejin on 28/03/2018.
 */

public class RecycleviewRecordAdapter  extends RecyclerView.Adapter {
    public RecycleviewRecordAdapter(List<DataRecord> mList) {
        this.mList = mList;
    }

    private List<DataRecord> mList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;

        DataRecord dataRecord = mList.get(position);
        vh.getLocation().setText(dataRecord.getLocation());
        vh.getDistance().setText(dataRecord.getDistance()+"");
        vh.getAmenity().setText(dataRecord.getAmenity());
        vh.getDuratoin().setText(dataRecord.getDuration());
        vh.getStart_time().setText(dataRecord.getStart_time());
        vh.getEnd_time().setText(dataRecord.getEnd_time());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView location;
        private TextView distance;
        private TextView amenity;
        private TextView duratoin;
        private TextView start_time;
        private TextView end_time;
        public ViewHolder(View itemView) {
            super(itemView);
            location = (TextView)itemView.findViewById(R.id.location_record_lending);
            distance = (TextView)itemView.findViewById(R.id.distance_record_lending);
            amenity  = (TextView)itemView.findViewById(R.id.amenity_record_lending);
            duratoin = (TextView)itemView.findViewById(R.id.duration_record_lending);
            start_time=(TextView)itemView.findViewById(R.id.start_time_record_lending);
            end_time = (TextView)itemView.findViewById(R.id.end_time_record_lending);

        }
        public View getItemView() {
            return itemView;
        }

        public TextView getLocation() {
            return location;
        }

        public TextView getDistance() {
            return distance;
        }

        public TextView getAmenity() {
            return amenity;
        }

        public TextView getDuratoin() {
            return duratoin;
        }

        public TextView getStart_time() {
            return start_time;
        }

        public TextView getEnd_time() {
            return end_time;
        }


    }
}
