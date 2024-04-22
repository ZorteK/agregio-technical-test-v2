# Agregion test technique

Bonjour correcteur :) 

## Énoncé 

Une partie du métier d'Agregio est de vendre de l'énergie sur plusieurs marchés, il y a 3 principaux marchés, celui de la Réserve Primaire, la Réserve Secondaire et la Réserve Rapide. Sur chacun de ces marchés Agregio peut placer une offre composée de plusieurs "blocs" horaires (une journée de 24h pourrait contenir 8 blocs de 3 heures). Chaque bloc horaire présente la quantité d'énergie (en MW) qui sera produite et un prix plancher au-dessous duquel on ne vendra pas.

Les parcs producteurs d'électricité, de différents types (solaires, éoliens ou hydrauliques), sont capables de fournir un certain nombre de MégaWatt pendant la durée d'un bloc horaire. Pour permettre la traçabilité de la production électrique (garantie d'origine), on doit pouvoir connaître le(s) parc(s) qui va(vont) produire l'électricité d'une offre.

Nous vous demandons d'implémenter les APIs permettant de créer une offre,
de créer un parc, de lister les offres proposées par Agregio pour chaque marché et d'obtenir la liste des parcs qui vendent sur un marché.


## Installation

Les prérequis : 
- JDK 21
- Docker
- Maven

Vous avez un docker-compose à lancer pour initialiser la base de données

Pour lancer l'application, vous pouvez utiliser la commande suivante :

```bash
mvn spring-boot:run
```


Pas d'IHM, mais vous pouvez tester l'application 
* via Swagger : http://localhost:8080/swagger-ui.html
* via la collection postman : agregio.postman_collection.json

## Ce qu'il reste à faire

* des tests plus propres (manque de temps)
* des testes de cas limite (fait en postman)
* une meilleure gestion d'exception
* passage a mapstruct ? 
* un gitlab-ci
* tester en native image
* Problablement un pb de N+1 requête sur la liste des offres, il faudrait revoir la requête JPA



