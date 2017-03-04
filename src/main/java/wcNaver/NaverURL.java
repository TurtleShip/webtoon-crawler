package wcNaver;

import org.jsoup.Connection;

/**
 * This class contains URLs for accessing Naver webtoons.
 * The below URLs are defined. The listed below URLs should be modified
 * if Naver decides to change its URLs.
 * <p>
 * BASE_URL : The base url where all the naver comics are held.
 * <p>
 * WEBTOON : The base url where all the naver webtoons are held.
 * WEBTOON_WEEKDAY_BASE : The base url for listing all webtoons for a
 * specific day.
 */

public final class NaverURL
{
    public static final String              BASE_URL   = "http://comic.naver.com";
    public static final String              LOGIN_URL  = "https://nid.naver.com/nidlogin.login";
    public static       String              ENC_URL    = "https://nid.naver.com/login/ext/keys.nhn";
    public static       Connection.Response LOGIN_FORM = null;
}
