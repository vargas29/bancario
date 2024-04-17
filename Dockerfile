# Usa una imagen base de OpenJDK 17
FROM adoptopenjdk/openjdk17:alpine-jre

# Define el directorio de trabajo en el contenedor
WORKDIR /src

# Copia el JAR de la aplicación al contenedor
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expone el puerto 8080 para que pueda ser accedido externamente
EXPOSE 8081
# Comando para ejecutar la aplicación cuando el contenedor se inicia
CMD ["java", "-jar", "demo.jar"]
