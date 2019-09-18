// z3a7.cpp
//
// Együtt támadjuk meg: http://progpater.blog.hu/2011/04/14/egyutt_tamadjuk_meg
// LZW fa építő 3. C++ átirata a C valtozatbol (+mélység, atlag és szórás)
// Programozó Páternoszter
//
// Copyright (C) 2011, 2012, Bátfai Norbert, nbatfai@inf.unideb.hu, nbatfai@gmail.com
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
// Ez a program szabad szoftver; terjeszthetõ illetve módosítható a
// Free Software Foundation által kiadott GNU General Public License
// dokumentumában leírtak; akár a licenc 3-as, akár (tetszõleges) késõbbi
// változata szerint.
//
// Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
// de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
// VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
// További részleteket a GNU General Public License tartalmaz.
//
// A felhasználónak a programmal együtt meg kell kapnia a GNU General
// Public License egy példányát; ha mégsem kapta meg, akkor
// tekintse meg a <http://www.gnu.org/licenses/> oldalon.
//
//
// Version history:
//
// 0.0.1,       http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor
// 0.0.2,       csomópontok mutatóinak NULLázása (nem fejtette meg senki :)
// 0.0.3,       http://progpater.blog.hu/2011/03/05/labormeres_otthon_avagy_hogyan_dolgozok_fel_egy_pedat
// 0.0.4,       z.cpp: a C verzióból svn: bevezetes/C/ziv/z.c átírjuk C++-ra
//              http://progpater.blog.hu/2011/03/31/imadni_fogjatok_a_c_t_egy_emberkent_tiszta_szivbol
// 0.0.5,       z2.cpp: az fgv(*mut)-ok helyett fgv(&ref)
// 0.0.6,       z3.cpp: Csomopont beágyazva
//              http://progpater.blog.hu/2011/04/01/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_2
// 0.0.6.1      z3a2.c: LZWBinFa már nem barátja a Csomopont-nak, mert annak tagjait nem használja direktben
// 0.0.6.2      Kis kommentezést teszünk bele 1. lépésként (hogy a kicsit lemaradt hallgatóknak is
//              könnyebb legyen, jól megtűzdeljük további olvasmányokkal)
//              http://progpater.blog.hu/2011/04/14/egyutt_tamadjuk_meg
//              (majd a 2. lépésben "beletesszük a d.c-t", majd s 3. lépésben a parancssorsor argok feldolgozását)
// 0.0.6.3      z3a2.c: Fejlesztgetjük a forrást: http://progpater.blog.hu/2011/04/17/a_tizedik_tizenegyedik_labor
// 0.0.6.4      SVN-beli, http://www.inf.unideb.hu/~nbatfai/p1/forrasok-SVN/bevezetes/vedes/
// 0.0.6.5      2012.03.20, z3a4.cpp: N betűk (hiányok), sorvégek, vezető komment figyelmen kívül: http://progpater.blog.hu/2012/03/20/a_vedes_elokeszitese
// 0.0.6.6      z3a5.cpp: mamenyaka kolléga észrevételére a több komment sor figyelmen kívül hagyása
//		http://progpater.blog.hu/2012/03/20/a_vedes_elokeszitese/fullcommentlist/1#c16150365
// 0.0.6.7	Javaslom ezt a verziót választani védendő programnak
// 0.0.6.8	z3a7.cpp: pár kisebb javítás, illetve a védések támogatásához további komment a <<
// 		eltoló operátort tagfüggvényként, illetve globális függvényként túlterhelő részekhez.
//		http://progpater.blog.hu/2012/04/10/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_4/fullcommentlist/1#c16341099
//

/* Az LZWBinFa osztályban absztraháljuk az LZW algoritmus bináris fa építését. Az osztály
 definíciójába beágyazzuk a fa egy csomópontjának az absztrakt jellemzését, ez lesz a
 beágyazott Csomopont osztály. Miért ágyazzuk be? Mert külön nem szánunk neki szerepet, ezzel
 is jelezzük, hogy csak a fa részeként számiolunk vele.*/

class LZWBinFa
{
    /* Szemben a bináris keresőfánkkal (BinFa osztály)
     http://progpater.blog.hu/2011/04/12/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_3
     itt (LZWBinFa osztály) a fa gyökere nem pointer, hanem a '/' betüt tartalmazó objektum,
     lásd majd a védett tagok között lent: Csomopont gyoker;
     A fa viszont már pointer, mindig az épülő LZW-fánk azon csomópontjára mutat, amit az
     input feldolgozása során az LZW algoritmus logikája diktál:
     http://progpater.blog.hu/2011/02/19/gyonyor_a_tomor
     Ez a konstruktor annyit csinál, hogy a fa mutatót ráállítja a gyökérre. (Mert ugye
     laboron, blogon, előadásban tisztáztuk, hogy a tartalmazott tagok, most "Csomopont gyoker"
     konstruktora előbb lefut, mint a tagot tartalmazó LZWBinFa osztály konstruktora, éppen a
     következő, azaz a fa=&gyoker OK.)
   */
    public LZWBinFa ()
    {
        fa = gyoker;
    }
//    ~LZWBinFa ()
//    {
//        szabadit (gyoker.egyesGyermek ());
//        szabadit (gyoker.nullasGyermek ());
//    }

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
    public void hozzarendel(char b)
    {
        // Mit kell betenni éppen, '0'-t?
        if (b == '0')
        {
            /* Van '0'-s gyermeke az aktuális csomópontnak?
           megkérdezzük Tőle, a "fa" mutató éppen reá mutat */
            if (fa.nullasGyermek () == null)	// ha nincs, hát akkor csinálunk
            {
                // elkészítjük, azaz páldányosítunk a '0' betű akt. parammal
                Csomopont uj = new Csomopont ('0');
                // az aktuális csomópontnak, ahol állunk azt üzenjük, hogy
                // jegyezze már be magának, hogy nullás gyereke mostantól van
                // küldjük is Neki a gyerek címét:
                fa.ujNullasGyermek (uj);
                // és visszaállunk a gyökérre (mert ezt diktálja az alg.)
                fa = gyoker;
            }
            else			// ha van, arra rálépünk
            {
                // azaz a "fa" pointer már majd a szóban forgó gyermekre mutat:
                fa = fa.nullasGyermek ();
            }
        }
        // Mit kell betenni éppen, vagy '1'-et?
        else
        {
            if (fa.egyesGyermek () == null)
            {
                Csomopont uj = new Csomopont ('1');
                fa.ujEgyesGyermek (uj);
                fa = gyoker;
            }
            else
            {
                fa = fa.egyesGyermek ();
            }
        }
    }
    /* A bejárással kapcsolatos függvényeink (túlterhelt kiir-ók, atlag, ratlag stb.) rekurzívak,
     tk. a rekurzív fabejárást valósítják meg (lásd a 3. előadás "Fabejárás" c. fóliáját és társait)

     (Ha a rekurzív függvénnyel általában gondod van => K&R könyv megfelelő része: a 3. ea. izometrikus
     részében ezt "letáncoltuk" :) és külön idéztük a K&R álláspontját :)
   */
    public void kiir ()
    {
        // Sokkal elegánsabb lenne (és más, a bevezetésben nem kibontandó reentráns kérdések miatt is, mert
        // ugye ha most két helyről hívják meg az objektum ilyen függvényeit, tahát ha kétszer kezd futni az
        // objektum kiir() fgv.-e pl., az komoly hiba, mert elromlana a mélység... tehát a mostani megoldásunk
        // nem reentráns) ha nem használnánk a C verzióban globális változókat, a C++ változatban példánytagot a
        // mélység kezelésére: http://progpater.blog.hu/2011/03/05/there_is_no_spoon
        melyseg = 0;
        // ha nem mondta meg a hívó az üzenetben, hogy hova írjuk ki a fát, akkor a
        // sztenderd out-ra nyomjuk
        kiir (gyoker, new java.io.BufferedWriter(new java.io.OutputStreamWriter(System.out)));
//        kiir (gyoker, new java.io.FileWriter(new java.io.OutputStreamWriter(System.out)));
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
    public int getMelyseg ()
    {
        melyseg = maxMelyseg = 0;
        rmelyseg (gyoker);
        return maxMelyseg - 1;
    }
    public double getAtlag ()
    {
        melyseg = atlagosszeg = atlagdb = 0;
        ratlag (gyoker);
        atlag = ((double) atlagosszeg) / atlagdb;
        return atlag;
    }
    public double getSzoras ()
    {
        atlag = getAtlag ();
        szorasosszeg = 0.0;
        melyseg = atlagdb = 0;

        rszoras (gyoker);

        if (atlagdb - 1 > 0)
            szoras = java.lang.Math.sqrt (szorasosszeg / (atlagdb - 1));
        else
            szoras = java.lang.Math.sqrt (szorasosszeg);

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
//    friend std::ostream & operator<< (std::ostream & os, LZWBinFa & bf)
//    {
//        bf.kiir (os);
//        return os;
//    }
    public void kiir (java.io.BufferedWriter os)
    {
        melyseg = 0;
        kiir (gyoker, os);
    }

    private class Csomopont
    {
        /* A paraméter nélküli konstruktor az elepértelmezett '/' "gyökér-betűvel" hozza
       létre a csomópontot, ilyet hívunk a fából, aki tagként tartalmazza a gyökeret.
       Máskülönben, ha valami betűvel hívjuk, akkor azt teszi a "betu" tagba, a két
       gyermekre mutató mutatót pedig nullra állítjuk, C++-ban a 0 is megteszi. */
//       public final DEFAULT_CHAR = '/';
        public Csomopont (char b)
        {
            betu = b;
            balNulla = null;
            jobbEgy = null;
        }
//        ~Csomopont ()
//        {
//        };
        // Aktuális csomópont, mondd meg nékem, ki a bal oldali gyermeked
        // (a C verzió logikájával műxik ez is: ha nincs, akkor a null megy vissza)
        public final Csomopont nullasGyermek ()
        {
            return balNulla;
        }
        // Aktuális csomópon,t mondd meg nékem, ki a jobb oldali gyermeked?
        public final Csomopont egyesGyermek ()
        {
            return jobbEgy;
        }
        // Aktuális csomópont, ímhol legyen a "gy" mutatta csomópont a bal oldali gyereked!
        public void ujNullasGyermek (Csomopont gy)
        {
            balNulla = gy;
        }
        // Aktuális csomópont, ímhol legyen a "gy" mutatta csomópont a jobb oldali gyereked!
        public void ujEgyesGyermek (Csomopont gy)
        {
            jobbEgy = gy;
        }
        // Aktuális csomópont: Te milyen betűt hordozol?
        // (a const kulcsszóval jelezzük, hogy nem bántjuk a példányt)
        public final char getBetu ()
        {
            return betu;
        }

        // friend class LZWBinFa; /* mert ebben a valtozatban az LZWBinFa metódusai nem közvetlenül
        // a Csomopont tagjaival dolgoznak, hanem beállító/lekérdező üzenetekkel érik el azokat */

        // Milyen betűt hordoz a csomópont
        private char betu;
        // Melyik másik csomópont a bal oldali gyermeke? (a C változatból "örökölt" logika:
        // ha hincs ilyen csermek, akkor balNulla == null) igaz
        private Csomopont balNulla;
        private Csomopont jobbEgy;
        // nem másolható a csomópont (ökörszabály: ha van valamilye a szabad tárban,
        // letiltjuk a másoló konstruktort, meg a másoló értékadást)
//        Csomopont (const Csomopont &); //másoló konstruktor
//        Csomopont & operator= (const Csomopont &);
    }

    /* Mindig a fa "LZW algoritmus logikája szerinti aktuális" csomópontjára mutat */
    private Csomopont fa;
    // technikai
    private int melyseg, atlagosszeg, atlagdb;
    private double szorasosszeg;
    // szokásosan: nocopyable
//    LZWBinFa (const LZWBinFa &);
//    LZWBinFa & operator= (const LZWBinFa &);

    /* Kiírja a csomópontot az os csatornára. A rekurzió kapcsán lásd a korábbi K&R-es utalást... */
    public void kiir (Csomopont elem, java.io.BufferedWriter os)
    {
        // Nem létező csomóponttal nem foglalkozunk... azaz ez a rekurzió leállítása
        if (elem != null)
        {
            try{
            ++melyseg;
            kiir (elem.egyesGyermek (), os);
            // ez a postorder bejáráshoz képest
            // 1-el nagyobb mélység, ezért -1
            for (int i = 0; i < melyseg; ++i)
                os.write("---");
            os.write(elem.getBetu () + "(" + (melyseg - 1) + ")\n");
            kiir (elem.nullasGyermek (), os);
            --melyseg;
            }
            catch(java.io.IOException e){
                System.out.println("Csomópont írása nem sikerült.");
            }
        }
    }
//    void szabadit (Csomopont * elem)
//    {
//        // Nem létező csomóponttal nem foglalkozunk... azaz ez a rekurzió leállítása
//        if (elem != NULL)
//        {
//            szabadit (elem->egyesGyermek ());
//            szabadit (elem->nullasGyermek ());
//            // ha a csomópont mindkét gyermekét felszabadítottuk
//            // azután szabadítjuk magát a csomópontot:
//            delete elem;
//        }
//    }
			// ha esetleg egyszer majd kiterjesztjük az osztályt, mert
    // akarunk benne valami újdonságot csinálni, vagy meglévő tevékenységet máshogy... stb.
    // akkor ezek látszanak majd a gyerek osztályban is

    /* A fában tagként benne van egy csomópont, ez erősen ki van tüntetve, Ő a gyökér: */
    protected Csomopont gyoker = new Csomopont('/');
    protected int maxMelyseg;
    protected double atlag, szoras;

    protected void rmelyseg (Csomopont elem)
    {
        if (elem != null)
        {
            ++melyseg;
            if (melyseg > maxMelyseg)
                maxMelyseg = melyseg;
            rmelyseg (elem.egyesGyermek ());
            // ez a postorder bejáráshoz képest
            // 1-el nagyobb mélység, ezért -1
            rmelyseg (elem.nullasGyermek ());
            --melyseg;
        }
    }
    protected void ratlag(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            ratlag(elem.egyesGyermek());
            ratlag(elem.nullasGyermek());
            --melyseg;
            if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
                ++atlagdb;
                atlagosszeg += melyseg;
            }
        }

    }
    protected void rszoras(Csomopont elem) {
        if (elem != null) {
            ++melyseg;
            rszoras(elem.egyesGyermek());
            rszoras(elem.nullasGyermek());
            --melyseg;
            if (elem.egyesGyermek() == null && elem.nullasGyermek() == null) {
                ++atlagdb;
                szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
            }
        }

    }



// Néhány függvényt az osztálydefiníció után definiálunk, hogy lássunk ilyet is ... :)
// Nem erőltetjük viszont a külön fájlba szedést, mert a sablonosztályosított tovább
// fejlesztésben az linkelési gondot okozna, de ez a téma már kivezet a laborteljesítés
// szükséges feladatából: http://progpater.blog.hu/2011/04/12/imadni_fogjak_a_c_t_egy_emberkent_tiszta_szivbol_3

// Egyébként a melyseg, atlag és szoras fgv.-ek a kiir fgv.-el teljesen egy kaptafa.

//public int LZWBinFa.getMelyseg()
//{
//    melyseg = maxMelyseg = 0;
//    rmelyseg (&gyoker);
//    return maxMelyseg - 1;
//}

//double
//LZWBinFa::getAtlag (void)
//{
//    melyseg = atlagosszeg = atlagdb = 0;
//    ratlag (&gyoker);
//    atlag = ((double) atlagosszeg) / atlagdb;
//    return atlag;
//}
//
//double
//LZWBinFa::getSzoras (void)
//{
//    atlag = getAtlag ();
//    szorasosszeg = 0.0;
//    melyseg = atlagdb = 0;
//
//    rszoras (&gyoker);
//
//    if (atlagdb - 1 > 0)
//        szoras = std::sqrt (szorasosszeg / (atlagdb - 1));
//    else
//        szoras = std::sqrt (szorasosszeg);
//
//    return szoras;
//}

//void
//LZWBinFa::rmelyseg (Csomopont * elem)
//{
//    if (elem != NULL)
//    {
//        ++melyseg;
//        if (melyseg > maxMelyseg)
//            maxMelyseg = melyseg;
//        rmelyseg (elem->egyesGyermek ());
//        // ez a postorder bejáráshoz képest
//        // 1-el nagyobb mélység, ezért -1
//        rmelyseg (elem->nullasGyermek ());
//        --melyseg;
//    }
//}
//
//void
//LZWBinFa::ratlag (Csomopont * elem)
//{
//    if (elem != NULL)
//    {
//        ++melyseg;
//        ratlag (elem->egyesGyermek ());
//        ratlag (elem->nullasGyermek ());
//        --melyseg;
//        if (elem->egyesGyermek () == NULL && elem->nullasGyermek () == NULL)
//        {
//            ++atlagdb;
//            atlagosszeg += melyseg;
//        }
//    }
//}
//
//void
//LZWBinFa::rszoras (Csomopont * elem)
//{
//    if (elem != NULL)
//    {
//        ++melyseg;
//        rszoras (elem->egyesGyermek ());
//        rszoras (elem->nullasGyermek ());
//        --melyseg;
//        if (elem->egyesGyermek () == NULL && elem->nullasGyermek () == NULL)
//        {
//            ++atlagdb;
//            szorasosszeg += ((melyseg - atlag) * (melyseg - atlag));
//        }
//    }
//}

// teszt pl.: http://progpater.blog.hu/2011/03/05/labormeres_otthon_avagy_hogyan_dolgozok_fel_egy_pedat
// [norbi@sgu ~]$ echo "01111001001001000111"|./z3a2
// ------------1(3)
// ---------1(2)
// ------1(1)
// ---------0(2)
// ------------0(3)
// ---------------0(4)
// ---/(0)
// ---------1(2)
// ------0(1)
// ---------0(2)
// depth = 4
// mean = 2.75
// var = 0.957427
// a laborvédéshez majd ezt a tesztelést használjuk:
// http://

/* Ez volt eddig a main, de most komplexebb kell, mert explicite bejövő, kimenő fájlokkal kell dolgozni
int
main ()
{
    char b;
    LZWBinFa binFa;

    while (std::cin >> b)
    {
        binFa << b;
    }

    //std::cout << binFa.kiir (); // így rajzolt ki a fát a korábbi verziókban de, hogy izgalmasabb legyen
    // a példa, azaz ki lehessen tolni az LZWBinFa-t kimeneti csatornára:

    std::cout << binFa; // ehhez kell a globális operator<< túlterhelése, lásd fentebb

    std::cout << "depth = " << binFa.getMelyseg () << std::endl;
    std::cout << "mean = " << binFa.getAtlag () << std::endl;
    std::cout << "var = " << binFa.getSzoras () << std::endl;

    binFa.szabadit ();

    return 0;
}
*/

/* A parancssor arg. kezelést egyszerűen bedolgozzuk a 2. hullám kapcsolódó feladatából:
 http://progpater.blog.hu/2011/03/12/hey_mikey_he_likes_it_ready_for_more_3
 de mivel nekünk sokkal egyszerűbb is elég, alig hagyunk meg belőle valamit...
 */

public static void usage ()
{
    System.out.println("Usage: lzwtree in_file -o out_file");
}

public static void main (String[] args)
{
    // http://progpater.blog.hu/2011/03/12/hey_mikey_he_likes_it_ready_for_more_3
    // alapján a parancssor argok ottani elegáns feldolgozásából kb. ennyi marad:
    // "*((*++argv)+1)"...

    // a kiírás szerint ./lzwtree in_file -o out_file alakra kell mennie, ez 4 db arg:
    System.out.println("Args length = " + args.length);
    System.out.println("Args 1 = " + args[0]);
    System.out.println("Args 2 = " + args[1].charAt(1));
    System.out.println("Args 3 = " + args[2]);
    
    boolean test = (args.length != 3);
    System.out.println(test);

    
    if (test == true)
    {
        // ha nem annyit kapott a program, akkor felhomályosítjuk erről a júzetr:
        usage ();
        // és jelezzük az operációs rendszer felé, hogy valami gáz volt...
        System.exit(-1);
    }

    // "Megjegyezzük" a bemenő fájl nevét
    String inFile = args[0];

    // a -o kapcsoló jön?
    if (args[1].charAt(1) != 'o')
    {
        System.out.println("Missing -o argument");
        usage ();
        System.exit(-2);
    }

    // ha igen, akkor az 5. előadásból kimásoljuk a fájlkezelés C++ változatát:
//    std::fstream beFile (inFile, std::ios_base::in);
try {
        System.out.println("Open file");
        java.io.FileInputStream beFile = new java.io.FileInputStream(inFile);
        java.io.DataInputStream beFile_datastream = new java.io.DataInputStream(beFile);
        java.io.BufferedReader beFile_bufferedreader = new java.io.BufferedReader(new java.io.InputStreamReader(beFile_datastream));
        System.out.println("file opened");
// fejlesztgetjük a forrást: http://progpater.blog.hu/2011/04/17/a_tizedik_tizenegyedik_labor
        
//        if (beFile.) {
//            std::cout << inFile << " nem letezik..." << std::endl;
//            usage();
//            return -3;
//        }
//        std::fstream kiFile( * ++argv, std::ios_base::out
        System.out.println("Set write stream");
        java.io.FileWriter kiFile = new java.io.FileWriter(args[2]);
        java.io.BufferedWriter kiFile_bufferedwriter = new java.io.BufferedWriter(kiFile);
        System.out.println("Done");
//        java.io.BufferedWriter std_out = new java.io.BufferedWriter(new java.io.OutputStreamWriter(System.out));
//        String b = new String();		// ide olvassik majd a bejövő fájl bájtjait
        System.out.println("Create LZW object");
        LZWBinFa binFa = new LZWBinFa(); // s nyomjuk majd be az LZW fa objektumunkba
        
//
//        // a bemenetet binárisan olvassuk, de a kimenő fájlt már karakteresen írjuk, hogy meg tudjuk
//        // majd nézni... :) l. az említett 5. ea. C -> C++ gyökkettes átírási példáit
        int c;
        while ((c = beFile_bufferedreader.read()) != -1){
//            b += (char) c;
            if (c == 0x0a) {
                break;
            }
        }
        boolean kommentben = false;

    while ((c = beFile_bufferedreader.read()) != -1)
    {
//        b += (char) c;
 
        if (c == 0x3e) {			// > karakter
                kommentben = true;
                continue;
            }

        if (c == 0x0a) {			// újsor
            kommentben = false;
            continue;
        }

        if (kommentben) {
            continue;
        }

        if (c == 0x4e) // N betű
        {
            continue;
        }

        // egyszerűen a korábbi d.c kódját bemásoljuk
        // laboron többször lerajzoltuk ezt a bit-tologatást:
        // a b-ben lévő bájt bitjeit egyenként megnézzük
        for (int i = 0; i < 8; ++i) {
            // maszkolunk eddig..., most már simán írjuk az if fejébe a legmagasabb helyiértékű bit vizsgálatát
            // csupa 0 lesz benne a végén pedig a vizsgált 0 vagy 1, az if megmondja melyik:
            System.out.println(c & 0x80);
            if ((c & 0x80) == 128) // ha a vizsgált bit 1, akkor az '1' betűt nyomjuk az LZW fa objektumunkba
//           if (c == 1) // ha a vizsgált bit 1, akkor az '1' betűt nyomjuk az LZW fa objektumunkba
            {
                binFa.hozzarendel('1');
            } else // különben meg a '0' betűt:
            {
                binFa.hozzarendel('0');
            }
            c <<= 1;
        }

    }
//
//    //std::cout << binFa.kiir (); // így rajzolt ki a fát a korábbi verziókban de, hogy izgalmasabb legyen
//    // a példa, azaz ki lehessen tolni az LZWBinFa-t kimeneti csatornára:
//
////    kiFile << binFa;		// ehhez kell a globális operator<< túlterhelése, lásd fentebb
////    // (jó ez az OO, mert mi ugye nem igazán erre gondoltunk, amikor írtuk, mégis megy, hurrá)
////
////    kiFile << "depth = " << binFa.getMelyseg () << std::endl;
////    kiFile << "mean = " << binFa.getAtlag () << std::endl;
////    kiFile << "var = " << binFa.getSzoras () << std::endl;
//
//    binFa.kiir(kiFile_bufferedwriter);
//    kiFile_bufferedwriter.write("depth = " + binFa.getMelyseg() + '\n');
//    kiFile_bufferedwriter.write("mean = " + binFa.getAtlag()+ '\n');
//    kiFile_bufferedwriter.write("var = " + binFa.getSzoras() + '\n');

    binFa.kiir(kiFile_bufferedwriter);
    kiFile_bufferedwriter.write("depth = " + binFa.getMelyseg() + '\n');
    kiFile_bufferedwriter.write("mean = " + binFa.getAtlag()+ '\n');
    kiFile_bufferedwriter.write("var = " + binFa.getSzoras() + '\n');

    kiFile_bufferedwriter.close();
    kiFile.close ();
    beFile_datastream.close ();
    beFile_bufferedreader.close();
    beFile.close();
    } catch (Exception e) {//Catch exception if any
//        java.lang.Throwable.StackTraceElement[] stack = e.getStackTrace();
//        System.err.println("Error: " + e.getStackTrace().toString());
        for (StackTraceElement elem : e.getStackTrace()) {
            System.err.println(elem);
        }
    }

}
}