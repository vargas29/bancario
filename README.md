# Proyecto de Microservicios Financieros

Este proyecto contiene varios microservicios relacionados con operaciones financieras, como la gestión de usuarios, clientes, cuentas y transacciones. Está construido con Spring Boot y utiliza Swagger para la documentación de la API.

## Microservicios

### Auth Service
El servicio de autenticación (`Auth Service`) maneja la autenticación de usuarios y la generación de tokens JWT.

### Cliente Service
El servicio de clientes (`Cliente Service`) maneja la gestión de clientes, incluyendo la creación, actualización y eliminación de clientes.

### Cuenta Service
El servicio de cuentas (`Cuenta Service`) maneja la gestión de cuentas, incluyendo la creación, consulta y eliminación de cuentas.

### Transacción Service
El servicio de transacciones (`Transacción Service`) maneja la realización de transacciones entre cuentas.

### User Service
El servicio de usuarios (`User Service`) maneja la gestión de usuarios del sistema, incluyendo la creación, consulta y eliminación de usuarios, así como la validación de credenciales de inicio de sesión.

## Documentación de la API

La documentación de la API se genera automáticamente utilizando Swagger. Puedes acceder a la documentación de cada microservicio a través de los siguientes enlaces:

- [Auth Service Swagger UI](http://localhost:8080/swagger-ui/index.html)
- [Cliente Service Swagger UI](http://localhost:8081/swagger-ui/index.html)
- [Cuenta Service Swagger UI](http://localhost:8082/swagger-ui/index.html)
- [Transacción Service Swagger UI](http://localhost:8083/swagger-ui/index.html)
- [User Service Swagger UI](http://localhost:8084/swagger-ui/index.html)

## Ejecución del Proyecto

1. Clona el repositorio a tu máquina local.
 Construir el Proyecto

Asegúrate de tener instalado Maven y Java 17 en tu sistema. Luego, ejecuta el siguiente comando en el directorio raíz del proyecto para construir el proyecto:

2. Importa el proyecto en tu IDE favorito.
3. Ejecuta cada microservicio de forma individual utilizando Maven o ejecutando la clase principal de cada servicio.
4. Accede a la documentación de la API de cada servicio utilizando los enlaces proporcionados anteriormente.

¡Disfruta explorando el proyecto!
```bash
mvn clean package
docker build -t demo-app .
docker run -p 8081:8081 demo-app