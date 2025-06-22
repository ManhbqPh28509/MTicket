package movie.fpoly.mticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.MovieDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.MovieAdapter;
import movie.fpoly.mticket.adapters_user.MovieAdapterCategory;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Categories;
import movie.fpoly.mticket.models.Movies;

public class Category extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private MovieDAO movieDAO;
    private List<Movies> moviesList = new ArrayList<>();
    private MovieAdapterCategory movieAdapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        TextView tvCategory_name = findViewById(R.id.tvCategory_name);
        ImageView img_back = findViewById(R.id.img_back);
        RecyclerView rv_movie = findViewById(R.id.rv_Movie);
        databaseHelper = new DatabaseHelper(this);
        movieDAO = new MovieDAO(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Categories category = (Categories) bundle.getSerializable("category");
        img_back.setOnClickListener(v -> {
            finish();
        });
        tvCategory_name.setText(category.getCategory_name());
        moviesList = movieDAO.getMovie("SELECT * FROM MOVIES WHERE category_id = " + category.getCategory_id());
        movieAdapterCategory = new MovieAdapterCategory(this, moviesList);
        rv_movie.setLayoutManager(new LinearLayoutManager(this));
        rv_movie.setHasFixedSize(true);
        rv_movie.setAdapter(movieAdapterCategory);
    }
}