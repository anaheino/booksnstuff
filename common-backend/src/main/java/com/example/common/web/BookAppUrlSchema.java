package com.example.common.web;

public class BookAppUrlSchema extends BaseAppUrlSchema {
    public static final String BOOK = API + "/book/{id}";
    public static final String BOOKS = API + "/books";
    public static final String BOOK_PAGE = API + "/book-page";
    public static final String BOOK_STUPID = BOOK_PAGE + "/stupid";
    public static final String BOOK_STUPID_ID = BOOK_STUPID + "/{id}";
}