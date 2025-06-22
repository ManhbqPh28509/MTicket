package movie.fpoly.mticket.fragments_users;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Users;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);
        EditText email = view.findViewById(R.id.email);
        EditText phone = view.findViewById(R.id.phone);
        Intent intent = requireActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Users user = (Users) bundle.getSerializable("user");
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
        }
        return view;
    }
}