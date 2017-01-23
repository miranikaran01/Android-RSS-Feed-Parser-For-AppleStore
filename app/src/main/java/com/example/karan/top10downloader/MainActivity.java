package com.example.karan.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ListView listView;
    private String fileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.xmlListView);
        button.setOnClickListener((v) -> {
                 ParseApplications parseApplications = new ParseApplications(fileContents);
                 parseApplications.process();
                 ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(
                         MainActivity.this,R.layout.list_item,parseApplications.getApps());
                 listView.setAdapter(arrayAdapter);
        });
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DownloadData extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            fileContents = downloadXMLFile(params[0]);
            if(fileContents == null){
                Log.d("DownloadData", "Error Downloading");
            }
            return fileContents;
        }

        private String downloadXMLFile(String link){
            StringBuilder tempBuffer = new StringBuilder();
            try{
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData","The response code was " + response);
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int charRead;
                char [] inputBuffer = new char[500];
                while (true){
                    charRead = isr.read(inputBuffer);
                    if(charRead <= 0)
                        break;
                    tempBuffer.append(String.copyValueOf(inputBuffer,0,charRead));
                }
                return tempBuffer.toString();
            }
            catch (IOException e){
                Log.d("DownloadData","IOException reading data" + e.getMessage());
            }catch(SecurityException e){
                Log.d("DownloadData","Didn't ask for internet permission");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData","Result was: "+ result);
        }


    }
}
