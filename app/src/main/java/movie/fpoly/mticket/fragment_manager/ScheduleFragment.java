package movie.fpoly.mticket.fragment_manager;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.CinemaDAO;
import movie.fpoly.mticket.DAO.MovieDAO;
import movie.fpoly.mticket.DAO.RoomDAO;
import movie.fpoly.mticket.DAO.ScheduleDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.ScheduleAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.models.Room;
import movie.fpoly.mticket.models.Schedule;
import movie.fpoly.mticket.spinner.CinemaNameSpinnerAdapter;
import movie.fpoly.mticket.spinner.ScheduleMovieNameSpinnerAdapter;
import movie.fpoly.mticket.spinner.ScheduleRoomNameSpinnerAdapter;

public class ScheduleFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private RecyclerView rv_Schedule;
    private FloatingActionButton add_schedule;
    private List<Movies> moviesList = new ArrayList<>();
    private List<Schedule> scheduleList = new ArrayList<>();
    private List<Cinemas> cinemasList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;
    private ScheduleDAO scheduleDAO;
    private MovieDAO movieDAO;
    private CinemaDAO cinemaDAO;
    private RoomDAO roomDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        scheduleDAO = new ScheduleDAO(requireContext());
        movieDAO = new MovieDAO(requireContext());
        cinemaDAO = new CinemaDAO(requireContext());
        roomDAO = new RoomDAO(requireContext());
        rv_Schedule = view.findViewById(R.id.rv_Schedule);
        add_schedule = view.findViewById(R.id.add_schedule);

        add_schedule.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_schedule, null);
            Spinner spScheduleMovie = dialogView.findViewById(R.id.spScheduleMovie);
            Spinner spScheduleCinema = dialogView.findViewById(R.id.spScheduleCinema);
            Spinner spScheduleRoom = dialogView.findViewById(R.id.spScheduleRoom);
            EditText edtScheduleDate = dialogView.findViewById(R.id.edtScheduleDate);
            spScheduleMovie.setAdapter(new ScheduleMovieNameSpinnerAdapter(requireContext(),
                    R.layout.item_selected_movie_spinner, moviesList));
            spScheduleCinema.setAdapter(new CinemaNameSpinnerAdapter(requireContext(),
                    R.layout.item_selected_cinema_spinner, cinemasList));
            spScheduleCinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Cinemas cinemas = cinemasList.get(position);
                    roomList = roomDAO.getRoom("SELECT * FROM ROOM WHERE cinema_id = " + cinemas.getCinema_id());
                    spScheduleRoom.setAdapter(new ScheduleRoomNameSpinnerAdapter(requireContext(),
                            R.layout.item_selected_room_spinner, roomList));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            builder.setView(dialogView);
            builder.setPositiveButton("Thêm", (dialog, which) -> {
                Movies movies = (Movies) spScheduleMovie.getSelectedItem();
                Room room = (Room) spScheduleRoom.getSelectedItem();
                String date = edtScheduleDate.getText().toString();
                if (isValidDate(date)) {
                    Schedule schedule = new Schedule(movies.getMovie_id(), room.getRoom_id(), date);
                    scheduleDAO.insertSchedule(schedule);
                    updateRV();
                    Toast.makeText(requireContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Ngày chiếu không hợp lệ ", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hủy", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        updateRV();
        return view;
    }

    private void updateRV() {
        moviesList = movieDAO.getMovie("SELECT * FROM MOVIES");
        scheduleList = scheduleDAO.getSchedule("SELECT * FROM SCHEDULE");
        cinemasList = cinemaDAO.getCinemas("SELECT * FROM CINEMAS");
        scheduleAdapter = new ScheduleAdapter(requireContext(), scheduleList, moviesList, new ScheduleAdapter.OnScheduleClickListener() {
            @Override
            public void onEditClick(Schedule schedule) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_schedule, null);
                TextView tvTitle = dialogView.findViewById(R.id.tv_title);
                Spinner spScheduleMovie = dialogView.findViewById(R.id.spScheduleMovie);
                Spinner spScheduleCinema = dialogView.findViewById(R.id.spScheduleCinema);
                Spinner spScheduleRoom = dialogView.findViewById(R.id.spScheduleRoom);
                EditText edtScheduleDate = dialogView.findViewById(R.id.edtScheduleDate);
                tvTitle.setText("Sửa suất chiếu");
                edtScheduleDate.setText(schedule.getSchedule_date());
                spScheduleMovie.setAdapter(new ScheduleMovieNameSpinnerAdapter(requireContext(),
                        R.layout.item_selected_movie_spinner, moviesList));
                for (int i = 0; i < moviesList.size(); i++) {
                    if (moviesList.get(i).getMovie_id() == schedule.getMovie_id()) {
                        spScheduleMovie.setSelection(i);
                        break;
                    }
                }

                spScheduleCinema.setAdapter(new CinemaNameSpinnerAdapter(requireContext(),
                        R.layout.item_selected_cinema_spinner, cinemasList));
                spScheduleCinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Cinemas cinemas = cinemasList.get(position);
                        List<Room> roomList1 = roomDAO.getRoom("SELECT * FROM ROOM WHERE cinema_id = " + cinemas.getCinema_id());
                        spScheduleRoom.setAdapter(new ScheduleRoomNameSpinnerAdapter(requireContext(),
                                R.layout.item_selected_room_spinner, roomList1));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                int id = 0;
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getRoom_id() == schedule.getRoom_id()) {
                        for (int j = 0; j < cinemasList.size(); j++) {
                            if (cinemasList.get(j).getCinema_id() == roomList.get(i).getCinema_id()) {
                                id = cinemasList.get(j).getCinema_id();
                                spScheduleCinema.setSelection(j);
                                break;
                            }
                        }
                    }
                }
                List<Room> roomList1 = roomDAO.getRoom("SELECT * FROM ROOM WHERE cinema_id = " + id);
                for (int i = 0; i < roomList1.size(); i++) {
                    if (roomList1.get(i).getRoom_id() == schedule.getRoom_id()) {
                        spScheduleRoom.setSelection(i);
                        break;
                    }
                }



                builder.setView(dialogView);
                builder.setPositiveButton("Sửa", (dialog, which) -> {
                    Movies movies = (Movies) spScheduleMovie.getSelectedItem();
                    Room room = (Room) spScheduleRoom.getSelectedItem();
                    String date = edtScheduleDate.getText().toString();
                    if (isValidDate(date)) {
                        schedule.setMovie_id(movies.getMovie_id());
                        schedule.setRoom_id(room.getRoom_id());
                        schedule.setSchedule_date(date);
                        scheduleDAO.updateSchedule(schedule);
                        updateRV();
                        Toast.makeText(requireContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), "Ngày chiếu không hợp lệ ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onDeleteClick(Schedule schedule) {

            }
        });
        rv_Schedule.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv_Schedule.setHasFixedSize(true);
        rv_Schedule.setAdapter(scheduleAdapter);
    }

    public static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}