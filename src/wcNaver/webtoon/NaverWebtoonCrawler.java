package wcNaver.webtoon;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class provides utility methods to crawl naver webtoon
 * TODO: Replace all pw.format() messages with logging
 */
public class NaverWebtoonCrawler {

    private static PrintWriter pw = new PrintWriter(System.out, true);


    /**
     * Download available webtoon information for given day
     *
     * @param day the day you want to download webtoon list from
     * @return Available webtoon information for the given day
     */
    public static NaverWebtoonInfo[] downloadWebtoonListByDay(Day day) {
        // Create necessary references
        NaverWebtoonInfo[] info;
        String href, titleId, url = "";
        Document doc;
        Element content, img, link;
        Elements imgList;
        int webtoonTotal; // total number of webtoons for this day
        Matcher mat;

        // Try connect to url.
        try {
            url = NaverWebtoonURL.getDayListURL(day);
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            pw.format("Unable to connect to %s\n", url);
            e.printStackTrace();
            return null;
        }

        // Grab available webtoon lists
        content = doc.getElementById("content");
        imgList = content.getElementsByClass("img_list").first().children();
        webtoonTotal = imgList.size();

        pw.format("Total of %d cartoons\n", webtoonTotal);
        info = new NaverWebtoonInfo[webtoonTotal];


        for (int i = 0; i < webtoonTotal; i++) {
            img = imgList.get(i);
            link = img.getElementsByClass("thumb").first()
                    .getElementsByTag("a").first();
            href = link.attr("href");

            // Use Regex to pull title id from the href link
            mat = NaverWebtoonURL.titleIdPat.matcher(href);
            mat.find();
            info[i] = new NaverWebtoonInfo(mat.group(1), link.attr("title"));

            // TODO: Replace below with log
            pw.format("link = %s\n", href);
            pw.format("title = %s\n", link.attr("title"));
            pw.format("titleId = %s\n", mat.group(1));
            pw.format("\n");
        }
        pw.format("\n");
        return info;
    }

    /**
     * Download all images for this webtoon.
     *
     * @param info Information about the webtoon you want to download.
     * @return true if the download was successful. False otherwise.
     */
    public static boolean downloadWebtoon(NaverWebtoonInfo info) {


        String wtListURL; // The url that list all available webtoon series
        String wtURL, imgURL, wtSeriesName, wtSeriesFolderName, wtImgName;
        String wtFileName;
        Element wtList, wtPage, wtViewer;
        Matcher wtTotalMatcher;
        Response wtRes;
        FileOutputStream wtOut;
        int wtTotal; //The total number of available webtoon series
        int wtCount;

        Path base, wtDir = null, wtSeriesDir;

        // Figure out the number of total series
        wtListURL = NaverWebtoonURL.getWebtoonListURL(info.getTitleId());
        try {
            wtList = Jsoup.connect(wtListURL).get();
        } catch (IOException e) {
            pw.format("Unable to connect to %s\n", wtListURL);
            e.printStackTrace();
            return false;
        }

        String href = wtList.getElementById("content")
                .getElementsByClass("title").first()
                .getElementsByTag("a").first().attr("href");

        // Use Regex to pull the total number of series from the href link
        wtTotalMatcher = NaverWebtoonURL.noPat.matcher(href);
        wtTotalMatcher.find();
        wtTotal = Integer.parseInt(wtTotalMatcher.group(1));

        try {
            base = Paths.get("").resolve("네이버 웹툰"); // get the base directory
            if (!Files.exists(base)) Files.createDirectory(base);

            wtDir = base.resolve(info.getTitleName()); // create the webtoon directory
            if (!Files.exists(wtDir)) Files.createDirectory(wtDir);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Go through each series and download them
        for (int curSeries = 1; curSeries <= wtTotal; curSeries++) {
            wtURL = NaverWebtoonURL.getWebtoonDetailURL(info.getTitleId(), curSeries);

            try {
                wtPage = Jsoup.connect(wtURL).get();
                wtSeriesName = wtPage.getElementsByClass("tit_area").first()
                        .getElementsByClass("view").first()
                        .getElementsByTag("h3").first().ownText();
                pw.format("Title : %s\n", wtSeriesName);
//                wtSeriesFolderName = String.format("%d_%s", curSeries, wtSeriesName);
                wtSeriesFolderName = wtSeriesName;
                wtSeriesDir = wtDir.resolve(wtSeriesFolderName);

                // If the folder exist, I assume that the contents have been
                // already downloaded
                if (Files.exists(wtSeriesDir)) continue;

                // Create the folder
                Files.createDirectory(wtSeriesDir);

                // download images
                wtCount = 1;
                wtViewer = wtPage.getElementsByClass("wt_viewer").first();
                for (Element imgLink : wtViewer.children()) {
                    imgURL = imgLink.absUrl("src");

                    // Check that imgURL is actually an image file
                    if (imgURL == null || !imgURL.endsWith(".jpg")) continue;

                    pw.format("downloading %s\n", imgURL);
                    wtRes = Jsoup.connect(imgURL).referrer(wtURL)
                            .ignoreContentType(true).execute();
                    wtFileName = String.format("Image_%d.jpg", wtCount++);
                    wtOut = new FileOutputStream(wtSeriesDir.resolve(wtFileName).toFile());

                    // save it!
                    wtOut.write(wtRes.bodyAsBytes());
                    wtOut.close();

                }

                // download all images within images
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return true;
    }


}
