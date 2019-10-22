/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhax.rsa;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author fupn26
 */
public class RSAPelda {

    public static void main(String[] args) throws IOException {

        KulcsPar jSzereplo = new KulcsPar();

        String tisztaSzoveg = "Hello, Vilag!";

        String titkos_output = new String();

        //kódol
        FileOutputStream writer = new FileOutputStream("output.txt");
//        byte[] buffer = tisztaSzoveg.getBytes();
//        java.math.BigInteger[] titkos = new java.math.BigInteger[buffer.length];
        for (int idx = 0; idx < tisztaSzoveg.length(); ++idx) {
            String tisztaszoveg = tisztaSzoveg.substring(idx, idx + 1);
            tisztaszoveg = tisztaszoveg.toLowerCase();
            byte[] buffer = tisztaszoveg.getBytes();
            java.math.BigInteger[] titkos = new java.math.BigInteger[buffer.length];
            byte[] output = new byte[buffer.length];

            for (int i = 0; i < titkos.length; ++i) {
                titkos[i] = new java.math.BigInteger(new byte[]{buffer[i]});
                titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
                output[i] = titkos[i].byteValue();
                writer.write(titkos[i].toByteArray());
                titkos_output += titkos[i].toString();
            }
            writer.write('\n');
        }
//        for (int i = 0; i < titkos.length; ++i){
//            titkos[i] = new java.math.BigInteger(new byte[] {buffer[i]});
//            titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
//        }
//        
//        //dekódol
//        for (int i = 0; i < titkos.length; ++i){
//            titkos[i] = titkos[i].modPow(jSzereplo.d, jSzereplo.m);
//            buffer[i] = titkos[i].byteValue();
//        }
//        
//        System.out.println(new String (buffer));
        BufferedReader inputStream = new BufferedReader(new FileReader("output.txt"));
        int lines = 0;
        String line[] = new String[100000];
        while ((line[lines] = inputStream.readLine()) != null) {
            lines++;
        }
        inputStream.close();
        Karakterek kar[] = new Karakterek[1000];
        boolean found;
        kar[0] = new Karakterek(line[0]);
        int count = 1;
        for (int i = 1; i < lines; i++) {
            found = false;
            for (int j = 0; j < count; j++) {
                if (kar[j].getEncrypted().equals(line[i])) {
                    kar[j].increment();
                    found = true;
                    break;
                }
            }
            if (!found) {
                kar[count] = new Karakterek(line[i]);
                count++;
            }
        }
    }

}
