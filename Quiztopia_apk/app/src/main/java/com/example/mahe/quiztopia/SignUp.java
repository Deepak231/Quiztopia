package com.example.mahe.quiztopia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahe.quiztopia.models.User;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.UserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2.setVisibility(View.INVISIBLE);

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(Color.rgb(1, 57, 134));

        if(!isNetworkAvailable()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUp.this);
            alertDialog.setTitle("Network Error").setCancelable(false);
            alertDialog.setMessage("Error Connecting Quiztopia");
            alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }


        final EditText editText1 = (EditText) findViewById(R.id.user_text2);

        final EditText editText2 = (EditText) findViewById(R.id.pass_text2);

        final EditText editText3 = (EditText) findViewById(R.id.pass_text3);

        Button button1 = (Button) findViewById(R.id.signup);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usertext1 = editText1.getText().toString();
                final String pass_text = editText2.getText().toString();
                final String con_pass_text = editText3.getText().toString();
                if(usertext1.equals("") || pass_text.equals("") || con_pass_text.equals("")) {
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_SHORT).show();
                } else if(!(pass_text.equals(con_pass_text))) {
                    Toast.makeText(getApplicationContext(),"Passwords are not same",Toast.LENGTH_SHORT).show();
                } else {
                    progressBar2.setVisibility(View.VISIBLE);
                    User newuser = new User();
                    newuser.setUsername(usertext1); newuser.setPassword(pass_text);
                    newuser.setLvl(1); newuser.setExp(0); newuser.setLl("Never");
                    UserService userService = ServiceBuilder.buildService(UserService.class);
                    final Call<User> createrequest = userService.createuser(newuser);
                    Call<ArrayList<User>> acceptrequest = userService.getuser();
                    acceptrequest.enqueue(new Callback<ArrayList<User>>() {
                        @Override
                        public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                            int num = response.body().size(); String name[] = new String[num]; String pass[] = new String[num];
                            int i; int flag = 0;
                            for (i = 0 ; i < num ; i++) {
                                name[i] = response.body().get(i).getUsername();
                                pass[i] = response.body().get(i).getPassword();
                            }

                            for (i = 0 ; i < num ; i++) {
                                if (name[i].equals(usertext1)) {
                                    flag = 1;
                                }
                            }

                            if (flag == 1) {
                                progressBar2.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignUp.this,"Username is already taken",Toast.LENGTH_SHORT).show();
                            } else {
                                createrequest.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        progressBar2.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUp.this,"Account Creation Successfull ",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp.this,Login.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        progressBar2.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SignUp.this,"SignUp failed",Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                            progressBar2.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUp.this,"Cant Connect To Server",Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {

            isAvailable = true;
        }
        return isAvailable;
    }
}
