package movie.fpoly.mticket.adapters_manager;

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
import movie.fpoly.mticket.models.Movies;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movies> moviesList;
    private Context context;
    private List<Categories> categoriesList;
    private OnMovieItemClickListener onMovieItemClickListener;

    public interface OnMovieItemClickListener{
        void OnItemClick(Movies movies);
        void OnItemLongClick(Movies movies);
    }

    public MovieAdapter(List<Movies> moviesList, List<Categories> categoriesList, Context context, OnMovieItemClickListener onMovieItemClickListener) {
        this.moviesList = moviesList;
        this.context = context;
        this.categoriesList = categoriesList;
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movies movies = moviesList.get(i);
        movieViewHolder.txtMovie_name.setText("Tên phim: " + movies.getMovie_name());
        for (Categories categories : categoriesList) {
            if (categories.getCategory_id() == movies.getCategory_id()) {
                movieViewHolder.txtCategory_name.setText("Thể loại: " + categories.getCategory_name());
            }
        }
        movieViewHolder.itemView.setOnClickListener(v -> {
            onMovieItemClickListener.OnItemClick(movies);
        });
        movieViewHolder.itemView.setOnLongClickListener(v -> {
            onMovieItemClickListener.OnItemLongClick(movies);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView txtMovie_name;
        TextView txtCategory_name;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovie_name = itemView.findViewById(R.id.txtMovie_name);
            txtCategory_name = itemView.findViewById(R.id.txtCategory_name);
        }
    }
}
