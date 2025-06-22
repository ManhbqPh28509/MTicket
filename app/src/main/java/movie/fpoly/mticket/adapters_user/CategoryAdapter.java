package movie.fpoly.mticket.adapters_user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Categories;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Categories> categoriesList;
    private OnCategoryItemClickListener onCategoryItemClickListener;

    public interface OnCategoryItemClickListener {
        void OnItemClick(Categories category);
    }

    public CategoryAdapter(Context context, List<Categories> categoriesList, OnCategoryItemClickListener onCategoryItemClickListener) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.onCategoryItemClickListener = onCategoryItemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        Categories categories = this.categoriesList.get(i);
        categoryViewHolder.tvCategory.setText(categories.getCategory_name());
        categoryViewHolder.itemView.setOnClickListener(v -> {
            onCategoryItemClickListener.OnItemClick(categories);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.txtCategory_name);
        }
    }
}
