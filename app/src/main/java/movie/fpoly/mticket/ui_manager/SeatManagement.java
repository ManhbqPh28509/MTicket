package movie.fpoly.mticket.ui_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.SeatDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.SeatAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Room;
import movie.fpoly.mticket.models.Seats;

public class SeatManagement extends AppCompatActivity {
    private RecyclerView rvSeat;
    private SeatAdapter seatAdapter;
    private FloatingActionButton addSeat;
    private SeatDAO seatDAO;
    private DatabaseHelper databaseHelper;
    private List<Seats> seatsList = new ArrayList<>();
    private ImageView imgBack;
    private static final String REGEX_ROW = "^[abcd]$\n";
    private static final String REGEX_NUMBER = "^[1-6]$\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_management);
        rvSeat = findViewById(R.id.rv_Seat);
        addSeat = findViewById(R.id.add_seat);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Room room = (Room) bundle.getSerializable("room");
        updateRV(room);

        addSeat.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_seat, null);
            builder.setView(view1);
            EditText edtRoom = view1.findViewById(R.id.edtRoom);
            EditText edtRow = view1.findViewById(R.id.edtRow);
            EditText edtNumber = view1.findViewById(R.id.edtNumber);
            CheckBox cbStatus = view1.findViewById(R.id.cbStatus);
            edtRoom.setText(room.getRoom_name());
            builder.setPositiveButton("OK", (dialog, which) -> {
                String row = edtRow.getText().toString();
                String number = edtNumber.getText().toString();
                if (row.matches(REGEX_ROW) && number.matches(REGEX_NUMBER)) {
                    Seats seats = new Seats(room.getRoom_id(), row, Integer.parseInt(number), cbStatus.isChecked());
                    seatDAO.insertSeat(seats);
                    updateRV(room);
                    Toast.makeText(this, "Thêm ghế thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    private void updateRV(Room room) {
        seatDAO = new SeatDAO(this);
        seatsList = seatDAO.getSeats("SELECT * FROM SEATS WHERE room_id = " + room.getRoom_id() + " ORDER BY row_seat, number");
        seatAdapter = new SeatAdapter(this, seatsList, new SeatAdapter.OnSeatClickListener() {
            @Override
            public void onDeleteClick(Seats seats) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SeatManagement.this);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa ghế này?");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    seatDAO.deleteSeat(seats);
                    updateRV(room);
                    Toast.makeText(SeatManagement.this, "Xóa ghế thành công", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onEditClick(Seats seats) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SeatManagement.this);
                View view1 = getLayoutInflater().inflate(R.layout.dialog_add_seat, null);
                builder.setView(view1);
                TextView tvSeat = view1.findViewById(R.id.tvSeat);
                EditText edtRoom = view1.findViewById(R.id.edtRoom);
                EditText edtRow = view1.findViewById(R.id.edtRow);
                EditText edtNumber = view1.findViewById(R.id.edtNumber);
                CheckBox cbStatus = view1.findViewById(R.id.cbStatus);
                tvSeat.setText("Sửa ghế");
                edtRoom.setText(room.getRoom_name());
                edtRow.setText(seats.getRow_seat());
                edtNumber.setText(String.valueOf(seats.getNumber()));
                cbStatus.setChecked(seats.isSeat_status());
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String row = edtRow.getText().toString();
                    String number = edtNumber.getText().toString();
                    if (!row.matches(REGEX_ROW) || !number.matches(REGEX_NUMBER)) {
                        seats.setRow_seat(row);
                        seats.setNumber(Integer.parseInt(number));
                        seats.setSeat_status(cbStatus.isChecked());
                        seatDAO.updateSeat(seats);
                        updateRV(room);
                        Toast.makeText(SeatManagement.this, "Sửa ghế thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SeatManagement.this, "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        rvSeat.setLayoutManager(gridLayoutManager);
        rvSeat.setHasFixedSize(true);
        rvSeat.setAdapter(seatAdapter);
    }
}