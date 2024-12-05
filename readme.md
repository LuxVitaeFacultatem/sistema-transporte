# Sistema de Transporte API

Backend REST API para gestionar conductores y vehículos en un sistema de transporte. Implementada con Spring Boot, utilizando una base de datos MySQL.

---

## Tabla de Contenidos

1. [Requisitos](#requisitos)
2. [Instalación](#instalación)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Endpoints](#endpoints)
5. [Modelos de Datos](#modelos-de-datos)
6. [Dependencias](#dependencias)
7. [Pruebas](#pruebas)

---

## Requisitos

- **Java 11**
- **Maven 3.6+**
- **MySQL 8.0+**

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/LuxVitaeFacultatem/sistema-transporte
   cd sistema-transporte
   ```

2. Configurar la base de datos:
    ### Contamos con dos opciones:
    1. - Crear una base de datos en MySQL.
       - Actualizar las credenciales en `application.properties`:
         ```properties
         spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
         spring.datasource.username=usuario
         spring.datasource.password=contraseña
         ```
    2. Correr la BD MySql dockerizada previamente configurada siguiendo los pasos documentados en el readme.md del siguiente [repositorio](https://github.com/LuxVitaeFacultatem/DB)


3. Compilar y ejecutar el proyecto:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

---

## Estructura del Proyecto

```
src/main/java/com/api/transporte/sistematransporte
├── controller    # Controladores REST
├── dto           # Data Transfer Objects
├── model         # Entidades de base de datos
├── repository    # Interfaces JPA Repository
├── service       # Lógica de negocio
src/test/java/com/api/transporte/sistematransporte
└── config       # Directorio de configutacion CORS
```

---

## Endpoints

### Conductores

- **GET** `/api/conductores`
    - Retorna la lista de conductores registrados.

- **POST** `/api/conductores`
    - Registra un nuevo conductor.
    - **Body JSON**:
      ```json
      {
        "identificacion": "123456789",
        "apellido": "Pérez",
        "nombre": "Juan",
        "telefono": "3101234567",
        "direccion": "Calle 10 #20-30"
      }
      ```

---

### Vehículos

- **GET** `/api/vehiculos`
    - Retorna la lista de vehículos registrados.

- **POST** `/api/vehiculos`
    - Registra un nuevo vehículo.
    - **Body JSON**:
      ```json
      {
        "placa": "XYZ123",
        "modelo": "2023",
        "capacidad": "2000"
      }
      ```

- **GET** `/api/vehiculos/no-asignados`
    - Lista los vehículos no asignados a conductores.

- **POST** `/api/vehiculos/{id}/asociar-conductor/{conductorId}`
    - Asocia un conductor a un vehículo.

- **POST** `/api/vehiculos/{id}/desasociar-conductor`
    - Desasocia un conductor de un vehículo.

---

## Modelos de Datos

### Conductor

| Campo          | Tipo     | Descripción                     |
|----------------|----------|---------------------------------|
| `id`           | `Long`   | Identificador único.           |
| `identificacion` | `String` | Identificación del conductor. |
| `nombre`       | `String` | Nombre del conductor.          |
| `apellido`     | `String` | Apellido del conductor.        |
| `telefono`     | `String` | Teléfono del conductor.        |
| `direccion`    | `String` | Dirección del conductor.       |

### Vehículo

| Campo          | Tipo     | Descripción                     |
|----------------|----------|---------------------------------|
| `id`           | `Long`   | Identificador único.           |
| `placa`        | `String` | Placa del vehículo.            |
| `modelo`       | `String` | Modelo del vehículo.           |
| `capacidad`    | `String` | Capacidad del vehículo.        |
| `conductorId`  | `Long`   | ID del conductor asociado.     |

---

## Dependencias

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## Pruebas

El proyecto incluye pruebas unitarias para los servicios y controladores.

### Ejecutar pruebas:

```bash
 ./mvnw test   
```

---

## Autor

- **Camilo Andres Ramirez Villegas**  
  Desarrollador FullStack.  
  Contacto: [camilos1900@gmail.com](mailto:camilos1900@gmail.com)

---