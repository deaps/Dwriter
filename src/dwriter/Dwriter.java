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
import dwriter.ctrl.WorkFileFactory;
import dwriter.i18n.I18N;
import dwriter.ui.FileChooser;
import dwriter.ui.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
    private static final String USER_PROPERTIES_FILENAME = "user_props.xml";

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
     * The main window of the application.
     */
    private Frame frame;

    /**
     * Creates the Dwriter application.
     */
    public Dwriter() {

        workFileFactory = new WorkFileFactory();
        // Loads default properties
        loadProperties();

        addNewWorkFile();

        // Loads extensions
        //ExtensionManager.getInstance();
        // Creates user interface
        frame = new dwriter.ui.Frame.Creator(this).createAndWait();
    }

    /**
     * Adds a new workfile to the arraylist of workfiles in display on the
     * frame. And sets this new WorkFile as the active one.
     */
    public void addNewWorkFile() {
        // For now its only one tab...
        // otherwise remove this if
        if (workfiles.size() == 1) {
            workfiles.remove(0);
        }

        WorkFile obj = workFileFactory.getWorkFileV1(I18N.getInstance().
                getString("frame_top_title"), "", null);

        workfiles.add(obj);

        activeWorkFile = workfiles.get(workfiles.size() - 1);

    }

    /**
     * Adds a new workfile to the arraylist of workfiles in display on the
     * frame. And sets this new WorkFile as the active one.
     *
     * @param workFile
     */
    public void addNewWorkFile(WorkFile workFile) {
        // For now its only one tab...
        workfiles.remove(0);

        workfiles.add(workFile);
        activeWorkFile = workfiles.get(workfiles.size() - 1);
    }

    /**
     *
     * @return list of workfiles
     */
    public ArrayList<WorkFile> getWorkFiles() {
        return workfiles;
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
     *
     * @return the main Frame
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     *
     * @return
     */
    public boolean saveActiveWorkFile() {
        int op = -1;
        // Checks if its a new file or not ()
        if (activeWorkFile.getFile() != null) {
            String content = loadContent(activeWorkFile.getFile());
            // Checks if the content has been altered
            if (!activeWorkFile.getContent().equals(content)) {
                op = yesNoCancelMessage(
                        I18N.getInstance().getString("yncm_save_changes"),
                        I18N.getInstance().getString("yncm_save_changes_top"));
                switch (op) {
                    case 0:
                        // hit the Yes option (will save)
                        saveFile(activeWorkFile);
                        return true;

                    case 1:
                        // hit the No option (wont save)
                        return true;

                    default:
                        break;
                }
            } else {
                // No need to save (No changes was made to the active work file)
                return true;
            }
        } else if (!activeWorkFile.getContent().equals("")) {
            op = yesNoCancelMessage(
                    I18N.getInstance().getString("yncm_save_changes"),
                    I18N.getInstance().getString("yncm_save_changes_top"));
            switch (op) {
                case 0:
                    // hit the Yes option (will save)
                    saveAsFile(activeWorkFile);
                    return true;

                case 1:
                    // hit the No option (wont save)
                    return true;

                default:
                    break;
            }
        } else {
            return true;
        }

        return false;
    }

    /**
     *
     * @param workFile
     */
    public void saveFile(WorkFile workFile) {
        Formatter formatter;
        try {
            formatter = new Formatter(workFile.getFile());
            formatter.format(workFile.getContent());
            formatter.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param workFile
     */
    public void saveAsFile(WorkFile workFile) {
        FileChooser chooser = new FileChooser(
                I18N.getInstance().getString("fchooser_saveas_title"));
        Formatter formatter;
        File saveFile;

        int r = chooser.showSaveDialog(frame);
        if (r == JFileChooser.APPROVE_OPTION) {
            saveFile = chooser.getSelectedFile();
            try {
                formatter = new Formatter(saveFile);
                formatter.format(workFile.getContent());
                formatter.close();

            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     *
     * @param message
     * @param top
     * @return
     */
    public int yesNoCancelMessage(String message, String top) {
        int op = -1;
        Object[] options = {I18N.getInstance().getString("yncm_option_yes"),
            I18N.getInstance().getString("yncm_option_no"),
            I18N.getInstance().getString("yncm_option_cancel")};
        op = JOptionPane.showOptionDialog(frame,
                message,
                top,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                2);

        return op;
    }

    /**
     * This method loads the data of the selected file. If an error occurred it
     * will return null.
     *
     * @param file
     * @return file content
     */
    public String loadContent(File file) {

        Scanner in = null;
        String data;
        StringBuilder stringB = new StringBuilder((int) file.length());
        String lineSeparator = System.getProperty("line.separator");

        try {
            in = new Scanner(file);

            while (in.hasNextLine()) {
                stringB.append(in.nextLine()).append(lineSeparator);
            }

            data = stringB.toString();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            in.close();
        }

        return data;
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

            FileInputStream uProps = new FileInputStream(
                    USER_PROPERTIES_FILENAME);
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

            FileOutputStream out = new FileOutputStream(
                    USER_PROPERTIES_FILENAME);

            //File f = new File(USER_PROPERTIES_FILENAME);
            //OutputStream out = new FileOutputStream(f);
            uProps.storeToXML(out, null);
            out.close();
            props = uProps;
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Error creating user properties!");
        }

    }

    public void saveUserProperties() {
        props.replace("mainwindow.width", "" + frame.getWidth());
        props.replace("mainwindow.height", "" + frame.getHeight());
        // falta language

        // save properties in file
        try {
            FileOutputStream out = new FileOutputStream(
                    USER_PROPERTIES_FILENAME);

            //File f = new File(USER_PROPERTIES_FILENAME);
            //OutputStream out = new FileOutputStream(f);
            props.storeToXML(out, null);
            out.close();
        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Error saving user properties!");
        }
    }

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

        boolean flag = saveActiveWorkFile();
        if (flag) {
            // Stores properties
            saveUserProperties();

            // Terminates the virtual machine
            System.exit(0);
        }

    }

    /**
     * Starts Driter from the command-line.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        Dwriter app = new Dwriter();
    }

}
