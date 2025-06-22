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
import movie.fpoly.mticket.models.Room;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private Context context;
    private List<Room> roomList;
    private List<Cinemas> cinemasList;
    private OnRoomItemClickListener onRoomItemClickListener;

    public interface OnRoomItemClickListener{
        void OnItemClick(Room room);
        void OnLongClick(Room room);
    }

    public RoomAdapter(Context context, List<Room> roomList, List<Cinemas> cinemasList, OnRoomItemClickListener onRoomItemClickListener) {
        this.context = context;
        this.roomList = roomList;
        this.cinemasList = cinemasList;
        this.onRoomItemClickListener = onRoomItemClickListener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_room, viewGroup, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder roomViewHolder, int i) {
        Room room = this.roomList.get(i);
        roomViewHolder.tvRoom_name.setText("Tên phòng chiếu: " + room.getRoom_name());
        for (Cinemas cinema : cinemasList) {
            if (cinema.getCinema_id() == room.getCinema_id()) {
                roomViewHolder.tvCinema_name.setText("Rạp chiếu: " + cinema.getCinema_name());
            }
        }
        roomViewHolder.itemView.setOnClickListener(v -> {
            onRoomItemClickListener.OnItemClick(room);
        });
        roomViewHolder.itemView.setOnLongClickListener(v -> {
            onRoomItemClickListener.OnLongClick(room);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder{
        TextView tvRoom_name;
        TextView tvCinema_name;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoom_name = itemView.findViewById(R.id.txtRoom_name);
            tvCinema_name = itemView.findViewById(R.id.txtCinema_name);
        }
    }
}
