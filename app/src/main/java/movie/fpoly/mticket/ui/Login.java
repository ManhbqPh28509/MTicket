package movie.fpoly.mticket.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.UserDao;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Users;
import movie.fpoly.mticket.ui_manager.Administration;

public class Login extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private UserDao userDAO;
    private List<Users> usersList = new ArrayList<>();
    private TextInputEditText edt_Password,edt_Email;
    private TextView tv_SignUp,tv_ForgotPass;
    private Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.blue2));
        }
        userDAO = new UserDao(this);
        usersList = userDAO.getUsers("SELECT * FROM USERS");

        edt_Email = findViewById(R.id.edt_Email);
        edt_Password = findViewById(R.id.edt_Password);
        tv_SignUp = findViewById(R.id.tv_SignUp);
        tv_ForgotPass = findViewById(R.id.tv_ForgotPassword);
        btn_Login = findViewById(R.id.btn_Login);

        btn_Login.setOnClickListener(v -> {
            String usernameLogin = edt_Email.getText().toString().trim();
            String passwordLogin = edt_Password.getText().toString().trim();
            Log.d("usernameLogin", usernameLogin);
            if (usernameLogin.isEmpty() || passwordLogin.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (usernameLogin.equals("admin") && passwordLogin.equals("admin")) {
                Toast.makeText(this, "Đăng nhập quản trị viên thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( Login.this, Administration.class);
                Log.d("usernameLogin", usernameLogin);
                startActivity(intent);
                finish();
            } else {
                for (int i = 0; i < usersList.size(); i++) {
                    if (usernameLogin.equals(usersList.get(i).getUsername()) && passwordLogin.equals(usersList.get(i).getPassword())) {
                        Intent intent = new Intent(Login.this, Home.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", usersList.get(i));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });

       tv_SignUp.setOnClickListener(v -> {
            startActivity(new Intent( Login.this, Register.class));
        });
    }
}