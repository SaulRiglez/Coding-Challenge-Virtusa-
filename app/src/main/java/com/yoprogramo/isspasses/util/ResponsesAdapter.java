package com.yoprogramo.isspasses.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoprogramo.isspasses.R;
import com.yoprogramo.isspasses.model.Response;

import java.util.List;


public class ResponsesAdapter extends RecyclerView.Adapter<ResponsesAdapter.ResponsesViewHolder> {

    Context context;
    private List<Response> responsesList;


    public ResponsesAdapter(List<Response> responsesList, Context context) {
        this.context = context;
        this.responsesList = responsesList;
    }

    @Override
    public ResponsesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.responses_row_data, parent, false);
        return new ResponsesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResponsesViewHolder holder, int position) {

        Response response = this.responsesList.get(position);
        holder.duration.setText(response.getDuration().toString());
        holder.time.setText(response.getRisetime().toString());
        holder.counter.setText(String.format("%S", position + 1));

    }

    @Override
    public int getItemCount() {
        return responsesList.size();
    }

    public class ResponsesViewHolder extends RecyclerView.ViewHolder {

        TextView duration;
        TextView time;
        TextView counter;

        public ResponsesViewHolder(View itemView) {
            super(itemView);


            duration = (TextView) itemView.findViewById(R.id.tv_duration);
            time = (TextView) itemView.findViewById(R.id.tv_time);
            counter = (TextView) itemView.findViewById(R.id.counter);
        }

    }

}
