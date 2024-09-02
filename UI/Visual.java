package UI;

import Encrypt.Encryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Visual extends JFrame {
    private JButton encryptButton;
    private JButton decryptButton;
    private JLabel statusLabel;

    public Visual() {
        super("File Encryptor and Decryptor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        statusLabel = new JLabel();

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String password = JOptionPane.showInputDialog("Enter password:");
                    try {
                        Encryption.encryptFile(file, password);
                        statusLabel.setText("File encrypted successfully!");
                    } catch (Exception ex) {
                        statusLabel.setText("Error encrypting the file.");
                        ex.printStackTrace();
                    }
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String password = JOptionPane.showInputDialog("Enter password:");
                    try {
                        Encryption.decryptFile(file, password);
                        statusLabel.setText("File decrypted successfully!");
                    } catch (Exception ex) {
                        statusLabel.setText("Error decrypting the file.");
                        ex.printStackTrace();
                    }
                }
            }
        });

        add(encryptButton);
        add(decryptButton);
        add(statusLabel);

        setSize(300, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Visual();
            }
        });
    }
}
