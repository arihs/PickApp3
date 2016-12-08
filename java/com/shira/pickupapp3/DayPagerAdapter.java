package com.shira.pickupapp3;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by shirag on 11/16/2016.
 */
public class DayPagerAdapter extends FragmentPagerAdapter {

    private String[] days;
    private String[] sitters;
    private String[] chugim;
    private String dayName;
    private String currentDate;

    public DayPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        System.out.println("DayPagerAdapter.Constructor(): Occurs once every time app started"); //use to get today's date/day
        Resources resources = context.getResources();
        days = resources.getStringArray(R.array.days);
        chugim = resources.getStringArray(R.array.chugim);
        sitters = resources.getStringArray(R.array.sitters);

        Calendar calendar = Calendar.getInstance();
        Locale israelLocale = new Locale("IL");
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd", israelLocale);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        String formattedDate = df.format(calendar.getTime());
        String formattedDay = dayFormat.format(calendar.getTime());
        int todaysDayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("DayPagerAdapter.Constructor()- today's date/day is: " + formattedDate + ", " + formattedDay + "(which is todaysDayIndex as " + todaysDayIndex + "---------------------!!!");
        currentDate = formattedDate;

    }

    /**
     * This function returns a Fragment object of DayFragment and a bundle with the details to display.
     * @param position
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        System.out.println("DayPagerAdapter.getItem()------------ for position " + position + " (Sunday = 0)");
//        Calendar calendar = Calendar.getInstance();
//        Locale israelLocale = new Locale("IL");
//        SimpleDateFormat df = new SimpleDateFormat("MMMM dd", israelLocale);
//        String formattedDate = df.format(calendar.getTime());

        Bundle bundle = new Bundle();
        switch(position) {
            case 0:
                bundle.putString(DayFragment.yayaSitterKey, "Ben");
                bundle.putString(DayFragment.twinsSitterKey, "Ben");
                bundle.putString(DayFragment.laviSitterKey, "Ben");
                bundle.putString("date", getDate(0));
                break;
            case 1:
                bundle.putString(DayFragment.yayaSitterKey, "Sabrina");
                bundle.putString(DayFragment.twinsSitterKey, "Sabrina");
                bundle.putString(DayFragment.laviSitterKey, "Tova");
                bundle.putInt(DayFragment.chugIDKey, getChugImageID(position));
                bundle.putString("date", getDate(1));
                break;
            case 2:
                bundle.putString(DayFragment.yayaSitterKey, "Shira");
                bundle.putString(DayFragment.twinsSitterKey, "Shira");
                bundle.putString(DayFragment.laviSitterKey, "Shira");
                bundle.putInt(DayFragment.chugIDKey, getChugImageID(position));
                bundle.putString("date", getDate(2));
                break;
            case 3:
                bundle.putString(DayFragment.yayaSitterKey, "Gabriella");
                bundle.putString(DayFragment.twinsSitterKey, "Gabriella");
                bundle.putString(DayFragment.laviSitterKey, "Tova");
                bundle.putString("date", getDate(3));
                break;
            case 4:
                bundle.putString(DayFragment.yayaSitterKey, "Sabrina");
                bundle.putString(DayFragment.twinsSitterKey, "Sabrina");
                bundle.putString(DayFragment.laviSitterKey, "Tova");
                bundle.putString("date", getDate(4));
                break;
        }

//        bundle.putString("date", formattedDate);
        bundle.putString("day", days[position]);
        DayFragment dayFragment = new DayFragment();
        dayFragment.setArguments(bundle);

        return dayFragment;

    }

    private int getChugImageID(int position){
        int id = 0;
        switch(position){
            case 0:
                break;
            case 1:
                id = R.drawable.gymnastics;
                break;
            case 2:
                id = R.drawable.swimming;
                break;
            case 3:
                break;
            case 4:
                break;
        }
        return id;
    }

    @Override
    public int getCount() {
        return days.length;
    }

    /**
     * This function check todays date and day. Accordingly, it sets Sunday's date (today's date minus today's day position (num between 0-4). THen, we can set date of other days by taking Sunday's date and adding the amount of days necessary (according to day position index)
     * @param position
     * @return String of a formatted date.
     */
    public String getDate(int position){
        Calendar calendar = Calendar.getInstance();
        Locale israelLocale = new Locale( "IL");
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd", israelLocale);

        int todaysDayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("date today: " + df.format(calendar.getTime()));
        //find date of position(0)/Sunday. then we can add on days to calendar according to position
        calendar.add(Calendar.DATE, -todaysDayIndex);
        System.out.println("sunday's date was: " + calendar.getTime());

        //then, need to add/subtract according to position provided
        calendar.add(Calendar.DATE, position);
        System.out.println("date of day in position " + position + " is " + df.format(calendar.getTime()));
        String generatedDate = df.format(calendar.getTime());
//returning date of positionDay.
        return generatedDate;
    }


    //NEVER CALLED
    @Override
    public CharSequence getPageTitle(int position) {
        System.out.println("DayPagerAdapter.getPageTitle() for position " + position);
        return days[position];
    }

}
