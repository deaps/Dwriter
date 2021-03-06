/*
 * The MIT License
 *
 * Copyright 2014 Joao Andrade.
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
package dwriter.ui;

import dwriter.Dwriter;
import dwriter.i18n.I18N;
import dwriter.ui.ctrl.AboutAction;
import dwriter.ui.ctrl.CopyAction;
import dwriter.ui.ctrl.CutAction;
import dwriter.ui.ctrl.DeleteAction;
import dwriter.ui.ctrl.ExitAction;
import dwriter.ui.ctrl.KeyListener;
import dwriter.ui.ctrl.NewAction;
import dwriter.ui.ctrl.OpenAction;
import dwriter.ui.ctrl.PasteAction;
import dwriter.ui.ctrl.RedoAction;
import dwriter.ui.ctrl.SaveAction;
import dwriter.ui.ctrl.SaveAsAction;
import dwriter.ui.ctrl.SelectAllAction;
import dwriter.ui.ctrl.TimeDateAction;
import dwriter.ui.ctrl.UndoAction;
import dwriter.ui.ctrl.UndoListener;
import dwriter.ui.ctrl.WrapTextAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

/**
 * The main frame of the GUI.
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class Frame extends JFrame {

    /**
     * The Dwriter application
     */
    private Dwriter app;

    /**
     * Part of the title in the top of the frame
     */
    private static final String PARTIAL_FRAME_TITLE = " - Dwriter";

    /**
     * Frame's KeyListener
     */
    private KeyListener keyListener;

    /**
     * UndoManager for the document
     */
    private final UndoManager undoManager = new UndoManager();

    /**
     * Temporary memory section for copy/cut/paste operations
     */
    private String heap;

    /*Design Vars*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu;
    private JMenuItem menuItem;
    private JTextArea textArea;
    private JScrollPane textAreaScrollPane;

    /**
     * Document object used for undo/redo operations
     */
    private Document document;

    /**
     * Class Constructor.
     *
     * @param app
     * @param title
     */
    public Frame(Dwriter app, String title) {
        super(title);
        this.app = app;
        keyListener = new KeyListener(app);

        createWindow();
        defaultConfig();
    }

    /**
     * Creates the window.
     */
    private void createWindow() {

        // Icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                Frame.class.getResource("resources/img/drawing.png")));

        //menuBar
        setJMenuBar(menuBar);

        //menu file
        menu = new JMenu(I18N.getInstance().getString("frame_menu_file_title"));
        menu.setMnemonic(I18N.getInstance().
                getString("frame_menu_file_mnemonic").charAt(0));

        menuItem = new JMenuItem(new NewAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new OpenAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new SaveAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new SaveAsAction(app));
        menu.add(menuItem);

        menu.addSeparator();

// NOT YET SUPPORTED 
//        it = new JMenuItem("Print...");
//        it.addActionListener(tl);
//        it.setMnemonic('p');
//        it.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
//        me.add(it);
//
//        me.addSeparator();
        menuItem = new JMenuItem(new ExitAction(app));
        menu.add(menuItem);

        menuBar.add(menu);

        //menu editar
        menu = new JMenu(I18N.getInstance().getString("frame_menu_edit_title"));
        menu.setMnemonic(I18N.getInstance().
                getString("frame_menu_edit_mnemonic").charAt(0));

        menuItem = new JMenuItem(new UndoAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new RedoAction(app));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem(new CutAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new CopyAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new PasteAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new DeleteAction(app));
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem(new SelectAllAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new TimeDateAction(app));
        menu.add(menuItem);

        menuBar.add(menu);

        //menu format
        menu = new JMenu(I18N.getInstance().
                getString("frame_menu_format_title"));
        menu.setMnemonic(I18N.getInstance().
                getString("frame_menu_format_mnemonic").charAt(0));

        menuItem = new JMenuItem(new WrapTextAction(app));
        menu.add(menuItem);

        menuBar.add(menu);

        menu = new JMenu(I18N.getInstance().
                getString("frame_menu_about_title"));
        menu.setMnemonic(I18N.getInstance().
                getString("frame_menu_about_mnemonic").charAt(0));

        menuItem = new JMenuItem(new AboutAction(app));
        menu.add(menuItem);

        menuBar.add(menu);

        //CENTER
        textArea = new JTextArea();
        textArea.addKeyListener(keyListener);
        textArea.setText(app.getActiveWorkFile().getContent());
        textArea.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        document = textArea.getDocument();
        document.addUndoableEditListener(new UndoListener(undoManager));

        /*if (file.isFile()) {
         try {
         ta.setFont(DWriter.getFont());
         ta.setBackground(DWriter.getBackground());
         ta.setForeground(DWriter.getForeground());
         if (!DWriter.getWrapMode()) {
         taSP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
         }
         ta.repaint();
         } catch (NullPointerException ex) {
         System.out.println(ex.getMessage());
         }
         }*/
        add(textAreaScrollPane, BorderLayout.CENTER);
    }

    /**
     * Reloads the frame. Acts lika a refresh button.
     */
    public void reload() {
        // CHANGES ARE NEEDED FOR THE NEW VERSION WITH MULTIPLE TABS
        setTitle(app.getActiveWorkFile().getName() + PARTIAL_FRAME_TITLE);
        textArea.setText(app.getActiveWorkFile().getContent());
    }

    /**
     * Returns the Undo Manager.
     * 
     * @return
     */
    public UndoManager getUndoManager() {
        return undoManager;
    }

    /**
     * Gets the text area. With the content currently on display.
     * @return TextArea
     */
    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * Gets the main scrollpane.
     * @return JScrollPane
     */
    public JScrollPane getTextAreaScrollPane() {
        return textAreaScrollPane;
    }

    /**
     * Gets the heap content.
     * @return heap's content
     */
    public String getHeap() {
        return heap;
    }

    /**
     * Sets  content to the heap.
     * 
     * @param heap
     */
    public void setHeap(String heap) {
        this.heap = heap;
    }

    /**
     * The defaultConfig sets the properties of the DWriterGUI Object.
     */
    private void defaultConfig() {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(200, 200);
        setUserWindowDimensions();
        setMinimumSize(new Dimension(200, 200));
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                app.exit();
            }
        });
    }

    /**
     * This function will get the width and the height from the properties file
     * in order to set the last dimensions in use. In case of some error, this
     * function will set a default dimension.
     */
    private void setUserWindowDimensions() {

        int width;
        int height;

        try {
            width = Integer.parseInt(app.getUserProperties()
                    .getProperty("mainwindow.width"));
            height = Integer.parseInt(app.getUserProperties()
                    .getProperty("mainwindow.height"));
        } catch (NumberFormatException e) {
            width = 500;
            height = 550;
        }

        setSize(width, height);
    }

    /**
     * A utility for creating a Frame on the AWT event dispatching thread.
     *
     * @author João Andrade (joaodeaps@gmail.com)
     */
    public static class Creator implements Runnable {

        /**
         * The component that was created
         */
        private Frame frame;

        /**
         * The Dwriter application
         */
        private Dwriter app;

        /**
         * Class Constructor.
         *
         * @param app
         */
        public Creator(Dwriter app) {
            this.app = app;
        }

        /**
         * Creates a component on the AWT event dispatching thread.
         *
         * @return the component that was created
         */
        public Frame createAndWait() {
            try {
                EventQueue.invokeAndWait(this);
            } catch (InterruptedException |
                    java.lang.reflect.InvocationTargetException e) {
                return null;
            }

            return frame;
        }

        /**
         * Asks the event queue to create a component at a later time. (Included
         * for conformity.)
         */
        public void createLater() {
            EventQueue.invokeLater(this);
        }

        @Override
        public void run() {
            frame = new Frame(app, app.getActiveWorkFile().getName()
                    + PARTIAL_FRAME_TITLE);
        }

    }
}
