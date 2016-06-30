package com.codepath.apps.mysimpletweets;

/**
 * Created by sophiehouser on 6/29/16.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Given a date String of the format given by the Twitter API, returns a display-formatted
 * String representing the relative time difference, e.g. "2m", "6d", "23 May", "1 Jan 14"
 * depending on how great the time difference between now and the given date is.
 * This, as of 2016-06-29, matches the behavior of the official Twitter app.
 */
public class TimeFormatter {
    public static String getTimeDifference(String pDate) {
        String time = "";
        int diffInDays = 0;
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        format.setLenient(true);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar c = Calendar.getInstance();
        String formattedDate = format.format(c.getTime());

        Date d1 = null;
        Date d2 = null;
        try {

            d1 = format.parse(formattedDate);
            d2 = format.parse(pDate);
            long diff = d1.getTime() - d2.getTime();

            diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
            if (diffInDays > 0) {
                if (diffInDays == 1) {
                    time = (diffInDays + "d");
                } else if (diffInDays < 7) {
                    time = (diffInDays + "d");
                } else {
                    Calendar now = Calendar.getInstance();
                    now.setTime(d1);
                    Calendar then = Calendar.getInstance();
                    then.setTime(d2);
                    if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR)) {
                        time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                                + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
                    } else {
                        time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                                + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                                + " " + String.valueOf(then.get(Calendar.YEAR) - 2000);
                    }
                }
            } else {
                int diffHours = (int) (diff / (60 * 60 * 1000));
                if (diffHours > 0) {
                    if (diffHours == 1) {
                        time = (diffHours + "h");
                    } else {
                        time = (diffHours + "h");
                    }
                } else {
                    int diffMinutes = (int) ((diff / (60 * 1000) % 60));
                    if (diffMinutes < 1) {
                        int diffSeconds = (int) (diff / (1000));
                        time = (diffSeconds + "s");
                    } else if (diffMinutes == 1) {
                        time = (diffMinutes + "m");
                    } else {
                        time = (diffMinutes + "m");
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

}
