import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        // reskin Swing to look like the user's OS
        setLookAndFeel();
        // Create and show the GUI on the Swing event thread (important)
        // (Main.createAndShow is called)
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    /**
     * Causes any Swing components to be reskinned, looking more like a native
     * app on the user's system instead of Swing's ugly default L&F.
     */
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.out.println("Note: user's system does not support L&F");
        }
    }
    
    /**
     * Method to be called by the Swing event processing thread.
     * 
     * Creates and shows the GUI for the program.
     */
    private static void createAndShowGUI() {
        new MainFrame().createAndShow();
    }

}
