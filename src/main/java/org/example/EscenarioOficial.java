package org.example;

import org.example.model.LineaEvolutiva;
import org.example.model.Pokemon;

public class EscenarioOficial {

    public static LineaEvolutiva Pokemon1() {
        Pokemon charizard = new Pokemon("Charizard", 78, 84, 78, -1, null);
        Pokemon charmeleon = new Pokemon("Charmeleon", 58, 64, 58, 5000, charizard);
        Pokemon charmander = new Pokemon("Charmander", 39, 52, 43, 1500, charmeleon);

        return new LineaEvolutiva(charmander);
    }
    public static LineaEvolutiva Pokemon2() {
        Pokemon pikachu = new Pokemon("pikachu", 78, 84, 78, -1, null);
        Pokemon raichu = new Pokemon("raichu", 58, 64, 58, 5000, pikachu);

        return new LineaEvolutiva(raichu);
    }
     public static LineaEvolutiva Pokemon3() {
        Pokemon greninja = new Pokemon("greninja", 39, 52, 43, 1500, null);
        return new LineaEvolutiva(greninja);
    }
       

    public static Pokemon[] generarHordaCaterpie(int cantidad) {
        Pokemon[] horda = new Pokemon[cantidad];
        for (int i = 0; i < cantidad; i++) {
            horda[i] = new Pokemon("Caterpie", 45, 30, 35, -1);
        }
        return horda;
    }
}

