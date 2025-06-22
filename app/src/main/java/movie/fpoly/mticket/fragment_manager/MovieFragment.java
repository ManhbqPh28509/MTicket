package movie.fpoly.mticket.fragment_manager;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import movie.fpoly.mticket.DAO.CategoryDAO;
import movie.fpoly.mticket.DAO.MovieDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.MovieAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Categories;
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.spinner.MovieCategoryNameSpinnerAdapter;

public class MovieFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private List<Movies> moviesList = new ArrayList<>();
    private List<Categories> categoriesList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private MovieDAO movieDAO;
    private CategoryDAO categoryDAO;
    FloatingActionButton add_movie;
    RecyclerView rvMovie;
    Bitmap bitmap;
    ImageView imgPoster;
    int REQUEST_CODE = 100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        movieDAO = new MovieDAO(requireContext());
        categoryDAO = new CategoryDAO(requireContext());
        rvMovie = view.findViewById(R.id.rv_Movie);
        add_movie = view.findViewById(R.id.add_movie);

        updateRV();
        add_movie.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_movie, null);
            TextView edtMovie_name = dialogView.findViewById(R.id.edtMovie_name);
            TextView edtMovie_trailer = dialogView.findViewById(R.id.edtMovie_trailer);
            TextView edtMovie_release = dialogView.findViewById(R.id.edtMovie_release);
            imgPoster = dialogView.findViewById(R.id.imgPoster);
            TextView edtMovie_length = dialogView.findViewById(R.id.edtMovie_length);
            TextView edtMovie_description = dialogView.findViewById(R.id.edtMovie_description);
            Spinner spCategoryName = dialogView.findViewById(R.id.spCategoryName);

            imgPoster.setOnClickListener(v1 -> {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            });
            spCategoryName.setAdapter(new MovieCategoryNameSpinnerAdapter(requireContext(), R.layout.item_selected_category_spinner ,categoriesList));
            builder.setView(dialogView);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String movieName = edtMovie_name.getText().toString();
                String trailer = edtMovie_trailer.getText().toString();
                String release = edtMovie_release.getText().toString();
                String length = edtMovie_length.getText().toString();
                String description = edtMovie_description.getText().toString();
                byte[] poster = getBytes(imgPoster);
                Categories categories = (Categories) spCategoryName.getSelectedItem();
                if (movieName.isEmpty() || trailer.isEmpty() || release.isEmpty() || length.isEmpty() || description.isEmpty() || categories == null || poster.length == 0) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Movies movies = new Movies(categories.getCategory_id(), movieName, description, trailer, release, poster, length);
                    movieDAO.insertMovies(movies);
                    updateRV();
                    Toast.makeText(requireContext(), "Thêm phim thành công", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return view;
    }

    private void updateRV() {
        moviesList = movieDAO.getMovie("SELECT * FROM MOVIES");
        categoriesList = categoryDAO.getCategories("SELECT * FROM CATEGORIES");
        movieAdapter = new MovieAdapter(moviesList, categoriesList, requireContext(), new MovieAdapter.OnMovieItemClickListener() {
            @Override
            public void OnItemClick(Movies movies) {
                Toast.makeText(requireContext(), "Click" + movies.getMovie_trailer(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_movie, null);
                TextView edtMovie_name = dialogView.findViewById(R.id.edtMovie_name);
                TextView edtMovie_trailer = dialogView.findViewById(R.id.edtMovie_trailer);
                TextView edtMovie_release = dialogView.findViewById(R.id.edtMovie_release);
                imgPoster = dialogView.findViewById(R.id.imgPoster);
                TextView edtMovie_length = dialogView.findViewById(R.id.edtMovie_length);
                TextView edtMovie_description = dialogView.findViewById(R.id.edtMovie_description);
                Spinner spCategoryName = dialogView.findViewById(R.id.spCategoryName);
                spCategoryName.setAdapter(new MovieCategoryNameSpinnerAdapter(requireContext(), R.layout.item_selected_category_spinner ,categoriesList));
                imgPoster.setOnClickListener(v1 -> {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE);
                });
                edtMovie_name.setText(movies.getMovie_name());
                edtMovie_trailer.setText(movies.getMovie_trailer());
                edtMovie_release.setText(movies.getMovie_release());
                edtMovie_length.setText(movies.getMovie_length());
                edtMovie_description.setText(movies.getMovie_description());
                for (int i = 0; i < categoriesList.size(); i++) {
                    if (categoriesList.get(i).getCategory_id() == movies.getCategory_id()) {
                        spCategoryName.setSelection(i);
                        break;
                    }
                }
                imgPoster.setImageBitmap(BitmapFactory.decodeByteArray(movies.getMovie_poster(), 0, movies.getMovie_poster().length));
                builder.setView(dialogView);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String movieName = edtMovie_name.getText().toString();
                    String description = edtMovie_trailer.getText().toString();
                    String release = edtMovie_release.getText().toString();
                    String length = edtMovie_length.getText().toString();
                    String trailer = edtMovie_description.getText().toString();
                    byte[] poster = getBytes(imgPoster);
                    Categories categories = (Categories) spCategoryName.getSelectedItem();
                    if (movieName.isEmpty() ||
                            trailer.isEmpty() ||
                            release.isEmpty()  ||
                            length.isEmpty() ||
                            description.isEmpty() ||
                            categories == null ||
                            poster.length == 0){
                        Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        movies.setCategory_id(categories.getCategory_id());
                        movies.setMovie_name(movieName);
                        movies.setMovie_trailer(trailer);
                        movies.setMovie_release(release);
                        movies.setMovie_length(length);
                        movies.setMovie_description(description);
                        movies.setMovie_poster(poster);
                        movieDAO.updateMovies(movies);
                        updateRV();
                        Toast.makeText(requireContext(), "Sửa phim thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void OnItemLongClick(Movies movies) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa phim này?");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    movieDAO.deleteMovies(movies);
                    updateRV();
                    Toast.makeText(requireContext(), "Xóa phim thành công", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        rvMovie.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(movieAdapter);
    }

    private byte[] getBytes(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgPoster.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}