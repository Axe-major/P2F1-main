package org.example;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.example.model.LineaEvolutiva;
import org.example.model.Pokemon;
import org.example.model.ReporteBatalla;
import org.example.util.PerformanceReporter;

public class Main {

    public static void main(String[] args) {
        int enemigos =  400;
        LineaEvolutiva miPokemon1 = EscenarioOficial.Pokemon1();
        LineaEvolutiva miPokemon2 = EscenarioOficial.Pokemon2();
        LineaEvolutiva miPokemon3 = EscenarioOficial.Pokemon3();
        Pokemon[] horda = EscenarioOficial.generarHordaCaterpie(enemigos);
        Queue <LineaEvolutiva> equipo = new LinkedList<>();
        equipo.add(miPokemon1);
        equipo.add(miPokemon2);
        equipo.add(miPokemon3);

        Stack<ReporteBatalla> batallas = new Stack<>();
        final int capacidad = 10;
        int i = 1;
        for (Pokemon enemigo : horda) {
            Simulador.ResultadoBatalla resultado =
            Simulador.combatir(equipo.peek().getFaseActual(), enemigo);

            if (resultado.gano) {
                equipo.peek().ganarExperiencia(Simulador.XP_POR_VICTORIA);
                System.out.println("Gano: " + equipo.peek().getNombreActual());
                i++;
                ReporteBatalla nuevo = new ReporteBatalla(equipo.peek().getNombreActual(), enemigo.getNombre());
                if (!batallas.isEmpty()
                        && batallas.peek().getNombre().equals(nuevo.getNombre())
                        && batallas.peek().getEnemigo().equals(nuevo.getEnemigo())) {

                    ReporteBatalla ultimo = batallas.peek();
                    ultimo.setDerrotados(ultimo.getDerrotados() + 1);

                } else {
                    if (batallas.size() == capacidad) {
                        batallas.remove(0);
                    }

                    batallas.push(nuevo);
                }
            }

            if (i % 50 == 0) {
                LineaEvolutiva stat = equipo.poll();
                equipo.offer(stat);
                System.out.println("Build actual " + equipo.peek().getNombreActual());
            
            }
            if (i == enemigos) {
                break;
            }
        }

        PerformanceReporter.ReporteCajaNegra(batallas);
    }
}

