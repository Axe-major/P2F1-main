package org.example;

import org.example.util.PerformanceReporter;
import org.example.model.LineaEvolutiva;
import org.example.model.Pokemon;

public class Simulador {

    public static final int XP_POR_VICTORIA = 50;

    public static class ResultadoBatalla {
        public final int hpAtacanteRestante;
        public final int hpEnemigoRestante;
        public final boolean gano;

        public ResultadoBatalla(int hpAtacanteRestante, int hpEnemigoRestante, boolean gano) {
            this.hpAtacanteRestante = hpAtacanteRestante;
            this.hpEnemigoRestante = hpEnemigoRestante;
            this.gano = gano;
        }
    }

    public static ResultadoBatalla combatir(Pokemon atacante, Pokemon enemigo) {
        int hpAtacante = atacante.getPuntosDeVidaMaximos();
        int hpEnemigo = enemigo.getPuntosDeVidaMaximos();

        while (hpEnemigo > 0 && hpAtacante > 0) {
            int danoMio = Math.max(1, atacante.getAtaque() - enemigo.getDefensa());
            hpEnemigo -= danoMio;
            if (hpEnemigo <= 0) {
                break;
            }
            int danoEnemigo = Math.max(1, enemigo.getAtaque() - atacante.getDefensa());
            hpAtacante -= danoEnemigo;
        }

        boolean gano = hpEnemigo <= 0;
        return new ResultadoBatalla(hpAtacante, hpEnemigo, gano);
    }

    public static void iniciarEntrenamientoMasivo(LineaEvolutiva miPokemon, Pokemon[] hordaEnemigos) {
        PerformanceReporter.reportarMemoriaSistema(); // snapshot antes

        long inicioNs = System.nanoTime();

        for (Pokemon enemigo : hordaEnemigos) {
            ResultadoBatalla resultado = combatir(miPokemon.getFaseActual(), enemigo);
            if (resultado.gano) {
                miPokemon.ganarExperiencia(XP_POR_VICTORIA);
            }
        }

        long finNs = System.nanoTime();

        PerformanceReporter.reportarTiempoEjecucion("Entrenamiento masivo (" + hordaEnemigos.length + " enemigos)", finNs - inicioNs);
        PerformanceReporter.reportarMemoriaSistema(); // snapshot despues
        PerformanceReporter.medirPesoObjeto(miPokemon, "LineaEvolutiva final");
    }
}