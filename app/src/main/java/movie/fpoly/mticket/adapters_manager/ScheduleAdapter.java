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
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.models.Schedule;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private Context context;
    private List<Schedule> scheduleList;
    private List<Movies> moviesList;
    private OnScheduleClickListener onScheduleClickListener;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList, List<Movies> moviesList, OnScheduleClickListener onScheduleClickListener) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.moviesList = moviesList;
        this.onScheduleClickListener = onScheduleClickListener;
    }

    public interface OnScheduleClickListener {
        void onEditClick(Schedule schedule);
        void onDeleteClick(Schedule schedule);
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule, viewGroup, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int i) {
        Schedule schedule = scheduleList.get(i);
        for (Movies movies : moviesList) {
            if (movies.getMovie_id() == schedule.getMovie_id()) {
                scheduleViewHolder.txtSchedule_movie_name.setText("Phim: " + movies.getMovie_name());
            }
        }
        scheduleViewHolder.txtSchedule_date.setText("Ngày chiếu: " + schedule.getSchedule_date());
        scheduleViewHolder.itemView.setOnClickListener(v -> {
            onScheduleClickListener.onEditClick(schedule);
        });
        scheduleViewHolder.itemView.setOnLongClickListener(v -> {
            onScheduleClickListener.onDeleteClick(schedule);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView txtSchedule_movie_name;
        TextView txtSchedule_date;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSchedule_movie_name = itemView.findViewById(R.id.txtSchedule_movie_name);
            txtSchedule_date = itemView.findViewById(R.id.txtSchedule_date);
        }
    }
}
