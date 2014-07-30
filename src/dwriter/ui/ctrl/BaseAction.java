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

import dwriter.ui.BlankIcon;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * An base-class for actions.
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public abstract class BaseAction extends AbstractAction {

    /**
     * Creates a new base action.
     *
     */
    public BaseAction() {
        // Configures action
        String name = getName();
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
        putValue(ACTION_COMMAND_KEY, name);
        putValue(SMALL_ICON, new BlankIcon(16, 16));
        defineProperties();
    }

    /**
     * Returns the action's name.
     *
     * @return the action's name, which is used as short description and action
     * command
     */
    protected abstract String getName();

    /**
     * Defines the action's properties.
     */
    protected abstract void defineProperties();

    /**
     * Shows the user an error message.
     * @param message
     */
    protected void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
