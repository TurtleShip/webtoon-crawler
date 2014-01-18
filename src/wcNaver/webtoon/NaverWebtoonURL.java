package wcNaver.webtoon;

import wcNaver.NaverURL;

import java.util.regex.Pattern;

/**
 * This class defines URLs needed to access Naver webtoons.
 */
public class NaverWebtoonURL {

    public static final String WEBTOON_BASE
            = String.format("%s/webtoon", NaverURL.BASE_URL);
    public static final String WEBTOON_LIST_BASE
            = String.format("%s/list.nhn?titleId=", WEBTOON_BASE);
    public static final String WEBTOON_DETAIL_BASE
            = String.format("%s/detail.nhn?", WEBTOON_BASE);

    private static final String WEBTOON_WEEKDAY_BASE
            = String.format("%s/weekdayList.nhn?week=", WEBTOON_BASE);

    /* titleIdPat is used to pull titieId from a href links.
     ex> It is used to pull titleId from
     /webtoon/list.nhn?titleId=530311&weekday=sat */
    public static final Pattern titleIdPat
            = Pattern.compile("titleId=(\\d*)");
    public static final Pattern noPat = Pattern.compile("no=(\\d*)");


    public static String getDayListURL(final Day day) {
        return String.format("%s%s", WEBTOON_WEEKDAY_BASE, day.getDay());
    }

    public static String getWebtoonListURL(final String titleId) {
        return String.format("%s%s", WEBTOON_LIST_BASE, titleId);
    }

    public static String getWebtoonDetailURL(final String titleId, final int num) {
        return String.format("%stitleId=%s&no=%d", WEBTOON_DETAIL_BASE, titleId, num);
    }

}
