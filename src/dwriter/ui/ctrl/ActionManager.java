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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 * This class manage the UI actions.
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class ActionManager {

    /**
     * The map of file actions
     */
    private Map<String, BaseAction> actions = new HashMap<>();

    private final Dwriter app;
    //private final UIController uiController;
    private final JFileChooser chooser;

    /**
     * Creates a new action manager.
     *
     * @param app the Dwriter application
     * @param uiController the user interface controller
     * @param chooser a file chooser
     */
    public ActionManager(Dwriter app, /*UIController uiController,*/
            JFileChooser chooser) {
        //ActionEnabler enabler = new ActionEnabler();
        //app.addSpreadsheetAppListener(enabler);
        //uiController.addEditListener(enabler);
        this.app = app;
        //this.uiController = uiController;
        this.chooser = chooser;
    }

    /**
     * Returns the action with the given identifier
     *
     * @param identifier the unique identifier of the action
     * @return the action of the given type
     */
    public BaseAction getAction(String identifier) {
        return actions.get(identifier);
    }

    /**
     * Registers the given action with the manager
     *
     * @param identifier the unique identifier of the action
     * @param action the action to register
     */
    public void registerAction(String identifier, BaseAction action) {
        actions.put(identifier, action);
        /*if (action.requiresModification()) {
         modificationActions.add(action);
         }
         if (action.requiresFile()) {
         fileActions.add(action);
         }*/
    }
}
