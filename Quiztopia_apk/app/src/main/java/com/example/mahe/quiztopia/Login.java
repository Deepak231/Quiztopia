package com.example.mahe.quiztopia;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.quiztopia.models.User;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.UserService;

import java.sql.SQLClientInfoException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class Login extends Activity {

    int lvl,exp; String ll; public static String ll_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar1.setVisibility(View.INVISIBLE);
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(Color.rgb(1, 57, 134));

        if(!isNetworkAvailable()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
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

        TextView text4 = (TextView) findViewById(R.id.text_link1);
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Login.this,SignUp.class);
                startActivity(intent1);
            }
        });

        final EditText editText1 = (EditText) findViewById(R.id.user_text1);
        final EditText editText2 = (EditText) findViewById(R.id.pass_text1);

        Button button1 = (Button) findViewById(R.id.login);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_text = editText1.getText().toString();
                final String pass_text = editText2.getText().toString();
                if(user_text.equals("") || pass_text.equals("")) {
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_SHORT).show();
                } else {
                    progressBar1.setVisibility(View.VISIBLE);

                    //using retrofit , performing GET operation from the server
                    UserService userService = ServiceBuilder.buildService(UserService.class);
                    Call<ArrayList<User>> call = userService.getuser();

                    call.enqueue(new Callback<ArrayList<User>>() {
                        @Override
                        public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                            int num = response.body().size(); String name[] = new String[num]; String pass[] = new String[num];
                            int i; int flag = 0;
                            for (i = 0 ; i < num ; i++) {
                                name[i] = response.body().get(i).getUsername();
                                pass[i] = response.body().get(i).getPassword();

                            }

                            for (i = 0 ; i < num ; i++) {
                                if (name[i].equals(user_text) && pass[i].equals(pass_text)) {
                                    flag = 1;
                                    lvl = response.body().get(i).getLvl();
                                    exp = response.body().get(i).getExp();
                                    ll = response.body().get(i).getLl();
                                }
                            }

                            if (flag == 1) {

                                progressBar1.setVisibility(View.INVISIBLE);
                                Toast.makeText(Login.this,"Last Active : "+ll,Toast.LENGTH_SHORT).show();
                                ll_1 = ll;
                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                String str = df.format(c);
                                ll = "";
                                ll = ll + str + " ";
                                SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss", Locale.US);
                                str = df1.format(c);
                                ll = ll + str;

                                Intent intent = new Intent(Login.this,MainPage.class);
                                intent.putExtra("username",user_text);
                                intent.putExtra("lvl",lvl);
                                intent.putExtra("exp",exp);
                                intent.putExtra("ll",ll);
                                startActivity(intent);
                                finish();
                            } else {
                                progressBar1.setVisibility(View.INVISIBLE);
                                Toast.makeText(Login.this,"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                            progressBar1.setVisibility(View.INVISIBLE);
                            Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isNetworkAvailable()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
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
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {

            isAvailable = true;
        }
        return isAvailable;
    }
}
