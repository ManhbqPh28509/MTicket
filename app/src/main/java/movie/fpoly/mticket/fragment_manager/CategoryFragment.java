package movie.fpoly.mticket.fragment_manager;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.CategoryDAO;
import movie.fpoly.mticket.DAO.MovieDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_manager.CategoryAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Categories;
import movie.fpoly.mticket.models.Movies;

public class CategoryFragment extends Fragment {
    DatabaseHelper databaseHelper;
    private List<Categories> categoriesList = new ArrayList<>();
    private List<Movies> moviesList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;
    FloatingActionButton add_category;
    RecyclerView rvCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        categoryDAO = new CategoryDAO(requireContext());
        movieDAO = new MovieDAO(requireContext());
        rvCategory = view.findViewById(R.id.rv_Category);
        add_category = view.findViewById(R.id.add_category);

        updateRV();
        add_category.setOnClickListener(v -> {
            View view1 = getLayoutInflater().inflate(R.layout.dialog_add_category, null);
            EditText edt_category_name = view1.findViewById(R.id.edtCategory_name);
            TextView tv_title = view1.findViewById(R.id.tv_title);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setView(view1);
            builder.setPositiveButton("OK", (dialog, which) -> {
                String category_name = edt_category_name.getText().toString();
                if (category_name.isEmpty()) {
                    tv_title.setText("Vui lòng nhập tên thể loại");
                } else {
                    Categories category = new Categories(category_name);
                    categoryDAO.insertCategory(category);
                    updateRV();
                    Toast.makeText(requireContext(), "Add category success", Toast.LENGTH_SHORT).show();
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
        categoriesList = categoryDAO.getCategories("SELECT * FROM CATEGORIES");
        categoryAdapter = new CategoryAdapter(getContext(), categoriesList, new CategoryAdapter.OnCategoryItemClickListener() {
            @Override
            public void OnItemClick(Categories category) {
                View view1 = getLayoutInflater().inflate(R.layout.dialog_add_category, null);
                EditText edt_category_name = view1.findViewById(R.id.edtCategory_name);
                TextView tv_title = view1.findViewById(R.id.tv_title);
                edt_category_name.setText(category.getCategory_name());
                tv_title.setText("Sửa thể loại");
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setView(view1);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String category_name = edt_category_name.getText().toString();
                    if (category_name.isEmpty()) {
                        tv_title.setText("Vui lòng nhập tên thể loại");
                    } else {
                        category.setCategory_name(category_name);
                        categoryDAO.updateCategory(category);
                        updateRV();
                        Toast.makeText(requireContext(), "Sửa thể loại thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void OnItemLongClick(Categories category) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Xóa thể loại");
                builder.setMessage("Bạn có chắc chắn muốn xóa thể loại này?");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    moviesList = movieDAO.getMovie("SELECT * FROM MOVIES WHERE category_id = " + category.getCategory_id());
                    for (Movies movie : moviesList) {
                        movieDAO.deleteMovies(movie);
                    }
                    categoryDAO.deleteCategory(category);
                    updateRV();
                    Toast.makeText(requireContext(), "Xóa thể loại thành công", Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(categoryAdapter);
    }
}