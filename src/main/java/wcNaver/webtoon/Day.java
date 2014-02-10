package wcNaver.webtoon;

public enum Day {
    MON("mon"), TUES("tue"), WEDS("wed"), THURS("thu"), FRI("fri"),
    SAT("sat"), SUN("sun"), ALL("all");

    private String day;

    private Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

}
