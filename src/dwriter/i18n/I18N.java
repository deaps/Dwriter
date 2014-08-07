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
package dwriter.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton used to retrive the application translations.
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class I18N {

    /**
     * The singleton instance.
     */
    private static I18N instance = null;
    
    /**
     * The resource name of the bundle.
     */
    private final String MESSAGES_BUNDLE = "dwriter.i18n.lang.MessagesBundle";
    
    /**
     * The Locale object, if the wanted Locale is diferent of the current 
     * machine.
     */
    private Locale locale;
    
    /**
     * A bundle with the translation Strings
     */
    private ResourceBundle labels;
    
    /**
     * Main constructor. for private use.
     * @param language 
     */
    private I18N(Lang language) {
        if (language == null) {
            labels = ResourceBundle.getBundle(MESSAGES_BUNDLE);
        } else {
            locale = new Locale(language.name().toLowerCase());
            labels = ResourceBundle.getBundle(MESSAGES_BUNDLE, locale);
        }
    }

    /**
     * The getInstance method of the singleton.
     * 
     * @return instance
     */
    public static I18N getInstance() {
        if (instance == null) {
            instance = new I18N(null);
        } 
        return instance;
    }

    /**
     * Changes the language of the application.
     * @param language target language
     */
    public void setLanguage(Lang language) {
        locale = new Locale(language.name().toLowerCase());
        labels = ResourceBundle.getBundle(MESSAGES_BUNDLE, locale);
    }
    
    /**
     * This method returns the correspondent string of the label passed by
     * parameter.
     * @param label
     * @return 
     */
    public String getString(String label) {
        return labels.getString(label);
    }
}
