package wcNaver.challenge;

import wcNaver.NaverURL;

import java.util.regex.Pattern;

/**
 * Contains URL for naver challenge webtoons.
 */
public class NaverCHURL {

    public static final String CH_GENRE_BASE =
            NaverURL.BASE_URL + "/genre/challenge?m=";

    public static final String CH_LIST_BASE =
            NaverURL.BASE_URL + "/challenge/list?titleId=";

    public static final String CH_DETAIL_BASE =
            NaverURL.BASE_URL + "/challenge/detail?";

    public static final Pattern titleIdPat
            = Pattern.compile("titleId=(\\d*)");

    public static final Pattern noPat = Pattern.compile("no=(\\d*)");

    public static String getGenreListURL(final Genre genre, int pageNum) {
        return CH_GENRE_BASE + genre.getGenre() + "&page=" + pageNum;
    }

    public static String getChListURL(final String titleId) {
        return CH_LIST_BASE + titleId;
    }

    public static String getChDetailURL(final String titleId, final int num) {
        return CH_DETAIL_BASE + "titleId=" + titleId + "&no=" + num;
    }

}
