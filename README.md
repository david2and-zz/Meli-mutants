![ML](https://img.shields.io/badge/ML-Marcado%20Libre-yellow)
![Java](https://img.shields.io/badge/Java-1.8-brightgreen)

# Meli-mutants

## Uso de la Aplicacion

### Montaje Local

En el proyecto se encuentra un archivo docker-compose el cual puede levantar dos contenedores, tanto de DynamoDB como de SonarCube para analisis de codigo por medio del siguiente comando:
```java
	docker-compose up
```

### Uso del api

Se pueden ver los request y response del api y de los diferentes endpoints a traves del swagger del proyecto en la siguiente ruta:

http://melimutants-env.eba-mqxjjbba.us-east-2.elasticbeanstalk.com/swagger-ui.html#/


## Arquitectura de Software

El enfoque que se uso para el desarrollo de la prueba es una arquitectura de microservicios desplegada sobre la nube de AWS, se uso lenguaje de programacion Java bajo el framework Spring Boot, en el siguiente diagrama se muestra explicacion basica de la arquitectura del microservicio:

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/BasicArchitecture.png?raw=true)

En donde:
* controller: Se encuentran los receptores de las peticiones HTTP.
* services: Se encuentra la lógica de negocio para validar la secuencia de adn y de registrar los stats.
* modelos: Clases que representan los objetos de negocio.
* dynamo: Base de datos no-relacional en la que se guardan los registros unicos.

### Vista lógica

La representacion de las clases de la aplicacion se ve en el siguiente diagrama:

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/ClassDiagram.png?raw=true)

En este diagrama se puede ver que se usan practicas SOLID como las siguientes:
* Segregacion de interfaces para bajo acoplamiento.
* Principio de Open/Close en el cual gracias a la segregacion de interfaces se logra que solo se tenga que implementar lo necesario y sea solo abierto a extencion.
* Gracias al framework Spring boot se maneja la Inyeccion de dependencias.
* Se sigue el principio de responsabilidad unica en cuanto a servicios y modelos.

Se maneja la buena practica de bajo acoplamiento y alta cohesion.

### Vista de Despliegue

Mediante el siguiente diagrama se puede ver los componentes AWS usados en el despliegue de la aplicacion:

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/AWS%20Components.png?raw=true)

El microservicio de aloja en una maquina EC2 la cual esta bajo una capa de Grupo de seguridad y de Grupo de escalabilidad, para poder tener los recursos necesarios para el crecimiento de peticiones que pueda tener la aplicacion, las cuales recibe por medio de un balanceador de carga.

Se usa una base de datos no relacional DynamoDB para cubrir con las peticiones de carga requeridas en la prueba.

### Vista de Procesos

El siguiente diagrama de secuencia muestra el flujo de la aplicacion para los distintos procesos:

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/SecuenceDiagram.png?raw=true)

En el siguiente diagrama se puede ver el resultado de prueba de carga en el tiempo, con 10.000 peticiones:

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/ReportOverTime.PNG?raw=true)

### Vista Fisica

Para el despliegue de la aplicacion, por temas de tiempo se realiza por medio de AWS Elastic Beanstalk.

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/elastic-beanstalk.jpg?raw=true)

Para el cual, se podria alternativamente se podria realizar por creacion de infraestructura por Terraform, con lo que se podria agregar a la arquitectura un cluster de Kubernetes, con un patron de microservicios ServiceMesh junto con un patron SideCar, adicional a esto se podria agregar un Api-Gateway para mejorar la gestion de las solicitudes y en algun posible caso de requerimiento a nivel global se podria extender a una capa de CloudFront.

## Pruebas Unitarias y Cobertura - Code Smells
Se realiza la medicion de la cobertura y validacion de codigo por medio de Jacoco y SonarCubo:

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/Coverage.PNG?raw=true)

![Screenshot](https://github.com/david2and/Meli-mutants/blob/master/docs/resources/CoveragePorcentage.PNG?raw=true)





