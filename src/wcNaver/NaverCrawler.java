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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NaverCrawlerGUI();
            }
        });
    }

}
