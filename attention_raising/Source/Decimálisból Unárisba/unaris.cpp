#include <iostream>

int main()
{
	int a;
	int szamlalo = 0;
	std::cout<<"Add meg egy decimalis szamot!\n";
	std::cin >> a;
	std::cout<<"Unárisban:\n";
	for (int i = 0; i < a; ++i)
	{
		std::cout<<"|";
		++szamlalo;
		if (szamlalo % 5 == 0) std::cout<<" ";
	}
	std::cout<<'\n';
	return 0;
}
		 
