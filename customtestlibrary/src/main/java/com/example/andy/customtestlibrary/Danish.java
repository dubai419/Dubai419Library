package com.example.andy.customtestlibrary;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Danish extends Application {

    static Danish mContext;

    public static Danish getInstance() {
        if (mContext == null)
            mContext = new Danish();
        return mContext;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        this.mContext = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        //_dbHelper = null;
        //_global = null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    //Create Custom Functions and methods

    public static String mCustomDateFunction(String mStringDate, int mAddSubtract, int mCalendar) {
        try {
            SimpleDateFormat sdf;
            Calendar now = Calendar.getInstance();
            if (mStringDate.contains(":")) {
                sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH);
            } else {
                sdf = new SimpleDateFormat("dd-MMM-yyyy");
            }
            now.setTime(sdf.parse(mStringDate));
            now.add(mCalendar, mAddSubtract); // number of days to add
            mStringDate = sdf.format(now.getTime());

        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return mStringDate;
    }

    public static String getMonthcode() {
        String monthCode;
        int month = Calendar.getInstance().get(Calendar.MONTH);
        month = month + 1;
        if (String.valueOf(month).length() == 1) {
            monthCode = Calendar.getInstance().get(Calendar.YEAR) + "0" + month;

        } else {
            monthCode = monthCode = Calendar.getInstance().get(Calendar.YEAR) + "" + month;

        }
        return monthCode;
    }

    public static void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    public static String getTextFormattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
                case 1:
                    //return new SimpleDateFormat("d'st' 'of' MMMM yyyy").format(date);
                    return new SimpleDateFormat("d'st' MMMM yyyy").format(date);
                case 2:
                    return new SimpleDateFormat("d'nd' MMMM yyyy").format(date);
                case 3:
                    return new SimpleDateFormat("d'rd' MMMM yyyy").format(date);
                default:
                    return new SimpleDateFormat("d'th' MMMM yyyy").format(date);
            }
        return new SimpleDateFormat("d'th' 'of' MMMM yyyy").format(date);
    }

    public static String mConvertDate(String mDate, String mRequiredFormat) {

        try {
            DateFormat mFormat = null;

            if (mDate.contains(":")) {
                mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH);
            } else if (mDate.contains("/")) {
                mFormat = new SimpleDateFormat("d/M/yyyy", Locale.ENGLISH);
            } else {
                mFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            }

            Date mNewFormat = mFormat.parse(mDate);
            if (mRequiredFormat != null) {
                SimpleDateFormat serverFormat = new SimpleDateFormat(mRequiredFormat);
                return serverFormat.format(mNewFormat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String mConvertDate(Date mDate, String mRequiredFormat) {

        try {
            DateFormat mFormat = null;
            mFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

            //Date mNewFormat = mFormat.parse(mDate);
            if (mRequiredFormat != null) {
                SimpleDateFormat serverFormat = new SimpleDateFormat(mRequiredFormat);
                return serverFormat.format(mDate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String Numeric_Seperator(double Value) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(Value);
        return yourFormattedString;
    }

    public static String normalizeDecimalValues(double value) {

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(value);
    }

    public static RoundedBitmapDrawable createRoundedBitmapDrawableWithBorder(Bitmap bitmap, Context con) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int borderWidthHalf = 2; // In pixels

        int bitmapRadius = Math.min(bitmapWidth, bitmapHeight) / 2;

        int bitmapSquareWidth = Math.min(bitmapWidth, bitmapHeight);

        int newBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf;

        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth, newBitmapSquareWidth, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(roundedBitmap);

        //   canvas.drawColor(Color.GREEN);

        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;

        canvas.drawBitmap(bitmap, x, y, null);

        // Initializing a new Paint instance to draw circular border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf * 2);
        borderPaint.setColor(Color.WHITE);

        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2, newBitmapSquareWidth / 2, borderPaint);
        // Create a new RoundedBitmapDrawable
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(con.getResources(), roundedBitmap);

        roundedBitmapDrawable.setCornerRadius(bitmapRadius);

        roundedBitmapDrawable.setAntiAlias(true);
        // Return the RoundedBitmapDrawable
        return roundedBitmapDrawable;
    }

    public static String mGetCurrentCustomDateTime(String mDateFormat) {
        // " EEEE return name like this : Saturday"
        // " E return day name like this : Sat"
        // " M return month code like this : 7 --Jul"
        // " MM return month code like this : 07 --Jul"
        // " MMM return month name like this : Jul "
        // " MMMM return complete month like this : July"
        // you can return date time in custom format EEEE dd-MMMM-YYYY hh:mm:ss a
        String mCurrentDateandTime = "";
        SimpleDateFormat mSimpleDateFormat;
        try {
            mSimpleDateFormat = new SimpleDateFormat(mDateFormat);
            mCurrentDateandTime = mSimpleDateFormat.format(new Date());
        } catch (Exception ex) {
            Log.e("mGetCurrentDate", ex + "");
        }
        return mCurrentDateandTime;
    }

    public static String mGetTwoTimeDifference(String mDate1, String mDate2) {
        String mDifference = "";
        String mDay = "", mMinute = "", mHour = "", mSecond = "", mYear = "";
        SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        if (mDate1 == null || mDate2 == "") {
            return "Active";
        }
        try {
            final Date mSdfDate1 = mSdf.parse(mDate1);
            final Date mSdfDate2 = mSdf.parse(mDate2);
            long mMilliSeconds = mSdfDate2.getTime() - mSdfDate1.getTime();
            long mDiffSeconds = mMilliSeconds / 1000 % 60;
            long mDiffMinutes = mMilliSeconds / (60 * 1000) % 60;
            long mDiffHours = mMilliSeconds / (60 * 60 * 1000);
            long mDiffDays = (mMilliSeconds / (60 * 60 * 1000)) / 24;
            long mDiffYear = ((mMilliSeconds / (60 * 60 * 1000)) / 24) / 365;

            mSecond = "" + mDiffSeconds;
            mMinute = "" + mDiffMinutes;
            mHour = "" + mDiffHours;
            mDay = "" + mDiffDays;
            mYear = "" + mDiffYear;

            if (mDiffDays >= 0 && mDiffDays < 10) {
                mDay = "0" + mDiffDays;
            }

            if (mDiffHours >= 0 && mDiffHours < 10) {
                mHour = "0" + mDiffHours;
            }

            if (mDiffMinutes >= 0 && mDiffMinutes < 10) {
                mMinute = "0" + mDiffMinutes;
            }

            if (mDiffSeconds >= 0 && mDiffSeconds < 10) {
                mSecond = "0" + mDiffSeconds;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mHour = String.valueOf(Math.floorMod(Integer.parseInt(mHour), 24));
                mDay = String.valueOf(Math.floorMod(Integer.parseInt(mDay), 365));
            }

            if (Integer.parseInt(mYear) == 0) {
                if (Integer.parseInt(mDay) == 0) {
                    if (Integer.parseInt(mHour) == 0) {
                        if (Integer.parseInt(mMinute) == 0) {
                            mDifference = mSecond + " Seconds";
                        } else {
                            mDifference = mMinute + " Minutes , " + mSecond + " Seconds";
                        }
                    } else {
                        mDifference = mHour + " Hours , " + mMinute + " Minutes , " + mSecond + " Seconds";
                    }
                } else {
                    mDifference = mDay + " Days , " + mHour + " Hours , " + mMinute + " Minutes , " + mSecond + " Seconds";
                }
            } else {
                mDifference = "" + mYear + " Year , " + mDay + " Days , " + mHour + " Hours , " + mMinute + " Minutes , " + mSecond + " Seconds";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDifference;
    }

    public static boolean mIsFirstDateBigBoolean(String mFirstDate, String mLastDate) {

        Date mConvertedStartTime;
        Date mConvertedEndTime;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            mConvertedStartTime = dateFormat.parse(mFirstDate);
            mConvertedEndTime = dateFormat.parse(mLastDate);

            if (mConvertedEndTime.after(mConvertedStartTime)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {

        }
        return false;
    }

    public static String mCheckGreaterDateString(String mStartDate, String mEndDate) {

        Date mConvertedStartTime;
        Date mConvertedEndTime;
        String mDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            mConvertedStartTime = dateFormat.parse(mStartDate);
            mConvertedEndTime = dateFormat.parse(mEndDate);

            if (mConvertedEndTime.after(mConvertedStartTime)) {
                return mDate;
            } else {
                return mDate;
            }
        } catch (Exception ex) {

        }
        return mDate;
    }

    public static String mReturnBigDate(String mFirstDate, String mLastDate){
        Date mConvertedStartTime;
        Date mConvertedEndTime;
        String mDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            mConvertedStartTime = dateFormat.parse(mFirstDate);
            mConvertedEndTime = dateFormat.parse(mLastDate);

            if (mConvertedEndTime.after(mConvertedStartTime)) {
                mDate = Danish.mConvertDate(mConvertedEndTime,"yyyy-MM-dd hh:mm:ss a");
            } else {
                mDate = Danish.mConvertDate(mConvertedStartTime,"yyyy-MM-dd hh:mm:ss a");
            }

        } catch (Exception ex) {

        }
        return mDate;
    }

    public static String mReturnSmallDate(String mFirstDate, String mLastDate){
        Date mConvertedStartTime;
        Date mConvertedEndTime;
        String mDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            mConvertedStartTime = dateFormat.parse(mFirstDate);
            mConvertedEndTime = dateFormat.parse(mLastDate);

            if (mConvertedEndTime.after(mConvertedStartTime)) {
                mDate = Danish.mConvertDate(mConvertedStartTime,"yyyy-MM-dd hh:mm:ss a");
            } else {
                mDate = Danish.mConvertDate(mConvertedEndTime,"yyyy-MM-dd hh:mm:ss a");
            }

        } catch (Exception ex) {

        }
        return mDate;
    }



}
