package org.example.model;

import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LineaEvolutiva {

    private final Pokemon cabezaInicial;
    private Pokemon faseActual;
    private long experienciaAcumulada;
    private static final Logger log = LogManager.getLogger(LineaEvolutiva.class);

    public LineaEvolutiva(Pokemon cabezaInicial) {
        if (cabezaInicial == null) {
            throw new IllegalArgumentException("La cabeza inicial de la linea evolutiva no puede ser nula.");
        }
        this.cabezaInicial = cabezaInicial;
        this.faseActual = cabezaInicial;
        this.experienciaAcumulada = 0;
    }

    public void ganarExperiencia(long experienciaPuntos) {
        if (experienciaPuntos < 0) {
            throw new IllegalArgumentException("La experiencia ganada no puede ser negativa.");
        }
        this.experienciaAcumulada += experienciaPuntos;
        evaluarEvolucion();
    }

    public boolean evaluarEvolucion() {
        boolean evoluciono = false;

        while (faseActual.getSiguienteEvolucion() != null &&
                this.experienciaAcumulada >= faseActual.getExperienciaRequerida()) {

            Pokemon faseAnterior = faseActual;
            faseActual = faseActual.getSiguienteEvolucion();
            evoluciono = true;

            log.info("{} ha evolucionado en {}", faseAnterior.getNombre(), faseActual.getNombre());
        }

        return evoluciono;
    }

    public boolean estaEnFaseFinal() {
        return faseActual.getSiguienteEvolucion() == null;
    }

    public Pokemon getFaseActual() { return faseActual; }
    public Pokemon getCabezaInicial() { return cabezaInicial; }
    public long getExperienciaAcumulada() { return experienciaAcumulada; }
    public String getNombreActual() { return faseActual.getNombre(); }
    public int getPuntosDeVidaMaximosActuales() { return faseActual.getPuntosDeVidaMaximos(); }
    public int getAtaqueActual() { return faseActual.getAtaque(); }
    public int getDefensaActual() { return faseActual.getDefensa(); }

    public String obtenerCaminoEvolutivo() {
        StringBuilder sb = new StringBuilder();
        Pokemon actual = cabezaInicial;
        while (actual != null) {
            if (actual.equals(faseActual)) {
                sb.append("[").append(actual.getNombre()).append(" (ACTUAL)]");
            } else {
                sb.append(actual.getNombre());
            }
            if (actual.getSiguienteEvolucion() != null) {
                sb.append(" -> ");
            }
            actual = actual.getSiguienteEvolucion();
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaEvolutiva otra = (LineaEvolutiva) o;
        return experienciaAcumulada == otra.experienciaAcumulada &&
                Objects.equals(cabezaInicial, otra.cabezaInicial) &&
                Objects.equals(faseActual, otra.faseActual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cabezaInicial, faseActual, experienciaAcumulada);
    }

    @Override
    public String toString() {
        return "LineaEvolutiva{" +
                "faseActual=" + faseActual.getNombre() +
                ", XP Acumulada=" + experienciaAcumulada +
                ", Cadena: " + obtenerCaminoEvolutivo() +
                '}';
    }
}