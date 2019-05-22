package ru.kuznecov.ivan.rent.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.kuznecov.ivan.rent.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<String> mList;
    private Listener mListener;
    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public Adapter(List<String> list) {

        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String string = mList.get(i);
        View view = viewHolder.itemView;

        TextView textView = (TextView)view.findViewById(R.id.my_text);
        textView.setText(string);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
    }
}
