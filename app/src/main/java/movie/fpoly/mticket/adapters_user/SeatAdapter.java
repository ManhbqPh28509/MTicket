package movie.fpoly.mticket.adapters_user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Seats;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private Context context;
    private List<Seats> seatsList;
    private OnSeatClickListener onSeatClickListener;


    public SeatAdapter(Context context, List<Seats> seatsList, OnSeatClickListener onSeatClickListener) {
        this.context = context;
        this.seatsList = seatsList;
        this.onSeatClickListener = onSeatClickListener;
    }

    public interface OnSeatClickListener {
        void onSeatClick(Seats seats);
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_seat, viewGroup, false);
        return new SeatViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull SeatViewHolder seatViewHolder, int i) {
        Seats seats = seatsList.get(i);
        String number = String.valueOf(seats.getNumber());
        if (seats.isSeat_status()) {
            seatViewHolder.tvSeatNumber.setBackgroundResource(R.drawable.custom_item_seat_booked);
        }
        seatViewHolder.tvSeatNumber.setText(seats.getRow_seat() + number);
        seatViewHolder.itemView.setOnClickListener((View v) -> {
            if (seats.isSeat_status()) {
                Toast.makeText(context, "Ghế đã được đặt", Toast.LENGTH_SHORT).show();
            } else {
                onSeatClickListener.onSeatClick(seats);
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatsList.size();
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView tvSeatNumber;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSeatNumber = itemView.findViewById(R.id.tvSeatNumber);
        }
    }
}
