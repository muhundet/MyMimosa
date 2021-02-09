package zw.co.mimosa.mymimosa.ui.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import zw.co.mimosa.mymimosa.R;
import zw.co.mimosa.mymimosa.models.RequestModel;

public class RequestsRecyclerAdapter extends RecyclerView.Adapter<RequestsRecyclerAdapter.RequestViewHolder>  {

    ArrayList<RequestModel> requests;

    public RequestsRecyclerAdapter(ArrayList<RequestModel> requests) {
        this.requests = requests;
    }
    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.requests_list_item, parent, false);

        return new RequestViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        RequestModel  requestModel = requests.get(position);
        holder.bind(requestModel);

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }


    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //        TextView textMeetingId;
        TextView tvRequestSubject, tvRequestStatus;


        public RequestViewHolder(View itemView) {
            super(itemView);
            tvRequestSubject = (TextView) itemView.findViewById(R.id.tv_request_subject);
            tvRequestStatus = (TextView) itemView.findViewById(R.id.tv_request_status);

            itemView.setOnClickListener(this);

        }
        public void bind (RequestModel requestModel) {
//            textMeetingId.setText(meeting.meetingId);
            tvRequestSubject.setText(requestModel.getRequestSubject());
            tvRequestStatus.setText(requestModel.getRequestStatus());
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            //gets the request from the arrayList
//            RequestModel selectedRequest = requests.get(position);
//            Intent intent = new Intent(view.getContext(), ViewRequestsActivity.class);
//            intent.putExtra("request", selectedRequest);
//            view.getContext().startActivity(intent);
        }
    }
}
