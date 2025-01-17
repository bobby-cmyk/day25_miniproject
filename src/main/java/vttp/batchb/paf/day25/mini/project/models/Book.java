package vttp.batchb.paf.day25.mini.project.models;

import java.time.LocalDate;

public class Book {
    private int id;
    private String title;
    private int pages;
    private LocalDate dateReleased;
    private String language;
    private int authorId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public LocalDate getDateReleased() {
        return dateReleased;
    }
    public void setDateReleased(LocalDate dateReleased) {
        this.dateReleased = dateReleased;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
