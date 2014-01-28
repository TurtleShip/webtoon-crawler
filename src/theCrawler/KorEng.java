package theCrawler;

/**
 * This class support a tuple of Korean and English.
 */
public class KorEng {
    private String korean;
    private String english;

    public KorEng(String korean, String english) {
        this.korean = korean;
        this.english = english;
    }

    public String getKorean() {
        return korean;
    }

    public String getEnglish() {
        return english;
    }

    @Override
    public String toString() {
        return korean;
    }
}
