package Modelo;

/**
 * Representa a un solicitante de préstamo bancario dentro del sistema
 * de evaluación de créditos.
 * <p>
 * La invariante se verifica automáticamente al construir el objeto.
 * Si algún atributo la viola, se lanza una {@link IllegalArgumentException}.
 * </p>
 *
 * @author Keiver
 */
public class Solicitante {

    /**
     * Nombre completo del solicitante. No puede ser {@code null} ni estar en
     * blanco.
     */
    private String nombre;

    /** Edad del solicitante en años. Debe ser mayor que 0. */
    private int edad;

    /**
     * Puntaje crediticio del solicitante en escala de 0 a 850.
     * <ul>
     * <li>Menor a 500: riesgo alto, rechazo automático.</li>
     * <li>Entre 500 y 699: riesgo medio, depende del empleo.</li>
     * <li>Mayor o igual a 700: riesgo bajo, posible aprobación.</li>
     * </ul>
     */
    private int puntajeCrediticio;

    /**
     * Indica si el solicitante tiene deudas pendientes al momento de la solicitud.
     * {@code true} si tiene deudas, {@code false} en caso contrario.
     */
    private boolean tieneDeudas;

    /**
     * Años de empleo continuo del solicitante en su trabajo actual.
     * Un valor mayor o igual a 2.0 se considera empleo estable.
     * No puede ser negativo.
     */
    private double aniosEmpleo;

    /**
     * Ingreso mensual del solicitante.
     * Determina el monto máximo de préstamo permitido (5 veces este valor).
     * No puede ser negativo.
     */
    private double ingresoMensual;

    /**
     * Construye un nuevo solicitante con los datos proporcionados y verifica
     * que cumplan la invariante de clase.
     *
     * @param nombre            nombre completo (no nulo, no en blanco)
     * @param edad              edad en años
     * @param puntajeCrediticio puntaje crediticio
     * @param tieneDeudas       si tiene deudas pendientes
     * @param aniosEmpleo       años de empleo continuo
     * @param ingresoMensual    ingreso mensual en moneda local
     * @throws IllegalArgumentException si algún parámetro viola la invariante de
     *                                  clase
     */
    public Solicitante(String nombre, int edad, int puntajeCrediticio, boolean tieneDeudas, double aniosEmpleo,
            double ingresoMensual) {
        this.nombre = nombre;
        this.edad = edad;
        this.puntajeCrediticio = puntajeCrediticio;
        this.tieneDeudas = tieneDeudas;
        this.aniosEmpleo = aniosEmpleo;
        this.ingresoMensual = ingresoMensual;
        verificarInvariante();
    }

    /**
     * Verifica que todos los atributos cumplan la invariante de clase.
     * Se llama automáticamente al construir el objeto.
     *
     * @throws IllegalArgumentException si alguna condición de la invariante es
     *                                  violada
     */
    private void verificarInvariante() {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (edad <= 0) {
            throw new IllegalArgumentException("Edad debe ser > 0");
        }
        if (puntajeCrediticio < 0 || puntajeCrediticio > 850) {
            throw new IllegalArgumentException(
                    "Puntaje debe estar entre 0 y 850");
        }
        if (aniosEmpleo < 0) {
            throw new IllegalArgumentException(
                    "Años de empleo no pueden ser negativos");
        }
        if (ingresoMensual < 0) {
            throw new IllegalArgumentException(
                    "Ingreso mensual no puede ser negativo");
        }
    }

    /**
     * Retorna el nombre completo del solicitante.
     *
     * @return nombre del solicitante
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del solicitante.
     *
     * @param nombre nuevo nombre (no nulo, no en blanco)
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna la edad del solicitante en años.
     *
     * @return edad del solicitante
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del solicitante.
     *
     * @param edad nueva edad en años
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Retorna el puntaje crediticio del solicitante.
     *
     * @return puntaje crediticio
     */
    public int getPuntajeCrediticio() {
        return puntajeCrediticio;
    }

    /**
     * Establece el puntaje crediticio del solicitante.
     *
     * @param puntajeCrediticio nuevo puntaje
     */
    public void setPuntajeCrediticio(int puntajeCrediticio) {
        this.puntajeCrediticio = puntajeCrediticio;
    }

    /**
     * Indica si el solicitante tiene deudas pendientes.
     *
     * @return {@code true} si tiene deudas, {@code false} en caso contrario
     */
    public boolean isTieneDeudas() {
        return tieneDeudas;
    }

    /**
     * Establece si el solicitante tiene deudas pendientes.
     *
     * @param tieneDeudas {@code true} si tiene deudas, {@code false} si no tiene
     *                    deudas
     */
    public void setTieneDeudas(boolean tieneDeudas) {
        this.tieneDeudas = tieneDeudas;
    }

    /**
     * Retorna los años de empleo continuo del solicitante.
     *
     * @return años de empleo (&ge; 0)
     */
    public double getAniosEmpleo() {
        return aniosEmpleo;
    }

    /**
     * Establece los años de empleo continuo del solicitante.
     *
     * @param aniosEmpleo nuevos años de empleo
     */
    public void setAniosEmpleo(double aniosEmpleo) {
        this.aniosEmpleo = aniosEmpleo;
    }

    /**
     * Retorna el ingreso mensual del solicitante.
     *
     * @return ingreso mensual en moneda local
     */
    public double getIngresoMensual() {
        return ingresoMensual;
    }

    /**
     * Establece el ingreso mensual del solicitante.
     *
     * @param ingresoMensual nuevo ingreso mensual
     */
    public void setIngresoMensual(double ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    /**
     * Retorna una representación textual del solicitante con todos sus atributos.
     *
     * @return cadena con los datos del solicitante en formato legible
     */
    @Override
    public String toString() {
        return "Solicitante{" + "nombre=" + nombre + ", edad=" + edad + ", puntajeCrediticio=" + puntajeCrediticio
                + ", tieneDeudas=" + tieneDeudas + ", aniosEmpleo=" + aniosEmpleo + ", ingresoMensual=" + ingresoMensual
                + '}';
    }

}