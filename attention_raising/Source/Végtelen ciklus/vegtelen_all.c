//Fordítás: gcc vegtelen_all.c -o vegtelen_all -fopenmp
int main()
{
	#pragma omp parallel
	for (;;);
}
