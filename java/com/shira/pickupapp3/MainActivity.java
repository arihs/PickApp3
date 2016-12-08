package com.shira.pickupapp3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DayFragment.OnDataPass {
//changed from extends FragmentActivity to AppCompatActivity
    DayPagerAdapter dayPagerAdapter;
    ViewPager mViewPager;
    DayFragment.OnDataPass dataPasser;

    private static final String BEN_PHONE_NUMBER = "0546808290";
    private static final String SABRINA_PHONE_NUMBER = "0587915650";
    private static final String TOVA_PHONE_NUMBER = "0548350647";
    private static final String SHIRA_PHONE_NUMBER = "0509100611";
    private static final String GABRIELLA_PHONE_NUMBER = "0542021006";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the adapter that will return a fragment for each day of the week.
        dayPagerAdapter = new DayPagerAdapter(getSupportFragmentManager(),getApplicationContext());

        //setup the View pager with the adapter
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(dayPagerAdapter);

        //set which fragment to appear upon app start, according to today's day
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("MainActivity.onCreate()- Today is " + dayOfWeek + " (Sunday is 1");
        if(dayOfWeek<6) { // sunday is day 1
            mViewPager.setCurrentItem(dayOfWeek-1);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /**
     * This function creates an intent that opens the dialler with a deesignated number. that number is the corresponding static variable to the nameClicked as supplied parameter.
     * @param nameClicked
     */
    @Override
    public void callPerson(String nameClicked) {
        String number = null;
        switch (nameClicked) {
            case "Tova":
                number = TOVA_PHONE_NUMBER;
                break;
            case "Gabriella":
                number = GABRIELLA_PHONE_NUMBER;
                break;
            case "Sabrina":
                number = SABRINA_PHONE_NUMBER;
                break;
            case "Shira":
                number = SHIRA_PHONE_NUMBER;
                break;
            case "Ben":
                number = BEN_PHONE_NUMBER;
                break;
        }
        System.out.println("MainActivity.callPerson() got name: " + nameClicked + " with number: " + number);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }


    /**
     * This function creates an intent that opens whatsapp with a designated number. that number is the corresponding static variable to the nameClicked as supplied parameter.
     * @param nameClicked
     */
    @Override
    public void whatsAppPerson(String nameClicked) {
        String number = null;
        switch (nameClicked) {
            case "Tova":
                number = TOVA_PHONE_NUMBER;
                break;
            case "Gabriella":
                number = GABRIELLA_PHONE_NUMBER;
                break;
            case "Sabrina":
                number = SABRINA_PHONE_NUMBER;
                break;
            case "Shira":
                number = SHIRA_PHONE_NUMBER;
                break;
            case "Ben":
                number = BEN_PHONE_NUMBER;
                break;
        }
        System.out.println("MainActivity.whatsAppPerson() received name:" + nameClicked + " with number " + number);
        Uri uri = Uri.parse("sms:" + number);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setData(uri);
        intent.setPackage("com.whatsapp");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Just checking, you're picking up today?");
        startActivity(Intent.createChooser(intent, ""));
    }

    /**
     * This function creates an intent opening a view with maps, within out app
     * @param lat
     * @param longitude
     */
    @Override
    public void startGoogleMapIntent(Double lat, Double longitude) {
        Intent localIntent = new Intent(getApplicationContext(), MapsActivity.class);
        localIntent.putExtra("lat", lat);
        localIntent.putExtra("lng", longitude);
        startActivity(localIntent);
        System.out.println("MainActivity.startGoogleMapIntent().... intent -> MapActivity");


        //for opening in google maps app
/*
        String geoUri = "http://maps.google.com/maps?q=loc:" + lat + "," + longitude + " (Gan Zvia)";
        Intent intentExternal = new Intent();
        intentExternal.setAction(Intent.ACTION_VIEW);
        intentExternal.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
        startActivity(intentExternal);
*/
    }

//    public void sendNotification(){
//        //Intent to happen when notification clicked
//        Intent resultIntent = new Intent(this, MainActivity.class);
//
//        //set the intent as pending
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        //resultIntent.addCategory("android.intent.category.DEFAULT");
//
//        //builder
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
//        notificationBuilder.setSmallIcon(R.drawable.ic_stat_kids);
//        notificationBuilder.setContentText("Confirm today's pickup");
//        notificationBuilder.setContentTitle("Pickup confirmation");
//        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Blah blah blah"));
//        notificationBuilder.addAction (R.drawable.ic_stat_dismiss,"Dismiss", piDismiss);
//                .addAction (R.drawable.ic_stat_snooze,
//                        getString(R.string.snooze), piSnooze);
//
//
//        //create notification object
//        notificationBuilder.setContentIntent(pendingIntent);
//
//        //set notification to close when touched
//       // notification.flags = Notification.FLAG_AUTO_CANCEL;
//
//        //issue intent:
//        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//
//        //display notification
//        notificationManager.notify(0, notificationBuilder.build());
//    }
}
