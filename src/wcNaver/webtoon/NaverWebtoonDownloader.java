package wcNaver.webtoon;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import wcNaver.NaverToonInfo;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * This class download webtoon, and
 * implements thread.
 */
public class NaverWebtoonDownloader implements Runnable {

    private boolean shutdown = false;
    private boolean pause = false;
    private NaverToonInfo info;
    private JProgressBar totalProg;
    private JProgressBar partialProg;
    private PrintWriter pw = new PrintWriter(System.out, true);
    private Thread thread;
    private Path saveDir;

    public NaverWebtoonDownloader(NaverToonInfo info,
                                  JProgressBar totalProg,
                                  JProgressBar partialProg) {
        this.info = info;
        this.totalProg = totalProg;
        this.partialProg = partialProg;
        thread = new Thread(this);
    }

    public void setSaveDir(Path saveDir) {
        this.saveDir = saveDir;
    }

    public synchronized void shutdown() {
        pw.println("Shutdown!");
        shutdown = true;
        pause = false;
        notify();
    }

    public synchronized void pause() {
        pause = true;
    }

    public synchronized void resume() {
        pause = false;
        notify();
    }

    public synchronized boolean isPaused() {
        return pause;
    }

    public synchronized void start() {
        thread.start();
    }

    @Override
    public void run() {

        String wtListURL; // The url that lists all available webtoon series
        String wtURL, imgURL, wtSeriesName;
        String wtFileName;
        Element wtList = null, wtPage, wtViewer;
        Matcher wtTotalMatcher;
        Connection.Response wtRes;
        FileOutputStream wtOut;
        int wtTotal; //The total number of available webtoon series
        int wtCount;
        double totalInc;
        double partialInc;

        Path base, wtDir = null, wtSeriesDir;

        // Figure out the number of total series
        wtListURL = NaverWebtoonURL.getWebtoonListURL(info.getTitleId());
        try {
            wtList = Jsoup.connect(wtListURL).get();
        } catch (IOException e) {
            pw.println("Unable to connect to " + wtListURL);
            e.printStackTrace();
        }

        String href = wtList.getElementById("content")
                .getElementsByClass("title").first()
                .getElementsByTag("a").first().attr("href");

        // Use Regex to pull the total number of series from the href link
        wtTotalMatcher = NaverWebtoonURL.noPat.matcher(href);
        wtTotalMatcher.find();
        wtTotal = Integer.parseInt(wtTotalMatcher.group(1));

        totalInc = (totalProg.getMaximum() - totalProg.getMinimum()) /
                (double) wtTotal;

        try {
            /*
            TODO: saveDir.resolve doesn't work and causes this method to
             terminate when this application is run by double-click
             in Mac OS. It works find in Windows. Figure out what is
             wrong when I have time.
             */
            base = saveDir.resolve("네이버 웹툰"); // get the base directory
            if (!Files.exists(base)) Files.createDirectory(base);

            wtDir = base.resolve(info.getTitleName()); // create the webtoon directory
            if (!Files.exists(wtDir)) Files.createDirectory(wtDir);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Go through each series and download them
        for (int curSeries = 1; curSeries <= wtTotal; curSeries++) {
            // display the total progress
            totalProg.setValue((int) (totalInc * (curSeries - 1)));

            wtURL = NaverWebtoonURL.getWebtoonDetailURL(info.getTitleId(), curSeries);

            try {
                wtPage = Jsoup.connect(wtURL).get();
                wtSeriesName = wtPage.getElementsByClass("tit_area").first()
                        .getElementsByClass("view").first()
                        .getElementsByTag("h3").first().ownText();
                wtSeriesDir = wtDir.resolve(wtSeriesName);


                // If the folder exist, I assume that the contents have been
                // already downloaded
                if (Files.exists(wtSeriesDir)) {
                    continue;
                }

                // Create the folder
                Files.createDirectory(wtSeriesDir);

                // download images
                wtCount = 1;
                wtViewer = wtPage.getElementsByClass("wt_viewer").first();
                partialInc =
                        (partialProg.getMaximum() - partialProg.getMinimum())
                                / (double) wtViewer.children().size();

                int ct = 0;
                // Display what is being downloaded
                partialProg.setString(wtSeriesName);
                for (Element imgLink : wtViewer.children()) {

                    // Use synchronized block to check exit condition
                    synchronized (this) {
                        while (pause) try {
                            wait();
                        } catch (InterruptedException e) {
                            pw.println("Oh!? Spurious wake-up?");
                            e.printStackTrace();
                        }

                        if (shutdown) break;
                    }

                    // display the partial progress
                    partialProg.setValue((int) (partialInc * (ct++)));

                    imgURL = imgLink.absUrl("src");

                    // Check that imgURL is actually an image file
                    if (imgURL == null || !imgURL.endsWith(".jpg"))
                        continue;

                    wtRes = Jsoup.connect(imgURL).referrer(wtURL)
                            .ignoreContentType(true).execute();
                    wtFileName = "Image_" + (wtCount++) + ".jpg";
                    wtOut = new FileOutputStream(wtSeriesDir.resolve(wtFileName).toFile());

                    // save it!
                    wtOut.write(wtRes.bodyAsBytes());
                    wtOut.close();


                }

                // Use synchronized block to check exit condition
                synchronized (this) {
                    while (pause) try {
                        wait();
                    } catch (InterruptedException e) {
                        pw.println("Oh!? Spurious wake-up?");
                        e.printStackTrace();
                    }

                    if (shutdown) break;
                    else {
                        // Show that the current download is complete
                        partialProg.setValue(partialProg.getMaximum());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        synchronized (this) {
            // If the program was not abruptly shutdown,
            // show that the download is complete.
            if (!shutdown) totalProg.setValue(totalProg.getMaximum());
        }


    }

}
