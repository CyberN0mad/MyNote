package com.geektech.mynote.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.geektech.mynote.R;
import com.geektech.mynote.adapter.NoteAdapter;
import com.geektech.mynote.databinding.FragmentHomeBinding;
import com.geektech.mynote.model.NoteModel;
import com.geektech.mynote.utils.App;
import com.geektech.mynote.utils.Constants;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NoteAdapter adapter;
    private boolean isDashboard = false;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        getDataFromDB();
        Log.e("TAG", "onCreateView: DATABASE" + App.getDatabase().getDao().getAll());
        return binding.getRoot();
    }

    private void getDataFromDB() {
        App.getDatabase().getDao().getAll().observe(getViewLifecycleOwner(), noteModels -> {
            adapter.addModel(noteModels);
        });
    }

    private void getData() {
        getParentFragmentManager().setFragmentResultListener(Constants.REQUEST_KEY, getViewLifecycleOwner(), (requestKey, result) -> {
            NoteModel model = (NoteModel) result.getSerializable(Constants.BUNDLE_KEY);
            adapter.addNote(model);
            if (isDashboard) {
                binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
            } else {
                binding.recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
    }

    private void initView() {
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(requireContext());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        initView();

        getData();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.dashboard) {
            isDashboard = !isDashboard;
            if (isDashboard) {
                item.setIcon(R.drawable.ic_list);
            } else {
                item.setIcon(R.drawable.ic_dashboard);
            }
            binding.recyclerView.setLayoutManager(isDashboard ? staggeredGridLayoutManager : linearLayoutManager);
            binding.recyclerView.setAdapter(adapter);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}