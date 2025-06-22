package movie.fpoly.mticket.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Cinemas;

public class CinemaNameSpinnerAdapter extends ArrayAdapter<Cinemas> {
    public CinemaNameSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Cinemas> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_cinema_spinner, parent, false);
        TextView tvCinemaNameSelected = convertView.findViewById(R.id.tv_Cinema_name_selected);
        Cinemas cinemas = this.getItem(position);
        tvCinemaNameSelected.setText(cinemas.getCinema_name());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cinema_spinner, parent, false);
        TextView tvCinemaName = convertView.findViewById(R.id.tv_Cinema_name);
        Cinemas cinemas = this.getItem(position);
        tvCinemaName.setText(cinemas.getCinema_name());
        return convertView;
    }
}
