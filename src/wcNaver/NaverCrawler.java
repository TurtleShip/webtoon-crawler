package wcNaver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wcNaver.webtoon.Day;
import wcNaver.webtoon.NaverWebtoonCrawler;
import wcNaver.webtoon.NaverWebtoonInfo;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Visits Naver and crawls.
 *
 *
 * Compile:
 * javac -cp "../jsoup-1.7.3.jar:" wcNaver/NaverCrawler.java
 *
 * Run:
 * java -cp "../jsoup-1.7.3.jar:" wcNaver.NaverCrawler
 */
public class NaverCrawler {

    public static void main(String[] args) throws Exception {
        NaverWebtoonInfo[] infos = null;

//        for(Day day : Day.values())
//            infos = NaverWebtoonCrawler.downloadWebtoonList(day);
//        infos = NaverWebtoonCrawler.downloadWebtoonListByDay(Day.MON);

//        NaverWebtoonCrawler.downloadWebtoon(infos[0]);
        NaverWebtoonInfo info = new NaverWebtoonInfo("460689", "기타맨");
        NaverWebtoonCrawler.downloadWebtoon(info);
    }

}
