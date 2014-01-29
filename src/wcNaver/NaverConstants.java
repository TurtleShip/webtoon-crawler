package wcNaver;


import theCrawler.LabelValueTuple;
import wcNaver.webtoon.Day;

import java.util.ArrayList;
import java.util.List;

/**
 * Constants for Naver
 */
public interface NaverConstants {

    public static final List<LabelValueTuple<String>> MAIN_CAT =
        new ArrayList<LabelValueTuple<String>>() {{
            add(new LabelValueTuple<>("웹툰", "webtoon"));
            add(new LabelValueTuple<>("베스트 도전", "best"));
            add(new LabelValueTuple<>("도전만화", "challenge"));
        }};

    public static final List<LabelValueTuple<Day>> WEBTOON_CAT =
            new ArrayList<LabelValueTuple<Day>>() {{
                add(new LabelValueTuple<>("월", Day.MON));
                add(new LabelValueTuple<>("화", Day.TUES));
                add(new LabelValueTuple<>("수", Day.WEDS));
                add(new LabelValueTuple<>("목", Day.THURS));
                add(new LabelValueTuple<>("금", Day.FRI));
                add(new LabelValueTuple<>("토", Day.SAT));
                add(new LabelValueTuple<>("일", Day.SUN));
                add(new LabelValueTuple<>("완결", Day.ALL));
            }};


    public static final String[] BEST_CAT =
            {"아직 준비 안 됬어요~"};
    public static final String[] CHALLENGE_CAT =
            {"만드는 중입니다. 기둘려 주세요. :-)"};

}
