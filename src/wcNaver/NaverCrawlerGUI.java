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

//    private JProgressBar totalProg;
//    private JProgressBar partialProg;

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
//        progressLabel = new JLabel("나는야 프로그래스 레이블");

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

//        progressPanel.add(progressLabel);
        progressPanel.add(chseDirBtn);
        progressPanel.add(saveDirMsgLabel);
        progressPanel.add(saveDirLabel);


        // Create a webtoon list panel.
        wtListPanel = new JPanel();
        wtListPanel.setLayout(new BoxLayout(wtListPanel, BoxLayout.Y_AXIS));

//        wtListPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

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
        for (NaverWebtoonInfo info : infos) {
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
    }

    public void getBestChallengeList() {

    }

    public void getChallengeList() {

    }


}
