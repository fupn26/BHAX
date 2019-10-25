/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhax.alternativtabella;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author fupn26
 */
// Programozó Páternoszter
// Bátfai Norbert, nbatfai@gmail.com
//
// http://progpater.blog.hu/2011/03/11/alternativ_tabella
// lásd még az alábbiakat
// elmélet: http://nehogy.fw.hu/wp-content/uploads/Prog1_2.pdf
// labor: http://progpater.blog.hu/2011/02/13/bearazzuk_a_masodik_labort
class Wiki2Matrix {

    private ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
    Wiki2Matrix() {

        int[][] kereszt = {
            {0, 0, 0, 1, 0, 3, 2, 3, 3, 2, 0, 0, 0, 2, 2, 3},
            {3, 0, 2, 1, 3, 2, 0, 3, 3, 3, 0, 0, 0, 0, 0, 1},
            {1, 1, 0, 0, 3, 1, 3, 0, 0, 0, 3, 1, 1, 0, 2, 0},
            {0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 2, 1, 1},
            {3, 3, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0},
            {1, 0, 3, 1, 0, 0, 0, 1, 3, 2, 0, 0, 0, 1, 2, 3},
            {0, 2, 0, 0, 0, 1, 0, 1, 1, 0, 3, 0, 1, 3, 3, 1},
            {0, 0, 1, 1, 3, 0, 0, 0, 0, 1, 3, 1, 1, 1, 3, 0},
            {0, 0, 1, 2, 3, 0, 0, 1, 0, 0, 0, 2, 1, 1, 3, 1},
            {0, 1, 1, 2, 0, 0, 3, 1, 1, 0, 0, 0, 0, 1, 3, 3},
            {2, 3, 0, 2, 1, 1, 0, 0, 1, 2, 0, 1, 0, 0, 0, 2},
            {3, 3, 0, 0, 0, 3, 3, 0, 2, 1, 1, 0, 2, 0, 0, 0},
            {1, 2, 0, 1, 0, 2, 1, 0, 0, 1, 3, 1, 0, 0, 0, 2},
            {2, 1, 2, 0, 1, 2, 1, 0, 0, 0, 3, 1, 1, 0, 0, 0},
            {1, 3, 1, 0, 2, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 1, 0, 1, 0, 3, 1, 1, 0, 0, 1, 2, 1, 3, 0}
        };

        int[][] pontotSzerez = new int[kereszt.length][kereszt.length];

        for (int i = 0; i < pontotSzerez.length; ++i) {
            for (int j = 0; j < pontotSzerez[i].length; ++j) {
                pontotSzerez[i][j] = 0;
            }
        }

        for (int i = 0; i < pontotSzerez.length; ++i) {
            for (int j = 0; j < pontotSzerez[i].length; ++j) {

                if (kereszt[i][j] == 1) { // zφld

                    ++pontotSzerez[i][j];

                } else if (kereszt[i][j] == 2) { // sαrga

                    ++pontotSzerez[i][j];
                    ++pontotSzerez[j][i];

                } else if (kereszt[i][j] == 3) { // piros

                    ++pontotSzerez[j][i];

                } else if (kereszt[i][j] == 0) { // όres

                    ;

                } else {
                    System.out.println("Luke, zavart érzek az erőben...");
                }

            }
        }

        System.out.println("A x pontot-szerez y-tól mátrix");

        nyom(pontotSzerez);

        System.out.println("\nSor és oszlop összegekkel");

        nyom2(pontotSzerez);

        int oszlopOsszeg[] = new int[pontotSzerez.length];

        for (int i = 0; i < pontotSzerez.length; ++i) {

            int o = 0;
            for (int j = 0; j < pontotSzerez[i].length; ++j) {
                o += pontotSzerez[j][i];
            }
            oszlopOsszeg[i] = o;
        }

        System.out.println("\nA \"link\" mátrix");

        nyom3(pontotSzerez, oszlopOsszeg);

    }

    void nyom(int[][] k) {
        for (int i = 0; i < k.length; ++i) {
            System.out.println();
            for (int j = 0; j < k[i].length; ++j) {
                System.out.print(k[i][j] + ", ");
            }
        }
    }

    void nyom2(int[][] k) {

        for (int i = 0; i < k.length; ++i) {
            System.out.println();
            int o = 0;
            for (int j = 0; j < k[i].length; ++j) {
                System.out.print(k[i][j] + ", ");
                o += k[i][j];
            }
            System.out.print("  " + o);
        }

        System.out.println();
        System.out.println();

        for (int i = 0; i < k.length; ++i) {

            int o = 0;
            for (int j = 0; j < k[i].length; ++j) {
                o += k[j][i];
            }
            System.out.print(o + " ");
        }

    }

    void nyom3(int[][] k, int[] oszlopOsszeg) {
        for (int i = 0; i < k.length; ++i) {
            matrix.add(new ArrayList<>());
            System.out.println();
            System.out.print("{");
            for (int j = 0; j < k[i].length; ++j) {

                if (oszlopOsszeg[j] != 0.0) {
                    System.out.print(k[i][j] * (1.0 / oszlopOsszeg[j]) + ", ");
//                    matrix[i][j] = k[i][j] * (1.0 / oszlopOsszeg[j]);
                    matrix.get(i).add(k[i][j] * (1.0 / oszlopOsszeg[j]));
                    
                } else {
                    System.out.print(k[i][j] + ", ");
                    matrix.get(i).add(k[i][j] * 1.0);
                }

            }
            System.out.print("},");
        }
    }
    
    ArrayList<ArrayList<Double>> getMatrix(){
        return matrix;
    }
}
