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

    private JPanel selection;

    private JLabel mainLabel;
    private JLabel subLabel;

    private JList mainSelector;
    private JList subSelector;

    private JButton jbtnGetList;

    public NaverCrawlerGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e) {
            System.out.println("Failed to set cross-platform look and feel");
            e.printStackTrace();
        }

        JFrame jfrm = new JFrame("네이버 크롤러");
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(600, 400);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Create a selection panel where users will select cartoons
        selection = new JPanel();
        selection.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        selection.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        selection.setPreferredSize(new Dimension(310, 180));

        // Create the main selector with a label
        mainLabel = new JLabel("메인 카테고리");
        mainLabel.setPreferredSize(new Dimension(150, 10));
        mainSelector = new JList(MAIN_CAT);
        mainSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainSelector.setSelectedIndex(0);
        mainSelector.setPreferredSize(new Dimension(150, 130));
        mainSelector.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Create the sub selector with a label
        subLabel = new JLabel("하위 카테고리");
        subLabel.setPreferredSize(new Dimension(150, 10));
        subSelector = new JList(WEBTOON_CAT);
        subSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subSelector.setPreferredSize(new Dimension(150, 130));
        subSelector.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        // Add a button that user can click to download stuff
        jbtnGetList = new JButton("웹툰 목록 가져오기~");
        jbtnGetList.setPreferredSize(new Dimension(150, 20));

        // add them all to the selection panel
        selection.add(mainLabel);
        selection.add(subLabel);
        selection.add(mainSelector);
        selection.add(subSelector);
        selection.add(jbtnGetList);



        // Add the selection to the main frame
        jfrm.add(selection);

        jfrm.setVisible(true);
    }
}
