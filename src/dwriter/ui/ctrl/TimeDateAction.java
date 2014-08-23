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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static javax.swing.Action.ACCELERATOR_KEY;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.KeyStroke;

/**
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class TimeDateAction extends BaseAction {

    private final Dwriter app;

    public TimeDateAction(Dwriter app) {
        this.app = app;
    }

    @Override
    protected String getName() {
        return "Time/Date";
    }

    @Override
    protected void defineProperties() {
        putValue(MNEMONIC_KEY, KeyEvent.VK_F5);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
        //putValue(SMALL_ICON, new ImageIcon(Dwriter.class.getResource("res/img/new.gif")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //CAN BE IMPROVED 
        // after the action, the caret position goes to the end of the file)
        int selectionStart;
        int selectionEnd;
        String temp1;
        String temp2;

        Calendar timeDate = Calendar.getInstance();
        // Timedate property in i18n
        SimpleDateFormat dataf = new SimpleDateFormat("h:m a dd/MM/yyyy");
        String timeDateAdd = dataf.format(timeDate.getTime());

        selectionStart = app.getFrame().getTextArea().getSelectionStart();
        selectionEnd = app.getFrame().getTextArea().getSelectionEnd();

        temp1 = app.getFrame().getTextArea().getText().
                substring(0, selectionStart);
        temp2 = app.getFrame().getTextArea().getText().
                substring(selectionEnd);
        app.getFrame().getTextArea().
                setText(temp1 + "" + timeDateAdd + "" + temp2);

    }

}
