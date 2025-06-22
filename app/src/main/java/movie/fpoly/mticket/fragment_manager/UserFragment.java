package movie.fpoly.mticket.fragment_manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.UserDao;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.UserAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Users;
import movie.fpoly.mticket.ui_manager.UserInfo;

public class UserFragment extends Fragment {
    DatabaseHelper databaseHelper;
    private List<Users> usersList;
    private UserAdapter userAdapter;
    private UserDao userDao;
    FloatingActionButton add_user;
    RecyclerView rvUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        userDao = new UserDao(requireContext());
        rvUser = view.findViewById(R.id.rv_User);
        add_user = view.findViewById(R.id.add_user);
        
        updateRV();
        add_user.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_user, null);
            EditText edt_username = view1.findViewById(R.id.edtUsername);
            EditText edt_password = view1.findViewById(R.id.edtPassword);
            EditText edt_email = view1.findViewById(R.id.edtEmail);
            EditText edt_phone = view1.findViewById(R.id.edtPhone);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setView(view1);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                String email = edt_email.getText().toString();
                String phone = edt_phone.getText().toString();
                Users user = new Users(username, password, email, phone);
                userDao.insertUser(user);
                Toast.makeText(requireContext(), "Add user success", Toast.LENGTH_SHORT).show();
                updateRV();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return view;
    }

    private void updateRV() {
        usersList = new ArrayList<>();
        usersList = userDao.getUsers("SELECT * FROM USERS");
        userAdapter = new UserAdapter(requireActivity(), usersList, user -> {
            Intent intent = new Intent(requireActivity(), UserInfo.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        rvUser.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvUser.setHasFixedSize(true);
        rvUser.setAdapter(userAdapter);
    }
}