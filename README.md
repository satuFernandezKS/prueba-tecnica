# Saturio Fern谩ndez Pach贸n - prueba-tecnica

## Introducci贸n
Este servicio nace de la necesidad de realizar una prueba t茅cnica de acceso como desarrollador back-end java en la conocida empresa "X"

## Comenzando 馃殌

Estas instrucciones te permitir谩n obtener una copia del proyecto en funcionamiento en tu m谩quina local para prop贸sitos de desarrollo y pruebas.

Mira **Despliegue** para conocer c贸mo desplegar el proyecto.


### Pre-requisitos 馃搵

Para poder ejecutar el servicio en tu m谩quina debes tener instalado:

```
Apache Maven
Java 11+
git (opcional)
```

### Instalaci贸n 馃敡

Puedes conseguir una copia del proyecto clonando el repositorio GitHub (es necesario tener instalado git)

_Para hacerlo debes ejecutar el siguiente comando en la terminal de tu ordenador:_

```
git clone https://github.com/satuFernandezKS/prueba-tecnica.git
```

Si no dispones de git, puedes descargarte una copia del proyecto desde el repositorio, en formato zip

_Puedes descargarlo en el siguiente enlace:_
```
https://github.com/satuFernandezKS/prueba-tecnica/archive/refs/heads/main.zip
```

## Ejecutando las pruebas 鈿欙笍

Para ejecutar las pruebas (tests) debes abrir un terminal de tu ordenador y posicionarte dentro de la carpeta del proyecto. Una vez posicionados, ejecutamos el siguiente comando:
```
mvn test
```

Sabremos que las pruebas son satisfactorias si tras la ejecuci贸n vemos un mensaje como el siguiente:
```
[INFO] Tests run: xx, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## Despliegue 馃摝

Para desplegar y ejecutar el servicio debes posicionarte con el terminal de tu ordenador dentro de la carpeta del proyecto y ejecutar los siguientes comandos:
```
mvn package
o
mvn install
```
_Puede aprender m谩s sobre Maven y el ciclo de vida de las aplicaciones en [este enlace](https://maven.apache.org/)_

Para arrancar el servicio habr铆a que lanzar el siguiente comando en el terminal:
```
mvn spring-boot:run
```
_Este comando, por defecto, tambi茅n lanzar谩 las pruebas (tests)_

## Construido con 馃洜锔?

* [Maven](https://maven.apache.org/)
* [Java](https://www.java.com)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [H2 Database](https://www.h2database.com/html/main.html)
* Arquitectura de adaptadores y puertos

## Autor 鉁掞笍

* **Saturio Fern谩ndez Pach贸n** - [satuFernandezKS](https://github.com/satuFernandezKS)