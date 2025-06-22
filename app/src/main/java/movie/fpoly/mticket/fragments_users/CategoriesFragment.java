package movie.fpoly.mticket.fragments_users;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import movie.fpoly.mticket.DAO.CategoryDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_user.CategoryAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Categories;
import movie.fpoly.mticket.ui.Category;

public class CategoriesFragment extends Fragment {
    RecyclerView rv_category;
    private List<Categories> categoryList = new ArrayList<>();
    private CategoryAdapter categoriesAdapter;
    private DatabaseHelper databaseHelper;
    private CategoryDAO categoryDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);
        rv_category = view.findViewById(R.id.rv_category);
        databaseHelper = new DatabaseHelper(requireContext());
        categoryDAO = new CategoryDAO(requireContext());
        categoryList = categoryDAO.getCategories("SELECT * FROM CATEGORIES");
        categoriesAdapter = new CategoryAdapter(requireContext(), categoryList, category -> {
            Intent intent = new Intent(requireContext(), Category.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("category", category);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        rv_category.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv_category.setHasFixedSize(true);
        rv_category.setAdapter(categoriesAdapter);
        return view;
    }
}