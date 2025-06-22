package movie.fpoly.mticket.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.models.Room;
import movie.fpoly.mticket.models.Seats;

public class Purchase extends AppCompatActivity {
    TextView movie_name, cinema_name, seat_name, room_name, tv_total, tv_pay;
    ImageView img_back;
    ImageView movie_poster;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Movies movies = (Movies) bundle.getSerializable("movie");
        Cinemas cinemas = (Cinemas) bundle.getSerializable("cinema");
        Seats seats = (Seats) bundle.getSerializable("seat");
        Room room = (Room) bundle.getSerializable("room");
        movie_name = findViewById(R.id.movie_name);
        cinema_name = findViewById(R.id.cinema_name);
        room_name = findViewById(R.id.room_name);
        seat_name = findViewById(R.id.seat_name);
        img_back = findViewById(R.id.img_back);
        movie_poster = findViewById(R.id.img_poster);
        tv_total = findViewById(R.id.tv_total);
        tv_pay = findViewById(R.id.tv_pay);

        img_back.setOnClickListener(v -> {
            finish();
        });

        movie_name.setText(movies.getMovie_name());
        cinema_name.setText("Rạp: " + cinemas.getCinema_name());
        room_name.setText("Phòng: " + room.getRoom_name());
        seat_name.setText("Ghế: " + seats.getRow_seat() + seats.getNumber());
        movie_poster.setImageBitmap(BitmapFactory.decodeByteArray(movies.getMovie_poster(), 0, movies.getMovie_poster().length));
        tv_total.setText("50000đ");

        tv_pay.setOnClickListener(v -> {

        });
    }
}