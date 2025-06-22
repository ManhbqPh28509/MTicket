package movie.fpoly.mticket.fragment_manager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.CinemaDAO;
import movie.fpoly.mticket.DAO.RoomDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.RoomAdapter;
import movie.fpoly.mticket.adapters_manager.UserAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Room;
import movie.fpoly.mticket.spinner.CinemaNameSpinnerAdapter;
import movie.fpoly.mticket.ui_manager.SeatManagement;
import movie.fpoly.mticket.ui_manager.UserInfo;


public class RoomFragment extends Fragment {
    DatabaseHelper databaseHelper;
    private List<Room> roomsList;
    private List<Cinemas> cinemasList;
    private RoomAdapter roomAdapter;
    private RoomDAO roomDao;
    private CinemaDAO cinemaDAO;
    CinemaNameSpinnerAdapter spinnerAdapter;
    RecyclerView rvRoom;
    FloatingActionButton addRoom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        roomDao = new RoomDAO(requireContext());
        cinemaDAO = new CinemaDAO(requireContext());
        rvRoom = view.findViewById(R.id.rv_Room);
        addRoom = view.findViewById(R.id.add_Room);
        updateRV();
        addRoom.setOnClickListener(v -> {
            spinnerAdapter = new CinemaNameSpinnerAdapter(requireContext(), R.layout.item_selected_cinema_spinner, cinemasList);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_room, null);
            EditText edtRoomName = dialogView.findViewById(R.id.edtRoom_name);
            Spinner spCinemaName = dialogView.findViewById(R.id.spCinemaName);
            spCinemaName.setAdapter(spinnerAdapter);
            builder.setView(dialogView);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String roomName = edtRoomName.getText().toString();
                Cinemas cinema = (Cinemas) spCinemaName.getSelectedItem();
                if (roomName.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập tên phòng chiếu", Toast.LENGTH_SHORT).show();
                } else {
                    Room room = new Room(cinema.getCinema_id(), roomName);
                    roomDao.insertRoom(room);
                    updateRV();
                    Toast.makeText(requireContext(), "Thêm phòng chiếu thành công", Toast.LENGTH_SHORT).show();
                }
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
        roomsList = new ArrayList<>();
        roomsList = roomDao.getRoom("SELECT * FROM ROOM");
        cinemasList = new ArrayList<>();
        cinemasList = cinemaDAO.getCinemas("SELECT * FROM CINEMAS");
        roomAdapter = new RoomAdapter(requireActivity(), roomsList, cinemasList, new RoomAdapter.OnRoomItemClickListener() {
            @Override
            public void OnItemClick(Room room) {
                Intent intent = new Intent(requireActivity(), SeatManagement.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("room", room);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void OnLongClick(Room room) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Xóa phòng chiếu");
                builder.setMessage("Bạn có muốn xóa phòng chiếu này?");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    roomDao.deleteRoom(room.getRoom_id());
                    Toast.makeText(requireContext(), "Xóa phòng chiếu thành công", Toast.LENGTH_SHORT).show();
                    updateRV();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        rvRoom.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvRoom.setHasFixedSize(true);
        rvRoom.setAdapter(roomAdapter);
    }
}