# Saturio Fernández Pachón - prueba-tecnica

## Introducción
Este servicio nace de la necesidad de realizar una prueba técnica de acceso como desarrollador back-end java en la conocida empresa "X"

## Comenzando 🚀

Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.

Mira **Despliegue** para conocer cómo desplegar el proyecto.


### Pre-requisitos 📋

Para poder ejecutar el servicio en tu máquina debes tener instalado:

```
Apache Maven
Java 11+
git (opcional)
```

### Instalación 🔧

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

## Ejecutando las pruebas ⚙️

Para ejecutar las pruebas (tests) debes abrir un terminal de tu ordenador y posicionarte dentro de la carpeta del proyecto. Una vez posicionados, ejecutamos el siguiente comando:
```
mvn test
```

Sabremos que las pruebas son satisfactorias si tras la ejecución vemos un mensaje como el siguiente:
```
[INFO] Tests run: xx, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## Despliegue 📦

Para desplegar y ejecutar el servicio debes posicionarte con el terminal de tu ordenador dentro de la carpeta del proyecto y ejecutar los siguientes comandos:
```
mvn package
o
mvn install
```
_Puede aprender más sobre Maven y el ciclo de vida de las aplicaciones en [este enlace](https://maven.apache.org/)_

Para arrancar el servicio habría que lanzar el siguiente comando en el terminal:
```
mvn spring-boot:run
```
_Este comando, por defecto, también lanzará las pruebas (tests)_

## Construido con 🛠️

* [Maven](https://maven.apache.org/)
* [Java](https://www.java.com)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [H2 Database](https://www.h2database.com/html/main.html)
* Arquitectura de patrones y puertos

## Autor ✒️

* **Saturio Fernández Pachón** - [satuFernandezKS](https://github.com/satuFernandezKS)