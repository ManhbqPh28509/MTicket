package movie.fpoly.mticket.fragments_users;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import movie.fpoly.mticket.DAO.MovieDAO;
import movie.fpoly.mticket.R;
import movie.fpoly.mticket.adapters_user.MovieAdapterUser;
import movie.fpoly.mticket.adapters_user.SliderAdapter;
import movie.fpoly.mticket.databases.DatabaseHelper;
import movie.fpoly.mticket.models.Movies;
import movie.fpoly.mticket.models.Photo_slider;
import movie.fpoly.mticket.services.DepthPageTransformer;
import movie.fpoly.mticket.ui.ReviewMovie;

public class HomeFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private List<Movies> moviesList = new ArrayList<>();
    private MovieAdapterUser movieAdapter;
    private MovieDAO movieDAO;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<Photo_slider> list;
    private RecyclerView rvPhim;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        movieDAO = new MovieDAO(requireContext());
        moviesList = movieDAO.getMovie("SELECT * FROM MOVIES");
        movieAdapter = new MovieAdapterUser(moviesList, requireContext(), movies -> {
            Intent intent = new Intent(requireContext(), ReviewMovie.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("movie", movies);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        viewPager2 = view.findViewById(R.id.viewpager2);
        circleIndicator3 = view.findViewById(R.id.circle3);
        rvPhim = view.findViewById(R.id.rvPhim);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvPhim.setLayoutManager(gridLayoutManager);
        rvPhim.setAdapter(movieAdapter);

        list = getPhotoList();
        SliderAdapter sliderAdapter = new SliderAdapter(list);
        viewPager2.setAdapter(sliderAdapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });
        viewPager2.setPageTransformer(new DepthPageTransformer());
        return view;
    }

    private List<Photo_slider> getPhotoList() {
        List<Photo_slider> photoList = new ArrayList<>();
        photoList.add(new Photo_slider(R.drawable.anh1));
        photoList.add(new Photo_slider(R.drawable.anh2));
        photoList.add(new Photo_slider(R.drawable.anh3));
        return photoList;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }
}