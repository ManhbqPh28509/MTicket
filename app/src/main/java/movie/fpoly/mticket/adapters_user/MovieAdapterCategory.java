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
import movie.fpoly.mticket.models.Movies;

public class MovieAdapterCategory extends RecyclerView.Adapter<MovieAdapterCategory.MovieViewHolder> {

    private Context context;
    private List<Movies> moviesList;

    public MovieAdapterCategory(Context context, List<Movies> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movies movies = moviesList.get(i);
        movieViewHolder.tv_movie_name.setText(movies.getMovie_name());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tv_movie_name;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_movie_name = itemView.findViewById(R.id.txtCategory_name);
        }
    }
}
