# U1_Post1_Castellanos_Keiver

## Sistema de Evaluación de Préstamos Bancarios

> **Laboratorio · Unidad 1 — Especificación Formal con Contratos y Tablas de Decisión**  
> Universidad de Santander (UDES)

---

## Descripción

Este proyecto implementa el módulo de evaluación de préstamos de un banco aplicando
**especificación formal** mediante contratos (precondiciones / postcondiciones / invariantes de clase)
y una **tabla de decisión** de 9 reglas. La implementación está en Java 21 con pruebas JUnit 5.

---

---

## Requisitos

| Herramienta | Versión |
| ----------- | ------- |
| Java JDK    | 21      |
| Maven       | 3.8+    |

---

## Compilación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/KeiversJ/U1_Post1_Castellanos_Keiver.git
cd U1_Post1_Castellanos_Keiver/SistemaBancario
```

### 2. Compilar el proyecto

```bash
mvn compile
```

### 3. Ejecutar todas las pruebas

```bash
mvn test
```

> Las assertions Java (`assert`) se activan automáticamente con `-ea`
> gracias a la configuración del plugin Surefire en `pom.xml`.

### 4. Ejecutar desde NetBeans / IntelliJ IDEA

1. Abrir la carpeta `SistemaBancario/` como proyecto Maven.
2. En **Run → Edit Configurations → VM Options** agregar: `-ea`
3. Click derecho sobre `TestEvaluadorPrestamos` → **Run**.

### 5. Resultado esperado

```
Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Reglas de negocio implementadas

| Regla | Condiciones                                                         | Resultado                                    |
| ----- | ------------------------------------------------------------------- | -------------------------------------------- |
| R1    | Edad ≥ 18, puntaje ≥ 700, sin deudas, monto ≤ 5× ingreso            | **APROBADO**                                 |
| R2    | Edad ≥ 18, puntaje ≥ 700, con deudas, monto ≤ 5× ingreso            | **REVISIÓN MANUAL**                          |
| R3    | Edad ≥ 18, puntaje ∈ [500,699], empleo ≥ 2 años, monto ≤ 5× ingreso | **REVISIÓN MANUAL**                          |
| R4    | Edad ≥ 18, puntaje ∈ [500,699], empleo < 2 años                     | **RECHAZADO**                                |
| R5    | Edad ≥ 18, puntaje < 500                                            | **RECHAZADO**                                |
| R6    | Edad < 18                                                           | **RECHAZADO** (excepción en evaluarPrestamo) |
| R7    | Edad ≥ 18, puntaje ≥ 700, sin deudas, monto > 5× ingreso            | **RECHAZADO**                                |
| R8    | Edad ≥ 18, puntaje ≥ 700, con deudas, monto > 5× ingreso            | **RECHAZADO**                                |
| R9    | Edad ≥ 18, puntaje ∈ [500,699], empleo ≥ 2 años, monto > 5× ingreso | **RECHAZADO**                                |

---

## Decisiones de diseño

### Contratos con excepciones vs. assert

Las **precondiciones** usan `IllegalArgumentException` porque son errores del llamador
que deben detectarse siempre, incluso en producción (no dependen de `-ea`).
Las **postcondiciones** usan `assert` porque son verificaciones internas que solo
se activan en desarrollo y pruebas, sin costo en producción.

### Invariante de clase en Solicitante

`verificarInvariante()` se llama al final del constructor para garantizar que
ningún objeto `Solicitante` pueda existir en estado inválido. Los atributos
no son `final` para permitir setters, pero la invariante protege la construcción.

### Menor de edad como precondición de evaluarPrestamo

La regla 6 del cliente (edad < 18) se valida en `evaluarPrestamo` y no en el
constructor de `Solicitante`, porque la restricción de edad es una regla de
**negocio bancario**, no una restricción estructural del objeto. Un `Solicitante`
de 16 años es un objeto válido; simplemente no puede solicitar préstamos.

### Prioridad del rechazo por monto

La condición `monto > 5× ingreso` se evalúa **primero** en la cadena de decisión,
antes de analizar puntaje o deudas, porque es un límite absoluto que aplica a
cualquier perfil crediticio sin excepción.

### Constantes nombradas

Se usan constantes (`PUNTAJE_ALTO`, `FACTOR_MONTO`, etc.) en lugar de números
mágicos para que el código sea legible y fácil de modificar si las reglas cambian.

---

## Cobertura de pruebas

| Tipo                          | Cantidad |
| ----------------------------- | -------- |
| Reglas de decisión (R1–R9)    | 10       |
| Violaciones de precondiciones | 5        |
| **Total**                     | **15**   |
