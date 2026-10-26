package Admin;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;

import javax.swing.filechooser.FileNameExtensionFilter;

public class BackgroundPicture {

    private String filePath;

    public BackgroundPicture() {
        filePath = null;
    }

    public void setBackground() {
        JFileChooser fileSelect = new JFileChooser();
        fileSelect.setCurrentDirectory(new File("C:\\Users\\jjc\\Pictures\\IT203"));

        FileNameExtensionFilter filler = new FileNameExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)","png", "jpg", "jpeg");

        fileSelect.setFileFilter(filler);
        fileSelect.setAcceptAllFileFilterUsed(false);

        int result = fileSelect.showDialog(fileSelect, "Upload");

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileSelect.getSelectedFile();
            if (file.exists() && file.isFile()) {
                filePath = file.getAbsolutePath();
                try (FileWriter writer = new FileWriter("backgroundImage.txt")) {
                    writer.write(filePath);
                    JOptionPane.showMessageDialog(null, "Background image successfully changed", null, JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Unable to save the background image path", null, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid image file", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getBackground() {
        try (BufferedReader reader = new BufferedReader(new FileReader("backgroundImage.txt"))) {

            filePath = reader.readLine();

            if (filePath != null && new File(filePath).exists()) {
                return filePath;
            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }

    public void removeBackground() {
        try {
            FileWriter writer = new FileWriter("backgroundImage.txt");
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
