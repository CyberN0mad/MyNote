package com.geektech.mynote.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.mynote.databinding.NoteItemBinding;
import com.geektech.mynote.model.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    ArrayList<NoteModel> list = new ArrayList<>();
    NoteItemBinding binding;

    public void addNote(NoteModel text) {
        list.add(text);
        notifyDataSetChanged();
    }

    public void deleteModel(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addModel(List<NoteModel> models) {
        list.clear();
        list.addAll(models);
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        private void onBind(NoteModel s) {
            binding.itemTitle.setText(s.getTitle());
        }
    }
}
