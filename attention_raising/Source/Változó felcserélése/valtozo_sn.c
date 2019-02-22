//csere segédváltozó nélkül
#include <stdio.h>
int main()
{
	int a = 5;
	int b = 7;
	printf("Az a értéke:");
	printf("%d\n",a);
	printf("Az b értéke:");
	printf("%d\n",b);
	
	//csere
	a = a+b;
	b = a-b;
	a = a-b;
	
	printf("Az a értéke:");
	printf("%d\n",a);
	printf("Az b értéke:");
	printf("%d\n",b);
	
}
