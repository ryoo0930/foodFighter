package kr.ac.jbnu.se.foodfighter;

public class ReviewTable {
    String title;
    String review;

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getreview() {
        return review;
    }

    public void setreview(String review) {
        this.review = review;
    }

    public ReviewTable (String title, String review) {
        this.title = title;
        this.review = review;
    }


}
