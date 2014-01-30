package wcNaver.bestChallenge;

import wcNaver.NaverURL;

/**
 * Contains url for Naver best challenge webtoons.
 */
public class NaverBCURL {

    public static final String BC_GENRE_BASE =
            NaverURL.BASE_URL + "/genre/bestChallenge.nhn?m=";

    public static final String BC_LIST_BASE =
            NaverURL.BASE_URL + "/bestChallenge/list.nhn?titleId=";

    public static final String BC_DETAIL_BASE =
            NaverURL.BASE_URL + "/bestChallenge/detail.nhn?";

    // 전체 : http://comic.naver.com/genre/bestChallenge.nhn?=
    // 장르 : http://comic.naver.com/genre/bestChallenge.nhn?m=omnibus

    // 리스트 : http://comic.naver.com/bestChallenge/list.nhn?titleId=552954

    // 만화 : http://comic.naver.com/bestChallenge/detail.nhn?titleId=552954&no=35

    public static String getBCListURL(final String titleId) {
        return BC_LIST_BASE + titleId;
    }

    public static String getBCDetailURL(final String titleId, final int num) {
        return BC_DETAIL_BASE + "titleId=" + titleId + "&no=" + num;
    }
}
