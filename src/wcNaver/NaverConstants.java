package wcNaver;


import theCrawler.LabelValueTuple;
import wcNaver.bestChallenge.Genre;
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
//            add(new LabelValueTuple<>("베스트 도전", "best"));
//            add(new LabelValueTuple<>("도전만화", "challenge"));
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

    public static final List<LabelValueTuple<Genre>> CHALLENGE_CAT =
            new ArrayList<LabelValueTuple<Genre>>() {{
                add(new LabelValueTuple<>("에피소드", Genre.EPISODE));
                add(new LabelValueTuple<>("옴니버스", Genre.OMNIBUS));
                add(new LabelValueTuple<>("스토리", Genre.STORY));
                add(new LabelValueTuple<>("일상", Genre.DAILY));
                add(new LabelValueTuple<>("개그", Genre.COMIC));
                add(new LabelValueTuple<>("판타지", Genre.FANTASY));
                add(new LabelValueTuple<>("액션", Genre.ACTION));
                add(new LabelValueTuple<>("드라마", Genre.DRAMA));
                add(new LabelValueTuple<>("순정", Genre.PURE));
                add(new LabelValueTuple<>("감성", Genre.SENSIBILITY));
                add(new LabelValueTuple<>("스릴러", Genre.THRILL));
                add(new LabelValueTuple<>("시대극", Genre.HISTORICAL));
                add(new LabelValueTuple<>("스포츠", Genre.SPORTS));
                add(new LabelValueTuple<>("전체만화", Genre.ALL));
            }};


}
