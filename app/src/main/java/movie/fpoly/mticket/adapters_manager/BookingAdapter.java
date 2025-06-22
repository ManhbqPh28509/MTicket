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
import movie.fpoly.mticket.models.Booking;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private Context context;
    private List<Booking> bookingList;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_booking, viewGroup, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder bookingViewHolder, int i) {
        Booking booking = bookingList.get(i);
        bookingViewHolder.txtBooking_username.setText("Người đặt: " + booking.getUser_id());
        bookingViewHolder.txBooking_schedule.setText("Suất chiếu: " + booking.getSchedule_id());
        bookingViewHolder.txBooking_seat.setText("Ghế: " + booking.getSeat_id());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder{
        TextView txtBooking_username, txBooking_schedule, txBooking_seat, txBooking_price;
        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBooking_username = itemView.findViewById(R.id.txtBooking_username);
            txBooking_schedule = itemView.findViewById(R.id.txBooking_schedule);
            txBooking_seat = itemView.findViewById(R.id.txtBooking_seat);
            txBooking_price = itemView.findViewById(R.id.txtBooking_price);
        }
    }
}
