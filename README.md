# 🐾 PetAdopt - Sistema de Gestión de Refugios de Animales

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-FF0000?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

**PetAdopt** es una aplicación de escritorio diseñada para optimizar, digitalizar y simplificar la administración diaria de un refugio de animales. Desarrollada con una arquitectura robusta orientada a objetos, permite controlar de manera eficiente los registros de animales, las asignaciones del personal voluntario, el seguimiento de tareas diarias y el histórico de adopciones.

---

## 🚀 Características Principales

* **Panel de Control Dinámico (Dashboard):** Visualización en tiempo real del total de animales refugiados, voluntarios activos y tareas pendientes del día.
* **Gestión de Animales:** Control total (CRUD) de fichas de animales clasificadas por especie (`PERRO`, `GATO`, `CONEJO`, `AVE`, `REPTIL`), edad y asignación de cuidadores o adoptantes.
* **Control de Personal Voluntario:** Registro de personal clasificado por áreas de especialidad (`VETERINARIA`, `LIMPIEZA`, `ADIESTRAMIENTO`, `PASEO`).
* **Planificación de Tareas (Relación N:M):** Módulo avanzado para asignar tareas específicas a voluntarios fijando fechas exactas de ejecución y control de estado (`Completada` / `Pendiente`).
* **Módulo de Adoptantes:** Gestión y vinculación segura de procesos de adopción para evitar registros duplicados mediante control estricto de DNI.

---

## 🛠️ Arquitectura y Tecnologías Utilizadas

* **Lenguaje:** Java (OpenJDK) con gestor de dependencias **Maven**.
* **Interfaz Gráfica:** **JavaFX** y Scene Builder para el diseño de interfaces intuitivas y limpias basadas en FXML.
* **Persistencia de Datos:** **MySQL / MariaDB** como motor de almacenamiento relacional.
* **Patrones de Diseño:**
    * **Patrón DAO (Data Access Object):** Desacoplamiento completo entre la lógica de la interfaz y el acceso a la base de datos.
    * **Singleton:** Centralización de instancias de acceso a datos para optimizar recursos en memoria.
* **Seguridad:** Uso estricto de `PreparedStatement` para mitigar vulnerabilidades de Inyección SQL.

---

## 📊 Modelo de Datos (Esquema Relacional)

La base de datos cuenta con una estructura optimizada que garantiza la integridad referencial de la información mediante reglas avanzadas de borrado:

* **`animal` -> `adoptante`/`voluntario` (1:N):** Configurada con `ON DELETE SET NULL`. Si un voluntario o adoptante se elimina, las fichas de los animales permanecen seguras en el sistema para evitar pérdidas de historial.
* **`voluntario` -> `tarea` (N:M):** Resuelta a través de la tabla intermedia fuerte **`realiza`**, controlada por su propio DAO e integrada con `ON DELETE CASCADE` para limpiar asignaciones huérfanas de forma automatizada.

---

## 🔧 Requisitos e Instalación

### Prerrequisitos
* Java JDK v17 o superior.
* MySQL Server activo.

### Instalación Rápida
1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/tu-usuario/PetAdopt.git](https://github.com/tu-usuario/PetAdopt.git)
