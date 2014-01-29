package wcNaver;

import theCrawler.LabelValueTuple;
import wcNaver.webtoon.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;


/**
 * GUI for Naver Crawler.
 * TODO: Implement a method to return JPanel so that
 * TODO: NaverCrawlerGUI can be a part of WebtoonCrawler.
 * TODO: Since the initial beta version will only support
 * TODO: Naver webtoon, the current implementation is okay.
 */
public class NaverCrawlerGUI implements NaverConstants {

    private JPanel myFacePanel; // TODO: When I am ready, post my handsome face.
    private JPanel selectionPanel;
    private JPanel wtListPanel;
    private JScrollPane wtListScrollPane;
    private JPanel progressPanel;
    private JPanel wtPanel;

    private JLabel mainLabel;
    private JLabel subLabel;
    private JLabel progressLabel;
    private JLabel wtListLabel;
    private JLabel wtLabel;
    private JLabel saveDirMsgLabel;
    private JLabel saveDirLabel;

    private JList mainSelectorList;
    private JList subSelectorList;

    private JButton getListBtn;
    private JButton chseDirBtn;

    private JFileChooser saveDirChsr;

    private JProgressBar totalProg;
    private JProgressBar partialProg;

    public NaverCrawlerGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Failed to set cross-platform look and feel");
            e.printStackTrace();
        }

        JFrame jfrm = new JFrame("네이버 크롤러");
        jfrm.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        jfrm.setSize(625, 720);
        jfrm.setResizable(false); // Make it not resizable for now.
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Create a selection panel where users will select cartoons
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        selectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        selectionPanel.setPreferredSize(new Dimension(310, 180));

        // Create the main selector with a label
        mainLabel = new JLabel("메인 카테고리");
        mainLabel.setPreferredSize(new Dimension(150, 10));
        mainSelectorList = new JList(MAIN_CAT.toArray());
        mainSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainSelectorList.setSelectedIndex(0);
        mainSelectorList.setPreferredSize(new Dimension(150, 130));
        mainSelectorList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Create the sub selector with a label.
        subLabel = new JLabel("하위 카테고리");
        subLabel.setPreferredSize(new Dimension(150, 10));
        subSelectorList = new JList(WEBTOON_CAT.toArray());
        subSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subSelectorList.setSelectedIndex(0);
        subSelectorList.setPreferredSize(new Dimension(150, 130));
        subSelectorList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Add a button that user can click to download stuff.
        getListBtn = new JButton("웹툰 목록 가져오기~");
        getListBtn.setPreferredSize(new Dimension(150, 20));

        // add them all to the selectionPanel panel.
        selectionPanel.add(mainLabel);
        selectionPanel.add(subLabel);
        selectionPanel.add(mainSelectorList);
        selectionPanel.add(subSelectorList);
        selectionPanel.add(getListBtn);


        // Create a progress panel.
        progressPanel = new JPanel();
        progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        progressPanel.setPreferredSize(new Dimension(310, 180));
        progressPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create components for the progress panel.
        progressLabel = new JLabel("나는야 프로그래스 레이블");

        saveDirChsr = new JFileChooser();
        saveDirChsr.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        saveDirMsgLabel = new JLabel("저장 경로를 선택하세요~!");
        saveDirLabel = new JLabel("");

        chseDirBtn = new JButton("저장 경로 선택");
        chseDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (saveDirChsr.showOpenDialog(progressPanel)
                        == JFileChooser.APPROVE_OPTION) {
                    saveDirMsgLabel.setText("저장경로 : ");
                    saveDirLabel.setText(
                            saveDirChsr.getSelectedFile().getAbsolutePath());
                }
            }
        });

        progressPanel.add(progressLabel);
        progressPanel.add(chseDirBtn);
        progressPanel.add(saveDirMsgLabel);
        progressPanel.add(saveDirLabel);


        // Create a webtoon list panel.
        wtListPanel = new JPanel();
        wtListPanel.setLayout(new BoxLayout(wtListPanel, BoxLayout.Y_AXIS));

        wtListPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        wtListScrollPane = new JScrollPane();
        wtListScrollPane.setPreferredSize(new Dimension(620, 500));
        wtListScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        // Create components for the webtoon list panel.
        wtListLabel = new JLabel("엡툰 목록은 여기에 다운로드~!");
        wtListPanel.add(wtListLabel);
        wtListScrollPane.getViewport().setView(wtListPanel);

        // Add the selectionPanel to the main frame.
        jfrm.add(selectionPanel);
        jfrm.add(progressPanel);
        jfrm.add(wtListScrollPane);

        // Set up buttons
        setupGetListBtn();

        jfrm.setVisible(true);
    }


    public void setupGetListBtn() {
        getListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Find out what the user selected
                String mainCat =
                        ((LabelValueTuple<String>) mainSelectorList.getSelectedValue())
                                .getValue();

                wcNaver.webtoon.Day subCat =
                        ((LabelValueTuple<wcNaver.webtoon.Day>) subSelectorList.getSelectedValue())
                                .getValue();
                wtListLabel.setText("Current selection : " + mainCat + " - "
                        + subCat);

                // Clear list panel
                wtListPanel.removeAll();

                switch (mainCat) {
                    case "webtoon":
                        getWebtoonList(subCat);
                        break;

                    case "best":
                        wtListLabel.setText("베스트 도전 아직 안되요.");
                        break;

                    case "challenge":
                        wtListLabel.setText("도전 만화 아직 안되요.");
                        break;

                    default:
                        wtListLabel.setText("... 헐?");
                }
            }
        });
    }

    public void getWebtoonList(wcNaver.webtoon.Day day) {
        NaverWebtoonInfo[] infos
                = NaverWebtoonCrawler.downloadWebtoonListByDay(day);


        // Now display the information
        for (final NaverWebtoonInfo info : infos) {
            // A panel to hold a webtoon info
            JPanel curWtPanel = new JPanel();
            curWtPanel.setLayout(new FlowLayout());
            curWtPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            // Create a thumbnail with its title
            JLabel curLabel = new JLabel(info.getTitleName(), info.getThumb(),
                    SwingConstants.LEFT);
            curLabel.setVerticalTextPosition(SwingConstants.TOP);
            curLabel.setHorizontalTextPosition(SwingConstants.CENTER);


            // Create a button to download and a button to cancel
            final JButton wtDownload = new JButton("웹툰 다운로드");
            final JButton wtCancel = new JButton("다운로드 취소");
            final JLabel wtMsgLabel = new JLabel();

            // Create a progress bar for the total progress
            // and another bar for the partial progress
            totalProg = new JProgressBar();
            partialProg = new JProgressBar();

            // Display the percentage string.
            totalProg.setStringPainted(true);
            partialProg.setStringPainted(true);

            final NaverWebtoonDownloader downloader
                    = new NaverWebtoonDownloader(info, totalProg, partialProg);

            wtCancel.setEnabled(false);

            // Add functionality to the buttons
            wtDownload.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    // Grab a path to save folders
                    if (saveDirLabel.getText() == "") {
                        wtMsgLabel.setText("저장 경로를 선택하세요!");
                        return;
                    }

                    downloader.setSaveDir(Paths.get(saveDirLabel.getText()));

                    // Adjust buttons accordingly
                    wtDownload.setEnabled(false);
                    wtCancel.setEnabled(true);

                    wtMsgLabel.setText("저장 위치: " + saveDirLabel.getText());

                    // Start the download
                    downloader.start();
                }
            });

            wtCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    wtDownload.setEnabled(true);
                    wtCancel.setEnabled(false);
                    downloader.shutdown();
                }
            });


            curWtPanel.add(curLabel);
            curWtPanel.add(wtDownload);
            curWtPanel.add(wtCancel);
            curWtPanel.add(wtMsgLabel);
            curWtPanel.add(totalProg);
            curWtPanel.add(partialProg);

            wtListPanel.add(curWtPanel);
        }

        wtListPanel.revalidate();
        wtListPanel.repaint();
    }

    public void getBestChallengeList() {

    }

    public void getChallengeList() {

    }


}
