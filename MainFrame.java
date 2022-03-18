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
        // Main panel will lay its children out horizontally (left to right)
        Box pane = Box.createHorizontalBox();
        // The next paint is our "main" pane; horizontal layers will be added to it
        // This panel lays its children out vertically
        Box layeredPane = Box.createVerticalBox();
        // Center-align layeredPane by adding horizontal glue before
        // (and later after) it
        pane.add(Box.createHorizontalGlue());
        // Create a minimum 10px padding between the left window border and the
        // layeredPane
        pane.add(Box.createHorizontalStrut(10));
            // Add 10 px of padding between the top of the window and the
            // first layer
            layeredPane.add(Box.createVerticalStrut(10));
            // Define the first layer, a left-aligned label with the text
            // "Cipher:". This layer lays its children out left to right
            Box layer = Box.createHorizontalBox();
            layer.add(new JLabel("Cipher:"));
            // align label left by adding glue to the right of it
            layer.add(Box.createHorizontalGlue());
            // Add this layer to the layer pane
            layeredPane.add(layer);

            // Same as above but a text field
            layer = Box.createHorizontalBox();
            layer.add(cipherField = new JTextField(26));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);
            
            // Make the next layer a horizontally-centered button
            // with the text "Generate"
            layer = Box.createHorizontalBox();
            layer.add(generateButton = new JButton("Generate"));
            layeredPane.add(layer);

            // Make the next layer a left-aligned label
            layer = Box.createHorizontalBox();
            layer.add(new JLabel("Plaintext:"));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            // Another left-aligned text field layer
            layer = Box.createHorizontalBox();
            layer.add(plaintextField = new JTextField(26));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            // Another left-aligned label layer
            layer = Box.createHorizontalBox();
            layer.add(new JLabel("Ciphertext:"));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            // Another text field label
            layer = Box.createHorizontalBox();
            layer.add(ciphertextField = new JTextField(26));
            layer.add(Box.createHorizontalGlue());
            layeredPane.add(layer);

            // Add a final layer with two buttons, one aligned left
            // and the other aligned right
            // This alignment is achieved by adding glue between the
            // two buttons (the glue resizes while the buttons don't)
            layer = Box.createHorizontalBox();
            layer.add(encryptButton = new JButton("Encrypt"));
            layer.add(Box.createHorizontalGlue());
            layer.add(decryptButton = new JButton("Decrypt"));
            layeredPane.add(layer);
            layeredPane.add(Box.createVerticalStrut(10));
        // Finally, add the layered pane to the main pane
        pane.add(layeredPane);
        // Add another 10px of padding, but to the right edge this time
        pane.add(Box.createHorizontalStrut(10));
        // Allow the space between the content and border to resize with glue
        pane.add(Box.createHorizontalGlue());

        // Add the pane to the window
        add(pane);

        // Set window's closing behavior to exit the program
        // (Default behavior is to simply hide the window and keep the
        // process running, which is bad practice unless you need to catch
        // the user pressing the close button, i.e. for saving purposes)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Prevent the user from resizing the window (try commenting this out for fun)
        setResizable(false);

        // Resize the window so it can fit all of its child components
        pack();

        // Prevent the text fields from resizing (they will by default)
        cipherField.setMaximumSize(cipherField.getSize());
        plaintextField.setMaximumSize(plaintextField.getSize());
        ciphertextField.setMaximumSize(ciphertextField.getSize());

        // Make the buttons do something when they're clicked by adding
        // ActionListeners to each of them (the function after the :: is
        // called)
        generateButton.addActionListener(this::generatePressed);
        encryptButton.addActionListener(this::encryptPressed);
        decryptButton.addActionListener(this::decryptPressed);

        // Center the window on the user's desktop
        setLocationRelativeTo(null);
        // Show the window.
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