package Modelo;

/**
 *
 * @author Keiver
 */
public class Solicitante {

    private String nombre;
    private int edad;
    private int puntajeCrediticio;
    private boolean tieneDeudas;
    private double aniosEmpleo;
    private double ingresoMensual;

    public Solicitante(String nombre, int edad, int puntajeCrediticio, boolean tieneDeudas, double aniosEmpleo, double ingresoMensual) {
        this.nombre = nombre;
        this.edad = edad;
        this.puntajeCrediticio = puntajeCrediticio;
        this.tieneDeudas = tieneDeudas;
        this.aniosEmpleo = aniosEmpleo;
        this.ingresoMensual = ingresoMensual;
        verificarInvariante();
    }

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPuntajeCrediticio() {
        return puntajeCrediticio;
    }

    public void setPuntajeCrediticio(int puntajeCrediticio) {
        this.puntajeCrediticio = puntajeCrediticio;
    }

    public boolean isTieneDeudas() {
        return tieneDeudas;
    }

    public void setTieneDeudas(boolean tieneDeudas) {
        this.tieneDeudas = tieneDeudas;
    }

    public double getAniosEmpleo() {
        return aniosEmpleo;
    }

    public void setAniosEmpleo(double aniosEmpleo) {
        this.aniosEmpleo = aniosEmpleo;
    }

    public double getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(double ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    @Override
    public String toString() {
        return "Solicitante{" + "nombre=" + nombre + ", edad=" + edad + ", puntajeCrediticio=" + puntajeCrediticio + ", tieneDeudas=" + tieneDeudas + ", aniosEmpleo=" + aniosEmpleo + ", ingresoMensual=" + ingresoMensual + '}';
    }

}
