#ifndef LZWBINFA_H
#define LZWBINFA_H

#include <iostream>		// mert olvassuk a std::cin, írjuk a std::cout csatornákat
#include <cmath>		// mert vonunk gyököt a szóráshoz: std::sqrt
#include "csomopont.h"

class Csomopont;
class LZWBinFa
{
public:
    LZWBinFa ();
    ~LZWBinFa ();

    LZWBinFa (const LZWBinFa & forras);
    LZWBinFa & operator= (const LZWBinFa & forras);

    LZWBinFa (LZWBinFa&& forras);
    LZWBinFa& operator= (LZWBinFa&& forras);

    void operator<< (char b);
    void kiir (void);

    int getMelyseg (void);
    double getAtlag (void);
    double getSzoras (void);

    friend std::ostream & operator<< (std::ostream & os, LZWBinFa & bf);
    void kiir (std::ostream & os);
private:
    /* Mindig a fa "LZW algoritmus logikája szerinti aktuális" csomópontjára mutat */
    Csomopont *fa;
    // technikai
    int melyseg, atlagosszeg, atlagdb;
    double szorasosszeg;
    /* Kiírja a csomópontot az os csatornára. A rekurzió kapcsán lásd a korábbi K&R-es utalást... */
    void kiir (Csomopont * elem, std::ostream & os);
    void szabadit (Csomopont * elem);
    Csomopont* masol (Csomopont* elem, Csomopont* regi_fa);

protected:			// ha esetleg egyszer majd kiterjesztjük az osztályt, mert
    // akarunk benne valami újdonságot csinálni, vagy meglévő tevékenységet máshogy... stb.
    // akkor ezek látszanak majd a gyerek osztályban is

    /* A fában tagként benne van egy csomópont, ez erősen ki van tüntetve, Ő a gyökér: */
    Csomopont *gyoker;
    int maxMelyseg;
    double atlag, szoras;

    void rmelyseg (Csomopont * elem);
    void ratlag (Csomopont * elem);
    void rszoras (Csomopont * elem);

};

#endif // LZWBINFA_H
