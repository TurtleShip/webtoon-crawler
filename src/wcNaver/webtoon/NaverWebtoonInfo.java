package wcNaver.webtoon;

/**
 * This class contains information related to a naver webtoon.
 */
public class NaverWebtoonInfo {
    private String titleId;
    private String titleName;

    public NaverWebtoonInfo(String titleId, String titleName) {
        this.titleId = titleId;
        this.titleName = titleName;
    }

    public String getTitleId() {
        return titleId;
    }

    public String getTitleName() {
        return titleName;
    }
}
