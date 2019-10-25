/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhax.alternativtabella;

import java.util.ArrayList;

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
public class AlternativTabella {

    public static void main(String[] args) {
        
        Wiki2Matrix Matrix = new Wiki2Matrix();
        
        ArrayList<ArrayList<Double>> L = Matrix.getMatrix();

        // az eredeti tabella sorrendje, 2011.03.11
        String[] csapatNevE = {
            "Videoton",
            "Ferencváros",
            "Paks",
            "Debreceni VSC",
            "Zalaegerszegi TE",
            "Kaposvári Rákóczi",
            "Lombard Pápa",
            "Kecskeméti TE",
            "Újpest",
            "Győri ETO",
            "Budapest Honvéd",
            "MTK Budapest",
            "Vasas",
            "Szombathelyi Haladαs",
            "BFC Siófok",
            "Szolnoki MAV"
        };
        // az eredeti tabella pontjai, 2011.03.11
        int[] ep = {
            40,
            34,
            31,
            31,
            30,
            29,
            27,
            24,
            23,
            23,
            22,
            22,
            21,
            20,
            18,
            9
        };

        // az L mαtrix kιszνtιsekori sorrend (a blogra kettιbontottban ez a Wikis keresttαbla), 2011.03.11
        String[] csapatNevL = {
            "BFC Siófok",
            "Budapest Honvéd",
            "Vasas",
            "Debreceni VSC",
            "Ferencváros",
            "Győri ETO",
            "Kaposvári Rákóczi",
            "Kecskeméti TE",
            "Lombard Pápa",
            "MTK Budapest",
            "Paksi FC",
            "Szolnoki MΑV FC",
            "Szombathelyi Haladás",
            "Újpest",
            "Videoton",
            "Zalaegerszegi TE"
        };

        double[] hv = new double[L.size()];
        double[] h = new double[L.size()];

        for (int i = 0; i < hv.length; ++i) {
            hv[i] = 1.0 / L.size();
            h[i] = 0.0;
        }

        double reginorma = 0.0, regiosszeg = 0.0;

        boolean amig = true;
        while (amig) {

            for (int i = 0; i < L.size(); ++i) {
                for (int j = 0; j < L.get(i).size(); ++j) {
                    h[i] += L.get(i).get(j) * hv[j];
                }
            }

            // kicsit elegánsabban volt a laboron:
            // http://progpater.blog.hu/2011/02/13/bearazzuk_a_masodik_labort
            double osszeg = 0.0;
            for (int i = 0; i < h.length; ++i) {
                osszeg += (hv[i] - h[i]) * (hv[i] - h[i]);
            }

            System.out.println("iteráció...");

            double norma = Math.sqrt(osszeg);
            System.out.println("norma = " + norma);

            if (norma < .000000000001) {
                amig = false;

                System.out.println("+++");
                double osszegEll = 0.0;
                for (int i = 0; i < h.length; ++i) {

                    osszegEll += h[i];
                    System.out.println(h[i]);

                }
                System.out.println("összeg ell. = " + osszegEll);
                System.out.println("+++");

            }

            //double d = 0.98;
            double d = 0.87;
            //double d = 0.7;

            osszeg = 0.0;
            for (int i = 0; i < h.length; ++i) {

                //hv[i] = h[i];
                hv[i] = d * h[i] + (1.0 - d) / L.size();

                osszeg += hv[i];
                h[i] = 0.0;
            }

            System.out.println("összeg = " + osszeg);

            if (osszeg == regiosszeg && norma == reginorma) {
                amig = false;

                System.out.println("***");
                for (int i = 0; i < h.length; ++i) {
                    System.out.println(hv[i]);
                }

                System.out.println("****");

                rendezCsapatok(csapatNevL, hv, csapatNevE, ep);

            }
            reginorma = norma;
            regiosszeg = osszeg;

        } // while
    }

    public static void rendezCsapatok(String[] s, double h[], String[] e, int ep[]) {

        System.out.println("\nCsapatok rendezve:\n");

        int csapatNevekMeret = h.length;

        Csapat csapatok[] = new Csapat[csapatNevekMeret];

        for (int i = 0; i < csapatNevekMeret; i++) {
            csapatok[i] = new Csapat(s[i], h[i]);
        }

        java.util.List<Csapat> rendezettCsapatok = java.util.Arrays.asList(csapatok);
        java.util.Collections.sort(rendezettCsapatok);
        java.util.Collections.reverse(rendezettCsapatok);
        java.util.Iterator iterv = rendezettCsapatok.iterator();
        int ii = 0;
        while (iterv.hasNext()) {
            Csapat csapat = (Csapat) iterv.next();

            String ds = Double.toString(csapat.ertek);
            int idx = ds.indexOf(".");
            StringBuffer sb = new StringBuffer();
            sb = sb.delete(0, sb.length());
            sb.append(ds.substring(0, idx + 5));
            String e4 = sb.toString();
            System.out.println("|-");
            System.out.println("| " + e[ii]);
            System.out.println("| " + ep[ii]);
            System.out.println("| " + csapat.nev);
            System.out.println("| " + e4);

            ++ii;
        }
        System.out.println("|-");

    }
}
