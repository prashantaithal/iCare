package com.example.prash.cmpe295b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener  {
    DynamoDBMapper dynamoDBMapper;
    private ArrayList<Data> dataList = new ArrayList<Data>();

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonOne = (Button) findViewById(R.id.button2);
        buttonOne.setOnClickListener(this);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {

                //Make a network call to retrieve the identity ID
                // using IdentityManager. onIdentityId happens UPon success.
                IdentityManager.getDefaultIdentityManager().getUserID(new IdentityHandler() {
                    @Override
                    public void onIdentityId(String s) {

                    }

                    @Override
                    public void handleError(Exception e) {
                        Log.e("MainActivity", "Error in retrieving Identity ID: " + e.getMessage());
                    }
                });
            }
        }).execute();

        // Instantiate a AmazonDynamoDBMapperClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        String strInputMsg = "com.example.prash.cmpe295b.action.FOO";
        Intent msgIntent = new Intent(this, DBIntentService.class);
        msgIntent.putExtra(DBIntentService.ACTION_FOO, strInputMsg);
//       startService(msgIntent);





        // readPulse();


    }

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {
            int j = 0;

            @Override
            public void run() {

                while (true) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                            SensorDataSQLHelper dbHelper = new SensorDataSQLHelper(getApplicationContext());

                            dataList = dbHelper.obtainSensorDataBase();
//                            Log.d("PULSE", Integer.toString(dataList.size()));

                            Data home = dataList.get(j);
                            j++;
                            Double k = home.getPulseSQL();
                            // Thread.sleep(600);

                            mTextMessage.setText(k.toString());
                            Log.d("YahooD", k.toString());

                            if (j == dataList.size()) {
                                j = 0;
                            }
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    public void readPulse() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                        SensorDataSQLHelper dbHelper = new SensorDataSQLHelper(getApplicationContext());

                        dataList = dbHelper.obtainSensorDataBase();
                        Log.d("PULSE", Integer.toString(dataList.size()));

                        for (int j = 0; j < dataList.size(); j++) {
                            Data home = dataList.get(j);

                            Double k = home.getPulseSQL();
                            Thread.sleep(600);

                            mTextMessage.setText(k.toString());
                            Log.d("YahooD", k.toString());

                            if (j == dataList.size()) {
                                j = 0;
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("", e.getMessage());
                        break;
                    }

                }
            }
        }).start();
    }

    public void updateTextView(String toThis) {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(toThis);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.button2: {
                Intent i = new Intent(
                        MainActivity.this,
                        DynamicGraphActivity.class);

                     startActivity(i);
                break;
            }
        }
    }
}

