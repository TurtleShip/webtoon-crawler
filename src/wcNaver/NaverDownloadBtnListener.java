package wcNaver;

import wcNaver.webtoon.NaverWebtoonDownloader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

/**
 * This class provides ActionListener to download-related buttons.
 * I created this class to share NaverWebtoonDownloader downloader
 * among the buttons.
 */
public class NaverDownloadBtnListener implements ActionListener {

    private NaverWebtoonDownloader downloader;
    private NaverToonInfo info;

    private JLabel saveDirLabel;
    private JLabel wtMsgLabel;

    private JButton wtDownload;
    private JButton wtCancel;
    private JButton wtPause;

    private JProgressBar totalProg;
    private JProgressBar partialProg;


    public NaverDownloadBtnListener(
            NaverToonInfo info,
            JProgressBar totalProg,
            JProgressBar partialProg,
            JLabel saveDirLabel,
            JLabel wtMsgLabel,
            JButton wtDownload,
            JButton wtCancel,
            JButton wtPause) {
        this.info = info;
        this.totalProg = totalProg;
        this.partialProg = partialProg;
        this.saveDirLabel = saveDirLabel;
        this.wtMsgLabel = wtMsgLabel;
        this.wtDownload = wtDownload;
        this.wtCancel = wtCancel;
        this.wtPause = wtPause;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();

        switch (cmd) {
            case "download":
                // Grab a path to save folders
                if (saveDirLabel.getText() == "") {
                    wtMsgLabel.setText("저장 경로를 선택하세요!");
                    return;
                }
                downloader
                        = new NaverWebtoonDownloader(info, totalProg, partialProg);
                downloader.setSaveDir(Paths.get(saveDirLabel.getText()));

                // Adjust buttons accordingly
                wtDownload.setEnabled(false);
                wtPause.setEnabled(true);
                wtCancel.setEnabled(true);

                wtMsgLabel.setText("저장 위치: " + saveDirLabel.getText());

                // Start the download
                downloader.start();
                break;

            case "pause":
                wtDownload.setEnabled(false);
                if (downloader.isPaused()) {
                    wtPause.setText("일시정지");
                    downloader.resume();
                } else {
                    wtPause.setText("다시시작");
                    downloader.pause();
                }
                wtCancel.setEnabled(true);
                break;

            case "cancel":
                wtDownload.setEnabled(true);
                wtPause.setText("일시정지");
                wtPause.setEnabled(false);
                wtCancel.setEnabled(false);
                downloader.shutdown();
                break;

            default:
                wtDownload.setText("뭥미?");
                break;
        }
    }

}
