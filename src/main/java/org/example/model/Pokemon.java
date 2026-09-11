package org.example.model;
import java.util.Objects;

public class Pokemon {

    private String nombre;
    private int puntosDeVidaMaximos;
    private int ataque;
    private int defensa;
    private int experienciaRequerida;
    private Pokemon siguienteEvolucion;

    public Pokemon(String nombre, int puntosDeVidaMaximos, int ataque,
                   int defensa, int experienciaRequerida, Pokemon siguienteEvolucion) {
        this.nombre = nombre;
        this.puntosDeVidaMaximos = puntosDeVidaMaximos;
        this.ataque = ataque;
        this.defensa = defensa;
        this.experienciaRequerida = experienciaRequerida;
        this.siguienteEvolucion = siguienteEvolucion;
    }
    public Pokemon(String nombre, int puntosDeVidaMaximos, int ataque,
                   int defensa, int experienciaRequerida) {
        this(nombre, puntosDeVidaMaximos, ataque, defensa, experienciaRequerida, null);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntosDeVidaMaximos() { return puntosDeVidaMaximos; }
    public void setPuntosDeVidaMaximos(int puntosDeVidaMaximos) { this.puntosDeVidaMaximos = puntosDeVidaMaximos; }

    public int getAtaque() { return ataque; }
    public void setAtaque(int ataque) { this.ataque = ataque; }

    public int getDefensa() { return defensa; }
    public void setDefensa(int defensa) { this.defensa = defensa; }

    public int getExperienciaRequerida() { return experienciaRequerida; }
    public void setExperienciaRequerida(int experienciaRequerida) { this.experienciaRequerida = experienciaRequerida; }

    public Pokemon getSiguienteEvolucion() { return siguienteEvolucion; }
    public void setSiguienteEvolucion(Pokemon siguienteEvolucion) { this.siguienteEvolucion = siguienteEvolucion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return puntosDeVidaMaximos == pokemon.puntosDeVidaMaximos &&
                ataque == pokemon.ataque &&
                defensa == pokemon.defensa &&
                experienciaRequerida == pokemon.experienciaRequerida &&
                Objects.equals(nombre, pokemon.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, puntosDeVidaMaximos, ataque, defensa, experienciaRequerida);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "nombre='" + nombre + '\'' +
                ", HP=" + puntosDeVidaMaximos +
                ", ATK=" + ataque +
                ", DEF=" + defensa +
                ", XP Requerida=" + experienciaRequerida +
                ", Siguiente=" + (siguienteEvolucion != null ? siguienteEvolucion.getNombre() : "Fase Final") +
                '}';
    }
}