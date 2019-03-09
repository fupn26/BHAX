%{
  #include <stdio.h>
  #include <string.h>
  int betuk_szama = 0, szavak_szama = 0, szamok_szama = 0, sorok_szama = 0;
%}
%%
.						++betuk_szama;
\n						++sorok_szama;
[0-9]+					{++szavak_szama; ++szamok_szama,
						  printf("szam=[%s]", yytext);
						  betuk_szama += strlen(yytext);}
[a-zA-Z][a-zA-Z0-9]*	{++szavak_szama; betuk_szama += strlen(yytext);}
%%
int
main ()
{
	yylex ();
	printf("%d betu %d szo %d szam %d sor", betuk_szama, szavak_szama, szamok_szama, sorok_szama);

	return 0;
}
