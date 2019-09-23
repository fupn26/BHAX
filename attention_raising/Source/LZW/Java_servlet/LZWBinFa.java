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

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class LZWBinFa extends HttpServlet
{
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
    public void kiir ()
    {
        melyseg = 0;
        kiir (gyoker);
    }
    
    public StringBuffer kimenet = new StringBuffer();

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
    // technikai
    private int melyseg, atlagosszeg, atlagdb;
    private double szorasosszeg;
    // szokásosan: nocopyable
//    LZWBinFa (const LZWBinFa &);
//    LZWBinFa & operator= (const LZWBinFa &);

    /* Kiírja a csomópontot az os csatornára. A rekurzió kapcsán lásd a korábbi K&R-es utalást... */
    public void kiir (Csomopont elem)
    {
        // Nem létező csomóponttal nem foglalkozunk... azaz ez a rekurzió leállítása
        if (elem != null)
        {

            ++melyseg;
            kiir (elem.egyesGyermek ());
            // ez a postorder bejáráshoz képest
            // 1-el nagyobb mélység, ezért -1
            for (int i = 0; i < melyseg; ++i)
                kimenet.append("---");
            kimenet.append(elem.getBetu () + "(" + (melyseg - 1) + ")<br>\n");
            kiir (elem.nullasGyermek ());
            --melyseg;
            
        }
    }
			// ha esetleg egyszer majd kiterjesztjük az osztályt, mert
    // akarunk benne valami újdonságot csinálni, vagy meglévő tevékenységet máshogy... stb.
    // akkor ezek látszanak majd a gyerek osztályban is

    /* A fában tagként benne van egy csomópont, ez erősen ki van tüntetve, Ő a gyökér: */
    protected Csomopont gyoker = new Csomopont('/');
    private Csomopont fa = gyoker;
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



public void CreateStringBuffer(String bemenet)
{
    for(int i = 0; i < bemenet.length(); ++i){
        int c = bemenet.charAt(i);
        if(c == 0x0a){
            break;
        }
    }
    boolean kommentben = false;
        
        
    for(int i = 0; i < bemenet.length(); ++i)
    {
        int c = bemenet.charAt(i);
 
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
        for (int j = 0; j < 8; ++j) {
            // maszkolunk eddig..., most már simán írjuk az if fejébe a legmagasabb helyiértékű bit vizsgálatát
            // csupa 0 lesz benne a végén pedig a vizsgált 0 vagy 1, az if megmondja melyik:
            if ((c & 0x80) == 128) // ha a vizsgált bit 1, akkor az '1' betűt nyomjuk az LZW fa objektumunkba
            {
                hozzarendel('1');
            } else // különben meg a '0' betűt:
            {
                hozzarendel('0');
            }
            c <<= 1;
        }

    }


    kiir();
    kimenet.append("depth = " + getMelyseg() + "<br>\n");
    kimenet.append("mean = " + getAtlag()+ "<br>\n");
    kimenet.append("var = " + getSzoras() + "<br>\n");

    }
   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      
      // Set response content type
      response.setContentType("text/html");
      
    if(request.getParameter("bemenet") == null){
        return;
    }
    else
      CreateStringBuffer(request.getParameter("bemenet"));
    
    PrintWriter out = response.getWriter();
    String title = "LZWBinFa";
    String docType =
         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
         
    out.println(docType +
         "<html>\n" +
            "<head><title>" + title + "</title></head>\n" +
            "<body>\n" +
               "<h1 align = \"center\">" + title + "</h1>\n" + 
                 kimenet + 
            "</body>" +
         "</html>"
      );
   }

}