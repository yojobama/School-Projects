package com.example.mylibrary;

public class Book {
    private String m_Author, m_Title;
    private int m_Pages, m_PagesRead;


    public Book() {
        m_Title = "";
        m_Author = "";
        m_Pages = 0;
        m_PagesRead = 0;
    }

    public Book( String title, String author, int pages, int pagesRead) {
        this.m_Title = title;
        this.m_Author = author;
        this.m_Pages = pages;
        this.m_PagesRead = pagesRead;
    }

    public String getAuthor() {
        return m_Author;
    }

    public void setAuthor(String m_Author) {
        this.m_Author = m_Author;
    }

    public String getTitle() {
        return m_Title;
    }

    public void setTitle(String m_Title) {
        this.m_Title = m_Title;
    }

    public int getPages() {
        return m_Pages;
    }

    public void setPages(int m_Pages) {
        this.m_Pages = m_Pages;
    }

    public int getPagesRead() {
        return m_PagesRead;
    }

    public void setPagesRead(int m_PagesRead) {
        this.m_PagesRead = m_PagesRead;
    }
}
