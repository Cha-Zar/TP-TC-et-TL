# Exercice 10 - Analyse académique des ambiguïtés dans un programme Flex

## 1. Introduction

L'objectif de cet exercice est d'étudier le comportement d'un analyseur lexical écrit avec Flex lorsque plusieurs règles peuvent reconnaître la même entrée. Cette situation est appelée ambiguïté lexicale. L'intérêt pédagogique de l'exercice est de montrer comment Flex résout automatiquement ces ambiguïtés à l'aide de deux principes :

1. la priorité à la plus longue correspondance ;
2. la priorité à la première règle écrite en cas d'égalité.

Le programme étudié se trouve dans [exercice10_ambiguite.l](/home/chazar/Desktop/TC_TL/TC/exercice10_ambiguite.l).

## 2. Présentation du programme

Le fichier Flex définit les éléments lexicaux suivants :

- les mots-clés `if` et `while` ;
- les opérateurs relationnels et d'affectation `<`, `<=`, `=` et `==` ;
- les nombres réels ;
- les nombres entiers ;
- les chaînes reconnues par le motif `{ID}` ;
- les espaces, qui sont ignorés ;
- tout caractère non reconnu, signalé par la règle `.`.

Les définitions auxiliaires sont les suivantes :

```lex
DIGIT   [0-9]
ID      [a-zA-Z_][a-zA-Z0-9_]*
```

La définition `DIGIT` représente un chiffre décimal. La définition `ID` représente une suite commençant par une lettre ou un souligné, suivie éventuellement de lettres, chiffres ou soulignés. D'un point de vue lexical, cette règle correspond à un identificateur. Dans le code actuel, l'affichage associé est `chaine de caractere`, mais sur le plan théorique il s'agit bien d'un motif d'identificateur.

## 3. Règles lexicales considérées

Le cœur du scanner est constitué des règles suivantes :

```lex
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
```

Ce choix de règles est pertinent pour illustrer les ambiguïtés, car certaines entrées peuvent être reconnues par plusieurs expressions régulières concurrentes.

## 4. Nature de l'ambiguïté lexicale dans ce code

L'ambiguïté apparaît lorsqu'une même séquence d'entrée peut satisfaire plusieurs règles.

### 4.0 Cas ajouté : `aa` et `a+`

Les deux règles que vous avez ajoutées en tête du fichier Flex constituent un exemple simple et très lisible d'ambiguïté lexicale :

- `"aa"` reconnaît uniquement la chaîne exacte `aa` ;
- `a+` reconnaît une suite d'au moins un `a`.

Pour l'entrée `a`, seule la règle `a+` s'applique. Pour l'entrée `aaa`, la règle `a+` est aussi retenue, car elle reconnaît une séquence plus longue que `"aa"`. En revanche, pour l'entrée `aa`, les deux règles reconnaissent exactement les mêmes caractères. Flex applique alors sa seconde règle de décision : à longueur égale, la première règle écrite est prioritaire. C'est donc `"aa"` qui est choisie avant `a+`.

Ce couple de règles illustre donc à la fois la priorité à la plus longue correspondance et, en cas d'égalité, l'importance de l'ordre des règles dans le fichier.

### 4.1 Ambiguïté entre mot-clé et identificateur

L'entrée `if` peut être reconnue par :

- la règle littérale `"if"` ;
- la règle générale `{ID}`.

Les deux règles reconnaissent exactement la même chaîne, de même longueur. Dans ce cas, Flex applique la deuxième règle de résolution : il choisit la première règle écrite dans le fichier. Ainsi, `if` est classé comme `structure conditionnelle if`.

Le même raisonnement s'applique à l'entrée `while`, qui peut être reconnue :

- par la règle `"while"` ;
- par la règle `{ID}`.

Là encore, l'ordre des règles détermine le résultat final.

### 4.2 Ambiguïté entre mot-clé court et identificateur plus long

L'entrée `if2` introduit une autre forme d'ambiguïté :

- la règle `"if"` reconnaît le préfixe `if` ;
- la règle `{ID}` reconnaît l'ensemble `if2`.

Ici, Flex ne choisit pas la première règle, car il applique d'abord la priorité à la plus longue correspondance. Le lexème `if2` est donc reconnu entièrement par la règle `{ID}`.

Le même principe vaut pour `while1` :

- `"while"` reconnaît seulement `while` ;
- `{ID}` reconnaît `while1` en entier.

La reconnaissance retenue est donc `chaine de caractere -> while1`.

### 4.3 Ambiguïté entre opérateurs de tailles différentes

L'entrée `<=` peut être interprétée de deux manières :

- soit comme le symbole `<` ;
- soit comme le symbole composé `<=`.

Comme `<=` constitue une correspondance plus longue que `<`, Flex choisit la règle `"<="`.

La même logique s'applique à `==` :

- `"="` peut reconnaître le premier caractère ;
- `"=="` reconnaît les deux caractères.

Flex retient donc `==` comme unité lexicale unique.

### 4.4 Ambiguïté entre entier et réel

L'entrée `25.75` satisfait partiellement la règle des entiers et totalement la règle des réels :

- `{DIGIT}+` reconnaît `25` ;
- `{DIGIT}+\.{DIGIT}+` reconnaît `25.75`.

Puisque la seconde règle consomme une partie plus grande de l'entrée, c'est elle qui est choisie.

## 5. Expérimentation

La compilation du programme peut être réalisée par :

```bash
flex -o TC/lex.yy.c TC/exercice10_ambiguite.l
gcc -o TC/exercice10 TC/lex.yy.c -lfl
```

Un test global représentatif peut être effectué avec :

```bash
printf 'if if2 while while1 < <= = == 25 25.75 @\n' | ./TC/exercice10
```

## 6. Résultats observés

Pour l'entrée précédente, on obtient un comportement de la forme :

```text
structure conditionnelle if      -> if
chaine de caractere  -> if2
boucle while   -> while
chaine de caractere  -> while1
inferieur       -> <
inferieur ou egale  -> <=
affectation     -> =
egalite       -> ==
entier          -> 25
reel            -> 25.75
INCONNU         -> @
```

Ces résultats confirment que Flex ne choisit jamais une règle au hasard. Le comportement reste déterministe et obéit strictement aux priorités de reconnaissance.

## 7. Interprétation académique

L'analyse du programme permet de dégager deux constats fondamentaux.

### 7.1 Premier principe : la plus longue correspondance

Flex cherche d'abord la règle qui reconnaît le plus grand nombre de caractères à partir de la position courante dans l'entrée. Ce principe explique la reconnaissance de :

- `if2` par `{ID}` plutôt que par `"if"` ;
- `while1` par `{ID}` plutôt que par `"while"` ;
- `<=` par `"<="` plutôt que par `"<"` ;
- `==` par `"=="` plutôt que par `"="` ;
- `25.75` par la règle du réel plutôt que par la règle de l'entier.

### 7.2 Deuxième principe : l'ordre des règles

Lorsque deux règles reconnaissent exactement la même chaîne avec la même longueur, Flex choisit la règle qui apparaît la première dans le fichier. Cela explique la reconnaissance de :

- `if` comme mot-clé plutôt que comme identificateur ;
- `while` comme mot-clé plutôt que comme identificateur.

Ce point est essentiel en analyse lexicale : les mots-clés doivent être placés avant la règle générale des identificateurs si l'on veut qu'ils soient distingués correctement.

## 8. Discussion critique

Sur le plan pédagogique, le code remplit bien l'objectif de l'exercice, car il illustre clairement plusieurs cas d'ambiguïtés lexicales résolues par Flex.

Cependant, dans une rédaction plus rigoureuse, deux remarques peuvent être formulées :

- le libellé `chaine de caractere` n'est pas totalement exact au sens lexical ; la règle `{ID}` reconnaît plutôt des identificateurs ;
- certains messages affichés gagneraient à être harmonisés sur le plan terminologique, par exemple `inferieur ou egale`, qui pourrait être reformulé en `inferieur ou egal`.

Ces points n'affectent pas le mécanisme de reconnaissance, mais ils amélioreraient la précision académique du document et du programme.

## 9. Conclusion

L'ambiguïté présente dans ce programme n'est pas une erreur de conception ; elle est volontaire et sert à mettre en évidence la stratégie de résolution utilisée par Flex. L'étude montre que :

1. Flex privilégie la correspondance la plus longue ;
2. en cas d'égalité, Flex choisit la première règle déclarée.

Ainsi, l'exercice démontre de manière concrète l'importance de l'ordre des règles dans un analyseur lexical et la nécessité de distinguer correctement les règles spécifiques, comme les mots-clés, des règles générales, comme les identificateurs.
