/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2013 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */
package de.cau.cs.kieler.sim.benchmark;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * This Benchmark class is supposed to contain basic benchmark constants and code.
 * 
 * @author cmot
 * @kieler.design 2012-11-28 proposed
 * @kieler.rating 2012-11-28 proposed
 * 
 */
public class Benchmark {

    /** The Constant BENCHMARK_SIGNAL. */
    public static final String BENCHMARK_SIGNAL_TIME = "benchTime";

    /** The Constant BENCHMARK_SIGNAL. */
    public static final String BENCHMARK_SIGNAL_SOURCE = "benchSource";

    /** The Constant BENCHMARK_SIGNAL. */
    public static final String BENCHMARK_SIGNAL_EXECUTABLE = "benchExecutable";

    /** A Constant used for more acurate normed benchmark results. */
    public static final int BENCHMARK_NORMED_RUNS = 100000;

    //--------------------------------------------------------------------------

    /**
     * Adds timing measurement code.
     * 
     * @param filePath
     *            the file path
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String addTimingCode(final String cCodeString) throws IOException {
        StringBuilder returnCCodeString = new  StringBuilder();
        
        String[] lines = cCodeString.split("\n");
        for (String lineIn : lines) {

            // Before the main function, add
            // #include<cycle.h>
            // ticks t0, t1;
            if (lineIn.contains("int main")) {
              returnCCodeString.append("#include <usertime.h>\n");
              returnCCodeString.append("double t;\n");
            }
            
            // double elapsed(ticks t1, ticks t0);

            // Instead of the tick() function add
            // t0 = getticks();
            // tick();
            // t1 = getticks();
            // value = cJSON_CreateObject();
            // cJSON_AddItemToObject(value, "value",
            // cJSON_CreateNumber((double)((double)(t1)-(double)(t0))));
            // cJSON_AddItemToObject(value, "present", cJSON_CreateTrue());
            // cJSON_AddItemToObject(output, "cycles", value);
            if (lineIn.contains("tick();")) {
                returnCCodeString.append("resetusertime();");
                returnCCodeString.append("tick();");
                returnCCodeString.append("t =  getusertime();");
                // fileContent.add("value = cJSON_CreateObject();");
                // fileContent.add("cJSON_AddItemToObject(value, \"value\", "
                // + "cJSON_CreateNumber((double)((double)(t1)-(double)(t0))));");
                // fileContent.add("cJSON_AddItemToObject(value, \"present\", cJSON_CreateTrue());");
                returnCCodeString.append("cJSON_AddItemToObject(output, \"" + Benchmark.BENCHMARK_SIGNAL_TIME + "\""
                        + ", cJSON_CreateNumber((double)(((double) t)*1)));");
            } else {
                returnCCodeString.append(lineIn + "\n");
            }
        }
        return returnCCodeString.toString();
    }
    
    // ---------------------------------------------------------------------------------

    
    /**
     * Adds timing measurement code.
     * 
     * @param filePath
     *            the file path
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static String addTimingCodeFile(final String filePath) throws IOException {

        String newFilePath = filePath.replace(".c", ".timing.c");

//        LinkedList<String> fileContent = new LinkedList<String>();

        String fileContent = "";
        
        // Load original SC file
        FileInputStream fis = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String lineIn;
        while ((lineIn = br.readLine()) != null) {
            fileContent = lineIn + "\n";
        }
        br.close();
        fis.close();
        
        String fileOutContent = addTimingCode(fileContent);
        
        // Write out SC modified file
        PrintWriter out = new PrintWriter(newFilePath);
        out.print(fileOutContent);
        // for (String lineOut : fileContent) {
        // out.println(lineOut);
        // }
        out.close();

        return newFilePath;
    }
    
}
