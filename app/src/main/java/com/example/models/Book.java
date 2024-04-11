package com.example.models;

public class Book {
    int bookId;
    String bookName;
    String bookPublication;
    int bookPrint;
    double bookPrice;
    byte[] bookCover;

    public Book(int bookId, String bookName, String bookPublication, int bookPrint, double bookPrice, byte[] bookCover) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookPublication = bookPublication;
        this.bookPrint = bookPrint;
        this.bookPrice = bookPrice;
        this.bookCover = bookCover;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPublication() {
        return bookPublication;
    }

    public void setBookPublication(String bookPublication) {
        this.bookPublication = bookPublication;
    }

    public int getBookPrint() {
        return bookPrint;
    }

    public void setBookPrint(int bookPrint) {
        this.bookPrint = bookPrint;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public byte[] getBookCover() {
        return bookCover;
    }

    public void setBookCover(byte[] bookCover) {
        this.bookCover = bookCover;
    }
}
