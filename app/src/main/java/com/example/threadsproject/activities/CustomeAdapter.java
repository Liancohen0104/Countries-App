package com.example.threadsproject.activities;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.threadsproject.R;
import com.example.threadsproject.models.Country;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder>
{
    private ArrayList<Country> originalList; // The original full list
    private ArrayList<Country> filteredList; // The filtered list to display

    public CustomeAdapter(ArrayList<Country> dataSet) {
        this.originalList = new ArrayList<>(dataSet); // Save a copy of the original list
        this.filteredList = dataSet; // Initialize with the full list
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPopulation;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView);
            textViewPopulation = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country dataModel = filteredList.get(position);
        holder.textViewName.setText(dataModel.getName());
        holder.textViewPopulation.setText(dataModel.getPopulation());

        Picasso.get()
                .load(dataModel.getFlag()) // URL של התמונה
                .into(holder.imageView); // ImageView שבו נטען התמונה

        holder.itemView.setOnClickListener(v -> {
            // יצירת ImageView חדש להצגה בדיאלוג
            ImageView imageViewCopy = new ImageView(v.getContext());
            imageViewCopy.setImageDrawable(holder.imageView.getDrawable());
            imageViewCopy.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            // יצירת דיאלוג
            AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                    .setTitle(holder.textViewName.getText())
                    .setMessage(holder.textViewPopulation.getText())
                    .setView(imageViewCopy)
                    .setPositiveButton("Close", (dialogInterface, which) -> dialogInterface.dismiss())
                    .create();

            dialog.show();

            // שינוי גודל הדיאלוג לאחר ההצגה
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(800, 800); // גודל קבוע לדיאלוג
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String text)
    {
        text = text.toLowerCase();
        filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (Country item : originalList) {
                if (item.getName().toLowerCase().contains(text))
                {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}

