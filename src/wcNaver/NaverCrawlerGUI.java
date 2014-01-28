package wcNaver;

import javax.swing.*;
import java.awt.*;


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
    private JPanel webtoonListPanel;
    private JPanel progressPanel;

    private JLabel mainLabel;
    private JLabel subLabel;

    private JList mainSelectorList;
    private JList subSelectorList;

    private JButton jbtnGetListBtn;

    public NaverCrawlerGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e) {
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
        mainSelectorList = new JList(MAIN_CAT);
        mainSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainSelectorList.setSelectedIndex(0);
        mainSelectorList.setPreferredSize(new Dimension(150, 130));
        mainSelectorList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Create the sub selector with a label
        subLabel = new JLabel("하위 카테고리");
        subLabel.setPreferredSize(new Dimension(150, 10));
        subSelectorList = new JList(WEBTOON_CAT);
        subSelectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subSelectorList.setPreferredSize(new Dimension(150, 130));
        subSelectorList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Add a button that user can click to download stuff
        jbtnGetListBtn = new JButton("웹툰 목록 가져오기~");
        jbtnGetListBtn.setPreferredSize(new Dimension(150, 20));

        // add them all to the selectionPanel panel
        selectionPanel.add(mainLabel);
        selectionPanel.add(subLabel);
        selectionPanel.add(mainSelectorList);
        selectionPanel.add(subSelectorList);
        selectionPanel.add(jbtnGetListBtn);



        // Create a progress panel
        progressPanel = new JPanel();
        progressPanel.setPreferredSize(new Dimension(310, 180));
        progressPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Create a webtoon list panel
        webtoonListPanel = new JPanel();
        webtoonListPanel.setPreferredSize(new Dimension(620, 500));
        webtoonListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Add the selectionPanel to the main frame
        jfrm.add(selectionPanel);
        jfrm.add(progressPanel);
        jfrm.add(webtoonListPanel);

        jfrm.setVisible(true);
    }
}
