package com.ranaus.instatheme;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName,edtProfileBio,edtProfileProfession,edtProfileHobbies,edtProfileSport;
    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.profile_name);
        edtProfileBio = view.findViewById(R.id.profile_bio);
        edtProfileProfession = view.findViewById(R.id.profile_profession);
        edtProfileHobbies = view.findViewById(R.id.profile_hobbies);
        edtProfileSport = view.findViewById(R.id.profile_sport);

        btnUpdateInfo = view.findViewById(R.id.btn_update_info);

        ParseUser parseUser = ParseUser.getCurrentUser();

        ProgressDialog progressDialog = new ProgressDialog(super.getContext());

        edtProfileName.setText(parseUser.get("profileName")+"");
        edtProfileBio.setText(parseUser.get("profileBio")+"");
        edtProfileProfession.setText(parseUser.get("profileProfession")+"");
        edtProfileHobbies.setText(parseUser.get("profileHobbies")+"");
        edtProfileSport.setText(parseUser.get("profileSport")+"");

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                parseUser.put("profileName",edtProfileName.getText().toString());
                parseUser.put("profileBio",edtProfileBio.getText().toString());
                parseUser.put("profileProfession",edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies",edtProfileHobbies.getText().toString());
                parseUser.put("profileSport",edtProfileSport.getText().toString());

                progressDialog.setMessage("wait...");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null)
                        {
                            FancyToast.makeText(getContext(), " inserted",
                                    Toast.LENGTH_SHORT,FancyToast.INFO,true).show();
                        }
                        else
                        {
                            FancyToast.makeText(getContext(),e.getMessage(),
                                    Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                        }

                    }
                }
                );progressDialog.dismiss();

            }
        });

        return view;
    }

}
