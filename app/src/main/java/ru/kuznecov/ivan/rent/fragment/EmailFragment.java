package ru.kuznecov.ivan.rent.fragment;

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


public class EmailFragment extends Fragment {

    private static final String TAG = "EmailFragment";

    private ListenerEmail listener;
    public interface ListenerEmail {
        void onNextEmail(String email);
    }

    private Button btnNextEmail;
    private EditText edTexEmail;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edTexEmail = (EditText)view.findViewById(R.id.email_register_input);
        btnNextEmail = (Button)view.findViewById(R.id.register_email_btn);
        btnNextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edTexEmail.getText().toString();
                listener.onNextEmail(email);
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ListenerEmail) context;
    }
}
