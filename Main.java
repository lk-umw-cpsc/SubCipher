import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    
    private static void createAndShowGUI() {
        new MainFrame().createAndShow();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        // Make the created Swing GUI look like a native application on the
        // user's OS
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Create and show the GUI on the Swing event thread (important)
        // (Main.createAndShow is called)
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

}
