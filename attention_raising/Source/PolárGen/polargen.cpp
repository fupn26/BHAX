#include <iostream>
#include <cstdlib>	
#include <cmath>	
#include <ctime>	


//Random osztály
class PolarGen {

	public:

		PolarGen(); //konstruktor

		~PolarGen(){} //destruktor

		double kovetkezo(); //random lekérés

	private:

		bool nincsTarolt;
		double tarolt; //random értéke

};


PolarGen::PolarGen() { //a konstruktor kifejtése
	nincsTarolt = false;
	std::srand (std::time(NULL)); //random inicializálás
};


double PolarGen::kovetkezo() { //random lekérő függvény kifejtése
	if (nincsTarolt)
{
		double u1, u2, v1, v2, w;

		do{
			u1 = std::rand () / (RAND_MAX + 1.0); //innentől jön az algoritmus
			u2 = std::rand () / (RAND_MAX + 1.0);
			v1 = 2 * u1 - 1;
			v2 = 2 * u2 - 1;
			w = v1 * v1 + v2 * v2;
		    }
		while (w > 1);

		double r = std::sqrt ((-2 * std::log (w)) / w);

		tarolt = r * v2; 
		nincsTarolt = !nincsTarolt;

		return r * v1; //idáig tart az algoritmus
	}
	
	else
	{
		nincsTarolt = !nincsTarolt; //ha van korábbi random érték, akkor azt adja vissza
		return tarolt;
	}
};    

int main() 
{

	PolarGen rnd;

	for (int i = 0; i < 10; ++i) std::cout << rnd.kovetkezo() << std::endl; //10 random szám generálása

}
