package ru.kuznecov.ivan.rent.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.kuznecov.ivan.rent.R;

public class PhoneFragment extends Fragment {

    private String phone;
    private ListenerPhone listener;
    public interface ListenerPhone{
        void onNextPhone(String phone);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText edTexPhone = (EditText)view.findViewById(R.id.phone_register_input);
        Button btnNextPhone = (Button)view.findViewById(R.id.register_phone_btn);
        btnNextPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edTexPhone.getText().toString();
                listener.onNextPhone(phone);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ListenerPhone)context;
    }
}
