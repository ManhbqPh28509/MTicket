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
import movie.fpoly.mticket.models.Movies;

public class ScheduleMovieNameSpinnerAdapter extends ArrayAdapter<Movies> {
    public ScheduleMovieNameSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Movies> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_movie_spinner, parent, false);
        TextView tvMovieNameSelected = convertView.findViewById(R.id.tv_Movie_name_selected);
        Movies movies = this.getItem(position);
        tvMovieNameSelected.setText(movies.getMovie_name());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_spinner, parent, false);
        TextView tvMovieNameSelected = convertView.findViewById(R.id.tv_movie_name);
        Movies movies = this.getItem(position);
        tvMovieNameSelected.setText(movies.getMovie_name());
        return convertView;
    }
}
