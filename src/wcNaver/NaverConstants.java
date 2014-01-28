package wcNaver;


import theCrawler.KorEng;

/**
 * Constants for Naver
 */
public interface NaverConstants {

    public static final KorEng[] MAIN_CAT = {
            new KorEng("웹툰", "webtoon"),
            new KorEng("베스트 도전", "best challenge"),
            new KorEng("도전만화", "challenge")
    };
    public static final KorEng[] WEBTOON_CAT = {
            new KorEng("월", "mon"),
            new KorEng("화", "tue"),
            new KorEng("수", "wed"),
            new KorEng("목", "thu"),
            new KorEng("금", "fri"),
            new KorEng("토", "sat"),
            new KorEng("일", "sun")
    };

    public static final String[] BEST_CAT =
            {"아직 준비 안 됬어요~"};
    public static final String[] CHALLENGE_CAT =
            {"만드는 중입니다. 기둘려 주세요. :-)"};

}
