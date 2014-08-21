/*
 * The MIT License
 *
 * Copyright 2014 João Andrade.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dwriter;

import dwriter.core.WorkFile;
import dwriter.core.WorkFileV1;
import dwriter.ctrl.WorkFileFactory;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
    private static final String DEFAULT_PROPERTIES_FILENAME = "defaults.xml";

    /**
     * The filename of the user properties, loaded from the user's current
     * working directory
     */
    private static final String USER_PROPERTIES_FILENAME = "dwriter.xml";

    /**
     * The open files
     */
    private ArrayList<WorkFile> workfiles = new ArrayList<>();

    /**
     * The active file (on display)
     */
    private WorkFile activeWorkFile;

    /**
     * The factory of workfiles
     */
    private WorkFileFactory workFileFactory;

    /**
     * The application's properties
     */
    private Properties props;

    /**
     * Creates the Dwriter application.
     */
    public Dwriter() {

        workFileFactory = new WorkFileFactory();

        addNewWorkFile();
        /*System.out.println(I18N.getInstance().getString("test"));

         I18N.getInstance().setLanguage(Lang.EN);

         System.out.println(I18N.getInstance().getString("test"));*/

        // Loads extensions
        //ExtensionManager.getInstance();
        // Loads default properties
        loadProperties();

        //System.out.println(props.getProperty("i18n.language"));
    }

    /**
     * Adds a new workfile to the arraylist of workfiles in display on the
     * frame. And sets this new WorkFile as the active one.
     */
    public void addNewWorkFile() {
        WorkFile obj = workFileFactory.getWorkFileV1(null, null,
                null);

        // Test
        //obj.setContent("teste");
        workfiles.add(obj);

        activeWorkFile = workfiles.get(workfiles.size() - 1);

    }

    /**
     * Returns the workFile in use.
     *
     * @return the active workFile
     */
    public WorkFile getActiveWorkFile() {
        return activeWorkFile;
    }

    /**
     * Starts Driter from the command-line.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Dwriter app = new Dwriter();

        // Configures look and feel
        /*javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
         javax.swing.JDialog.setDefaultLookAndFeelDecorated(false);

         try {
         javax.swing.UIManager.setLookAndFeel(
         javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
         } catch (Exception e) {
         }*/
        // Creates user interface
        new dwriter.ui.Frame.Creator(app).createAndWait();
    }

    /**
     * Loads default properties.
     */
    private void loadDefaultProperties() {

        Properties defaultProps = new Properties();
        InputStream defaultStream = Dwriter.class.
                getResourceAsStream(DEFAULT_PROPERTIES_FILENAME);
        if (defaultStream != null) {
            try {
                defaultProps.loadFromXML(defaultStream);
            } catch (IOException e) {
                e.getLocalizedMessage();
                System.err.println("Could not load default application "
                        + "properties.");
            } finally {
                try {
                    if (defaultStream != null) {
                        defaultStream.close();
                    }
                } catch (IOException e) {
                }
            }
        }

        props = defaultProps;
    }

    /**
     * Loads user properties.
     */
    private void loadProperties() {

        try {

            FileInputStream uProps = new FileInputStream(USER_PROPERTIES_FILENAME);
            props = new Properties();
            //InputStream uProps = Dwriter.class.
            //        getResourceAsStream(USER_PROPERTIES_FILENAME);
            props.loadFromXML(uProps);
            uProps.close();
        } catch (NullPointerException | IOException ex) {
            ex.getMessage();
            System.err.println("no user properties.");
            loadDefaultProperties();
            createUserProperties();
        }

    }

    /**
     *
     */
    private void createUserProperties() {
        try {
            Properties uProps = new Properties();
            uProps.setProperty("mainwindow.width",
                    props.getProperty("mainwindow.width"));
            uProps.setProperty("mainwindow.height",
                    props.getProperty("mainwindow.height"));
            uProps.setProperty("i18n.language",
                    props.getProperty("i18n.language"));

            FileOutputStream out = new FileOutputStream(USER_PROPERTIES_FILENAME);

            //File f = new File(USER_PROPERTIES_FILENAME);
            //OutputStream out = new FileOutputStream(f);
            uProps.storeToXML(out, null);
            out.close();
            props = uProps;
        } catch (NullPointerException | IOException e) {
            e.getMessage();
            System.err.println("Error creating user properties!");
        }

    }

    //public void saveUserProperties() {}
    /**
     * Returns the current user properties.
     *
     * @return the current user properties
     */
    public Properties getUserProperties() {
        return props;
    }

    /**
     * Exits the application.
     */
    public void exit() {
        // Stores properties
        /*if (props.size() > 0) {
         try {
         props.storeToXML("CleanSheets User Properties ("
         + DateFormat.getDateTimeInstance().format(new Date()) + ")");
         } catch (IOException e) {
         System.err.println("An error occurred while saving properties.");
         }
         }*/

        // Terminates the virtual machine
        System.exit(0);
    }

}
