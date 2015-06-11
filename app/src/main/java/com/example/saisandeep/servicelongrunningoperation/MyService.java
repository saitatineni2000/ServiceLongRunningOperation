package com.example.saisandeep.servicelongrunningoperation;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by saisandeep on 3/27/2015.
 */
public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service is created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service is started", Toast.LENGTH_SHORT).show();

        try {
            new LongRunningOperation().execute(new URL("http://professorgustin.com/abigfile.jpg"),
                    new URL("https://www.google.com/"),
            new URL("https://www.youtube.com/watch?v=XZk8sGPJ4Nw&index=4&list=PL19C9CA2BF4E06984"));
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //the blackened is used for to show any example how anr is generated if ur doing a long running operation and u start to
        //interact with the ui

       /* try {
            int result=DownLoadFile(new URL("http://professorgustin.com/abigfile.jpg"));
            Toast.makeText(this,"Downloaded "+result+" bytes",Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

        return START_STICKY;
    }

    public class LongRunningOperation extends AsyncTask<URL,Integer,Long>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(URL... params) {
            int count=params.length;
            long totalBytesDownloaded=0;

            for(int i=0;i<count;i++)
            {
                totalBytesDownloaded+=DownLoadFile(params[i]);

                publishProgress((int)(((i+1)/(float)count)*100));
            }
            return totalBytesDownloaded;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("Downloading files ", String.valueOf(values[0])+"% downloaded");
            Toast.makeText(getBaseContext(),String.valueOf(values[0])+"% downloaded",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);

            Toast.makeText(getBaseContext(),"Downloaded total bytes",Toast.LENGTH_SHORT).show();
            stopSelf();
        }
    }

    private int DownLoadFile(URL url) {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 2000;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Service is destroyed",Toast.LENGTH_SHORT).show();
    }
}
