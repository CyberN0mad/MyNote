package com.geektech.mynote.ui.note;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.geektech.mynote.R;
import com.geektech.mynote.databinding.FragmentNoteBinding;
import com.geektech.mynote.model.NoteModel;
import com.geektech.mynote.utils.App;
import com.geektech.mynote.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NoteFragment extends Fragment {
    FragmentNoteBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    public String getDateConverter() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMMM HH:mm");
        Date date = new Date();
        return formatter.format(date);
    }

    private void initView() {
        binding.btnSave.setOnClickListener(v -> {
            String text = binding.editText.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                binding.editText.setError("Вы не правильно ввели");
                YoYo.with(Techniques.RotateIn)
                        .duration(700)
                        .repeat(3)
                        .playOn(binding.btnSave);
                return;
            }

            NoteModel noteModel = new NoteModel(text, getDateConverter());

            App.getDatabase().getDao().insertNote(noteModel);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BUNDLE_KEY, noteModel);
            getParentFragmentManager().setFragmentResult(Constants.REQUEST_KEY, bundle);
            close();
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }
}