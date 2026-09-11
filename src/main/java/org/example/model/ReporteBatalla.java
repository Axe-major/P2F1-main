package org.example.model;


public class ReporteBatalla {
    private String nombre;
    private String enemigo;
    private int derrotados;

    public ReporteBatalla (String nombre, String enemigo){
        this.nombre = nombre;
        this.enemigo = enemigo;
        this.derrotados = 1;
    }

    public int getDerrotados() {
        return derrotados;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEnemigo() {
        return enemigo;
    }
    
 
    public void setDerrotados(int derrotados) {
        this.derrotados = derrotados;
    }
    
}