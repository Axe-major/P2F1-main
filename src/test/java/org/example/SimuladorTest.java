package org.example;
import org.example.model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimuladorTest {

    @Test
    void combateContraRattataCoincideConElCasoDelEnunciado() {
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500);
        Pokemon rattata = new Pokemon("Rattata", 30, 56, 35, -1);

        Simulador.ResultadoBatalla resultado = Simulador.combatir(charmander, rattata);

        // Turno 1: dano = max(1, 52-35) = 17 -> Rattata 13 HP
        // Turno 2: dano = max(1, 56-43) = 13 -> Charmander 26 HP
        // Turno 3: dano = 17 -> Rattata a 0 HP, Charmander gana
        assertTrue(resultado.gano);
        assertEquals(26, resultado.hpAtacanteRestante);
        assertTrue(resultado.hpEnemigoRestante <= 0);
    }

    @Test
    void elDanoMinimoEsSiempreUnoAunqueLaDefensaSeaMuchoMayor() {
        Pokemon atacanteDebil = new Pokemon("Debil", 50, 10, 10, -1);
        Pokemon defensorFuerte = new Pokemon("Tanque", 100, 5, 999, -1);

        Simulador.ResultadoBatalla resultado = Simulador.combatir(atacanteDebil, defensorFuerte);

        // Con defensa 999 contra ataque 10, el max(1, ...) debe forzar dano = 1 por turno
        assertFalse(resultado.gano); // el atacante debil no deberia poder ganarle a un HP:100 con dano 1
    }
}