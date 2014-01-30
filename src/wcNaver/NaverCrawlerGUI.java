package wcNaver;

import theCrawler.LabelValueTuple;
import wcNaver.bestChallenge.Genre;
import wcNaver.bestChallenge.NaverBCCrawler;
import wcNaver.webtoon.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * GUI for Naver Crawler.
 * TODO: Implement a method to return JPanel so that
 * TODO: NaverCrawlerGUI can be a part of WebtoonCrawler.
 * TODO: Since the initial beta version will only support
 * TODO: Naver webtoon, the current implementation is okay.
 */
public class NaverCrawlerGUI implements NaverConstants {

    private JPanel selectionPanel;
    private JPanel wtListPanel;
    private JPanel myProfilePanel;
    private JPanel saveDirPanel;

    private JScrollPane wtListScrollPane;
    private JScrollPane selSubScrollPane;
    private JScrollPane savePathScrollPane;

    private JLabel mainLabel;
    private JLabel subLabel;
    private JLabel wtListLabel;
    private JLabel saveDirMsgLabel;
    private JLabel saveDirLabel;
    private JLabel tmpMsgLabel;

    private JList mainSelectorList;
    private JList subSelectorList;

    private JButton getListBtn;
    private JButton chseDirBtn;

    private JFileChooser saveDirChsr;

    private JProgressBar tmpProgBar;


    public NaverCrawlerGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Failed to set cross-platform look and feel");
            e.printStackTrace();
        }

        JFrame jfrm = new JFrame("네이버 크롤러");
        jfrm.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        jfrm.setSize(650, 760);
        jfrm.setResizable(false); // Make it not resizable for now.
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupSelectionPane();
        setupProgressPanel();
        setupWtListScrollPane();
        setupGetListBtn(); // Set up "웹툰 목록 가져오기~" button

        // Add the selectionPanel to the main frame.
        jfrm.add(selectionPanel);
        jfrm.add(myProfilePanel);
        jfrm.add(wtListScrollPane);

        jfrm.setVisible(true);
    }

    private void setupSelectionPane() {
        // Create a selection panel where users will select cartoons
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        selectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        selectionPanel.setPreferredSize(new Dimension(310, 220));

        // Create the main selector with a label
        mainLabel = new JLabel("메인 카테고리");
        mainLabel.setPreferredSize(new Dimension(150, 10));
        mainSelectorList = new JList(MAIN_CAT.toArray());
        mainSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainSelectorList.setSelectedIndex(0);
        mainSelectorList.setPreferredSize(new Dimension(150, 130));
        mainSelectorList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Create the sub selector with a scroll pane.
        selSubScrollPane = new JScrollPane();
        selSubScrollPane.setPreferredSize(new Dimension(150, 130));
        selSubScrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Create the sub selector with a label.
        subLabel = new JLabel("하위 카테고리");
        subLabel.setPreferredSize(new Dimension(150, 10));
        subSelectorList = new JList(WEBTOON_CAT.toArray());
        subSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subSelectorList.setSelectedIndex(0);
        selSubScrollPane.getViewport().setView(subSelectorList);

        // Switch sub selections based on the main selection
        mainSelectorList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent le) {
                switch (((LabelValueTuple<String>) mainSelectorList.getSelectedValue()
                ).getValue()) {
                    case "webtoon":
                        subSelectorList.setListData(WEBTOON_CAT.toArray());
                        break;

                    case "best":
                        subSelectorList.setListData(BEST_CAT.toArray());
                        break;

                    case "challenge":
                        subSelectorList.setListData(CHALLENGE_CAT);
                        break;
                }
            }
        });


        // Add a button that user can click to download stuff.
        getListBtn = new JButton("웹툰 목록 가져오기~");
        getListBtn.setPreferredSize(new Dimension(150, 20));

        saveDirChsr = new JFileChooser();
        saveDirChsr.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        saveDirMsgLabel = new JLabel("저장 경로를 선택하세요~!");
        saveDirLabel = new JLabel("");

        saveDirPanel = new JPanel();
        saveDirPanel.add(saveDirMsgLabel);
        saveDirPanel.add(saveDirLabel);

        savePathScrollPane = new JScrollPane();
        savePathScrollPane.setPreferredSize(new Dimension(300, 40));
        savePathScrollPane.getViewport().setView(saveDirPanel);

        chseDirBtn = new JButton("저장 경로 선택");
        chseDirBtn.setPreferredSize(new Dimension(120, 20));
        chseDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (saveDirChsr.showOpenDialog(selectionPanel)
                        == JFileChooser.APPROVE_OPTION) {
                    saveDirMsgLabel.setText("저장경로 : ");
                    saveDirLabel.setText(
                            saveDirChsr.getSelectedFile().getAbsolutePath());
                }
            }
        });

        // add them all to the selectionPanel panel.
        selectionPanel.add(mainLabel);
        selectionPanel.add(subLabel);
        selectionPanel.add(mainSelectorList);
        selectionPanel.add(selSubScrollPane);
        selectionPanel.add(chseDirBtn);
        selectionPanel.add(getListBtn);
        selectionPanel.add(savePathScrollPane);
    }

    private void setupProgressPanel() {
        // Create a progress panel.
        myProfilePanel = new JPanel();
        myProfilePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        myProfilePanel.setPreferredSize(new Dimension(310, 220));
        myProfilePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void setupWtListScrollPane() {
        // Create a webtoon list panel.
        wtListPanel = new JPanel();
        wtListPanel.setLayout(new BoxLayout(wtListPanel, BoxLayout.Y_AXIS));

        wtListScrollPane = new JScrollPane();
        wtListScrollPane.setPreferredSize(new Dimension(622, 500));
        wtListScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create components for the webtoon list panel.
        String howto = "<html><<center>사용 방법</center><br>" +
                "1. 메인 카테고리, 하위 케티고리를 선택한다.<br><br>" +
                "2. '저장 경로를 선택' 버튼을 누르고 저장 경로를 선택한다.<br><br>" +
                "3. '웹툰 목록 가져오기~' 버튼을 클릭한다.<br><br>" +
                "4. 보고 싶은 웹툰에 해당하는 '웹툰 다운로드' 버튼을 누른다.<br><br>";
        wtListLabel = new JLabel(howto);
        wtListPanel.add(wtListLabel);
        wtListScrollPane.getViewport().setView(wtListPanel);
    }

    private void setupGetListBtn() {
        tmpMsgLabel = new JLabel("목록을 가져오는 중입니다...");
        tmpProgBar = new JProgressBar();
        tmpProgBar.setIndeterminate(true);

        getListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Find out what the user selected
                String mainCat =
                        ((LabelValueTuple<String>) mainSelectorList.getSelectedValue())
                                .getValue();

                // Clear list panel
                wtListPanel.removeAll();

                // Show temporary message while downloading list
                getListBtn.setEnabled(false);
                wtListPanel.add(tmpMsgLabel);
                wtListPanel.add(tmpProgBar);
                wtListPanel.revalidate();
                wtListPanel.repaint();

                switch (mainCat) {
                    case "webtoon":
                        final Day day =
                                ((LabelValueTuple<Day>) subSelectorList.getSelectedValue())
                                        .getValue();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getWebtoonList(day);
                            }
                        }).start();
                        break;

                    case "best":
                        final Genre genre =
                                ((LabelValueTuple<Genre>) subSelectorList.getSelectedValue())
                                        .getValue();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getBestChallengeList(genre);
                            }
                        }).start();
                        break;

                    case "challenge":

                        break;

                    default:
                        wtListLabel.setText("... 헐?");
                }
            }
        });
    }

    public void getWebtoonList(Day day) {
        NaverToonInfo[] infos
                = NaverWebtoonCrawler.downloadWebtoonListByDay(day);
        populateNaverToon(infos);
    }

    public void getBestChallengeList(Genre genre) {
        System.out.println("Gettting list");
        NaverToonInfo[] infos
                = NaverBCCrawler.downloadBCListByGenre(genre, 1);
        populateNaverToon(infos);
    }

    public void getChallengeList() {

    }

    private void populateNaverToon(NaverToonInfo[] infos) {
        wtListPanel.removeAll();
        // Now display the information
        for (NaverToonInfo info : infos) {
            // A panel to hold a webtoon info
            JPanel curWtPanel = new JPanel();
            curWtPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
            curWtPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            curWtPanel.setPreferredSize(new Dimension(580, 120));

            // Create a thumbnail with its title
            JLabel curLabel = new JLabel(info.getTitleName(), info.getThumb(),
                    SwingConstants.LEFT);
            curLabel.setVerticalTextPosition(SwingConstants.TOP);
            curLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            curLabel.setPreferredSize(new Dimension(150, 110));

            // Create a Panel to hold buttons
            JPanel btnPanel = new JPanel();
            btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
            btnPanel.setPreferredSize(new Dimension(150, 110));

            // Create a Panel to hold progress info
            JPanel progInfoPanel = new JPanel();
            progInfoPanel.setLayout(
                    new BoxLayout(progInfoPanel, BoxLayout.Y_AXIS));
            progInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            progInfoPanel.setPreferredSize(new Dimension(280, 110));

            // Create a button to download and a button to cancel
            JButton wtDownload = new JButton("웹툰 다운로드");
            JButton wtPause = new JButton("일시정지");
            JButton wtCancel = new JButton("다운로드 취소");

            // Create a progress bar for the total progress
            // and another progress bar for the partial progress
            JProgressBar totalProg = new JProgressBar();
            JProgressBar partialProg = new JProgressBar();

            // Set progress bars' sizes appropriately
            totalProg.setPreferredSize(new Dimension(200, 20));
            partialProg.setPreferredSize(new Dimension(200, 20));

            // Display the percentage string.
            totalProg.setStringPainted(true);
            partialProg.setStringPainted(true);

            // Create labels for progress bars
            JLabel wtMsgLabel = new JLabel("저장 위치: ");
            JLabel totalProgLabel = new JLabel("전체 :");
            JLabel partialProgLabel = new JLabel("현재 :");

            // Set labels' sizes appropriately
            totalProgLabel.setPreferredSize(new Dimension(50, 20));
            partialProgLabel.setPreferredSize(new Dimension(50, 20));

            // Group progress bars and their labels together
            JPanel totalProgPanel = new JPanel();
            totalProgPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
            totalProgPanel.setPreferredSize(new Dimension(270, 50));
            totalProgPanel.add(totalProgLabel);
            totalProgPanel.add(totalProg);

            JPanel partialProgPanel = new JPanel();
            partialProgPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
            partialProgPanel.setPreferredSize(new Dimension(270, 50));
            partialProgPanel.add(partialProgLabel);
            partialProgPanel.add(partialProg);


            // Set the button settings
            wtDownload.setEnabled(true);
            wtPause.setEnabled(false);
            wtCancel.setEnabled(false);

            // Set the button commands
            wtDownload.setActionCommand("download");
            wtPause.setActionCommand("pause");
            wtCancel.setActionCommand("cancel");

            // Instantiate a NaverDownloadBtnLister, and link to
            // download-related buttons
            NaverDownloadBtnListener downloadListener
                    = new NaverDownloadBtnListener(
                    info, totalProg, partialProg, saveDirLabel,
                    wtMsgLabel, wtDownload, wtCancel, wtPause);
            wtDownload.addActionListener(downloadListener);
            wtPause.addActionListener(downloadListener);
            wtCancel.addActionListener(downloadListener);

            // Add the buttons to their panel with spacing
            btnPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            btnPanel.add(wtDownload);
            btnPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            btnPanel.add(wtPause);
            btnPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            btnPanel.add(wtCancel);

            // Add the progress msg and bars to their panel
            progInfoPanel.add(wtMsgLabel);
            progInfoPanel.add(totalProgPanel);
            progInfoPanel.add(partialProgPanel);

            // Add labels and panels to the current webtoon panel
            curWtPanel.add(curLabel);
            curWtPanel.add(btnPanel);
            curWtPanel.add(progInfoPanel);

            wtListPanel.add(curWtPanel);
        }

        wtListPanel.revalidate();
        wtListPanel.repaint();
        getListBtn.setEnabled(true);
    }


}
