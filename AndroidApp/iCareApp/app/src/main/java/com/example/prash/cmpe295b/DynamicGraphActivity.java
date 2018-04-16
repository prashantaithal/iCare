package com.example.prash.cmpe295b;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.Viewport;

public class DynamicGraphActivity extends Activity {

    public List<Double> numbers = new ArrayList<Double>();
    private ArrayList<Data> dataList = new ArrayList<Data>();
    public SensorDataSQLHelper dbHelper;
    public void numbersRandom()
    {
        for (int i = 0; i < 100; i++) {
            numbers.add(Math.random());
        }
    }

    private static final Random RANDOM = new Random();
        private LineGraphSeries<DataPoint> series;
        private int lastX = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_graph);
            // we get graph view instance
            GraphView graph = (GraphView) findViewById(R.id.graph);
            // data
            series = new LineGraphSeries<DataPoint>();
            SensorDataSQLHelper mDbHelper = new SensorDataSQLHelper(getApplicationContext());

            graph.addSeries(series);
            // customize a little bit viewport
            Viewport viewport = graph.getViewport();
            viewport.setYAxisBoundsManual(true);
            viewport.setMinY(0);
            viewport.setMaxY(10);
            viewport.setScrollable(true);

           // SensorDataSQLHelper dbHelper = new SensorDataSQLHelper(getApplicationContext());
           // dataList = dbHelper.obtainSensorDataBase();

        }

        @Override
        protected void onResume()
        {
            super.onResume();
            // we're going to simulate real time with thread that append data to the graph
            new Thread(new Runnable() {

                @Override
                public void run() {

                    while (true){
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                SensorDataSQLHelper dbHelper = new SensorDataSQLHelper(getApplicationContext());

                                dataList = dbHelper.obtainSensorDataBase();
                                Log.d("YahooSize", (Integer.toString(dataList.size())));

                                for (int i = 0; i < dataList.size(); i++) {
                                    Data home = dataList.get(i);
                                    Double d = home.getAccxSQL();
                                    Double e = home.getAccySQL();
                                    Double f = home.getAcczSQL();
                                    Double g = home.getGyrySQL();
                                    Double h = home.getTempSQL();

                                    Double k = home.getPulseSQL();

                                    Log.d("YahooD", k.toString());

                                }
                                addEntry();
                            }
                        });

                        // sleep to slow down the add of entries
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            // manage error ...
                        }
                    }
                }
            }).start();
        }

        // add random data to graph
        private void addEntry()
        {
            numbersRandom();
            // here, we choose to display max 10 points on the viewport and we scroll to end
            //series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 5);
            series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 5);
        }

}
