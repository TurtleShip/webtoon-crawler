package wcNaver.bestChallenge;

import wcNaver.NaverURL;
import wcNaver.challenge.Genre;

import java.util.regex.Pattern;

/**
 * Contains url for Naver best challenge webtoons.
 */
public class NaverBCURL {

    public static final String BC_GENRE_BASE =
            NaverURL.BASE_URL + "/genre/bestChallenge?m=";

    public static final String BC_LIST_BASE =
            NaverURL.BASE_URL + "/bestChallenge/list?titleId=";

    public static final String BC_DETAIL_BASE =
            NaverURL.BASE_URL + "/bestChallenge/detail?";

    public static final Pattern titleIdPat
            = Pattern.compile("titleId=(\\d*)");

    public static final Pattern noPat = Pattern.compile("no=(\\d*)");

    public static String getGenreListURL(final Genre genre, int pageNum) {
        return BC_GENRE_BASE + genre.getGenre() + "&page=" + pageNum;
    }

    public static String getBCListURL(final String titleId) {
        return BC_LIST_BASE + titleId;
    }

    public static String getBCDetailURL(final String titleId, final int num) {
        return BC_DETAIL_BASE + "titleId=" + titleId + "&no=" + num;
    }
}
