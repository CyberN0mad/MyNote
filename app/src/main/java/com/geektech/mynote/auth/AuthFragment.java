package com.geektech.mynote.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.geektech.mynote.R;
import com.geektech.mynote.databinding.FragmentAuthBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class AuthFragment extends Fragment {
    FragmentAuthBinding binding;
    FirebaseAuth mAuth;
    String codeFromInet;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(getLayoutInflater(), container, false);
        mAuth = FirebaseAuth.getInstance();
        initView();
        return binding.getRoot();
    }

    private void initView() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted: SUCCESSFUL");
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed: " + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                codeFromInet = s;
            }
        };

        binding.btnGetCode.setOnClickListener(v -> {
            checkPhoneNumber();
            binding.btnGetCode.setVisibility(View.GONE);
            binding.btnConfirm.setVisibility(View.VISIBLE);
        });
        binding.btnConfirm.setOnClickListener(v -> {
            String code = binding.code.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                binding.code.setError("error");
                return;
            }

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeFromInet, code);
            signInWithPhoneAuthCredential(credential);
        });
    }

    private void checkPhoneNumber() {
        String phone = binding.phoneNum.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            binding.phoneNum.setError("error");
            return;
        }

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TAG", "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                        navController.navigateUp();
                    } else {
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        }
                    }
                });
    }

}