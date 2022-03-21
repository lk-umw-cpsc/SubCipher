import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    // Number of pixels of padding placed between window frame and child components
    private static final int PADDING = 16;

    // Text fields
    private JTextField cipherField;
    private JTextField plaintextField;
    private JTextField ciphertextField;

    // Buttons
    private JButton generateButton;
    private JButton encryptButton;
    private JButton decryptButton;

    // Error message layer (displays when invalid cipher is given)
    private Box invalidCipherLayer;

    public MainFrame() {
        // Set window title
        super("Substitution Cipher by Lauren");
        // Close the app when the user clicks the X button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Prevent the user from resizing the window (try commenting this out)
        // setResizable(false);
    }

    /**
     * Builds this MainFrame's components, centers the frame on the screen,
     * and makes the frame visible.
     */
    public void createAndShow() {
        /**
         * Components are added to the frame in layers.
         * 
         * A layer is a horizontal box, created by Box.createHorizontalBox().
         * 
         * Layers, by nature of being horizontal Boxes, display their children
         * left to right, in the order they're added in.
         * 
         * Layers are added to verticalLayerContainer, a vertical Box created
         * by Box.createVerticalBox(). Layers are displayed top to bottom, in
         * the order they were added in.
         * 
         * Finally, the verticalLayerContainer is wrapped in another
         * horizontal Box, for the sake of placing padding around the components.
         * 
         * This way of creating UIs is a little more time consuming, but allows
         * for finer-grained control over the behavior of components, such as
         * aligning certain components left/right/center, adding space between
         * them, etc.
         */

        // Box which will house verticalLayerContainer
        Box horizontallyPaddedContainer = Box.createHorizontalBox();
        // Where all of our layers will go
        Box verticalLayerContainer = Box.createVerticalBox();

        // Surround verticalLayerContainer with horizontal glue and some padding
        // The glue has the effect of horizontally centering the vertical layer
        // container. This occurs because the horizontal glues change size
        // while the layer container does not.
        horizontallyPaddedContainer.add(Box.createHorizontalGlue());
        horizontallyPaddedContainer.add(Box.createHorizontalStrut(PADDING));
        horizontallyPaddedContainer.add(verticalLayerContainer);
        horizontallyPaddedContainer.add(Box.createHorizontalStrut(PADDING));
        horizontallyPaddedContainer.add(Box.createHorizontalGlue());

        // Start the layer container with some padding between the window title bar
        // and the first layer
        verticalLayerContainer.add(Box.createVerticalStrut(PADDING));

        // Start the first layer: a left-aligned label that says "Cipher:"
        Box layer = Box.createHorizontalBox();
            layer.add(new JLabel("Cipher:"));
            // Left-align by adding glue after the label
            layer.add(Box.createHorizontalGlue());
        verticalLayerContainer.add(layer);

        // Star the next layer: a text field that will contain the cipher
        // Note that JTextFields resize vertically *and* horizontally by default,
        // making glue useless unless you use setMaximumSize to limit their growth.
        layer = Box.createHorizontalBox();
            layer.add(cipherField = new JTextField(26));
        verticalLayerContainer.add(layer);

        // Create a the next layer: a layer that starts out hidden,
        // but will be made visible when the user attempts to encrypt/decrypt
        // with an invalid cypher
        layer = invalidCipherLayer = Box.createHorizontalBox();
            JLabel errorLabel = new JLabel("Please enter a valid cipher");
            // Red font
            errorLabel.setForeground(Color.RED);
            layer.add(errorLabel);
            // left-align the label with some glue
            layer.add(Box.createHorizontalGlue());
            // Start the layer hidden
            layer.setVisible(false);
        verticalLayerContainer.add(layer);
        
        // Next layer is a right-aligned button for generating the cypher
        layer = Box.createHorizontalBox();
            // right align by adding horizontal glue *before* the component
            layer.add(Box.createHorizontalGlue());
            layer.add(generateButton = new JButton("Generate"));
        verticalLayerContainer.add(layer);

        // Next layer is another left-aligned label
        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Plaintext:"));
            layer.add(Box.createHorizontalGlue());
        verticalLayerContainer.add(layer);

        // Next layer is a text field
        layer = Box.createHorizontalBox();
            layer.add(plaintextField = new JTextField(26));
        verticalLayerContainer.add(layer);

        // Another left-aligned label
        layer = Box.createHorizontalBox();
            layer.add(new JLabel("Ciphertext:"));
            layer.add(Box.createHorizontalGlue());
        verticalLayerContainer.add(layer);

        // another text field
        layer = Box.createHorizontalBox();
            layer.add(ciphertextField = new JTextField(26));
        verticalLayerContainer.add(layer);

        // Last layer left-aligns one button while right-aligning the other
        // This is possible by placing horizontal glue *between* the two
        // components
        layer = Box.createHorizontalBox();
            layer.add(encryptButton = new JButton("Encrypt"));
            layer.add(Box.createHorizontalGlue());
            layer.add(decryptButton = new JButton("Decrypt"));
            verticalLayerContainer.add(layer);
        verticalLayerContainer.add(Box.createVerticalStrut(PADDING));

        // Finally, add everything we just made to the frame
        add(horizontallyPaddedContainer);

        // Resize the window so it can fit all of its child components
        pack();

        // Prevent the text fields from resizing vertically (they will by default)
        cipherField.setMaximumSize(new Dimension(Integer.MAX_VALUE, cipherField.getHeight()));
        plaintextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, plaintextField.getHeight()));
        ciphertextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, ciphertextField.getHeight()));

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

    /**
     * Method called when the Decrypt button is pressed.
     * @param e Event information passed by Swing.
     */
    private void generatePressed(ActionEvent e) {
        // Generate a new cipher and place it in the cipher field
        cipherField.setText(SubstitutionCipher.generate());
        // Hide the invalid cipher error message if it's currently showing
        hideInvalidCipherErrorMessage();
    }

    /**
     * Method called when the Encrypt button is pressed.
     * @param e Event information passed by Swing
     */
    private void encryptPressed(ActionEvent e) {
        // Attempt to encrypt; if an invalid cipher is given,
        // display an error message instead.
        SubstitutionCipher cipher;
        try {
            cipher = new SubstitutionCipher(cipherField.getText());
        } catch (IllegalArgumentException exc) {
            showInvalidCipherErrorMessage();
            return;
        }
        // Encryption was successful. Hide the invalid cipher field if visible
        hideInvalidCipherErrorMessage();
        // Place the output in the ciphertextfield
        ciphertextField.setText(cipher.encrypt(plaintextField.getText()));
    }

    /**
     * Method called when Decrypt button is pressed
     * @param e Event information from Swing
     */
    private void decryptPressed(ActionEvent e) {
        // Attempt to decrypt; if an invalid cypher is given,
        // display an error message instead
        SubstitutionCipher cipher;
        try {
            cipher = new SubstitutionCipher(cipherField.getText());
        } catch (IllegalArgumentException exc) {
            showInvalidCipherErrorMessage();
            return;
        }
        // Decryption was successful; hide the invalid cipher error message
        // if it's showing
        hideInvalidCipherErrorMessage();
        // Place output in the plaintext field
        plaintextField.setText(cipher.decrypt(ciphertextField.getText()));
    }
    
    /**
     * Hides the "Please enter a valid cipher" error message
     */
    private void hideInvalidCipherErrorMessage() {
        if (invalidCipherLayer.isVisible()) {
            invalidCipherLayer.setVisible(false);
            // Resize the window to reflect changes
            pack();
        }
    }

    /**
     * Displays the "Please enter a valid cipher" error message
     */
    private void showInvalidCipherErrorMessage() {
        if (!invalidCipherLayer.isVisible()) {
            invalidCipherLayer.setVisible(true);
            // Resize the window to reflect changes
            pack();
        }
    }
}