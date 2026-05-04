%option noyywrap

%{
#include <stdio.h>
%}

DIGIT   [0-9]
ID      [a-zA-Z_][a-zA-Z0-9_]*

%%
"if"                            { printf("structure conditionnelle if      -> %s\n", yytext); }
"while"                         { printf("boucle while   -> %s\n", yytext); }
"<="                            { printf("inferieur ou egale  -> %s\n", yytext); }
"<"                             { printf("inferieur       -> %s\n", yytext); }
"=="                            { printf("egalite       -> %s\n", yytext); }
"="                             { printf("affectation     -> %s\n", yytext); }
{DIGIT}+\.{DIGIT}+              { printf("reel            -> %s\n", yytext); }
{DIGIT}+                        { printf("entier          -> %s\n", yytext); }
{ID}                            { printf("chaine de caractere  -> %s\n", yytext); }
[ \t\r\n]+                      { /* Ignorer les espaces. */ }
.                               { printf("INCONNU         -> %s\n", yytext); }
%%

int main(void)
{
    printf("Exercice 10 - Analyse des ambigu\xC3\xAFt\xC3\xA9s avec Flex\n");
    printf("Entrez un texte puis terminez avec Ctrl+D :\n");
    yylex();
    return 0;
}
