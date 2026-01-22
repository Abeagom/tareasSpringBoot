# CRUD MVC con Thymeleaf — RA3

## 1) Datos del alumno/a
- Entidad elegida (ej. Producto, Libro...): Sesion. Representa una sesión de trabajo o consulta.

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a MI fork: https://github.com/Abeagom/tareasSpringBoot
- Nº de commits realizados: 14

## 3) Arquitectura
Explica brevemente cómo has organizado:
- Modelo (Sesion): Representa la entidad Sesion. Se han aplicado Bean Validations (como @NotBlank o @NotNull).
- Repositorio (SesionRepository): Capa de acceso a datos que extiende de JpaRepository.
- Servicio: Interfaz (SesionService) e implementación (ImplSesionServiceMySQL).
- Controlador (SesionController): Actúa como intermediario entre la vista y el servicio. Gestiona las rutas (endpoints).
- Configuración (Carga de Datos CSV): Se ha implementado un componente de configuración que, al iniciar la aplicación (CommandLineRunner), detecta si la base de datos está vacía. En tal caso, lee un archivo CSV externo e inserta automáticamente los registros iniciales.

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
- CREATE DATABASE tareasSpringBoot;

## 6) Cómo ejecutar el proyecto
1. Requisitos (Java versión, Maven/Gradle, DB instalada si aplica)
- Java JDK: Versión 17 o superior instalada.
- Gestor de dependencias: Maven.
- Sistema de Gestión de Base de Datos: MySQL
- Base de Datos: Tener creado el esquema tareasSpringBoot
2. Comando de arranque:
   - ./mvnw spring-boot:run   (o equivalente)
3. URL de acceso:
   - http://localhost:8080

## 7) Pantallas / Rutas MVC
- GET /sesiones (listar con paginación)
- GET /sesiones/nuevo (formulario de creación)
- POST /sesiones (guardar nueva sesión)
- GET /sesiones/{id} (ver detalle de una sesión)
- GET /sesiones/{id}/editar (formulario de edición)
- POST /sesiones/{id} (actualizar sesión existente)
- POST /sesiones/{id}/borrar (eliminar sesión)

## 8) Mejoras extra (opcional)
- Validaciones: Uso de Bean Validation (@NotBlank, @NotNull, @Min, etc.)
- Estilos con Bootstrap 5
- Carga inicial mediante CSV
- Prueba con DataJpaTest
