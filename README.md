# README - Projet TP Tests Logiciels et Techniques de Compilation

## Contexte

Ce dépôt a été préparé dans le cadre de l'examen de TP de :

- Tests logiciels
- Techniques de compilation

D'après l'annonce fournie, l'évaluation se déroulera le mercredi 06/05/2026 sous forme d'exercices de programmation. Chaque groupe doit :

- réaliser un exercice en tests logiciels ;
- réaliser un exercice en techniques de compilation ;
- effectuer les tests nécessaires sur les solutions développées ;
- présenter le travail réalisé en 5 minutes, suivies de 5 minutes de questions et d'évaluation.

L'objectif de ce dossier est donc de regrouper, dans un même projet, les deux parties du travail demandées, avec le code, les tests et les explications associées.

## Ce que nous avons réalisé

Le projet est organisé en deux volets principaux :

1. un volet `TL` pour les tests logiciels ;
2. un volet `TC` pour les techniques de compilation.

Nous avons également conservé une petite version Java autonome pour faciliter les démonstrations rapides sans Maven.

## Structure du dossier

- [pom.xml](/home/chazar/Desktop/TC_TL/pom.xml) : configuration Maven du projet Java.
- [src/main/java/com/example/exams/ExamEvaluator.java](/home/chazar/Desktop/TC_TL/src/main/java/com/example/exams/ExamEvaluator.java) : logique métier de l'exercice de tests logiciels.
- [src/main/java/com/example/exams/ExamResult.java](/home/chazar/Desktop/TC_TL/src/main/java/com/example/exams/ExamResult.java) : résultat retourné par l'évaluation.
- [src/main/java/com/example/exams/Entitlement.java](/home/chazar/Desktop/TC_TL/src/main/java/com/example/exams/Entitlement.java) : type d'aide ou de droit obtenu.
- [src/test/java/com/example/exams/ExamEvaluatorTest.java](/home/chazar/Desktop/TC_TL/src/test/java/com/example/exams/ExamEvaluatorTest.java) : tests paramétrés représentant les règles de décision.
- [src/test/java/com/example/exams/ExamEvaluatorOptTest.java](/home/chazar/Desktop/TC_TL/src/test/java/com/example/exams/ExamEvaluatorOptTest.java) : version optimisée des cas de test.
- [TC/exercice10_ambiguite.l](/home/chazar/Desktop/TC_TL/TC/exercice10_ambiguite.l) : programme Flex réalisé pour l'exercice de techniques de compilation.
- [TC/EXERCICE_10_Analyse_des_ambiguites.md](/home/chazar/Desktop/TC_TL/TC/EXERCICE_10_Analyse_des_ambiguites.md) : analyse rédigée de l'exercice Flex.
- [TC/lex.yy.c](/home/chazar/Desktop/TC_TL/TC/lex.yy.c) : fichier C généré par Flex.
- [TC/exercice10](/home/chazar/Desktop/TC_TL/TC/exercice10) : exécutable généré du scanner lexical.
- [simple/](/home/chazar/Desktop/TC_TL/simple) : version Java autonome sans Maven, utile pour une exécution rapide.
- [TL.md](/home/chazar/Desktop/TC_TL/TL.md) : note sur la variante Java simple.

## Partie 1 - Tests logiciels

Cette partie porte sur un évaluateur d'examen implémenté en Java.

### Principe de l'exercice

La classe `ExamEvaluator` évalue trois notes. Les règles implémentées sont les suivantes :

- chaque note doit être comprise entre `0` et `2` ;
- si une note vaut `0`, le total retourné devient `0` et le résultat est `NONE` ;
- sinon, la somme des trois notes est calculée ;
- si la somme est supérieure à `4`, le résultat est `FULL` ;
- sinon, le résultat est `PARTIAL`.

### Travail réaliséle problème traité dans chaque matière ;
la solution développée ;
les tests effectués ;
la justification des résultats obtenus ;
la qualité et l'organisation du code produit.

- implémentation de la logique métier dans `ExamEvaluator` ;
- modélisation du résultat avec `ExamResult` ;
- définition du type `Entitlement` ;
- écriture de tests JUnit paramétrés ;
- conservation d'une version optimisée des cas de test ;
- ajout d'une version Java simple pour exécution hors Maven.

### Vérification

Les tests Maven ont été exécutés avec succès.

Résultat observé :

```text
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

### Commande utile

```bash
mvn test
```

## Partie 2 - Techniques de compilation

Cette partie porte sur un exercice Flex consacré à l'analyse des ambiguïtés lexicales.

### Principe de l'exercice

Le programme Flex contient plusieurs règles proches afin de montrer comment Flex résout les conflits entre règles concurrentes.

Les principaux cas étudiés sont :

- mot-clé contre identificateur : `if`, `while`, `if2`, `while1` ;
- opérateurs courts et longs : `<` contre `<=`, `=` contre `==` ;
- nombre entier contre nombre réel : `25` contre `25.75` ;
- caractère non reconnu : `@`.

### Travail réalisé

- écriture du fichier Flex `exercice10_ambiguite.l` ;
- génération du scanner C avec Flex ;
- génération d'un exécutable testable ;
- rédaction d'une analyse détaillée du comportement observé ;
- explication académique de la notion d'ambiguïté lexicale et des règles de priorité de Flex.

### Idée centrale démontrée

L'exercice met en évidence les deux règles fondamentales de Flex :

1. Flex choisit d'abord la correspondance la plus longue.
2. Si plusieurs règles reconnaissent une chaîne de même longueur, Flex choisit la première règle écrite.

### Commandes utiles

```bash
flex -o TC/lex.yy.c TC/exercice10_ambiguite.l
gcc -o TC/exercice10 TC/lex.yy.c -lfl
./TC/exercice10
```

Exemple d'exécution :

```bash
printf 'if if2 while while1 < <= = == 25 25.75 @\n' | ./TC/exercice10
```

## Version simple sans Maven

Le dossier [simple/](/home/chazar/Desktop/TC_TL/simple) contient une petite version Java autonome du volet tests logiciels.

Cette version est utile si l'on veut :

- faire une démonstration rapide ;
- éviter Maven pendant une présentation ;
- exécuter les cas de test avec `javac` et `java` uniquement.

Commandes :

```bash
javac simple/*.java
java -cp simple TestRunner
```



