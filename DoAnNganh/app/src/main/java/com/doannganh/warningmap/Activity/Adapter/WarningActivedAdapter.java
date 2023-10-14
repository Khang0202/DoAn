package com.doannganh.warningmap.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.doannganh.warningmap.Object.Address;
import com.doannganh.warningmap.Object.Warning;
import com.doannganh.warningmap.R;

import java.util.List;

public class WarningActivedAdapter extends RecyclerView.Adapter<WarningActivedAdapter.Viewholder> {
    List<Warning> items;
    Context context;

    public WarningActivedAdapter(List<Warning> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_warning_list, parent, false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.tvUploader.setText(items.get(position).getUploader().getFirstName());
        Address address = items.get(position).getAddress();
        String placeinfo = address.getStreetNumber()
                + ", " + address.getRoute()
                + ", " + address.getTown()
                + ", " + address.getDistrict().getDistrict()
                + ", " + address.getProvince().getProvince();
        holder.tvPlaceInfo.setText(placeinfo);
        holder.tvLatitude.setText(String.valueOf(items.get(position).getAddress().getLatitude()));
        holder.tvLongitude.setText(String.valueOf(items.get(position).getAddress().getLongtitude()));
        Glide.with(context).load(items.get(position).getLinkImage())
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.imgWarningInfo);
//        holder.itemView.setOnClickListener(v -> {
//            Intent intent = new Intent(holder.itemView.getContext(), .class);
//            StaticClass.infoWarning = items.get(position);
//            holder.itemView.getContext().startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tvUploader, tvPlaceInfo, tvLatitude, tvLongitude;
        ImageView imgWarningInfo;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvUploader = itemView.findViewById(R.id.tvUploader);
            tvPlaceInfo = itemView.findViewById(R.id.tvPlaceInfo);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            tvLongitude = itemView.findViewById(R.id.tvLongitude);
            imgWarningInfo = itemView.findViewById(R.id.imgWarningInfo);
        }
    }
}
