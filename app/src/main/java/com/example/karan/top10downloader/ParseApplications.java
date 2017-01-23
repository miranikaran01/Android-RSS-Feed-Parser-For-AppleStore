package com.example.karan.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by karan on 1/22/17.
 */

public class ParseApplications {
    private String xmlData;
    private ArrayList<Application> apps;

    public ParseApplications(String data) {
        this.xmlData = data;
        apps = new ArrayList<>();
    }

    public ArrayList<Application> getApps() {
        return apps;
    }

    public boolean process(){
        boolean status = true;
        Application currentApp = null;
        boolean inEntry = false;
        String textValue = "";
        try{
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            while(eventType != xmlPullParser.END_DOCUMENT){
                String tagName = xmlPullParser.getName();
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        if(tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentApp = new Application();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                inEntry = false;
                                apps.add(currentApp);
                            }
                            else if(tagName.equalsIgnoreCase("artist")){
                                 currentApp.setArtist(textValue);
                            }
                            else if(tagName.equalsIgnoreCase("name")){
                                 currentApp.setName(textValue);
                            }
                            else if(tagName.equalsIgnoreCase("releasedate")){
                                  currentApp.setReleaseDate(textValue);
                            }
                        }
                        break;
                    default:

                }
                eventType = xmlPullParser.next();
            }

        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        for(Application application : apps){
            Log.d("ParseApplication","***********");
            Log.d("ParseApplication ","Name: " + application.getName());
        }
        return true;
    }
}
