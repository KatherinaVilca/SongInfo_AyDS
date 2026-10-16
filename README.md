# SongInfo

**SongInfo** es una aplicación orientada a la búsqueda y visualización de información detallada sobre canciones y artistas. 
Desarrollada originalmente como proyecto práctico para la asignatura **Arquitectura y Diseño de Sistemas**, la aplicación sirvió como escenario para la aplicación de patrones de diseño, refactorización continua, buenas prácticas de código y principios SOLID.

---

## Descripción del Proyecto

La aplicación permite a los usuarios buscar canciones o artistas y obtener información enriquecida sobre su biografía, álbumes y detalles técnicos. El foco principal del desarrollo estuvo puesto en la **evolución incremental de la arquitectura de software**, transformando un código base monolítico/acoplado en una arquitectura limpia, extensible y testeable.

---

## Evolución Arquitectónica y Ramas del Repositorio

El desarrollo se estructuró de manera evolutiva reflejada en las distintas ramas (*branches*) del repositorio:

1. **Refactor Inicial (`Clean Code`)**: 
   * Identificación y eliminación de *code smells*.
   * Mejoras en legibilidad, nombres expresivos y reducción de duplicación de código.
2. **Clean MVP**:
   * Separación de responsabilidades aplicando el patrón **Model-View-Presenter (MVP)**.
   * Desacoplamiento de la interfaz de usuario de la lógica de negocio y presentación.
3. **Aplicación de Principios SOLID**:
   * Inversión de dependencias (DIP) e inyección de interfaces.
   * Principio de Responsabilidad Única (SRP) en servicios, repositorios y presentadores.
   * Modularización enfocada en mantenibilidad y escalabilidad.
4. **Abstracción e Integración de Servicio Externo (`Spotify Integration + Proxy`)**:
   * Integración con la API externa de **Spotify**.
   * Aplicación del **Patrón Proxy** para gestionar la interacción, caché o control de acceso hacia las peticiones del servicio de Spotify.

5. **Unit Testing**:
   * Incorporación de pruebas unitarias para validar la lógica del dominio, presentadores y adaptadores.
   * Cobertura de tests asegurando la robustez de la aplicación ante futuros cambios.

---

## Tecnologías y Patrones Aplicados

* **Patrones de Arquitectura:** Model-View-Presenter (MVP), Clean Architecture.
* **Patrones de Diseño:** Singleton, Factory, Decorator, Proxy Pattern (para servicios de Spotify).
* **Principios de Diseño:** SOLID, Clean Code.
* **Integración de APIs:** API Rest de Spotify.
* **Testing:** Pruebas unitarias de componentes y adaptadores.

---

## Cómo Ejecutar el Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/KatherinaVilca/SongInfo_AyDS.git](https://github.com/KatherinaVilca/SongInfo_AyDS.git)
