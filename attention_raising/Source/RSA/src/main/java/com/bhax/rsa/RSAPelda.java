/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhax.rsa;

import java.util.Base64;

/**
 *
 * @author fupn26
 */
public class RSAPelda {
    
    public static void main(String[] args){
        
        KulcsPar jSzereplo = new KulcsPar();
        
        String tisztaSzoveg = "Hello, Vilag!";
        
        String titkos_output = new String();
        
        //kódol
        byte[] buffer = tisztaSzoveg.getBytes();
        java.math.BigInteger[] titkos = new java.math.BigInteger[buffer.length];
//        for (int idx = 0; idx < tisztaSzoveg.length(); ++idx) {
//            String tisztaszoveg = tisztaSzoveg.substring(idx, idx + 1);
//            tisztaszoveg = tisztaszoveg.toLowerCase();
//            byte[] buffer_in = tisztaszoveg.getBytes();
//            java.math.BigInteger[] titkos_in = new java.math.BigInteger[buffer.length];
//            byte[] output = new byte[buffer.length];
//            
//            for (int i = 0; i < titkos.length; ++i) {
//                titkos[i] = new java.math.BigInteger(new byte[]{buffer[i]});
//                titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
//                output[i] = titkos[i].byteValue();
////                writer.print(titkos[i]);
//                titkos_output += titkos[i].toString();
//            }
//            writer.println();
//        }
               
        for (int i = 0; i < titkos.length; ++i){
            titkos[i] = new java.math.BigInteger(new byte[] {buffer[i]});
            titkos[i] = titkos[i].modPow(jSzereplo.e, jSzereplo.m);
        }
        
        //dekódol
        for (int i = 0; i < titkos.length; ++i){
            titkos[i] = titkos[i].modPow(jSzereplo.d, jSzereplo.m);
            buffer[i] = titkos[i].byteValue();
        }
        
        System.out.println(new String (buffer));
    }
    
}
