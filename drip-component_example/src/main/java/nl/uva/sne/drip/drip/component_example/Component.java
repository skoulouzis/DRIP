/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.drip.component_example;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is an example components. It is a dumy components to demonstrate a
 * simple application logic
 *
 * @author S. Koulouziss
 */
public class Component {

    private int input;
    private final File inputTextFile;
    private final File inputBinFile;
    private final ExamplePOJO book;

    public Component(int input, File inputTextFile, File inputBinFile, ExamplePOJO book) {
        this.input = input;
        this.inputTextFile = inputTextFile;
        this.inputBinFile = inputBinFile;
        this.book = book;
    }

    ExampleResult run() throws Exception {

        ExampleResult res = new ExampleResult();
        res.output = input++;
        res.inputTextFile = inputTextFile;
        res.inputBinFile = inputBinFile;
        res.book = book;
        return res;
    }

}
