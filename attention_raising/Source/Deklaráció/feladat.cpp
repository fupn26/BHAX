#include <iostream>
int* fakt(int szam){
    static int a = 1;
    if (szam < 2)
        return &a;
    while (szam>1){
        a = a*szam;
        --szam;
    }
    return &a;
}

int* sum(int egyik, int masik){
    static int sum = egyik + masik;
    return &sum;
}

int szorzat(int egyik, int masik){
    return egyik*masik;
}

int osztas(int egyik, int masik){
        return egyik/masik;
}

int (*pfgv (int a)) (int,int){
    if (a){
        return &szorzat;
    }
    else
        return &osztas;
}

int main()
{
    int a = 10;
    int b = 5;
    int* pa = &a;
    int& ra = a;
    int tomb[a];
    int(& rtomb)[a] = tomb;
    int* ptomb[2];
    ptomb[0] = &a;
    ptomb[1] = &b;
    int* fakt_a = fakt(a);
    int*(*psum)(int,int) = &sum;
    int (*(*p_pfgv) (int valami))(int, int) = &pfgv;
    std::cout<<"a és b szorzata "<<(pfgv(1))(a,b)<<std::endl;
    std::cout<<"a és b hányadosa "<<(pfgv(0))(a,b)<<std::endl;
    std::cout<<"a és b hányadosa "<<(p_pfgv(0))(a,b)<<std::endl;
    //int* pfakt_b = (*pfakt)(b);
    std::cout<<"a értéke "<<a<<'\t'<<"a! értékes "<<*fakt_a<<std::endl;
    std::cout<<"a és b összege "<<*psum(a,b)<<std::endl;
}
