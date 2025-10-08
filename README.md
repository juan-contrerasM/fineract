<div align="center">
  <h1>⚙️ Prueba Técnica - Extensión API Apache Fineract ⚙️</h1>
  <h3><strong>Solución</strong></h3>
</div>

<hr>

<h2>🚀 Descripción del Proyecto</h2>
<br>
Este proyecto contiene la solución a la prueba técnica para el cargo de <strong>Desarrollador Java</strong>. El objetivo principal fue <strong>extender la API REST de Apache Fineract</strong> para implementar nuevas funcionalidades de consulta sobre la información de los clientes, demostrando el manejo de la plataforma, buenas prácticas de desarrollo y la creación de endpoints .
<br><br>
Este desarrollo se realizó utilizando <strong>Java</strong> y el ecosistema de <strong>Spring Boot</strong>, aprovechando la capa de persistencia de Fineract basada en <strong>Spring Data JPA</strong>.

<h3>Autor</h3>
<ul>
  <li><strong>Nombre:</strong> Juan José Contreras Molina</li>
  <li><strong>Correo:</strong> juanjcontrerasmolina@gmail.com</li>
  <li><strong>Fecha:</strong> 07 de octubre de 2025</li>
</ul>

<hr>

<h2>🔒 Seguridad</h2>
<br>
Para garantizar la seguridad de los nuevos endpoints, se mantuvo el esquema de autenticación existente en Fineract, basado en <strong>Basic Auth</strong>. Todas las nuevas rutas requieren credenciales válidas para ser consumidas.

<hr>

<h2>🗃️ Persistencia de Datos</h2>
<br>
La persistencia de datos se gestionó a través de la capa de acceso a datos de Fineract, que utiliza <strong>Spring Data JPA</strong>. Para las consultas específicas requeridas, se implementaron <strong>consultas nativas (Native Queries)</strong> en los repositorios correspondientes, optimizadas para obtener la información de manera eficiente directamente desde la base de datos subyacente.

<hr>

<h2>🏗️ Estructura del Proyecto</h2>
<br>
La solución se adhiere a la arquitectura y patrones de diseño de Apache Fineract, que sigue una estructura similar al patrón <strong>MVC (Modelo-Vista-Controlador)</strong>:
<ul>
  <li><strong>Controlador (<code>Controller</code>):</strong> Se modificó la clase <code>ClientsApiResource.java</code> para añadir los nuevos endpoints REST.</li>
  <li><strong>Servicio (<code>Service</code>):</strong> Se extendió la interfaz <code>ClientReadPlatformService.java</code> y su implementación para alojar la lógica de negocio de cada nueva consulta.</li>
  <li><strong>Repositorio (<code>Repository</code>):</strong> Se utilizaron los repositorios existentes, como <code>ClientRepository.java</code>, para ejecutar las consultas nativas contra la base de datos.</li>
</ul>

<hr>

<h2>🛠️ Configuración y Credenciales</h2>
<br>
<h3>Base de Datos</h3>
La solución opera sobre la configuración de base de datos estándar de una instancia de Apache Fineract.
<br>
<h3>Credenciales para Endpoints</h3>
<ul>
  <li><strong>Tipo de Autenticación:</strong> Basic Auth</li>
  <li><strong>Usuario:</strong> <code>mifos</code></li>
  <li><strong>Contraseña:</strong> <code>password</code></li>
  <li><strong>Parámetro de Tenant:</strong> <code>tenantIdentifier=default</code> (debe ser incluido en todas las peticiones como un <em>query parameter</em>).</li>
</ul>

<hr>

<h2>📡 Listado de Endpoints</h2>
<br>
A continuación, se detallan los endpoints desarrollados. La URL base para consumir la API localmente es <code>https://localhost:8443/fineract-provider/api</code>.

<br>

<h3>1. Obtener Clientes con Créditos en Mora</h3>
<ul>
  <li><strong>Descripción:</strong> Devuelve una lista de clientes que tienen al menos un préstamo con saldo vencido.</li>
  <li><strong>Método:</strong> <code>GET</code></li>
  <li><strong>URL:</strong> <code>/v1/clients/credit-negative</code></li>
  <li><strong>Ejemplo (cURL):</strong></li>
</ul>

```bash
curl -k --location 'https://localhost:8443/fineract-provider/api/v1/clients/credit-negative?tenantIdentifier=default' \
--user 'mifos:password'
```

<img width="1349" height="707" alt="image" src="https://github.com/user-attachments/assets/7ebd6c87-0b55-4455-b61b-aa1e0c63fb84" />


<h3>2. Obtener Clientes con Balance de Ahorros Negativo</h3>
<ul>
<li><strong>Descripción:</strong> Devuelve una lista de clientes que tienen al menos una cuenta de ahorros activa con un balance negativo.</li>
<li><strong>Método:</strong> <code>GET</code></li>
<li><strong>URL:</strong> <code>/v1/clients/savings-negative</code></li>
<li><strong>Ejemplo (cURL):</strong></li>
</ul>

```bash
curl -k --location 'https://localhost:8443/fineract-provider/api/v1/clients/savings-negative?tenantIdentifier=default' \
--user 'mifos:password'
```
<img width="1338" height="666" alt="image" src="https://github.com/user-attachments/assets/1761d7b1-4a56-42e9-b74e-a105e5aa8704" />


<h3>3. Obtener Top 3 Clientes con Mayor Balance</h3>
<ul>
<li><strong>Descripción:</strong> Devuelve los tres clientes con el mayor saldo consolidado en sus cuentas de ahorro activas.</li>
<li><strong>Método:</strong> <code>GET</code></li>
<li><strong>URL:</strong> <code>/v1/clients/top-balance</code></li>
<li><strong>Ejemplo (cURL):</strong></li>
</ul>

```bash
curl -k --location 'https://localhost:8443/fineract-provider/api/v1/clients/savings-negative?tenantIdentifier=default' \
--user 'mifos:password
```
<img width="1335" height="699" alt="image" src="https://github.com/user-attachments/assets/601f5c2e-280a-4618-b003-23edd1866939" />


<h3>4. Obtener Clientes con Ahorros en Negativo (Formato JSON)</h3>
<ul>
<li><strong>Descripción:</strong> Endpoint específico que demuestra la conversión explícita de la lista de clientes a una cadena JSON.</li>
<li><strong>Método:</strong> <code>GET</code></li>
<li><strong>URL:</strong> <code>/v1/clients/savings-negative-json</code></li>
<li><strong>Ejemplo (cURL):</strong></li>
</ul>

```bash
curl -k --location 'https://localhost:8443/fineract-provider/api/v1/clients/savings-negative-json?tenantIdentifier=default' \
--user 'mifos:password'
```
<img width="1347" height="706" alt="image" src="https://github.com/user-attachments/assets/1e327762-556d-4271-ade4-eecb66896ab2" />


<hr>

## 📚 Respuestas Teóricas y Parte Práctica

> ⚠️ **Importante:**  
> Las **respuestas teóricas** y la **explicación detallada de la parte práctica** se encuentran disponibles en el siguiente documento:  
> 🔗 [**Ver documento completo en Google Docs**](https://docs.google.com/document/d/1TIDuuYFjtm2F-NmfDSMu_ehtlkZVdzi22Arn7s1SAu8/edit?usp=sharing)
>
> 📘 En este documento se describen:
> - Las respuestas a las preguntas teóricas de la prueba técnica.  
> - El contexto de datos utilizados para las pruebas.  
> - La ejecución paso a paso de los endpoints implementados.  
> - Evidencias de las consultas y resultados obtenidos en Fineract.  
>
> 🧩 **Nota adicional:**  
> Los cambios correspondientes a la parte práctica del desarrollo se encuentran en el siguiente commit del repositorio:  
> ```
> Add practical Java exercise for technical test Cofincafe
> ```
>
> *(Puedes revisar este commit para ver las clases modificadas y el código fuente del ejercicio práctico.)*
