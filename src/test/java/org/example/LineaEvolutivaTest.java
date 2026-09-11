package org.example;
import org.example.model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineaEvolutivaTest {

    // --- Helper para armar la cadena oficial del enunciado en cada prueba ---
    private LineaEvolutiva construirLineaOficial() {
        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1, null);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000, charizard);
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500, charmeleon);
        return new LineaEvolutiva(charmander);
    }

    @Test
    void estadoInicialEsCharmanderSinXp() {
        LineaEvolutiva linea = construirLineaOficial();

        assertEquals("Charmander", linea.getNombreActual());
        assertEquals(0, linea.getExperienciaAcumulada());
        assertFalse(linea.estaEnFaseFinal());
    }

    @Test
    void noEvolucionaConXpInsuficiente() {
        LineaEvolutiva linea = construirLineaOficial();

        linea.ganarExperiencia(1000); // menos de 1500 requeridos
        assertEquals("Charmander", linea.getNombreActual());
    }

    @Test
    void evolucionaACharmeleonAlAlcanzar1500XpTotal() {
        LineaEvolutiva linea = construirLineaOficial();

        linea.ganarExperiencia(1000);
        linea.ganarExperiencia(500); // total = 1500
        assertEquals("Charmeleon", linea.getNombreActual());
    }

    @Test
    void evolucionaACharizardAlAlcanzar5000XpTotal() {
        LineaEvolutiva linea = construirLineaOficial();

        linea.ganarExperiencia(1500); // evoluciona a Charmeleon
        linea.ganarExperiencia(3500); // total = 5000
        assertEquals("Charizard", linea.getNombreActual());
        assertTrue(linea.estaEnFaseFinal());
    }

    @Test
    void saltaDosFasesDeUnaSolaLlamadaSiLaXpEsSuficiente() {
        LineaEvolutiva linea = construirLineaOficial();

        linea.ganarExperiencia(6000); // deberia pasar Charmander -> Charmeleon -> Charizard de una vez
        assertEquals("Charizard", linea.getNombreActual());
    }

    @Test
    void hitosExactosDeLaPruebaDeEstres100Caterpie() {
        LineaEvolutiva linea = construirLineaOficial();

        for (int i = 1; i <= 29; i++) {
            linea.ganarExperiencia(50);
        }
        assertEquals("Charmander", linea.getNombreActual(), "Con 1450 XP no deberia evolucionar aun");

        linea.ganarExperiencia(50); // Caterpie #30, total 1500
        assertEquals("Charmeleon", linea.getNombreActual(), "Con 1500 XP debe evolucionar a Charmeleon");

        for (int i = 31; i <= 99; i++) {
            linea.ganarExperiencia(50);
        }
        assertEquals("Charmeleon", linea.getNombreActual(), "Con 4950 XP no deberia evolucionar aun");

        linea.ganarExperiencia(50); // Caterpie #100, total 5000
        assertEquals("Charizard", linea.getNombreActual(), "Con 5000 XP debe evolucionar a Charizard");
    }

    @Test
    void faseFinalNoEvolucionaMasAunConMuchaXp() {
        LineaEvolutiva linea = construirLineaOficial();
        linea.ganarExperiencia(6000); // ya en Charizard

        linea.ganarExperiencia(500); // xp de mas en fase final
        assertEquals("Charizard", linea.getNombreActual());
        assertTrue(linea.estaEnFaseFinal());
        assertNull(linea.getFaseActual().getSiguienteEvolucion());
    }

    @Test
    void ganarExperienciaNegativaLanzaExcepcion() {
        LineaEvolutiva linea = construirLineaOficial();
        assertThrows(IllegalArgumentException.class, () -> linea.ganarExperiencia(-1));
    }

    @Test
    void constructorConCabezaNulaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new LineaEvolutiva(null));
    }

    @Test
    void equalsYHashCodeDePokemonPorContenido() {
        Pokemon charmander1 = new Pokemon("Charmander", 39, 52, 43, 1500);
        Pokemon charmander2 = new Pokemon("Charmander", 39, 52, 43, 1500);

        assertEquals(charmander1, charmander2);
        assertEquals(charmander1.hashCode(), charmander2.hashCode());
    }

    @Test
    void obtenerCaminoEvolutivoMarcaLaFaseActual() {
        LineaEvolutiva linea = construirLineaOficial();
        linea.ganarExperiencia(1500); // ahora esta en Charmeleon

        String camino = linea.obtenerCaminoEvolutivo();
        assertTrue(camino.contains("[Charmeleon (ACTUAL)]"));
        assertTrue(camino.contains("Charmander"));
        assertTrue(camino.contains("Charizard"));
    }
}