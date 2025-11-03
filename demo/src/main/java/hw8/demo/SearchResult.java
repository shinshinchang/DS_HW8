package hw8.demo;

public class SearchResult {
    private String title;
    private String link;
    private String snippet;

    public SearchResult() {}
    public SearchResult(String title, String link, String snippet) {
        this.title = title;
        this.link = link;
        this.snippet = snippet;
    }
    public String getTitle() { return title; }
    public String getLink() { return link; }
    public String getSnippet() { return snippet; }
    public void setTitle(String title) { this.title = title; }
    public void setLink(String link) { this.link = link; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
}