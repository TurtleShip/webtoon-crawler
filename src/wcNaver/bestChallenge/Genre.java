package wcNaver.bestChallenge;

/**
 *
 */
public enum Genre {

    EPISODE("episode"), OMNIBUS("omnibus"), STORY("story"), DAILY("daily"),
    COMIC("comic"), FANTASY("fantasy"), ACTION("action"), DRAMA("drama"),
    PURE("pure"), SENSIBILITY("sensibility"), THRILL("thrill"),
    HISTORICAL("historical"), SPORTS("sports"), ALL("");

    private String genre;

    private Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
