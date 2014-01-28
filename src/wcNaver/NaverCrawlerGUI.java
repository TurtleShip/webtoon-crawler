package wcNaver;

import theCrawler.LabelValueTuple;
import wcNaver.webtoon.*;

import javax.swing.*;
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

    private JPanel myFacePanel; // TODO: When I am ready, post my handsome face.
    private JPanel selectionPanel;
    private JPanel wtListPanel;
    private JPanel progressPanel;

    private JLabel mainLabel;
    private JLabel subLabel;
    private JLabel progressLabel;
    private JLabel wtListLabel;

    private JList mainSelectorList;
    private JList subSelectorList;

    private JButton getListBtn;

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
        progressPanel.add(progressLabel);


        // Create a webtoon list panel.
        wtListPanel = new JPanel();
        wtListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        wtListPanel.setPreferredSize(new Dimension(620, 500));
        wtListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create components for the webtoon list panel.
        wtListLabel = new JLabel("엡툰 목록은 여기에 다운로드~!");
        wtListPanel.add(wtListLabel);

        // Add the selectionPanel to the main frame.
        jfrm.add(selectionPanel);
        jfrm.add(progressPanel);
        jfrm.add(wtListPanel);

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

                System.out.println("Clear!");

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
                = wcNaver.webtoon.NaverWebtoonCrawler.downloadWebtoonListByDay(day);


        // Now display the information
        for (NaverWebtoonInfo info : infos) {
            System.out.println(info.getTitleName());
            JLabel curLabel = new JLabel(info.getTitleName(), info.getThumb(),
                    SwingConstants.LEFT);

            wtListPanel.add(curLabel);
        }

        wtListPanel.revalidate();
    }

    public void getBestChallengeList() {

    }

    public void getChallengeList() {

    }
}
