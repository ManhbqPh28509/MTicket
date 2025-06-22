package movie.fpoly.mticket.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Movies;

public class ReviewMovie extends AppCompatActivity {
    TextView tv_buy_ticket, tv_description, tv_release_date, tv_duration, tv_title;
    ImageView img_back;
    WebView movie_trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_movie);
        img_back = findViewById(R.id.img_back);
        tv_title = findViewById(R.id.tv_title);
        tv_buy_ticket = findViewById(R.id.tv_buy_ticket);
        tv_description = findViewById(R.id.tv_description);
        tv_release_date = findViewById(R.id.tv_release_date);
        tv_duration = findViewById(R.id.tv_duration);
        movie_trailer = findViewById(R.id.movie_trailer);
        img_back.setOnClickListener(v -> {
            finish();
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Movies movies = (Movies) bundle.getSerializable("movie");
        tv_title.setText(movies.getMovie_name());
        tv_description.setText(movies.getMovie_description());
        tv_release_date.setText(movies.getMovie_release());
        tv_duration.setText(movies.getMovie_length() + " phút");
        tv_buy_ticket.setOnClickListener(v -> {
            Intent intent1 = new Intent(ReviewMovie.this, ChooseCinema.class);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("movie", movies);
            intent1.putExtras(bundle1);
            startActivity(intent1);
        });
        String video = movies.getMovie_trailer();
        movie_trailer.getSettings().setJavaScriptEnabled(true);
        movie_trailer.getSettings().setMediaPlaybackRequiresUserGesture(false);
        movie_trailer.setWebViewClient(new WebViewClient());
        movie_trailer.setWebChromeClient(new WebChromeClient());

        String html = "<!DOCTYPE html><html><body style=\"margin:0;padding:0;\">" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"" + video + "?autoplay=1&controls=1\" " +
                "frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen>" +
                "</iframe></body></html>";

        movie_trailer.loadDataWithBaseURL(
                null,
                html,
                "text/html",
                "utf-8",
                null
        );
    }

}