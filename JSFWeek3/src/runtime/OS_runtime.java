/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runtime;

import static java.lang.Runtime.getRuntime;

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
        System.out.println(runtimeObject.availableProcessors());
        System.out.println(runtimeObject.maxMemory());
        System.out.println(runtimeObject.freeMemory());
        System.out.println(runtimeObject.totalMemory());
        System.out.println(runtimeObject.());
        System.out.println(runtimeObject.freeMemory());
    }
}
