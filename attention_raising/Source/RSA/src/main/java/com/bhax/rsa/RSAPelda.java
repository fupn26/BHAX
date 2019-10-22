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
import java.io.PrintWriter;
import java.util.Base64;

/**
 *
 * @author fupn26
 */
public class RSAPelda {

    public static void main(String[] args) throws IOException {

        KulcsPar jSzereplo = new KulcsPar();

        String tisztaSzoveg = "Thus materials discussed in the course should be made available to students on the platform or made easily accessible online. Most often, this kind of access is best provided by works in the public domain.";

        System.out.println("EREDETI");
        System.out.println(tisztaSzoveg);
        
        //kódolás karakterenként és eredmény kiírása fájlba
        PrintWriter writer = new PrintWriter("output.txt");
        for (int idx = 0; idx < tisztaSzoveg.length(); ++idx) {
            Character tisztaszoveg = tisztaSzoveg.charAt(idx);
            tisztaszoveg = Character.toLowerCase(tisztaszoveg);
            byte[] buffer = tisztaszoveg.toString().getBytes();
            java.math.BigInteger[] titkos = new java.math.BigInteger[buffer.length];
            byte[] output = new byte[buffer.length];

            for (int i = 0; i < titkos.length; ++i) {
                titkos[i] = new java.math.BigInteger(new byte[]{buffer[i]});
                titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
                output[i] = titkos[i].byteValue();
                writer.print(titkos[i]);
            }
            writer.println();
        }
        writer.close();
        
        //fájl beolvasás
        BufferedReader inputStream = new BufferedReader(new FileReader("output.txt"));
        int lines = 0;
        String line[] = new String[100000];
        while ((line[lines] = inputStream.readLine()) != null) {
            lines++;
        }
        inputStream.close();
        
        //előforduló elemek gyűjtése tömbbe
        Karakterek kar[] = new Karakterek[1000];
        boolean found;
        kar[0] = new Karakterek(line[0]);
        int count = 1;
        for (int i = 1; i < lines; i++) {
            found = false;
            for (int j = 0; j < count; j++) {
                //ha már volt ilyen elem, akkor növeljük az előfordulást
                if (kar[j].getEncrypted().equals(line[i])) {
                    kar[j].increment();
                    found = true;
                    break;
                }
            }
            //ha nem volt, akkor új objektumot hozunk létre a tömbben
            if (!found) {
                kar[count] = new Karakterek(line[i]);
                count++;
            }
        }
        
        //karaktergyakoriság szerinti sorbarendezés
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (kar[i].getGyak() < kar[j].getGyak()) {
                    Karakterek x = kar[i];
                    kar[i] = kar[j];
                    kar[j] = x;
                }
            }
        }

        //hivatalos karakter gyakoriság beolvasása
        FileReader f = new FileReader("gyakorisag.txt");
        char[] karakter = new char[70];
        int karCount = 0;
        int k;
        while ((k = f.read()) != -1) {
            if ((char) k != '\n') {
                karakter[karCount] = (char) k;
                karCount++;
            }
        }
        f.close();

        //elemek helyettesítése karakterekkel, statisztikai gyakoriság alapján
        for (int i = 0; i < count; i++) {
            kar[i].setKarakter(karakter[i]);
        }

        //dekódolás
        System.out.println("EREDMÉNY");
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < count; j++) {
                if (line[i].equals(kar[j].getEncrypted())) {
                    System.out.print(kar[j].getKarakter());
                }
            }
        }
        System.out.println();
    }

}
