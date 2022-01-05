package wcNaver.webtoon;

import wcNaver.NaverURL;

import java.util.regex.Pattern;

/**
 * This class defines URLs needed to access Naver webtoons.
 */
public class NaverWebtoonURL {

    public static final String WEBTOON_BASE = NaverURL.BASE_URL + "/webtoon";

    public static final String WEBTOON_LIST_BASE
            = WEBTOON_BASE + "/list?titleId=";

    public static final String WEBTOON_DETAIL_BASE
            = WEBTOON_BASE + "/detail?";

    private static final String WEBTOON_WEEKDAY_BASE
            = WEBTOON_BASE + "/weekdayList?week=";

    /* titleIdPat is used to pull titieId from a href links.
     ex> It is used to pull titleId from
     /webtoon/list?titleId=530311&weekday=sat */
    public static final Pattern titleIdPat
            = Pattern.compile("titleId=(\\d*)");
    public static final Pattern noPat = Pattern.compile("no=(\\d*)");


    public static String getDayListURL(final Day day) {
        if(day == Day.ALL) return WEBTOON_BASE + "/finish";
        return WEBTOON_WEEKDAY_BASE + day.getDay();
    }

    public static String getWebtoonListURL(final String titleId) {
        return WEBTOON_LIST_BASE + titleId;
    }

    public static String getWebtoonDetailURL(final String titleId, final int num) {
        return WEBTOON_DETAIL_BASE + "titleId=" + titleId + "&no=" + num;
    }
}
