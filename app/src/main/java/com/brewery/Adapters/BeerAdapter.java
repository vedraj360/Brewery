package com.brewery.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.brewery.Models.BeerModel;
import com.brewery.R;

import java.util.ArrayList;


public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<BeerModel> arrayList;
    private ArrayList<BeerModel> mFilteredList;



    public BeerAdapter(Context context, ArrayList<BeerModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.beeritem, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BeerModel beerModel = mFilteredList.get(position);
        viewHolder.title.setText(beerModel.getName());
        viewHolder.style.setText(beerModel.getStyle());
        double av;
        if(!beerModel.getAbv().equals("")){
            av = Double.parseDouble(beerModel.getAbv());
        }
        else {
            av = 0.06;
        }

        if (av > 0.06) {
            viewHolder.abv.setText("Alcohol Level: " + beerModel.getAbv());
            viewHolder.abv.setTextColor(context.getResources().getColor(R.color.red));

        } else if (av < 0.06 && av > 0.04) {
            viewHolder.abv.setText("Alcohol Level: " + beerModel.getAbv());
            viewHolder.abv.setTextColor(context.getResources().getColor(R.color.mild));
//            viewHolder.abv.setTypeface(null, Typeface.BOLD);
        } else {
            viewHolder.abv.setText("Alcohol Level: " + beerModel.getAbv());
            viewHolder.abv.setTextColor(context.getResources().getColor(R.color.light));
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = arrayList;
                } else {

                    ArrayList<BeerModel> filteredList = new ArrayList<>();

                    for (BeerModel bm : arrayList) {

                        if (bm.getAbv().toLowerCase().contains(charString) || bm.getName().toLowerCase().contains(charString) || bm.getStyle().toLowerCase().contains(charString)) {

                            filteredList.add(bm);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<BeerModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, style, abv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            style = itemView.findViewById(R.id.style);
            abv = itemView.findViewById(R.id.abv);

        }
    }
}
