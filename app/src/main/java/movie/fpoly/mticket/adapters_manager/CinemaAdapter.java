package movie.fpoly.mticket.adapters_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Cinemas;
import movie.fpoly.mticket.models.Users;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {
    private Context context;
    private List<Cinemas> cinemas;
    private CinemaAdapter.OnCinemasItemClickListener onCinemasItemClickListener;

    public interface OnCinemasItemClickListener{
        void OnItemClick(Cinemas cinema);
    }

    public CinemaAdapter(Context context, List<Cinemas> cinemas, OnCinemasItemClickListener onCinemasItemClickListener) {
        this.context = context;
        this.cinemas = cinemas;
        this.onCinemasItemClickListener = onCinemasItemClickListener;
    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cinema, viewGroup, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder cinemaViewHolder, int i) {
        Cinemas cinemas = this.cinemas.get(i);
        cinemaViewHolder.tvCinema.setText("Tên rạp: " + cinemas.getCinema_name());
        cinemaViewHolder.tvAddress.setText("Địa chỉ: " + cinemas.getCinema_address());
        cinemaViewHolder.ln_Cinema.setOnClickListener(v -> {
            onCinemasItemClickListener.OnItemClick(cinemas);
        });
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public static class CinemaViewHolder extends RecyclerView.ViewHolder{
        TextView tvCinema;
        TextView tvAddress;
        LinearLayout ln_Cinema;

        public CinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCinema = itemView.findViewById(R.id.txtCinema_name);
            tvAddress = itemView.findViewById(R.id.txtCinema_address);
            ln_Cinema = itemView.findViewById(R.id.ln_Cinema);
        }
    }
}
