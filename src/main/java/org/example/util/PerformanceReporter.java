package org.example.util;

import java.util.Locale;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.ReporteBatalla;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import oshi.SystemInfo;

public class PerformanceReporter {

    private static final Logger log = LogManager.getLogger("performance");
    private static final Logger tiemposLog = LogManager.getLogger("tiempos");
    private static final Logger cajaNegraLog = LogManager.getLogger("cajaNegra");
    private static final SystemInfo si = new SystemInfo();
    private static final long BYTES_PER_MB = 1024L * 1024L;


    public static void medirPesoObjeto(Object objeto, String nombreObjeto) {
        long pesoBytes = ClassLayout.parseInstance(objeto).instanceSize();
        long pesoTotal = GraphLayout.parseInstance(objeto).totalSize();

        log.info("Objeto '{}' | tamaño superficial: {} bytes | con referencias: {} bytes",
                nombreObjeto, pesoBytes, pesoTotal);
    }

    public static void reportarMemoriaSistema() {
        Runtime runtime = Runtime.getRuntime();

        long totalHeap = runtime.totalMemory();
        long usedHeap = totalHeap - runtime.freeMemory();
        long maxHeap = runtime.maxMemory();
        double porcentajeUso = totalHeap == 0 ? 0 : (100.0 * usedHeap) / totalHeap;

        long totalFisica = si.getHardware().getMemory().getTotal();
        long disponibleFisica = si.getHardware().getMemory().getAvailable();

        log.info("Memoria JVM | usada: {} | asignada: {} | máxima: {} | uso: {}%",
                formatearMegabytes(usedHeap), formatearMegabytes(totalHeap),
                formatearMegabytes(maxHeap), formatearDecimal(porcentajeUso));

        log.info("Memoria física | usada: {} | total: {} | disponible: {}",
                formatearMegabytes(totalFisica - disponibleFisica),
                formatearMegabytes(totalFisica), formatearMegabytes(disponibleFisica));
    }

    public static void reportarTiempoEjecucion(String etiqueta, long nanos) {
        double ms = nanos / 1_000_000.0;
        tiemposLog.info("{} | tiempo: {} ms", etiqueta, formatearDecimal(ms));
    }
    public static void ReporteCajaNegra(Stack<ReporteBatalla> batallas) {
        cajaNegraLog.info("Estado final de la Caja Negra ({} registros):", batallas.size());

        for (int posicion = 0; posicion < batallas.size(); posicion++) {
            ReporteBatalla reporte = batallas.get(posicion);
            cajaNegraLog.info("Registro {} | Pokemon: {} | Enemigo: {} | Derrotados: {}",
                    posicion + 1,
                    reporte.getNombre(),
                    reporte.getEnemigo(),
                    reporte.getDerrotados());
        }
    }

    private static String formatearMegabytes(long bytes) {
        return formatearDecimal((double) bytes / BYTES_PER_MB) + " MB";
    }

    private static String formatearDecimal(double valor) {
        return String.format(Locale.ROOT, "%.2f", valor);
    }
}
