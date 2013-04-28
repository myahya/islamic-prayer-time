package org.myahya.prayertimes;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.myahya.prayertimes.PrayerTimes.Prayer;

import com.google.common.base.Preconditions;

/**
 * Prayer times for a single day.
 * 
 * Immutable
 * 
 * @author Mohamed Yahya (yahya.mohamed@gmail.com)
 * 
 */
public final class PrayerTimes extends TreeMap<Prayer, Date> {

  private static final long serialVersionUID = -6095922834206168980L;

  public enum Prayer {
    FAJR, SUNRISE, DUHUR, ASR, SUNSET, ISHA
  };

  public static int NUMBER_PRAYER_TIMES = Prayer.values().length;

  /**
   * Constructor
   * 
   * @param prayerTimes
   *          a list of prayer times, in order given in {@link Prayer}
   */
  public PrayerTimes(List<Date> prayerTimes) {
    Preconditions.checkArgument(prayerTimes.size() == NUMBER_PRAYER_TIMES,
        "You should supply times for all prayers.");

    for (int i = 0; i < NUMBER_PRAYER_TIMES; i++) {
      this.put(Prayer.values()[i], prayerTimes.get(i));
    }
  }

  /**
   * Constructor
   * 
   * @param prayerTimes
   *          A map giving the prayer times for the day. All times should be
   *          given.
   */
  public PrayerTimes(Map<Prayer, Date> prayerTimes) {
    Preconditions.checkArgument(prayerTimes.size() == NUMBER_PRAYER_TIMES,
        "You should supply times for all prayers.");
    this.putAll(prayerTimes);
  }

}
