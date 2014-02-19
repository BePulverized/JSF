/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import static java.lang.Runtime.getRuntime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeroen
 */
public class OS_runtime {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Runtime runtimeObject;
        runtimeObject = getRuntime();
        List<String[]> output = new ArrayList<>();

        // Opdracht A
        int availableProcessors = runtimeObject.availableProcessors();
        String[] opdrachtA = new String[3];
        opdrachtA[0] = "Opdracht A: Aantal beschikbare Processoren: ";
        opdrachtA[1] = availableProcessors + " processoren ";
        opdrachtA[2] = "";
        output.add(opdrachtA);

        // Opdracht B
        int totalMemory = (int) runtimeObject.totalMemory();
        String[] opdrachtB = new String[3];
        opdrachtB[0] = "Opdracht B: Total Memory: ";
        opdrachtB[1] = readableFormat(totalMemory);
        opdrachtB[2] = "(" + totalMemory + " b)";
        output.add(opdrachtB);

        // Opdracht C
        int maxMemory = (int) runtimeObject.maxMemory();
        String[] opdrachtC = new String[3];
        opdrachtC[0] = "Opdracht C: Max Memory: ";
        opdrachtC[1] = readableFormat(maxMemory);
        opdrachtC[2] = "(" + maxMemory + " b)";
        output.add(opdrachtC);

        // Opdracht D
        int freeMemory = (int) runtimeObject.freeMemory();
        String[] opdrachtD = new String[3];
        opdrachtD[0] = "Opdracht D: Free Memory: ";
        opdrachtD[1] = readableFormat(freeMemory);
        opdrachtD[2] = "(" + freeMemory + " b)";
        output.add(opdrachtD);

        // Opdracht E
        String[] opdrachtE = new String[3];
        opdrachtE[0] = "Opdracht E: Max-Free Memory: ";
        opdrachtE[1] = readableFormat(maxMemory - freeMemory);
        opdrachtE[2] = "(" + (maxMemory - freeMemory) + " b)";
        output.add(opdrachtE);

        // Write Output
        List<String> formattedOutput = formatTable(output);
        for (String s : formattedOutput) {
            System.out.println(s);
        }
    }

    private static List<String> formatTable(List<String[]> table) {
        List<String> returner = new ArrayList<>();
        int[] largestColumnLength = new int[table.get(0).length];
        for (int row = 0; row < table.size(); row++) {
            for (int column = 0; column < table.get(row).length; column++) {
                if (table.get(row)[column].length() > largestColumnLength[column]) {
                    largestColumnLength[column] = table.get(row)[column].length();
                }
            }
        }
        for (String[] row : table){
            StringBuilder rowBuilder = new StringBuilder();
            for (int column = 0; column < table.get(0).length; column++){
                while (row[column].length() < largestColumnLength[column]){
                    row[column] += " ";
                }
                rowBuilder.append(row[column]);
            }
            returner.add(rowBuilder.toString());
        }
        return returner;
    }

    private static String readableFormat(int bites) {
        String suffix = " b ";
        double converted = bites;
        while (converted > 1000) {
            converted /= 1000;
            suffix = nextSuffix(suffix);
        }
        double tmpRounding = converted * 100;
        tmpRounding = Math.round(tmpRounding);
        converted = tmpRounding / 100;
        return converted + suffix;
    }

    private static String nextSuffix(String suffix) {
        String returner = null;
        switch (suffix) {
            case " b ":
                returner = " Kb ";
                break;
            case " Kb ":
                returner = " Mb ";
                break;
            case " Mb ":
                returner = " Gb ";
                break;
            case " Gb ":
                returner = " Tb ";
                break;
        }
        return returner;
    }
}
