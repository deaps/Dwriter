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
package dwriter.ui.ctrl;

import dwriter.Dwriter;
import dwriter.core.WorkFile;
import dwriter.ctrl.WorkFileFactory;
import dwriter.ui.FileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import static javax.swing.Action.ACCELERATOR_KEY;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

/**
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class OpenAction extends BaseAction {

    private final Dwriter app;

    public OpenAction(Dwriter app) {
        this.app = app;
    }

    @Override
    protected String getName() {
        return "Open...";
    }

    @Override
    protected void defineProperties() {
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
        //putValue(SMALL_ICON, new ImageIcon(Dwriter.class.getResource("res/img/new.gif")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // First checks if the active file can be saved
        // THIS IS FOR THE FIRST VERSION!
        // When the multiple tabs system is implemented, the OpenAction
        // will only create a new tab and a new workbook
        boolean flag = app.saveActiveWorkFile();
        if (flag) {
            WorkFileFactory workFileFactory = new WorkFileFactory();
            FileChooser chooser = new FileChooser();
            File file;
            String name;
            String content;

            int r = chooser.showOpenDialog(app.getFrame());
            if (r == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();

                name = file.getName();
                content = app.loadContent(file);

                WorkFile workFile = workFileFactory.getWorkFileV1(name, content,
                        file);

                app.addNewWorkFile(workFile);
                app.getFrame().reload();
            }
        }
    }
}
