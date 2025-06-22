package movie.fpoly.mticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.SeatDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_user.SeatAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.models.Room;
import movie.fpoly.mticket.models.Seats;

public class ChooseSeat extends AppCompatActivity {
    private RecyclerView rvSeat;
    private SeatAdapter seatAdapter;
    private FloatingActionButton addSeat;
    private SeatDAO seatDAO;
    private DatabaseHelper databaseHelper;
    private List<Seats> seatsList = new ArrayList<>();
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        databaseHelper = new DatabaseHelper(this);
        seatDAO = new SeatDAO(this);
        rvSeat = findViewById(R.id.rv_Seat);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Room room = (Room) bundle.getSerializable("room");
        Cinemas cinemas = (Cinemas) bundle.getSerializable("cinema");
        Movies movies = (Movies) bundle.getSerializable("movie");

        seatsList = seatDAO.getSeats("SELECT * FROM SEATS WHERE room_id = " + room.getRoom_id() + " ORDER BY row_seat, number");
        seatAdapter = new SeatAdapter(this, seatsList, new SeatAdapter.OnSeatClickListener() {
            @Override
            public void onSeatClick(Seats seats) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseSeat.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_show_price, null);
                TextView edtMovie_name = dialogView.findViewById(R.id.edtMovie_name);
                TextView edtCinema_name = dialogView.findViewById(R.id.edtCinema_name);
                TextView edtRoom_name = dialogView.findViewById(R.id.edtRoom_name);
                TextView edtSeat_name = dialogView.findViewById(R.id.edtSeat_name);
                TextView edtPrice = dialogView.findViewById(R.id.edtPrice);

                edtMovie_name.setText("Tên phim: " + movies.getMovie_name());
                edtCinema_name.setText("Tên rạp: " + cinemas.getCinema_name());
                edtRoom_name.setText("Tên phòng: " + room.getRoom_name());
                edtSeat_name.setText("Vị trí ghế: " + seats.getRow_seat() + seats.getNumber());
                edtPrice.setText("Giá: 50000đ");
                builder.setView(dialogView);
                builder.setPositiveButton("Đặt vé", (dialog, which) -> {
                    Intent intent1 = new Intent(ChooseSeat.this, Purchase.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("seat", seats);
                    bundle1.putSerializable("cinema", cinemas);
                    bundle1.putSerializable("room", room);
                    bundle1.putSerializable("movie", movies);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                });
                builder.setNegativeButton("Huỷ", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        rvSeat.setLayoutManager(gridLayoutManager);
        rvSeat.setHasFixedSize(true);
        rvSeat.setAdapter(seatAdapter);
    }
}