package com.miroshynmv.fb2reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class FB2Reader
{
    // Поля для зберігання інформації про книгу
    private String title;
    private String author;
    private String publisher;

    // Метод для завантаження та парсингу FB2 файлу
    public void loadBook(String filePath)
    {
        try
        {
            File fb2File = new File(filePath);
            if (!fb2File.exists())
            {
                System.out.println("FB2 file not found: " + fb2File.getAbsolutePath());
                return;
            }

            // Парсинг FB2 у UTF-8
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder
                    .parse(new InputSource(new InputStreamReader(new FileInputStream(fb2File), "UTF-8")));
            doc.getDocumentElement().normalize();

            // Зчитування title
            NodeList titleList = doc.getElementsByTagName("book-title");
            if (titleList.getLength() > 0 && titleList.item(0) != null)
            {
                title = titleList.item(0).getTextContent();
            }
            else
            {
                title = "Unknown Title";
            }

            // Зчитування author (якщо є first-name та last-name)
            NodeList authors = doc.getElementsByTagName("author");
            if (authors.getLength() > 0 && authors.item(0) != null)
            {
                Node authorNode = authors.item(0);
                NodeList nameParts = authorNode.getChildNodes();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < nameParts.getLength(); i++)
                {
                    Node part = nameParts.item(i);
                    if (part != null && part.getTextContent() != null)
                    {
                        sb.append(part.getTextContent()).append(" ");
                    }
                }
                author = sb.toString().trim();
                if (author.isEmpty())
                {
                    author = "Unknown Author";
                }
            }
            else
            {
                author = "Unknown Author";
            }

            // Зчитування publisher
            NodeList publisherList = doc.getElementsByTagName("publisher");
            if (publisherList.getLength() > 0 && publisherList.item(0) != null)
            {
                publisher = publisherList.item(0).getTextContent();
            }
            else
            {
                publisher = "Unknown Publisher";
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Метод для відображення інформації про книгу
    public void displayBookInfo()
    {
        System.out.println("+==============================+");
        System.out.println("|       Book Information       |");
        System.out.println("================================");
        System.out.println("| Title: " + title + "         |");
        System.out.println("| Author: " + author + "       |");
        System.out.println("| Publisher: " + publisher + " |");
        System.out.println("+==============================+");
    }

    // Геттери для полів
    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getPublisher()
    {
        return publisher;
    }
}