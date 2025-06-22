package movie.fpoly.mticket.ui;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import movie.fpoly.mticket.DAO.CinemaDAO;
import movie.fpoly.mticket.DAO.RoomDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.models.Room;
import movie.fpoly.mticket.spinner.CinemaNameSpinnerAdapter;
import movie.fpoly.mticket.spinner.ScheduleRoomNameSpinnerAdapter;

public class ChooseCinema extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private CinemaDAO cinemaDAO;
    private RoomDAO roomDAO;
    private List<Cinemas> cinemasList;
    private List<Room> roomList;
    private TextView tvContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cinema);
        Intent intent = getIntent();
        Bundle bundle1 = intent.getExtras();
        Movies movies = (Movies) bundle1.getSerializable("movie");
        Spinner spCinema = findViewById(R.id.spCinema);
        Spinner spRoom = findViewById(R.id.spRoom);
        ImageView imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(v -> {
            finish();
        });
        tvContinue = findViewById(R.id.tv_buy_ticket);
        databaseHelper = new DatabaseHelper(this);
        cinemaDAO = new CinemaDAO(this);
        roomDAO = new RoomDAO(this);
        cinemasList = cinemaDAO.getCinemas("SELECT * FROM CINEMAS");
        roomList = roomDAO.getRoom("SELECT * FROM ROOM");
        spCinema.setAdapter(new CinemaNameSpinnerAdapter(this , R.layout.item_selected_cinema_spinner, cinemasList));
        spCinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cinemas cinemas = cinemasList.get(position);
                roomList = roomDAO.getRoom("SELECT * FROM ROOM WHERE cinema_id = " + cinemas.getCinema_id());
                spRoom.setAdapter(new ScheduleRoomNameSpinnerAdapter(parent.getContext(), R.layout.item_selected_room_spinner, roomList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvContinue.setOnClickListener(v -> {
            Cinemas cinemas = (Cinemas) spCinema.getSelectedItem();
            Room room = (Room) spRoom.getSelectedItem();
            Bundle bundle = new Bundle();
            bundle.putSerializable("cinema", cinemas);
            bundle.putSerializable("room", room);
            bundle.putSerializable("movie", movies);
            Intent intent1 = new Intent(this, ChooseSeat.class);
            intent1.putExtras(bundle);
            startActivity(intent1);
        });

    }
}