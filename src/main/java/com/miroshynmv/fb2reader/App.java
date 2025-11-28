package com.miroshynmv.fb2reader;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App
{
    private FB2Reader fb2Reader = new FB2Reader();
    private JFrame frame;
    private JTextArea textArea;
    private JLabel titleLabel, authorLabel, publisherLabel;

    public App()
    {
        frame = new JFrame("FB2 Reader");
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Відкрити");
        openItem.addActionListener(e -> openFB2File());
        fileMenu.add(openItem);
        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        JMenu EditMenu = new JMenu("Редагувати");
        JMenuItem copyItem = new JMenuItem("Копіювати");
        copyItem.addActionListener(e -> textArea.copy());
        EditMenu.add(copyItem);
        JMenuItem SelectAllItem = new JMenuItem("Вибрати все");
        SelectAllItem.addActionListener(e -> textArea.selectAll());
        EditMenu.add(SelectAllItem);
        menuBar.add(EditMenu);

        JMenu viewMenu = new JMenu("Вид");
        JMenuItem colorItem = new JMenuItem("Змінити колір фону");
        colorItem.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(frame, "Виберіть колір фону", textArea.getBackground());
            if (newColor != null)
            {
                textArea.setBackground(newColor);
            }
        });
        viewMenu.add(colorItem);
        JMenuItem zoomInItem = new JMenuItem("Збільшити");
        zoomInItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            textArea.setFont(new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 2));
        });
        viewMenu.add(zoomInItem);
        JMenuItem zoomOutItem = new JMenuItem("Зменшити");
        zoomOutItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            textArea.setFont(new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() - 2));
        });
        viewMenu.add(zoomOutItem);
        JMenuItem resetZoomItem = new JMenuItem("Скинути масштаб");
        resetZoomItem.addActionListener(e -> {
            Font currentFont = textArea.getFont();
            textArea.setFont(new Font(currentFont.getName(), currentFont.getStyle(), 12));
        });
        viewMenu.add(resetZoomItem);
        menuBar.add(viewMenu);

        JMenu helpMenu = new JMenu("Допомога");
        JMenuItem aboutItem = new JMenuItem("Про програму");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "FB2 Reader\nВерсія програми: 1.0\nАвтор програми: Микита Мірошин", "Про програму", JOptionPane.INFORMATION_MESSAGE);
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        
        frame.setJMenuBar(menuBar);
        
        JPanel topPanel = new JPanel(new GridLayout(3,1));
        titleLabel = new JLabel("Назва: ");
        authorLabel = new JLabel("Автор: ");
        publisherLabel = new JLabel("Видавництво: ");
        topPanel.add(titleLabel);
        topPanel.add(authorLabel);
        topPanel.add(publisherLabel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel bottomPanel = new JPanel();

        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void openFB2File()
    {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            fb2Reader.loadBook(filePath);
            titleLabel.setText("Title: " + fb2Reader.getTitle());
            authorLabel.setText("Author: " + fb2Reader.getAuthor());
            publisherLabel.setText("Publisher: " + fb2Reader.getPublisher());
        }
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
        });
    }
}