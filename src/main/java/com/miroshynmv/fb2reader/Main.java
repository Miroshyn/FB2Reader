package com.miroshynmv.fb2reader;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("FB2 Reader Application Started");

        String fb2FilePath = "books/sample.fb2";
        FB2Reader fb2Reader = new FB2Reader();
        fb2Reader.loadBook(fb2FilePath);
        fb2Reader.displayBookInfo();
    }
}