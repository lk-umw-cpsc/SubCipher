import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private JTextField cipherField;
    private JTextField plaintextField;
    private JTextField ciphertextField;

    private JButton generateButton;
    private JButton encryptButton;
    private JButton decryptButton;

    public MainFrame() {
        super("Sub Cipher by Lauren");
    }

    public void createAndShow() {
        Box pane = Box.createHorizontalBox();
        Box layeredPane = Box.createVerticalBox();
        pane.add(Box.createVerticalGlue());
        pane.add(Box.createHorizontalStrut(10));
            layeredPane.add(Box.createVerticalStrut(10));
            Box layer = Box.createHorizontalBox();
            layer.add(new JLabel("Cipher:"));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            layer = Box.createHorizontalBox();
            layer.add(cipherField = new JTextField(26));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);
            
            layer = Box.createHorizontalBox();
            layer.add(generateButton = new JButton("Generate"));
            layeredPane.add(layer);

            layer = Box.createHorizontalBox();
            layer.add(new JLabel("Plaintext:"));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            layer = Box.createHorizontalBox();
            layer.add(plaintextField = new JTextField(26));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            layer = Box.createHorizontalBox();
            layer.add(new JLabel("Ciphertext:"));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            layer = Box.createHorizontalBox();
            layer.add(ciphertextField = new JTextField(26));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            layer = Box.createHorizontalBox();
            layer.add(encryptButton = new JButton("Encrypt"));
            layer.add(Box.createHorizontalGlue());
            layer.add(decryptButton = new JButton("Decrypt"));
            layeredPane.add(layer);
            layeredPane.add(Box.createVerticalStrut(10));
        pane.add(layeredPane);
        pane.add(Box.createHorizontalStrut(10));
        pane.add(Box.createVerticalGlue());

        add(pane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        pack();
        cipherField.setMaximumSize(cipherField.getSize());
        plaintextField.setMaximumSize(plaintextField.getSize());
        ciphertextField.setMaximumSize(ciphertextField.getSize());

        generateButton.addActionListener(this::generatePressed);
        encryptButton.addActionListener(this::encryptPressed);
        decryptButton.addActionListener(this::decryptPressed);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generatePressed(ActionEvent e) {
        cipherField.setText(Cipher.generate());
    }

    private void encryptPressed(ActionEvent e) {
        Cipher cipher = new Cipher(cipherField.getText());
        ciphertextField.setText(cipher.encrypt(plaintextField.getText()));
    }

    private void decryptPressed(ActionEvent e) {
        Cipher cipher = new Cipher(cipherField.getText());
        plaintextField.setText(cipher.decrypt(ciphertextField.getText()));
    }
    
}