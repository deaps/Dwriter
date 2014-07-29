/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dwriter;

/**
 * Dwriter - the main class of the application. The class manages open files,
 * performs I/O operations (and provides support for notifying listeners when
 * new tabs (files) are created, loaded or saved).
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class Dwriter {

    /**
     * The filename of the default properties, loaded from the directory of the
     * class
     */
    private static final String DEFAULT_PROPERTIES_FILENAME = "res/defaults.xml";
    
    /**
     * The filename of the user properties, loaded from the user's current
     * working directory
     */
    private static final String USER_PROPERTIES_FILENAME = "csheets.xml";
    
    /**
     * The open files
     */
    //private Map<Workfile, File> workfiles = new HashMap<Workfile, File>();
    
    /**
     * The application's properties
     */
    //private NamedProperties props;
    
    /**
     * The listeners registered to receive events
     */
    //private List<SpreadsheetAppListener> listeners
    //        = new ArrayList<SpreadsheetAppListener>();
    /**
     * Creates the CleanSheets application.
     */
    /*public CleanSheets() {
     // Loads compilers
     FormulaCompiler.getInstance();

     // Loads language
     Language.getInstance();

     // Loads extensions
     ExtensionManager.getInstance();

     // Loads default properties
     Properties defaultProps = new Properties();
     InputStream defaultStream = CleanSheets.class.getResourceAsStream(DEFAULT_PROPERTIES_FILENAME);
     if (defaultStream != null) {
     try {
     defaultProps.loadFromXML(defaultStream);
     } catch (IOException e) {
     System.err.println("Could not load default application properties.");
     } finally {
     try {
     if (defaultStream != null) {
     defaultStream.close();
     }
     } catch (IOException e) {
     }
     }
     }

     // Loads user properties
     File propsFile = new File(USER_PROPERTIES_FILENAME);
     props = new NamedProperties(propsFile, defaultProps);
}*/

/**
 * Starts Driter from the command-line.
 *
 * @param args the command-line arguments (not used)
 */
public static void main(String[] args) {
        Dwriter app = new Dwriter();

        // Configures look and feel
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);

        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
        }

        // Creates user interface
        //new dwriter.ui.Frame.Creator(app).createAndWait();
        //app.create();
    }

    // mais
}
