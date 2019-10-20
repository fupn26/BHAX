/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhax.rsa;

import java.math.BigInteger;

/**
 *
 * @author fupn26
 */
public class RSAPelda {
    
    public static void main(String[] args){
        
        KulcsPar jSzereplo = new KulcsPar();
        
        String tisztaSzoveg = "Hello, Vilag!";
        
        //kódol
        byte[] buffer = tisztaSzoveg.getBytes();
        java.math.BigInteger[] titkos = new java.math.BigInteger[buffer.length];
        
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
