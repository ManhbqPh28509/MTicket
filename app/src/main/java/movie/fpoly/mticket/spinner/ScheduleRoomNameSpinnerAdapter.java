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
import movie.fpoly.mticket.models.Room;

public class ScheduleRoomNameSpinnerAdapter extends ArrayAdapter<Room> {
    public ScheduleRoomNameSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_room_spinner, parent, false);
        TextView tvRoomName = convertView.findViewById(R.id.tv_room_name_selected);
        Room room = this.getItem(position);
        tvRoomName.setText(room.getRoom_name());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_spinner, parent, false);
        TextView tvRoomName = convertView.findViewById(R.id.tv_room_name);
        Room room = this.getItem(position);
        tvRoomName.setText(room.getRoom_name());
        return convertView;
    }
}
