package ar.edu.itba.mygymapp.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.backend.App;
import ar.edu.itba.mygymapp.backend.apimodels.FullUser;
import ar.edu.itba.mygymapp.backend.repository.Resource;
import ar.edu.itba.mygymapp.backend.repository.Status;
import ar.edu.itba.mygymapp.databinding.FragmentProfileBinding;
import io.github.muddz.styleabletoast.StyleableToast;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private View root;
    private App app;
    private ImageView BSelectImage;
    private FullUser user;
    int SELECT_PICTURE = 200;
    String genderText;
    // One Preview Image
    private ImageView IVPreviewImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        app = (App) getActivity().getApplication();
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        binding.saveBtn.setOnClickListener(this::saveProfile);
        Spinner spinner = binding.gender;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        loadProfileData();

        // register the UI widgets with their appropriate IDs
        BSelectImage = binding.changePhoto;
        IVPreviewImage = binding.avatar;
        DisplayMetrics displayMetrics = root.getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        if (dpWidth >= 700) {
            binding.editButton.setTextSize(23);
            binding.cancelBtn.setTextSize(23);
            binding.saveBtn.setTextSize(23);
            binding.firstName.setTextSize(20);
            binding.lastName.setTextSize(20);
            binding.profileEmail.setTextSize(20);
            binding.profileUsername.setTextSize(21);
            binding.userDataBox.setLayoutParams(new LinearLayout.LayoutParams(500, 300));
            binding.setFname.setTextSize(22);
            binding.cardFirstName.setTextSize(22);
            binding.cardLastName.setTextSize(22);
            binding.setLname.setTextSize(22);
            binding.cardGender.setTextSize(22);
            binding.setGender.setTextSize(22);
        }

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.infoLayout.setVisibility(View.GONE);
                binding.editLayout.setVisibility(View.VISIBLE);
            }
        });
        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.infoLayout.setVisibility(View.VISIBLE);
                binding.editLayout.setVisibility(View.GONE);
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


    public void saveProfile(View view) {
        EditText fnView, lnView;
        String firstName, lastName;
        Spinner spinner;
        boolean error = false;

        fnView = binding.firstName;
        lnView = binding.lastName;
        spinner = binding.gender;
        firstName = fnView.getText().toString();
        lastName = lnView.getText().toString();

        if (firstName.trim().length() == 0) {
            fnView.setError(getText(R.string.invalid_first_name));
            error = true;
        }
        if (lastName.trim().length() == 0) {
            lnView.setError(getText(R.string.invalid_last_name));
            error = true;
        }
        if (error) {
            StyleableToast.makeText(getActivity(), getText(R.string.invalid_data).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
            return;
        }

        FullUser aux = app.getUserRepository().getUser();
        aux.setFirstName(firstName);
        aux.setLastName(lastName);
        aux.setGender(genderText);
        app.getUserRepository().setUser(aux);
        app.getUserRepository().editCurrentUser(aux).observe((LifecycleOwner) view.getContext(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                binding.infoLayout.setVisibility(View.VISIBLE);
                binding.editLayout.setVisibility(View.GONE);
                StyleableToast.makeText(view.getContext(), getText(R.string.profile_saved).toString(), Toast.LENGTH_LONG, R.style.successToast).show();
                loadProfileData();
            } else {
                Resource.defaultResourceHandler(r);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                genderText = "other";
                break;
            case 1:
                genderText = "female";
                break;
            case 2:
                genderText = "male";
                break;


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    String genderToString(String gender) {
        String[] genders = getResources().getStringArray(R.array.genders);
        switch (gender) {
            case "other":
                return genders[0];
            case "female":
                return genders[1];
            default:
                return genders[2];
        }
    }

    void loadProfileData() {
        user = app.getUserRepository().getUser();
        ImageView userImageView = binding.avatar;
        Glide.with(this).asBitmap().load(user.getAvatarUrl()).placeholder(R.drawable.avatar).into(userImageView);
        binding.profileUsername.setText(user.getUsername());
        binding.profileEmail.setText(user.getEmail());
        binding.setFname.setText(user.getFirstName());
        binding.setLname.setText(user.getLastName());
        binding.setGender.setText(genderToString(user.getGender()));

        binding.firstName.setText(user.getFirstName());
        binding.lastName.setText(user.getLastName());

        binding.gender.setSelection(user.getGenderId());
        genderText = user.getGender();
    }
}