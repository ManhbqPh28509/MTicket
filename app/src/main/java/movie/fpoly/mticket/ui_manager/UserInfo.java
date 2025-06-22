package movie.fpoly.mticket.ui_manager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Users;

public class UserInfo extends AppCompatActivity {
    private TextView txtUser_name, txtUser_email, txtUser_password, txtUser_phone;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Users user = (Users) bundle.getSerializable("user");
        txtUser_name = findViewById(R.id.txtUser_name);
        txtUser_email = findViewById(R.id.txtUser_email);
        txtUser_password = findViewById(R.id.txtUser_password);
        txtUser_phone = findViewById(R.id.txtUser_phone);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            finish();
        });
        txtUser_name.setText("Username: " + user.getUsername());
        txtUser_email.setText("Email: " + user.getEmail());
        txtUser_password.setText("Password: " + user.getPassword());
        txtUser_phone.setText("Phone: " + user.getPhone());
    }
}