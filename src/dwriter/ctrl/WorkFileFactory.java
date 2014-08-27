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
package dwriter.ctrl;

import dwriter.core.WorkFile;
import dwriter.core.WorkFileV1;
import java.io.File;

/**
 * Creates WorkFiles Objects.
 * 
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class WorkFileFactory {

    /**
     * Empty Constructor.
     */
    public WorkFileFactory() {

    }

    /**
     * Creates and returns a new WorkFile object.
     * In ths case is the first version (V1).
     * 
     * @param name file name
     * @param content file's content
     * @param file file object
     * @return workfile
     */
    public WorkFile getWorkFileV1(String name, String content, File file) {
        return new WorkFileV1(name, content, file);
    }
}
