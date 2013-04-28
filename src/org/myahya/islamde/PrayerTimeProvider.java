/**
 * 
 */
package org.myahya.islamde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.myahya.prayertimes.PrayerTimes;

/**
 * A provider of prayer times using files provided by Islam.de.
 * 
 * To obtain the prayer times file for a german city, go to:
 * http://islam.de/3455 and type the city name. You will be provided with link
 * to a text file that you can download.
 * 
 * For legal issues, I cannot distribute these files.
 * 
 * This class was created to parse files of the format shown in this file:
 * http://islam.de/sections/servicepoint/gebetszeiten/diwan2013/Aachen.txt
 * 
 * @author Mohamed Yahya (yahya.mohamed@gmail.com)
 * 
 */
public class PrayerTimeProvider {

  File prayerFile;

  public PrayerTimeProvider(File file) {
    this.prayerFile = file;
  }

  /** Locale used in file for month names, years */
  private static Locale LOCALE = Locale.US;

  /**
   * Prayer times for today.
   */
  public PrayerTimes getPrayerTimes() {
    return getPrayerTimes(new Date());
  }

  /**
   * Prayer times for a specific day.
   */
  public PrayerTimes getPrayerTimes(Date date) {
    return getPrayerTimesForDate(date);
  }

  private PrayerTimes getPrayerTimesForDate(Date date) {
    try {
      return dayLineToPrayerTimes(getDayLine(date), date);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private String getDayLine(Date date) throws IOException {
    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(date);

    // Locale used in files.
    DateFormatSymbols dfs = new DateFormatSymbols(LOCALE);

    String monthName = dfs.getMonths()[cal.get(Calendar.MONTH)];

    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

    BufferedReader br = new BufferedReader(new FileReader(prayerFile));
    String line = null;

    while ((line = br.readLine()) != null) {
      if (line.toLowerCase().contains(monthName.toLowerCase())) {
        break;
      }
    }

    // Skip 3 lines
    br.readLine();
    br.readLine();
    br.readLine();

    for (int i = 1; i < dayOfMonth && ((line = br.readLine()) != null); i++)
      ;

    br.close();

    return line;

  }

  private PrayerTimes dayLineToPrayerTimes(String dateLine, Date date) {
    List<Date> prayerTimes = new ArrayList<>(PrayerTimes.NUMBER_PRAYER_TIMES);

    String[] lineParts = dateLine.split("\t");

    System.out.println(Arrays.asList(lineParts));

    for (int i = 1; i < PrayerTimes.NUMBER_PRAYER_TIMES + 1; i++) {
      String time = lineParts[i].trim();

      Calendar cal = new GregorianCalendar();
      cal.setTime(date);
      cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
      cal.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      prayerTimes.add(cal.getTime());
    }

    return new PrayerTimes(prayerTimes);
  }

  /**
   * Example usage
   */
  public static void main(String[] args) {

    PrayerTimeProvider ptp = null;
    ptp = new PrayerTimeProvider(new File(
        "file:///home/myahya/Desktop/Saarbruecken.txt"));

    System.out.println(ptp.getPrayerTimes());

  }
}
