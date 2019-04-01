  /* Készítette: Szentpéteri Annamária
 * Dátum: 2014.03.17.
 * Az előadás jegyzet alapján készült a megoldás.
 */
 
#include <stdio.h>
#include <time.h>
#include <math.h>

void delay (unsigned long long  loops)
{
	unsigned long long i;
	for ( i=0; i<loops; i++)
		;
}

int main(void)
{
	unsigned long long loops_per_sec = 1;
	unsigned long long ticks;
	
	printf ("Calibrating delay loop..");
	fflush(stdout);
	
	while (loops_per_sec <<= 1 )
	{
		ticks = clock();
		delay (loops_per_sec);
		ticks = clock() - ticks;
		
		printf ("%llu %llu\n", ticks, loops_per_sec);
		
		if (ticks >= CLOCKS_PER_SEC)
		{
			loops_per_sec = (loops_per_sec / ticks) * CLOCKS_PER_SEC;
			
			printf ("ok - %llu.%02llu BogoMIPS\n", loops_per_sec/500000,
						(loops_per_sec/5000) % 100);
			printf("ok - %lld %f BogoMIPS\n", loops_per_sec, log(loops_per_sec));
			return 0;
		}
	}
	
	printf ("failed\n");
	return -1;
}
