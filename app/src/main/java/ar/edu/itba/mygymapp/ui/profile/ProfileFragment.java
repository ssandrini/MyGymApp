package ar.edu.itba.mygymapp.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.store.UserStore;
import ar.edu.itba.mygymapp.databinding.FragmentFavouritesBinding;
import ar.edu.itba.mygymapp.databinding.FragmentProfileBinding;
import ar.edu.itba.mygymapp.ui.favourites.FavouritesViewModel;
import ar.edu.itba.mygymapp.ui.routines.RoutinesAdapter;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private View root;
    private Button BSelectImage;
    int SELECT_PICTURE = 200;
    // One Preview Image
    private ImageView IVPreviewImage;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        ImageView userImageView = binding.avatar;
        Glide.with(this).asBitmap().load(UserStore.getUser().getAvatarUrl()).placeholder(R.drawable.avatar).into(userImageView);
        binding.saveBtn.setOnClickListener(this::saveProfile);

        Spinner spinner = binding.gender;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // register the UI widgets with their appropriate IDs
        BSelectImage = binding.changePhoto;
        IVPreviewImage = binding.avatar;

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        return root;
    }

    // this function is triggered when
    // the Select Image Button is clicked
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }


    public void saveProfile(View view){
        EditText fnView, lnView, phoneView;
        String firstName, lastName, phone;
        boolean error = false;

        fnView = binding.firstName;
        lnView = binding.lastName;
        phoneView = binding.editTextPhone;
        firstName = fnView.getText().toString();
        lastName = lnView.getText().toString();
        phone = phoneView.getText().toString();

        if (firstName.trim().length() == 0) {
            fnView.setError("Invalid first name");
            error = true;
        }
        if (lastName.trim().length() == 0) {
            lnView.setError("Invalid last name");
            error = true;
        }
        if(phone.trim().length() == 0){
            phoneView.setError("Invalid phone number");
            error = true;
        }
        if (error) {
            Toast.makeText(getActivity(),"Invalid data", Toast.LENGTH_SHORT).show();
            return ;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}