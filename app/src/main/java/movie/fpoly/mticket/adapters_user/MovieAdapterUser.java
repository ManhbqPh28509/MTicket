package movie.fpoly.mticket.adapters_user;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Movies;

public class MovieAdapterUser extends RecyclerView.Adapter<MovieAdapterUser.MovieViewHolder> {

    private Context context;
    private List<Movies> moviesList;
    private OnMovieClickListener onMovieClickListener;

    public MovieAdapterUser(List<Movies> moviesList, Context context, OnMovieClickListener onMovieClickListener) {
        this.moviesList = moviesList;
        this.context = context;
        this.onMovieClickListener = onMovieClickListener;
    }

    public interface OnMovieClickListener{
        void OnItemClick(Movies movies);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_user, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movies movies = moviesList.get(i);
        movieViewHolder.tv_movie_name.setText(movies.getMovie_name());
        movieViewHolder.img_movie_poster.setImageBitmap(BitmapFactory.decodeByteArray(movies.getMovie_poster(), 0, movies.getMovie_poster().length));
        movieViewHolder.itemView.setOnClickListener(v -> {
            onMovieClickListener.OnItemClick(movies);
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView img_movie_poster;
        TextView tv_movie_name;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            img_movie_poster = itemView.findViewById(R.id.img_movie_poster);
            tv_movie_name = itemView.findViewById(R.id.tv_movie_name);
        }
    }
}
