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
import dwriter.i18n.I18N;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static javax.swing.Action.ACCELERATOR_KEY;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class AboutAction extends BaseAction {

    private final Dwriter app;

    public AboutAction(Dwriter app) {
        this.app = app;
    }

    @Override
    protected String getName() {
        return I18N.getInstance().getString("ctrl_about_action_name");
    }

    @Override
    protected void defineProperties() {
        putValue(MNEMONIC_KEY, KeyEvent.VK_F1);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
        //putValue(SMALL_ICON, new ImageIcon(Dwriter.class.getResource("res/img/new.gif")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(app.getFrame(), 
                I18N.getInstance().getString("ctrl_about_action_dialog1")
                + I18N.getInstance().getString("ctrl_about_action_dialog2")
                + I18N.getInstance().getString("ctrl_about_action_dialog3"),
                I18N.getInstance().getString("ctrl_about_action_dialog4"),
                JOptionPane.INFORMATION_MESSAGE);
    }

}
