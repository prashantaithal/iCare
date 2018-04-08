package com.example.prash.cmpe295b;

import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.example.prash.cmpe295b.RpiTableDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    public String d = "Hello";
    Double count = 0.0;
    public List<Double> pulseList = new ArrayList<Double>();
    public List<Double> tempList = new ArrayList<Double>();
    public List<Double> accXList = new ArrayList<Double>();
    public List<Double> accYList = new ArrayList<Double>();
    public List<Double> accZList = new ArrayList<Double>();
    public List<Double> gyrXList = new ArrayList<Double>();
    public List<Double> gyrYList = new ArrayList<Double>();
    public List<Double> gyrZList = new ArrayList<Double>();
    public ArrayList<List<Double>> listOLists = new ArrayList<List<Double>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

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
                        //  Log.e("MainActivity", "Error in retrieving Identity ID: " + e.getMessage());
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

        // identityManager = IdentityManager.getDefaultIdentityManager();

//         createTemp();

//        MyTask task = new MyTask ();
//        task.execute();

        //    Log.d("News Item:", d);

        readTemp();

    }

    //    private class MyTask extends AsyncTask<Void , Void, Void > {
//
//        private Exception exception;
//
//        protected Void doInBackground(Void... urls) {
//       //     RpiTableDO sense = dynamoDBMapper.load(RpiTableDO .class, 2.0);
//            final RpiTableDO sense = new RpiTableDO();
//
//            // Item read
////            Log.d("News Item:", sense.getPulse());
//         //   d = sense.getPulse();
//              sense.setSerialNo(99.0);
//
//                sense.setPulse(5.0);
//               sense.setAccel(5.0);
//            dynamoDBMapper.save(sense);
//
//            return null;
//        }
//
//        protected void onPostExecute(Void feed) {
//
//        }
//    }
    public void readTemp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                        count++;
                        RpiTableDO sense = dynamoDBMapper.load(RpiTableDO.class, count);
                        Log.d("count", count.toString());

                        // Item read
                        //Log.d("Data Item:", sense.getGyrY());
                        gyrXList.add(Double.parseDouble(sense.getGyrX()));
                        gyrYList.add(Double.parseDouble(sense.getGyrY()));
                        gyrZList.add(Double.parseDouble(sense.getGyrZ()));

                        accXList.add(Double.parseDouble(sense.getAccX()));
                        accYList.add(Double.parseDouble(sense.getAccY()));
                        accZList.add(Double.parseDouble(sense.getAccZ()));

                        pulseList.add(sense.getPulse());
                        tempList.add(sense.getTemp());

                        listOLists.add(gyrXList);
                        listOLists.add(gyrYList);
                        listOLists.add(gyrZList);
                        listOLists.add(accXList);
                        listOLists.add(accYList);
                        listOLists.add(accZList);
                        listOLists.add(pulseList);
                        listOLists.add(tempList);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("", e.getMessage());
                        break;
                    }
                }
            }
        }).start();
    }

    public void createTemp() {
        final RpiTableDO sense = new RpiTableDO();

        sense.setSerialNo(99.0);
        sense.setPulse(5.0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(sense);
                // Item saved
            }
        }).start();
    }

}