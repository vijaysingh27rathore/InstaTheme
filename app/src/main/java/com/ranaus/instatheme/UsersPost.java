package com.ranaus.instatheme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPost extends AppCompatActivity {
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        linearLayout = findViewById(R.id.linerar_layout);
        Intent receivedIntentObject = getIntent();
        String receivedUserName =receivedIntentObject.getStringExtra("username");
        FancyToast.makeText(this,receivedUserName, Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();

        setTitle(receivedUserName+"'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByAscending("createdAt");
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null)
                {
                    for (ParseObject post : objects)
                    {
                        TextView imangeDescription = new TextView(UsersPost.this);
                        imangeDescription.setText(post.get("image_desc")+"");
                        ParseFile postPicture = (ParseFile) post.get("piccture");

                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {

                                if (data != null && e == null)
                                {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageView = new ImageView(UsersPost.this);
                                    LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageView_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams des_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,5);
                                    imangeDescription.setLayoutParams(des_params);
                                    imangeDescription.setGravity(Gravity.CENTER);
                                    imangeDescription.setBackgroundColor(Color.RED);
                                    imangeDescription.setTextColor(Color.WHITE);
                                    imangeDescription.setTextSize(30f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(imangeDescription);
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }
        });
    }
}
