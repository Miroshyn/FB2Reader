package com.miroshynmv.fb2reader;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FB2Reader
{
    // Основні властивості книги
    private String title;
    private String author;
    private String publisher;

    // Відкриває FB2 файл і зчитує основну інформацію про книгу
    public void loadBook(String filePath)
    {
        try
        {
            File fb2File = new File(filePath);
            if (!fb2File.exists())
            {
                System.out.println("FB2 file not found: " + filePath);
                return;
            }

            // Парсинг FB2 файлу (XML формат)
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(fb2File);
            doc.getDocumentElement().normalize();

            // Зчитування основної інформації про книгу
            this.title = doc.getElementsByTagName("book-title").item(0).getTextContent();
            this.author = doc.getElementsByTagName("author").item(0).getTextContent();
            this.publisher = doc.getElementsByTagName("publisher").item(0).getTextContent();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Відображає основну інформацію про книгу
    public void displayBookInfo()
    {
        System.out.println("Book Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Publisher: " + publisher);
    }

    // Геттер для отримання інформації про книгу
    public String getTitle()
    {
        return title;
    }

    // Геттер для отримання інформації про автора
    public String getAuthor()
    {
        return author;
    }

    // Геттер для отримання інформації про видавництво
    public String getPublisher()
    {
        return publisher;
    }
}