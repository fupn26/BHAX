#include <iostream>

using namespace std;

class Negyszog{
public:
    Negyszog(int a_oldal, int b_oldal, int c_oldal, int d_oldal){
        a = a_oldal;
        b = b_oldal;
        c = c_oldal;
        d = d_oldal;
    }
    int a;
    int b;
    int c;
    int d;
    int kerulet;
    int terulet;
};

class Negyzet:public Negyszog{
public:
    Negyzet(int a_oldal, int b_oldal, int c_oldal, int d_oldal):Negyszog(a_oldal, b_oldal, c_oldal, d_oldal){
    }
};

class Teglalap:public Negyzet{
public:
    Teglalap(int a_oldal, int b_oldal, int c_oldal, int d_oldal):Negyzet(a_oldal, b_oldal, c_oldal, d_oldal){
    }
};

void setKeruletTerulet(Negyzet &negyzet){
    negyzet.kerulet = negyzet.a * 4;
    negyzet.terulet = negyzet.a * negyzet.a;
}

int main()
{
    Negyzet& negyzet = *new Negyzet(5,5,5,5);
    Teglalap& teglalap = *new Teglalap(4,5,4,5);

    setKeruletTerulet(negyzet);
    setKeruletTerulet(teglalap);

    cout << "Téglalap kerülete: " << teglalap.kerulet  << ", Négyzet kerülete: " << negyzet.kerulet << endl;
}
