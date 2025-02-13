package com.login.login.Model;

public class Notice {
    private int id;
    private String title;
    private String createDate;
    private int views;

    public Notice(int id, String title, String createDate, int views) {
        this.id = id;
        this.title = title;
        this.createDate = createDate;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public int getViews() {
        return views;
    }

    public boolean hasAttachment() {
        // 예시로 첨부파일이 있는지 여부를 랜덤으로 반환
        return Math.random() > 0.5;
    }
}
