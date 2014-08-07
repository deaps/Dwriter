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
import dwriter.ui.ctrl.NewAction;
import dwriter.ui.ctrl.OpenAction;
import dwriter.ui.ctrl.SaveAction;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
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

    /*Design Vars*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu;
    private JMenuItem menuItem;
    private JTextArea textArea;
    private JScrollPane textAreaScrollPane;

    /*Edit Vars*/
    private String tempHeap = null;
    private UndoManager undoManager = new UndoManager();
    private int ini = -1;
    private int fin = -1;
    /*Text/Format Vars*/
    //private DWriterOptions options;
    //private InPut inPut;
    //private OutPut outPut;
    //private File file = null;
    private Document doc;
    private String originalData;
    private String docTitle;

    /**
     * Class Constructor.
     *
     * @param app
     */
    public Frame(Dwriter app) {
        this.app = app;

        createWindow();
        defaultConfig();
    }

    /**
     * Creates the window.
     */
    private void createWindow() {
        
        //menuBar
        
        //this.setIcon(getClass().getResource("drawing.png")).getImage();
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("drawing.png")));
        
        
        setJMenuBar(menuBar);

        //menu file
        menu = new JMenu("File");

        menuItem = new JMenuItem(new NewAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new OpenAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem(new SaveAction(app));
        menu.add(menuItem);

        menuItem = new JMenuItem("Save as...");
        //it.addActionListener(tl);
        menuItem.setMnemonic('a');
        menuItem.setAccelerator(KeyStroke.getKeyStroke("shift ctrl S"));
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

        menuItem = new JMenuItem("Exit");
        //it.addActionListener(tl);
        //it.setMnemonic('e');
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        menu.add(menuItem);

        menuBar.add(menu);
        //menu editar
        menu = new JMenu("Edit");

        menuItem = new JMenuItem("Reverse");
        //it.addActionListener(tl);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Cut");
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        //it.addActionListener(tl);
        
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Copy");
        //it.addActionListener(tl);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Paste");
        //it.addActionListener(tl);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Delete");
        //it.addActionListener(tl);
        
        
        menu.add(menuItem);
        
        menu.addSeparator();
        
        menuItem = new JMenuItem("Select all");
        //it.addActionListener(tl);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));

        
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Time/Date");
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
        //it.addActionListener(tl);
        
        menu.add(menuItem);

        menuBar.add(menu);

        //menu format
        menu = new JMenu("Format");

        menuItem = new JMenuItem("Wrap text");
        //it.addActionListener(tl);
        //it.setAccelerator(KeyStroke.getKeyStroke("ctrl "));
        
        menu.add(menuItem);

        menuItem = new JMenuItem("Text options");
        //it.addActionListener(tl);
        menu.add(menuItem);

        menuBar.add(menu);

        menu = new JMenu("About");

        menuItem = new JMenuItem("About DWriter");
        //it.addActionListener(tl);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
        
        menu.add(menuItem);
        
        menuBar.add(menu);

        //CENTER

        textArea = new JTextArea();
        //ta.setText(data);
        textArea.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

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
        doc = textArea.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        add(textAreaScrollPane, BorderLayout.CENTER);
    }
    
    /**
     * The defaultConfig sets the properties of the DWriterGUI Object.
     */
    private void defaultConfig() {
        

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(200, 200);
        setSize(500, 550);
        setMinimumSize(new Dimension(200, 200));
        setVisible(true);
        
        /*addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                //exit();
            }
        });*/
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
            } catch (InterruptedException e) {
                return null;
            } catch (java.lang.reflect.InvocationTargetException e) {
                e.printStackTrace();
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
            frame = new Frame(app);
        }

    }
}
