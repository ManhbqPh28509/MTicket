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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.CinemaDAO;
import movie.fpoly.mticket.DAO.UserDao;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.CinemaAdapter;
import movie.fpoly.mticket.adapters_manager.UserAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Users;
import movie.fpoly.mticket.ui_manager.UserInfo;

public class CinemaFragment extends Fragment {
    DatabaseHelper databaseHelper;
    private List<Cinemas> cinemasList;
    private CinemaAdapter cinemaAdapter;
    private CinemaDAO cinemaDAO;
    FloatingActionButton add_cinema;
    RecyclerView rvCinema;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        cinemaDAO = new CinemaDAO(requireContext());
        rvCinema = view.findViewById(R.id.rv_Cinema);
        add_cinema = view.findViewById(R.id.add_Cinema);
        updateRV();
        add_cinema.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_cinema, null);
            EditText edtCinema_name = view1.findViewById(R.id.edtCinema_name);
            EditText edtCinema_address = view1.findViewById(R.id.edtCinema_address);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setView(view1);
            builder.setPositiveButton( "OK", (dialog, which) -> {
                String cinema_name = edtCinema_name.getText().toString();
                String cinema_address = edtCinema_address.getText().toString();
                Cinemas cinema = new Cinemas(cinema_name, cinema_address);
                cinemaDAO.insertCinema(cinema);
                Toast.makeText(requireContext(), "Add cinema success", Toast.LENGTH_SHORT).show();
                updateRV();
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
        cinemasList = new ArrayList<>();
        cinemasList = cinemaDAO.getCinemas("SELECT * FROM CINEMAS");
        cinemaAdapter = new CinemaAdapter(requireActivity(), cinemasList, (Cinemas cinema) -> {
            Intent intent = new Intent(requireActivity(), UserInfo.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("cinema", cinema);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        rvCinema.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCinema.setHasFixedSize(true);
        rvCinema.setAdapter(cinemaAdapter);
    }
}