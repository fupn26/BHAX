#include "lzwbinfa.h"

LZWBinFa::LZWBinFa ()
{
    std::cout << "LZWBinFa tor" << std::endl;
    gyoker = new Csomopont ('/');
    fa = gyoker;
}
LZWBinFa::~LZWBinFa ()
{
    szabadit(gyoker);
}

LZWBinFa::LZWBinFa (const LZWBinFa & forras){
    std::cout << "Copy ctor" << std::endl;
    gyoker = new Csomopont('/');
    gyoker->ujEgyesGyermek(masol(forras.gyoker->egyesGyermek(), forras.fa));
    gyoker->ujNullasGyermek(masol(forras.gyoker->nullasGyermek(), forras.fa));
    if (forras.fa == forras.gyoker){
        fa = gyoker;
    }
}
LZWBinFa & LZWBinFa::operator= (const LZWBinFa & forras){
    std::cout << "Copy assaignement" << std::endl;

    gyoker->ujEgyesGyermek(masol(forras.gyoker->egyesGyermek(), forras.fa));
    gyoker->ujNullasGyermek(masol(forras.gyoker->nullasGyermek(), forras.fa));

    if (forras.fa == forras.gyoker){
        fa = gyoker;
    }
    return *this;
}

LZWBinFa::LZWBinFa (LZWBinFa&& forras)
{
    std::cout<<"Move ctor\n";
    gyoker = nullptr;
    *this = std::move(forras); //ezzel kényszerítjük ki, hogy a mozgató értékadást használja

}
LZWBinFa& LZWBinFa::operator= (LZWBinFa&& forras)
{
    std::cout<<"Move assignment ctor\n";
    std::swap(gyoker, forras.gyoker);
    return *this;
}

/* Tagfüggvényként túlterheljük a << operátort, ezzel a célunk, hogy felkeltsük a
 hallgató érdeklődését, mert ekkor így nyomhatjuk a fába az inputot: binFa << b; ahol a b
 egy '0' vagy '1'-es betű.
 Mivel tagfüggvény, így van rá "értelmezve" az aktuális (this "rejtett paraméterként"
 kapott ) példány, azaz annak a fának amibe éppen be akarjuk nyomni a b betűt a tagjai
 (pl.: "fa", "gyoker") használhatóak a függvényben.

 A függvénybe programoztuk az LZW fa építésének algoritmusát tk.:
 http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor

 a b formális param az a betű, amit éppen be kell nyomni a fába.

 a binFa << b (ahol a b majd a végén látszik, hogy már az '1' vagy a '0') azt jelenti
 tagfüggvényként, hogy binFa.operator<<(b) (globálisként így festene: operator<<(binFa, b) )

 */
void LZWBinFa::operator<< (char b)
{
    // Mit kell betenni éppen, '0'-t?
    if (b == '0')
    {
        /* Van '0'-s gyermeke az aktuális csomópontnak?
       megkérdezzük Tőle, a "fa" mutató éppen reá mutat */
        if (!fa->nullasGyermek ())	// ha nincs, hát akkor csinálunk
        {
            // elkészítjük, azaz páldányosítunk a '0' betű akt. parammal
            Csomopont *uj = new Csomopont ('0');
            // az aktuális csomópontnak, ahol állunk azt üzenjük, hogy
            // jegyezze már be magának, hogy nullás gyereke mostantól van
            // küldjük is Neki a gyerek címét:
            fa->ujNullasGyermek (uj);
            // és visszaállunk a gyökérre (mert ezt diktálja az alg.)
            fa = gyoker;
        }
        else			// ha van, arra rálépünk
        {
            // azaz a "fa" pointer már majd a szóban forgó gyermekre mutat:
            fa = fa->nullasGyermek ();
        }
    }
    // Mit kell betenni éppen, vagy '1'-et?
    else
    {
        if (!fa->egyesGyermek ())
        {
            Csomopont *uj = new Csomopont ('1');
            fa->ujEgyesGyermek (uj);
            fa = gyoker;
        }
        else
        {
            fa = fa->egyesGyermek ();
        }
    }
}
/* A bejárással kapcsolatos függvényeink (túlterhelt kiir-ók, atlag, ratlag stb.) rekurzívak,
 tk. a rekurzív fabejárást valósítják meg (lásd a 3. előadás "Fabejárás" c. fóliáját és társait)

 (Ha a rekurzív függvénnyel általában gondod van => K&R könyv megfelelő része: a 3. ea. izometrikus
 részében ezt "letáncoltuk" :) és külön idéztük a K&R álláspontját :)
*/
void LZWBinFa::kiir (void)
{
    // Sokkal elegánsabb lenne (és más, a bevezetésben nem kibontandó reentráns kérdések miatt is, mert
    // ugye ha most két helyről hívják meg az objektum ilyen függvényeit, tahát ha kétszer kezd futni az
    // objektum kiir() fgv.-e pl., az komoly hiba, mert elromlana a mélység... tehát a mostani megoldásunk
    // nem reentráns) ha nem használnánk a C verzióban globális változókat, a C++ változatban példánytagot a
    // mélység kezelésére: http://progpater.blog.hu/2011/03/05/there_is_no_spoon
    melyseg = 0;
    // ha nem mondta meg a hívó az üzenetben, hogy hova írjuk ki a fát, akkor a
    // sztenderd out-ra nyomjuk
    kiir (gyoker, std::cout);
}
/* már nem használjuk, tartalmát a dtor hívja
void szabadit (void)
{
szabadit (gyoker.egyesGyermek ());
szabadit (gyoker.nullasGyermek ());
// magát a gyökeret nem szabadítjuk, hiszen azt nem mi foglaltuk a szabad tárban (halmon).
}
*/

/* A változatosság kedvéért ezeket az osztálydefiníció (class LZWBinFa {...};) után definiáljuk,
 hogy kénytelen légy az LZWBinFa és a :: hatókör operátorral minősítve definiálni :) l. lentebb */
int
LZWBinFa::getMelyseg (void)
{
    melyseg = maxMelyseg = 0;
    rmelyseg (gyoker);
    return maxMelyseg - 1;
}

double
LZWBinFa::getAtlag (void)
{
    melyseg = atlagosszeg = atlagdb = 0;
    ratlag (gyoker);
    atlag = ((double) atlagosszeg) / atlagdb;
    return atlag;
}

double
LZWBinFa::getSzoras (void)
{
    atlag = getAtlag ();
    szorasosszeg = 0.0;
    melyseg = atlagdb = 0;

    rszoras (gyoker);

    if (atlagdb - 1 > 0)
        szoras = std::sqrt (szorasosszeg / (atlagdb - 1));
    else
        szoras = std::sqrt (szorasosszeg);

    return szoras;
}

/* Vágyunk, hogy a felépített LZW fát ki tudjuk nyomni ilyenformán: std::cout << binFa;
 de mivel a << operátor is a sztenderd névtérben van, de a using namespace std-t elvből
 nem használjuk bevezető kurzusban, így ez a konstrukció csak az argfüggő névfeloldás miatt
 fordul le (B&L könyv 185. o. teteje) ám itt nem az a lényeg, hanem, hogy a cout ostream
 osztálybeli, így abban az osztályban kéne módosítani, hogy tudjon kiírni LZWBinFa osztálybelieket...
 e helyett a globális << operátort terheljük túl,

 a kiFile << binFa azt jelenti, hogy

  - tagfüggvényként: kiFile.operator<<(binFa) de ehhez a kiFile valamilyen
  std::ostream stream osztály forrásába kellene beleírni ezt a tagfüggvényt,
  amely ismeri a mi LZW binfánkat...

  - globális függvényként: operator<<(kiFile, binFa) és pont ez látszik a következő sorban:

 */
std::ostream & operator<< (std::ostream & os, LZWBinFa & bf)
{
    bf.kiir (os);
    return os;
}
void LZWBinFa::kiir (std::ostream & os)
{
    melyseg = 0;
    kiir (gyoker, os);
}

void LZWBinFa::kiir (Csomopont * elem, std::ostream & os)
{
    // Nem létező csomóponttal nem foglalkozunk... azaz ez a rekurzió leállítása
    if (elem != nullptr)
    {
        ++melyseg;
        kiir (elem->egyesGyermek (), os);
        // ez a postorder bejáráshoz képest
        // 1-el nagyobb mélység, ezért -1
        for (int i = 0; i < melyseg; ++i)
            os << "---";
        os << elem->getBetu () << "(" << melyseg - 1 << ")" << std::endl;
        kiir (elem->nullasGyermek (), os);
        --melyseg;
    }
}
void LZWBinFa::szabadit (Csomopont * elem)
{
    // Nem létező csomóponttal nem foglalkozunk... azaz ez a rekurzió leállítása
    if (elem != nullptr)
    {
        szabadit (elem->egyesGyermek ());
        szabadit (elem->nullasGyermek ());
        // ha a csomópont mindkét gyermekét felszabadítottuk
        // azután szabadítjuk magát a csomópontot:
        delete elem;
    }
}
Csomopont* LZWBinFa::masol (Csomopont* elem, Csomopont* regi_fa){
    Csomopont* ujelem = nullptr;

    if (elem != nullptr){
        ujelem = new Csomopont (elem->getBetu());

        ujelem -> ujEgyesGyermek(masol(elem->egyesGyermek(), regi_fa));
        ujelem -> ujNullasGyermek(masol(elem->nullasGyermek(), regi_fa));

        if (regi_fa == elem){
            fa = ujelem;
        }
    }

    return ujelem;
}

void
LZWBinFa::rmelyseg (Csomopont * elem)
{
    if (elem != nullptr)
    {
        ++melyseg;
        if (melyseg > maxMelyseg)
            maxMelyseg = melyseg;
        rmelyseg (elem->egyesGyermek ());
        // ez a postorder bejáráshoz képest
        // 1-el nagyobb mélység, ezért -1
        rmelyseg (elem->nullasGyermek ());
        --melyseg;
    }
}

void
LZWBinFa::ratlag (Csomopont * elem)
{
    if (elem != nullptr)
    {
        ++melyseg;
        ratlag (elem->egyesGyermek ());
        ratlag (elem->nullasGyermek ());
        --melyseg;
        if (elem->egyesGyermek () == nullptr && elem->nullasGyermek () == nullptr)
        {
            ++atlagdb;
            atlagosszeg += melyseg;
        }
    }
}

void
LZWBinFa::rszoras (Csomopont * elem)
{
    if (elem != nullptr)
    {
        ++melyseg;
        rszoras (elem->egyesGyermek ());
        rszoras (elem->nullasGyermek ());
        --melyseg;
        if (elem->egyesGyermek () == nullptr && elem->nullasGyermek () == nullptr)
        {
            ++atlagdb;
            szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
        }
    }
}


