package movie.fpoly.mticket.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Categories;

public class MovieCategoryNameSpinnerAdapter extends ArrayAdapter<Categories> {
    public MovieCategoryNameSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Categories> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_category_spinner, parent, false);
        TextView tvCategoryNameSelected = convertView.findViewById(R.id.tv_Category_name_selected);
        Categories categories = this.getItem(position);
        tvCategoryNameSelected.setText(categories.getCategory_name());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category_spinner, parent, false);
        TextView tvCategoryNameSelected = convertView.findViewById(R.id.tv_Category_name);
        Categories categories = this.getItem(position);
        tvCategoryNameSelected.setText(categories.getCategory_name());
        return convertView;
    }


}
