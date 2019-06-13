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

public class NameFragment extends Fragment {

    private EditText edTexName;
    private EditText edTexPassword;
    private Button btnRegistrarion;
    private String name;
    private String password;
    private ListenerName listener;
    public interface ListenerName{
        void registration(String name, String password);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edTexName = (EditText)view.findViewById(R.id.name_register_input);
        edTexPassword = (EditText)view.findViewById(R.id.password_register_input);
        btnRegistrarion = (Button)view.findViewById(R.id.register_name_btn);
        btnRegistrarion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edTexName.getText().toString();
                password = edTexPassword.getText().toString();
                listener.registration(name, password);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ListenerName)context;
    }
}
