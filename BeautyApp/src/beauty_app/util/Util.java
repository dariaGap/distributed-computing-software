package beauty_app.util;

import java.util.Date;

public class Util {
    static final long ONE_MINUTE_IN_MILLIS=60000;

    public static Date addMinutes(Date time, int minutes) {
        return new Date(time.getTime() + (minutes * ONE_MINUTE_IN_MILLIS));
    }
}
