package wcNaver;

import javax.swing.*;

/**
 * This class contains information related to a naver webtoon.
 */
public class NaverToonInfo {
    private String titleId;
    private String titleName;
    private ImageIcon thumb;
    private NaverToonCategory cat;

    public NaverToonInfo(String titleId, String titleName, ImageIcon thumb,
                         NaverToonCategory cat) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.thumb = thumb;
        this.cat = cat;
    }

    public String getTitleId() {
        return titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public ImageIcon getThumb() {
        return thumb;
    }

    public NaverToonCategory getCategory() {
        return cat;
    }


}
