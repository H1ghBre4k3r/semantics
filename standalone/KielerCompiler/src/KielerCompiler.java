/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://www.informatik.uni-kiel.de/rtsys/kieler/
 * 
 * Copyright 2014 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 * See the file epl-v10.html for the license text.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The KIELER Compiler TCP client interface for compiling EMF models using KiCo. This is a plain
 * Java Application, and no Eclipse Plugin. This application can be run on any computer with Java
 * installed. It communicates with a running Eclipse instance with TCP. This Eclipse instance must
 * have the KiCo Web TCP server up and running in order to properly function.
 * 
 * @author cmot
 * @kieler.design 2014-06-08 proposed
 * @kieler.rating 2014-06-08 proposed yellow
 * 
 *                Example Call: C:\DATA\KEPLER\RemoteKielerCompiler\bin>cat ../ABRO.sct | java
 *                RemoteKielerCompiler localhost:5555 sct EXTENDED
 * 
 */
public class KielerCompiler {

    /**
     * The main method interprets all program parameters and starts the remote compilation.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {

        boolean verbose = false;
        boolean strict = false;
        String inputFile = null;
        String outputFile = null;
        ArrayList<String> includeFiles = new ArrayList<String>();

        if (args.length < 1 || args[0].startsWith("-")) {
            System.out
                    .println("\n"
                            + "====================\n"
                            + "== KielerCompiler ==\n"
                            + "====================\n"
                            + "Usage: \n"
                            + "  KielerCompiler <host>:<port> [Options] <transformation 1> ... <transformation n>\n"
                            + "Example 1:\n"
                            + "  cat <FILE.sct> | java -jar KielerCompiler.jar localhost:5555 EXTENDED CORE > code.c\n"
                            + "Example 2:\n"
                            + "  java -jar KielerCompiler.jar 5555 -f FILE.sct -o code.c CODEGENERATION \n"
                            + "\n"
                            + "Options:\n"
                            + "-f <filename> : Use a specific input file\n"
                            + "-i <filename> : Use each specific additional included input file that is referenced\n"
                            + "-o <filename> : Use a specific output file\n"
                            + "-v            : Use verbose compilation, more error messages\n"
                            + "-s            : Use strict mode in which only selected transformations are applied\n");
            return;
        }

        String hostAndPort[] = args[0].split(":");
        String host = hostAndPort[0];
        int port = 5555;
        if (hostAndPort.length > 1) {
            try {
                port = Integer.parseInt(hostAndPort[1]);
            } catch (Exception e) {
            }
        } else {
            try {
                port = Integer.parseInt(hostAndPort[0]);
            } catch (Exception e) {
            }
            if (port > 0) {
                // if only the port was specified, assume localhost
                host = "localhost";
            } else {
                // if only the hostname was specified, assume 5555 as the standard port
                port = 5555;
            }
        }

        String transformations = "";
        for (int c = 1; c < args.length; c++) {

            String option = args[c];

            if (option.startsWith("-")) {
                if (option.equals("-f") || option.equals("--file")) {
                    if (c + 1 < args.length) {
                        inputFile = args[c + 1];
                        c++;
                    }
                } else if (option.equals("-i") || option.equals("--include")) {
                    if (c + 1 < args.length) {
                        String includeFile = args[c + 1];
                        includeFiles.add(includeFile);
                        c++;
                    }
                } else if (option.equals("-o") || option.equals("--output")) {
                    if (c + 1 < args.length) {
                        outputFile = args[c + 1];
                        c++;
                    }
                } else if (option.equals("-v") || option.equals("--verbose")) {
                    verbose = true;
                } else if (option.equals("-s") || option.equals("--strict")) {
                    strict = true;
                }
            } else {
                if (!transformations.equals("")) {
                    transformations += ",";
                }
                transformations += args[c];
            }

        }

        // System.out.println("host: " + host);
        // System.out.println("port: " + port);
        // System.out.println("inputFile: " + inputFile);
        // System.out.println("outputFile: " + outputFile);
        // System.out.println("verbose: " + verbose);
        // System.out.println("strict: " + strict);
        // System.out.println("transformations: " + transformations);

        
        ArrayList<String> models = new ArrayList<String>();
        
        String model = readInputModel(inputFile);
        models.add(model);

        for (String includeFile : includeFiles) {
            String includedModel = readInputModel(includeFile);
            //model += "\n\n" + includedModel;
            models.add(includedModel);
        }
        // System.out.println("model: " + model);

        CompilationResult compilationResult =
                remoteCompile(host, port, outputFile, verbose, strict, models, transformations);

        if (outputFile == null || outputFile.trim().equals("")) {
            System.out.println(compilationResult.model);
        } else {
            writeOutputModel(outputFile, compilationResult.model);
        }
        if (compilationResult.error != null && compilationResult.error.length() > 0) {
            if (verbose || compilationResult.model.length() == 0) {
                System.out.println(compilationResult.error);
            }
            if (compilationResult.model.length() == 0) {
                System.exit(1);
            }
        }
        System.exit(0);

    }

    // -------------------------------------------------------------------------

    /**
     * Read input model from file.
     * 
     * @param inputFile
     *            the input file
     * @return the string
     */
    private static String readInputModel(String inputFile) {
        String model = "";
        if (inputFile == null || inputFile.trim().equals("")) {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String s;
            try {
                while (((s = in.readLine()) != null)) {
                    if (!model.equals("")) {
                        model += "\n";
                    }
                    model += s;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if ((new File(inputFile)).exists()) {
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader(inputFile));
                    String line = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    String ls = System.getProperty("line.separator");
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                        stringBuilder.append(ls);
                    }
                    reader.close();
                    model = stringBuilder.toString();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // file non exits
                System.out.println("Error: Input file not exists.");
                System.exit(1);
            }
        }
        return model;
    }

    // -------------------------------------------------------------------------

    /**
     * Write output model to file.
     * 
     * @param outputFile
     *            the output file
     * @param modelAsText
     *            the model as text
     */
    private static void writeOutputModel(String outputFile, String model) {
        PrintWriter out;
        try {
            out = new PrintWriter(outputFile);
            out.println(model);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Remote compile.
     * 
     * @param host
     *            the host
     * @param port
     *            the port
     * @param model
     *            the model
     * @param transformations
     *            the transformations
     * @return the string
     */
    public static CompilationResult remoteCompile(String host, int port, String outputFile,
            boolean verbose, boolean strict, ArrayList<String> models, String transformations) {
        CompilationResult result = new CompilationResult("", "");

        String options = "";
        if (verbose) {
            options += "v";
        }
        if (strict) {
            options += "s";
        }

        try {
            TCPClient client = new KielerCompiler.TCPClient(host, port);
            client.sndMessage(transformations + "\n");
            
            String header = "";
            for (String model : models) {
                if (header.length() > 0) {
                    header += ":";
                }
                header += model.split("\n").length + "";
            }
            header += options + "\n";
            client.sndMessage(header);
            
            for (String model : models) {
                client.sndMessage(model + "\n");
            }

            result = client.rcvCompilationResult();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    // -------------------------------------------------------------------------

    /**
     * The Class CompilationResult.
     */
    static private class CompilationResult {
        public String model;
        public String error;

        CompilationResult(String model, String error) {
            this.model = model;
            this.error = error;
        }
    }

    // -------------------------------------------------------------------------

    /**
     * The internal Class TCPClient.
     */
    static private class TCPClient {
        Socket socket = null;

        public TCPClient(String host, int port) throws IOException {
            socket = new Socket(host, port);
        }

        void sndMessage(String msg) throws IOException {
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter printWriter = new PrintWriter(out);
            printWriter.print(msg);
            printWriter.flush();
            out.flush();
        }

        CompilationResult rcvCompilationResult() throws IOException {
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);

            String[] linesArray = bufferedReader.readLine().split(":");
            int linesModel = Integer.parseInt(linesArray[0]);
            int linesError = 0;
            if (linesArray.length > 1) {
                linesError = Integer.parseInt(linesArray[1]);
            }

            String model = "";
            String error = "";
            String s;
            while (linesModel > 0) {
                s = bufferedReader.readLine();
                // System.out.println("M" + linesModel + ". " + s);
                linesModel--;
                if (!model.equals("")) {
                    model += "\n";
                }
                model += s;
            }
            while (linesError > 0) {
                s = bufferedReader.readLine();
                // System.out.println("E" + linesError + ". " + s);
                linesError--;
                if (!error.equals("")) {
                    error += "\n";
                }
                error += s;
            }

            return new CompilationResult(model, error);
        }

        void close() throws IOException {
            socket.close();
        }

    }

    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------

}
