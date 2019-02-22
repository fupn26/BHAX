//változó cseréje segédváltozóval
#include <stdio.h>
int main()
{
	int a = 5;
	int b = 7;
	int seged;
	printf("Az a értéke:");
	printf("%d\n",a);
	printf("Az b értéke:");
	printf("%d\n",b);
	
	//csere
	seged = a;
	a = b;
	b = seged;
	printf("Az a értéke:");
	printf("%d\n",a);
	printf("Az b értéke:");
	printf("%d\n",b);
	
}



