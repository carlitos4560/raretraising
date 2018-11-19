/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2bcg;

/**
 *
 * @author ariel
 */
public abstract class Objeto {
    double[] color=new double[]{1,1,1};
    double ks=0.5,kd=0.4;
    int n=12;
    int id;
    public abstract boolean existeInterseccion(Rayo rp);
    public abstract double getInterseccion(Rayo rayo);
    public abstract Punto obtenerPuntoInterseccion(Rayo rpixel,double t);
    public abstract Vector obtenerNormal(Punto punto);
}
