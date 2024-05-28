package dev.proj.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        EditText etUsername = view.findViewById(R.id.etUsernameFragReg);
        EditText etEmail = view.findViewById(R.id.etEmailFragReg);
        EditText etPassword = view.findViewById(R.id.etPasswordFragReg);
        Button btnRegister = view.findViewById(R.id.buttonRegisterFragReg);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean valUser = usernameFree(etUsername);
                boolean valEmail1 = emailFree(etEmail);
                boolean valEmail2 = validateEmail(etEmail);
                boolean valPassword = isStrongPassword(etPassword);

                String username = etUsername.getText().toString();

                // check all the necessary requirements for user registration
                if (valUser && valEmail1 && valEmail2 && valPassword){
                    Toast.makeText(getActivity(), "User successfully created!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("user", username);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    // delete entered credentials
                    etUsername.setText(null);
                    etEmail.setText(null);
                    etPassword.setText(null);
                } else if (!valUser) {
                    Toast.makeText(getActivity(), "Username is already taken!",
                            Toast.LENGTH_SHORT).show();
                } else if (!valEmail1) {
                    Toast.makeText(getActivity(), "Email already in use!",
                            Toast.LENGTH_SHORT).show();
                } else if (!valEmail2) {
                    Toast.makeText(getActivity(), "Invalid email format!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Password not strong enough!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    // checks if an entered username already exists
    public boolean usernameFree(EditText etUsername){
        String username = etUsername.getText().toString();

        return !username.isEmpty() && !username.equals("admin");
    }

    // checks if an entered email already exists
    public boolean emailFree(EditText etEmail){
        String email = etEmail.getText().toString();

        return !email.isEmpty() && !email.equals("admin@gmail.com");
    }

    // checks if an email is in its valid format
    public boolean validateEmail(EditText etEmail){
        String email = etEmail.getText().toString();

        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // checks if a password is strong enough
    public boolean isStrongPassword(EditText etPassword){
        String password = etPassword.getText().toString();
        boolean capital = false;
        boolean lower = false;
        boolean number = false;
        boolean character = false;

        // checks for capital char in a password
        for(int i = 0; i < password.length(); i++) {
            if(password.charAt(i) >= 65 && password.charAt(i) <= 90){
                capital =  true;
            }
        }

        // check for lower case char in password
        for(int i = 0; i < password.length(); i++) {
            if(password.charAt(i) >= 97 && password.charAt(i) <= 122){
                lower =  true;
            }
        }

        // check for numbers in password
        for(int i = 0; i < password.length(); i++) {
            if(password.charAt(i) >= 48 && password.charAt(i) <= 57){
                number =  true;
            }
        }

        // check for special characters in password
        for(int i = 0; i < password.length(); i++) {
            if((password.charAt(i) >= 33 && password.charAt(i) <= 47) ||
                    (password.charAt(i) >= 91 && password.charAt(i) <= 96) ||
                    (password.charAt(i) >= 123 && password.charAt(i) <= 126)){
                character =  true;
            }
        }

        // checks if every condition is satisfied
        return capital && lower && number && character && (password.length() >= 8);
    }
}