package wcNaver;

import javax.swing.*;


/**
 * Visits Naver and crawls.
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
