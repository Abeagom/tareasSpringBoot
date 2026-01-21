# CRUD MVC con Thymeleaf — RA3

## 1) Datos del alumno/a
- Entidad elegida (ej. Producto, Libro...): Sesion

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: https://github.com/Abeagom/tareasSpringBoot
- Nº de commits realizados: 12

## 3) Arquitectura
Explica brevemente cómo has organizado:
- Modelo: Sesion con validaciones
- Repositorio: SesionRepository
- Servicio: Interaz SesionService e implementación ImplSesionServiceMySQL
- Controlador: Con rutas
- Configuración: Archivo CSV con sesiones que se insertan al ejecutar por primera vez el proyecto

## 4) Base de datos elegida (marca una)
- [ ] H2
- [X] MySQL
- [ ] PostgreSQL

## 5) Configuración de la base de datos
Hay que crear BD tareasSpringBoot

### 5.1 Dependencias añadidas
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>

### 5.2 application.properties / application.yml
spring.application.name=crspring.application.name=crud
spring.datasource.url=jdbc:mysql://localhost:3306/tareasSpringBoot
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect


### 5.3 Pasos para crear la BD (si aplica)
- MySQL: CREATE DATABASE tareasSpringBoot;

## 6) Cómo ejecutar el proyecto
1. Requisitos (Java versión, Maven/Gradle, DB instalada si aplica)
2. Comando de arranque:
   - ./mvnw spring-boot:run   (o equivalente)
3. URL de acceso:
   - http://localhost:8080

## 7) Pantallas / Rutas MVC
- GET /ciudades (listar)

## 8) Mejoras extra (opcional)
- Validaciones
- Estilos Bootstrap
- Búsqueda
- Pruebas
- Paginación
