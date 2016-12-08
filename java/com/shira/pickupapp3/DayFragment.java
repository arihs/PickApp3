package com.shira.pickupapp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DayFragment extends Fragment implements View.OnLongClickListener, ActionMode.Callback, View.OnClickListener {
    private ActionMode mActionMode;
    private OnDataPass dataPasser;
    private static final String BEN_PHONE_NUMBER = "0546808290";
    private static final String SABRINA_PHONE_NUMBER = "0587915650";
    private static final String TOVA_PHONE_NUMBER = "0548350647";
    private static final String SHIRA_PHONE_NUMBER = "0509100611";
    private static final String GABRIELLA_PHONE_NUMBER = "0542021006";

    public static final String chugIDKey = "chugImageIdkey";
    public static final String yayaSitterKey = "yayaSitterKey";
    public static final String twinsSitterKey = "twinsSitterKey";
    public static final String laviSitterKey = "laviSitterKey";
    private String todayDay = null;
    private String todayDate = null;

    private String yayaSitter = null;
    private String twinsSitter = null;
    private String laviSitter = null;

    private String sitterNameClicked = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        //accessing bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            int chugImageId = bundle.getInt(chugIDKey);
            yayaSitter = bundle.getString(yayaSitterKey);
            twinsSitter = bundle.getString(twinsSitterKey);
            laviSitter = bundle.getString(laviSitterKey);
            todayDay = bundle.getString("day");
            todayDate = bundle.getString("date");
            setValues(view, chugImageId, yayaSitter, twinsSitter, laviSitter, todayDay, todayDate);
        }

        //register listeners for onclick for map icon
        ImageView ivYayaLocation = (ImageView)view.findViewById(R.id.map_img_yaya);
        ImageView ivTwinsLocation = (ImageView)view.findViewById(R.id.map_img_twins);
        ImageView ivLaviLocation = (ImageView)view.findViewById(R.id.map_img_lavi);
        ivYayaLocation.setOnClickListener(this);
        ivTwinsLocation.setOnClickListener(this);
        ivLaviLocation.setOnClickListener(this);

        //register listeners for longClick for CAB -> to contact sitter
        TextView tvWorkerYaya = (TextView) view.findViewById(R.id.babysitter_name_yaya);
        TextView tvWorkerTwins = (TextView) view.findViewById(R.id.babysitter_name_twins);
        TextView tvWorkerLavi = (TextView) view.findViewById(R.id.babysitter_name_lavi);

        tvWorkerYaya.setOnLongClickListener(this);
        tvWorkerTwins.setOnLongClickListener(this);
        tvWorkerLavi.setOnLongClickListener(this);

        // displaying date. not sure if this should be in activity or fragment
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.GERMANY);
        String formattedDate = df.format(calendar.getTime());
        TextView dateTV = (TextView)view.findViewById(R.id.currentDate);
        if (formattedDate != null) {
            //dateTV.setText(formattedDate);
            dateTV.setText(todayDate);
        }
        return view;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        System.out.println("DayFragment.onCreateActionMode()-----------------");
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.worker_contact_menu, menu);
        return true; //true bc event is over and menu is displayed
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        System.out.println("DayFragment.onPrepareActionMode()-----------------");
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        System.out.println("DayFragment.onActionItemClicked()-----------------");
        int contactMethodChosen = item.getItemId();
        switch (contactMethodChosen) {
            case R.id.call:
                dataPasser.callPerson(sitterNameClicked);
                mode.finish();
                break;
            case R.id.whatsapp:
                dataPasser.whatsAppPerson(sitterNameClicked);
                mode.finish();
                break;
            default:
                mode.finish();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
//        mode.finish();  removed this because it was causing app to crash with memory problems
        System.out.println("DayFragment.onDestroyActionMode()-----------------");
    }

    /**
     * the long click action on the sitters name opens an action bar which offers option of calling / sending whatsApp
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        System.out.println("DayFragment.onLongClick() ---- ");
        int personClicked = v.getId();
        switch (personClicked){
            case R.id.babysitter_name_yaya:
                sitterNameClicked = yayaSitter;
                break;
            case R.id.babysitter_name_twins:
                sitterNameClicked = twinsSitter;
                break;
            case R.id.babysitter_name_lavi:
                sitterNameClicked = laviSitter;
                break;
            default:
                break;
        }
        System.out.println("DayFragment.onLongClick() sitterNameClicked = " +sitterNameClicked);
        v.startActionMode(this);
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public void onClick(View v) {

        Double longitude = null;
        Double latitude = null;

        switch (v.getId()){
            case R.id.map_img_yaya:
                System.out.println("DayFragment.onClick() - you clicked on map item for Yaya");
                latitude = 31.765218;
                longitude = 35.214817;
                break;
            case R.id.map_img_twins:
                System.out.println("DayFragment.onClick() - you clicked on map item for Twins");
                latitude = 31.765613;
                longitude = 35.217288;
                break;
            case R.id.map_img_lavi:
                System.out.println("DayFragment.onClick() - you clicked on map item for Lavi");
                latitude = 31.762596;
                longitude = 35.209141;
                break;
            default:
                break;
        }
        dataPasser.startGoogleMapIntent(latitude, longitude);
    }

    /**
     * Interface to allow communication between Activity and Fragment
     * Specifically, Fragment calls the function to be implemented by the Activity
     */
    public interface OnDataPass {
        public void callPerson(String nameClicked);
        public void whatsAppPerson(String nameClicked);
        public void startGoogleMapIntent(Double lat, Double longitude);
    }


    /**
     * This function sets the specific 'day' details which were saved to the bundle by the DayPageAdapter
     *
     * @param view
     * @param chugImageId
     * @param yayaSitter
     * @param twinsSitter
     * @param laviSitter
     * @param day
     */
    private void setValues(View view, int chugImageId, String yayaSitter, String twinsSitter, String laviSitter, String day, String todayDate) {

        ImageView chugImageView = (ImageView) view.findViewById(R.id.chug);
        chugImageView.setImageResource(chugImageId);
        TextView yayaSitterTV = (TextView) view.findViewById(R.id.babysitter_name_yaya);
        yayaSitterTV.setText(yayaSitter);

        TextView twinsSitterTV = (TextView) view.findViewById(R.id.babysitter_name_twins);
        twinsSitterTV.setText(twinsSitter);

        TextView laviSitterTV = (TextView) view.findViewById(R.id.babysitter_name_lavi);
        laviSitterTV.setText(laviSitter);

        TextView todaysDay = (TextView) view.findViewById(R.id.day);
        todaysDay.setText(day);
    }

//    @Override
//    public void onClick(View v) {
//        int viewId = v.getId();
//        switch (viewId) {
//            case R.id.phone_img_yaya:
//                call(yayaSitter);
//                break;
//            case R.id.whatsapp_img_yaya:
//                whatsApp(yayaSitter);
//                break;
//            case R.id.phone_img_twins:
//                call(twinsSitter);
//                break;
//            case R.id.whatsapp_img_twins:
//                whatsApp(twinsSitter);
//                break;
//            case R.id.phone_img_lavi:
//                call(laviSitter);
//                break;
//            case R.id.whatsapp_img_lavi:
//                whatsApp(laviSitter);
//                break;
//        }
//    }



    public void openWaze(Location location) {
        //open according to location.getLongitude();
        //open according to location.getLatiitude();
        Intent intent = new Intent();
    }


//

}