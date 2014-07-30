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
package dwriter.core;

/**
 * This is the first implementation of WorkFile interface.
 *
 * @author João Andrade (joaodeaps@gmail.com)
 */
public class WorkFileV1 implements WorkFile {

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String canonicalPath;

    /**
     * 
     */
    public WorkFileV1() {
        this.name = "";
        this.content = "";
        this.canonicalPath = null;
    }
    
    /**
     * 
     * @param name
     * @param content
     * @param canonicalPath 
     */
    public WorkFileV1(String name, String content, String canonicalPath) {
        this.name = name;
        this.content = content;
        this.canonicalPath = canonicalPath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getCanonicalPath() {
        return canonicalPath;
    }

}
