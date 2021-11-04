package ar.edu.itba.mygymapp.ui.favourites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import ar.edu.itba.mygymapp.databinding.FragmentFavouritesBinding;
import ar.edu.itba.mygymapp.databinding.FragmentHomeBinding;
import ar.edu.itba.mygymapp.ui.home.HomeViewModel;
import ar.edu.itba.mygymapp.ui.routines.DestacadasRecViewAdapter;

public class FavouritesFragment extends Fragment {
    private FavouritesViewModel favouritesViewModel;
    private FragmentFavouritesBinding binding;
    DestacadasRecViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favouritesViewModel =
                new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new DestacadasRecViewAdapter();
        binding.destacadasRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.destacadasRecView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
