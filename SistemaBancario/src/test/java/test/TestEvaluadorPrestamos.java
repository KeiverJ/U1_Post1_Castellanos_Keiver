package test;


import Modelo.ResultadoPrestamo;
import Modelo.Solicitante;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Keiver
 */
/**
 * Suite de pruebas JUnit 5 para EvaluadorPrestamos. Cubre cada regla de
 * la tabla de decisión y casos de violación de precondiciones.
 */
public class TestEvaluadorPrestamos {

    // ─────────────────────────────────────────────────────────────────────────
    //  REGLAS DE LA TABLA DE DECISIÓN
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("R1: puntaje≥700, sin deudas, monto válido → APROBADO")
    void testR1_AprobadoAutomatico() {
        Solicitante sol = new Solicitante("Ana Torres", 30,
                750, false, 5.0, 3000.0);
        // monto = 10 000, límite = 5 × 3000 = 15 000  ✓
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 10_000.0);
        assertEquals(ResultadoPrestamo.APROBADO, result);
    }

    @Test
    @DisplayName("R2: puntaje≥700, con deudas, monto válido → REVISION_MANUAL")
    void testR2_PuntajeAltoConDeudas() {
        Solicitante sol = new Solicitante("Bob Reyes", 35,
                720, true, 3.0, 4000.0);
        // monto = 8 000, límite = 5 × 4000 = 20 000  ✓
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 8_000.0);
        assertEquals(ResultadoPrestamo.REVISION_MANUAL, result);
    }

    @Test
    @DisplayName("R3: puntaje∈[500,699], empleo≥2 años, monto válido → REVISION_MANUAL")
    void testR3_PuntajeMedioEmpleoEstable() {
        Solicitante sol = new Solicitante("Clara Díaz", 28,
                620, false, 3.0, 2500.0);
        // monto = 5 000, límite = 5 × 2500 = 12 500  ✓
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 5_000.0);
        assertEquals(ResultadoPrestamo.REVISION_MANUAL, result);
    }

    @Test
    @DisplayName("R4: puntaje∈[500,699], empleo<2 años, monto válido → RECHAZADO")
    void testR4_PuntajeMedioSinEmpleoEstable() {
        Solicitante sol = new Solicitante("David Mora", 22,
                580, false, 1.0, 1800.0);
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 4_000.0);
        assertEquals(ResultadoPrestamo.RECHAZADO, result);
    }

    @Test
    @DisplayName("R5: puntaje<500 → RECHAZADO siempre")
    void testR5_PuntajeBajo() {
        Solicitante sol = new Solicitante("Eva Gómez", 40,
                450, false, 10.0, 5000.0);
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 1_000.0);
        assertEquals(ResultadoPrestamo.RECHAZADO, result);
    }

    @Test
    @DisplayName("R6: menor de 18 años → excepción (precondición de negocio)")
    void testR6_MenorDeEdad() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Solicitante("Franco Kids", 16,
                        700, false, 0.0, 500.0));
        // Si el objeto se construye con edad > 0 pero < 18,
        // la excepción la lanza evaluarPrestamo.
        // Aquí forzamos la construcción con edad 17 válida para
        // el constructor (edad > 0) pero inválida para el evaluador.
        Solicitante menor = new Solicitante("Franco Kids", 17,
                700, false, 0.0, 500.0);
        assertThrows(IllegalArgumentException.class, ()
                -> EvaluadorPrestamos.evaluarPrestamo(menor, 1_000.0));
    }

    @Test
    @DisplayName("R7: puntaje≥700, sin deudas, monto>5× ingreso → RECHAZADO")
    void testR7_PuntajeAltoMontoExcesivo() {
        Solicitante sol = new Solicitante("Gina Peña", 45,
                800, false, 6.0, 2000.0);
        // monto = 15 000, límite = 5 × 2000 = 10 000  ✗
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 15_000.0);
        assertEquals(ResultadoPrestamo.RECHAZADO, result);
    }

    @Test
    @DisplayName("R8: puntaje≥700, con deudas, monto>5× ingreso → RECHAZADO")
    void testR8_PuntajeAltoDeudaYMontoExcesivo() {
        Solicitante sol = new Solicitante("Hugo Salas", 38,
                710, true, 4.0, 3000.0);
        // monto = 20 000, límite = 5 × 3000 = 15 000  ✗
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 20_000.0);
        assertEquals(ResultadoPrestamo.RECHAZADO, result);
    }

    @Test
    @DisplayName("R9: puntaje∈[500,699], empleo≥2 años, monto>5× ingreso → RECHAZADO")
    void testR9_PuntajeMedioMontoExcesivo() {
        Solicitante sol = new Solicitante("Irene Vega", 29,
                650, false, 3.0, 1000.0);
        // monto = 8 000, límite = 5 × 1000 = 5 000  ✗
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 8_000.0);
        assertEquals(ResultadoPrestamo.RECHAZADO, result);
    }

    @Test
    @DisplayName("Límite exacto: monto == 5× ingreso → no debe ser rechazado por monto")
    void testLimiteExactoMonto() {
        Solicitante sol = new Solicitante("Jorge Leal", 33,
                750, false, 5.0, 4000.0);
        // monto = 20 000 = 5 × 4000 (exactamente el límite permitido)
        ResultadoPrestamo result = EvaluadorPrestamos.evaluarPrestamo(sol, 20_000.0);
        assertEquals(ResultadoPrestamo.APROBADO, result);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  VIOLACIONES DE PRECONDICIONES
    // ─────────────────────────────────────────────────────────────────────────
    @Test
    @DisplayName("PRE: monto negativo → IllegalArgumentException")
    void testPrecondicion_MontoNegativo() {
        Solicitante sol = new Solicitante("Laura Cruz", 25,
                600, false, 1.0, 2000.0);
        assertThrows(IllegalArgumentException.class, ()
                -> EvaluadorPrestamos.evaluarPrestamo(sol, -500.0));
    }

    @Test
    @DisplayName("PRE: monto cero → IllegalArgumentException")
    void testPrecondicion_MontoCero() {
        Solicitante sol = new Solicitante("Mario Ortiz", 30,
                700, false, 3.0, 3000.0);
        assertThrows(IllegalArgumentException.class, ()
                -> EvaluadorPrestamos.evaluarPrestamo(sol, 0.0));
    }

    @Test
    @DisplayName("PRE: solicitante null → IllegalArgumentException")
    void testPrecondicion_SolicitanteNull() {
        assertThrows(IllegalArgumentException.class, ()
                -> EvaluadorPrestamos.evaluarPrestamo(null, 5_000.0));
    }

    @Test
    @DisplayName("INV: puntaje fuera de rango (>850) → IllegalArgumentException en constructor")
    void testInvariante_PuntajeFueraDeRango() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Solicitante("Nora Silva", 28, 900,
                        false, 2.0, 2000.0));
    }

    @Test
    @DisplayName("INV: nombre en blanco → IllegalArgumentException en constructor")
    void testInvariante_NombreBlanco() {
        assertThrows(IllegalArgumentException.class, ()
                -> new Solicitante("   ", 25, 650,
                        false, 2.0, 1500.0));
    }
}
