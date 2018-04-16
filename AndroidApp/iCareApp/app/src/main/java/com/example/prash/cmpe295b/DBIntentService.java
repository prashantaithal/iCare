package com.example.prash.cmpe295b;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DBIntentService extends IntentService {
    DynamoDBMapper dynamoDBMapper;
    private Data data;
    private String TAG = "Yahoo";
    Double count = 2.0;


    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.prash.cmpe295b.action.FOO";
    public static final String ACTION_BAZ = "com.example.prash.cmpe295b.action.BAZ";

    public static final String ACTION_MyUpdate = "com.example.androidintentservice.UPDATE";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "PARAM";
    public static final String EXTRA_PARAM2 = "com.example.prash.cmpe295b.extra.PARAM2";

    public DBIntentService() {
        super("DBIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Log.d("Yahoo", "Started4");

        Intent intent = new Intent(context, DBIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DBIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            Log.d("Yahoo", "Started1");
//
//            AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
//                @Override
//                public void onComplete(AWSStartupResult awsStartupResult) {
//
//                    //Make a network call to retrieve the identity ID
//                    // using IdentityManager. onIdentityId happens UPon success.
//                    IdentityManager.getDefaultIdentityManager().getUserID(new IdentityHandler() {
//
//                        @Override
//                        public void onIdentityId(String s) {
//
//                        }
//
//                        @Override
//                        public void handleError(Exception e) {
//                            //  Log.e("MainActivity", "Error in retrieving Identity ID: " + e.getMessage());
//                        }
//                    });
//                }
//            }).execute();
//
            // Instantiate a AmazonDynamoDBMapperClient
            AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
            dynamoDBMapper = DynamoDBMapper.builder()
                    .dynamoDBClient(dynamoDBClient)
                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                    .build();


               readTemp();

//            final String action = intent.getAction();
//            if (ACTION_FOO.equals(action)) {
//                Log.d("Yahoo", "Started0");
//
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);
//            } else if (ACTION_BAZ.equals(action)) {
//                Log.d("Yahoo", "Started9");
//
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionBaz(param1, param2);
//            }
            // processing done here….
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        Log.d(TAG, "Started5");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        Log.d(TAG, "Started6");
    }


    public void readTemp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                        count++;
                        RpiTableDO sense = dynamoDBMapper.load(RpiTableDO.class, count);
                        Log.d("Yahoocount", count.toString());


                        Double[] fields = new Double[9];
                        fields[0] = sense.getPulse();
                        fields[1] = sense.getTemp();
                        fields[2] = Double.parseDouble(sense.getAccX());
                        fields[3] = Double.parseDouble(sense.getAccY());
                        fields[4] = Double.parseDouble(sense.getAccZ());
                        fields[5] = Double.parseDouble(sense.getGyrX());
                        fields[6] = Double.parseDouble(sense.getGyrY());
                        fields[7] = Double.parseDouble(sense.getGyrZ());

                        data = new Data();
                        Log.d("YahooValue", "yahoo0");

                        data.setData(fields);
                        SensorDataSQLHelper mDbHelper = new SensorDataSQLHelper(getApplicationContext());

                        try{
                            saveDetails(mDbHelper);
                        }
                        catch (Exception e) {
                            Log.e("Exception ", "e");
                            System.out.println("Exception " + e);
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
    private void saveDetails(SensorDataSQLHelper mDbHelper) throws Exception {
        mDbHelper.insertHome(data);
        Log.d("YahooDATA","Details successfully saved");
    }
}