package Modelo;

import Modelo.Solicitante;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Keiver
 */
public class EvaluadorPrestamos {

    private static final int PUNTAJE_RECHAZO = 500;

    /**
     * Mínimo puntaje crediticio para aprobación/revisión alta.
     */
    private static final int PUNTAJE_ALTO = 700;

    /**
     * Multiplicador máximo permitido sobre el ingreso mensual.
     */
    private static final double FACTOR_MONTO = 5.0;

    /**
     * Mínimos años de empleo estable requeridos.
     */
    private static final double MIN_ANIOS_EMPLEO = 2.0;

    /**
     * Edad mínima para solicitar un préstamo.
     */
    private static final int EDAD_MINIMA = 18;

    public static ResultadoPrestamo evaluarPrestamo(
            Solicitante solicitante, double monto) {

        //  PRECONDICIONES
        if (solicitante == null) {
            throw new IllegalArgumentException(
                    "Precondición violada: solicitante no puede ser null.");
        }

        if (monto <= 0) {
            throw new IllegalArgumentException(
                    "Precondición violada: monto debe ser positivo. Valor recibido: " + monto);
        }

        if (Double.isNaN(monto) || Double.isInfinite(monto)) {
            throw new IllegalArgumentException(
                    "Precondición violada: monto no puede ser NaN ni infinito.");
        }

        // Regla 6: menor de edad → rechazo inmediato (precondición de negocio)
        if (solicitante.getEdad() < EDAD_MINIMA) {
            throw new IllegalArgumentException(
                    "Precondición violada: el solicitante debe tener al menos "
                    + EDAD_MINIMA + " años. Edad recibida: " + solicitante.getEdad());
        }

        //  LÓGICA — traducción de la tabla
        ResultadoPrestamo resultado;

        boolean montoValido = monto <= FACTOR_MONTO * solicitante.getIngresoMensual();
        boolean puntajeAlto = solicitante.getPuntajeCrediticio() >= PUNTAJE_ALTO;
        boolean puntajeMedio = solicitante.getPuntajeCrediticio() >= PUNTAJE_RECHAZO
                && solicitante.getPuntajeCrediticio() < PUNTAJE_ALTO;
        boolean sinDeudas = !solicitante.isTieneDeudas();
        boolean empleoEstable = solicitante.getAniosEmpleo() >= MIN_ANIOS_EMPLEO;

        // R7 / R8: monto excede 5× ingreso → RECHAZADO (aplica a cualquier puntaje)
        if (!montoValido) {
            resultado = ResultadoPrestamo.RECHAZADO;

            // R1: puntaje ≥ 700, sin deudas, monto válido → APROBADO
        } else if (puntajeAlto && sinDeudas) {
            resultado = ResultadoPrestamo.APROBADO;

            // R2: puntaje ≥ 700, con deudas, monto válido → REVISIÓN MANUAL
        } else if (puntajeAlto) {   // tieneDeudas == true implícito
            resultado = ResultadoPrestamo.REVISION_MANUAL;

            // R3: puntaje ∈ [500,699], empleo ≥ 2 años, monto válido → REVISIÓN MANUAL
        } else if (puntajeMedio && empleoEstable) {
            resultado = ResultadoPrestamo.REVISION_MANUAL;

            // R4: puntaje ∈ [500,699], empleo < 2 años → RECHAZADO
        } else if (puntajeMedio) {
            resultado = ResultadoPrestamo.RECHAZADO;

            // R5: puntaje < 500 → RECHAZADO siempre
        } else {
            resultado = ResultadoPrestamo.RECHAZADO;
        }

        //  POSTCONDICIONES 
        assert resultado != null : "Postcondición violada: resultado no puede ser null";

        assert resultado == ResultadoPrestamo.APROBADO
                || resultado == ResultadoPrestamo.RECHAZADO
                || resultado == ResultadoPrestamo.REVISION_MANUAL : "Postcondición violada: resultado fuera del dominio esperado";

        // Si resultado es APROBADO, deben cumplirse todas las condiciones favorables
        assert !(resultado == ResultadoPrestamo.APROBADO)
                || (puntajeAlto && sinDeudas && montoValido) : "Postcondición violada: APROBADO sin cumplir requisitos";

        return resultado;
    }
}
