package com.example.prash.cmpe295b;

import android.os.AsyncTask;
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

public class DatabaseActivity extends AppCompatActivity {


    private IdentityManager identityManager;
    DynamoDBMapper dynamoDBMapper;
    public String d ;
    Integer count;

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

                        //The network call to fetch AWS credentials succeeded, the cached
                        // user ID is available from IdentityManager throughout your app
                        Log.d("MainActivity", "Identity ID is: " + s);
                        Log.d("MainActivity", "Cached Identity ID: " + IdentityManager.getDefaultIdentityManager().getCachedUserID());
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

        identityManager = IdentityManager.getDefaultIdentityManager();

         createTemp();

//        MyTask task = new MyTask ();
//        task.execute();

        //    Log.d("News Item:", d);

        readTemp();

    }
    private class MyTask extends AsyncTask<Void , Void, Void > {

        private Exception exception;

        protected Void doInBackground(Void... urls) {
       //     RpiTableDO sense = dynamoDBMapper.load(RpiTableDO .class, 2.0);
            final RpiTableDO sense = new RpiTableDO();

            // Item read
//            Log.d("News Item:", sense.getPulse());
         //   d = sense.getPulse();
              sense.setSerialNo(99.0);

                sense.setPulse(5.0);
               sense.setAccel(5.0);
            dynamoDBMapper.save(sense);

            return null;
        }

        protected void onPostExecute(Void feed) {

        }
    }
    public void readTemp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
             //   count++;
                RpiTableDO sense = dynamoDBMapper.load(RpiTableDO .class, 7.0);
                //    dynamoDBMapper.marshallIntoObject()
                // Item read
                Log.d("News Item:", sense.getPulse().toString());
            }
        }).start();
    }

    public void createTemp() {
        final RpiTableDO sense = new RpiTableDO();

        sense.setSerialNo(99.0);
        sense.setPulse(5.0);
        sense.setAccel(5.0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(sense);
                // Item saved
            }
        }).start();
    }

}