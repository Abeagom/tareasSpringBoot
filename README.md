# CRUD MVC con Thymeleaf — RA3

## 1) Datos del alumno/a
- Entidad principal: Sesión (representa una sesión de trabajo o consulta)
- Entidad secundaria: Usuario (gestión de acceso y roles)

## 2) Repositorio (fork) y gestión de versiones
- Repositorio base: https://github.com/profeInformatica101/tareasSpringBoot
- Enlace a mi fork: https://github.com/Abeagom/tareasSpringBoot
- Nº de commits realizados: 14+

## 3) Arquitectura
Explica brevemente cómo has organizado:

El proyecto sigue el patrón MVC con Spring Boot.

### Modelo
- Sesion: Representa una sesión.
- Usuario: Representa un usuario del sistema.
- Rol: Enum con los roles disponibles (ADMIN, USER, etc.).

Se aplican validaciones con Bean Validation.

### Repositorio
- SesionRepository  
- UsuarioRepository  

Extienden de JpaRepository para el acceso a datos.

### Servicio
- SesionService / SesionServiceImplMySQL  
- UsuarioService / UsuarioServiceImpl  

Contienen la lógica de negocio.

### Controlador
- LoginController
- SesionController 
- UsuarioController

Gestionan las rutas y conectan vistas y servicios.

### Seguridad
- Configuración con Spring Security
- Autenticación mediante formulario login
- Control de acceso por roles

### Carga de datos
- Carga inicial mediante CSV usando CommandLineRunner
- Inserción automática si la base de datos está vacía

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

### Autenticación
- GET /login
- POST /login
- GET /logout

### Sesiones
- GET /sesiones (listar con paginación)
- GET /sesiones/nuevo (formulario de creación)
- POST /sesiones (guardar nueva sesión)
- GET /sesiones/{id} (ver detalle de una sesión)
- GET /sesiones/{id}/editar (formulario de edición)
- POST /sesiones/{id} (actualizar sesión existente)
- POST /sesiones/{id}/borrar (eliminar sesión)

### Usuarios
- GET /usuarios
- GET /usuarios/nuevo
- POST /usuarios
- GET /usuarios/{id}
- GET /usuarios/{id}/editar
- POST /usuarios/{id}
- POST /usuarios/{id}/borrar

## 8) Mejoras extra (opcional)
- Autenticación con Spring Security
- Roles (ADMIN / MANAGER/ USER)
- Búsqueda con filtros
- Paginación
- Validaciones: Uso de Bean Validation (@NotBlank, @NotNull, @Min, etc.)
- Estilos con Bootstrap 5
- Thymeleaf Fragments
- Carga inicial mediante CSV
- Prueba con DataJpaTest
