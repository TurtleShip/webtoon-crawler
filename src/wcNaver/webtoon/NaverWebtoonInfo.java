package wcNaver.webtoon;

import javax.swing.*;

/**
 * This class contains information related to a naver webtoon.
 */
public class NaverWebtoonInfo {
    private String titleId;
    private String titleName;
    private ImageIcon thumb;

    public NaverWebtoonInfo(String titleId, String titleName, ImageIcon thumb) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.thumb = thumb;
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


}
