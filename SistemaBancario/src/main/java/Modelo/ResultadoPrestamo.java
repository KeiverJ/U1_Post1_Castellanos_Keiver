package Modelo;

/**
 * Enumeración que representa los posibles resultados
 * de la evaluación de una solicitud de préstamo bancario.
 * {@link #APROBADO} – el préstamo es aprobado automáticamente.
 * {@link #RECHAZADO} – el préstamo es denegado.
 * {@link #REVISION_MANUAL} – el caso requiere revisión humana.
 * 
 */

/**
 *
 * @author Keiver
 */
public enum ResultadoPrestamo {
    APROBADO, RECHAZADO, REVISION_MANUAL
}
