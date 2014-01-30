package wcNaver.bestChallenge;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import wcNaver.NaverToonInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;

/**
 * This class provides utility methods to crawl naver best challenges
 */
public class NaverBCCrawler {

    private static PrintWriter pw = new PrintWriter(System.out, true);

    public static NaverToonInfo[] downloadBCListByGenre(Genre genre, int pageNum) {

        // Create necessary references
        NaverToonInfo[] info;
        String href, url = "", thumbURL;
        Document doc;
        Element listBox, img, imgSrc, link;
        Elements imgList;
        int BCTotal; // total number of Best Challenges for this genre and page
        Matcher mat;

        // Try connect to url.
        try {
            url = NaverBCURL.getGenreListURL(genre, pageNum);
            doc = Jsoup.connect(url).get();
        } catch (IOException ioe) {
            pw.println("Unable to connect to " + url);
            ioe.printStackTrace();
            return null;
        }

        // Grab available BC lists
        listBox = doc.getElementById("content")
                .getElementsByClass("weekchallengeBox").first();

        // Each element in imgList contains a thumbnail and a link to the BC
        imgList = listBox.getElementsByClass("fl");

        // Each element in titleList contains a title and a link to the BC
//        titleList = listBox.getElementsByClass("challengeTitle");

        // Initialize NaverToonInfo
        BCTotal = imgList.size();
        info = new NaverToonInfo[BCTotal];

        pw.println("Getting total of " + BCTotal + " best challenges");

        for (int i = 0; i < BCTotal; i++) {
            img = imgList.get(i);
            link = img.getElementsByTag("a").first();
            href = link.attr("href");

            thumbURL = link.child(0).absUrl("src");

            // Use Regex to pull title id from the href link
            mat = NaverBCURL.titleIdPat.matcher(href);
            mat.find();

            try {
                info[i] = new NaverToonInfo(mat.group(1),
                        link.child(0).attr("title"),
                        new ImageIcon(new URL(thumbURL)));
            } catch( MalformedURLException me ) {
                me.printStackTrace();
            }
        }

        return info;
    }
}
