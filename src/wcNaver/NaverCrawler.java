package wcNaver;

import javax.swing.*;


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
//        NaverWebtoonInfo[] infos = null;

//        for(Day day : Day.values())
//            infos = NaverWebtoonCrawler.downloadWebtoonList(day);
//        infos = NaverWebtoonCrawler.downloadWebtoonListByDay(Day.MON);

//        NaverWebtoonCrawler.downloadWebtoon(infos[0]);
//        NaverWebtoonInfo info = new NaverWebtoonInfo("460689", "기타맨");
//        NaverWebtoonCrawler.downloadWebtoon(info);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NaverCrawlerGUI();
            }
        });
    }

}
