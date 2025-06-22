package movie.fpoly.mticket.adapters_manager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import movie.fpoly.mticket.R;
import movie.fpoly.mticket.models.Users;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context context;
    private List<Users> users;
    private OnUserItemClickListener onUserItemClickListener;

    public interface OnUserItemClickListener{
        void OnItemClick(Users user);
    }

    public UserAdapter(Context context, List<Users> users, OnUserItemClickListener onUserItemClickListener) {
        this.context = context;
        this.users = users;
        this.onUserItemClickListener = onUserItemClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        Users users = this.users.get(i);
        userViewHolder.tvUser.setText("Username: " + users.getUsername());
        userViewHolder.tvEmail.setText("Email: " + users.getEmail());
        userViewHolder.ln_User.setOnClickListener(v -> {
            onUserItemClickListener.OnItemClick(users);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvUser;
        TextView tvEmail;
        LinearLayout ln_User;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.txtUser_name);
            tvEmail = itemView.findViewById(R.id.txtUser_email);
            ln_User = itemView.findViewById(R.id.ln_User);
        }
    }
}
