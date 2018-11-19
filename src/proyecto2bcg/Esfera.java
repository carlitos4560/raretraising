/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2bcg;

import java.awt.Color;

/**
 *
 * @author ariel
 */
public class Esfera extends Objeto{
    Punto centro;
    double radio;
    public Esfera(Punto centro,double radio,Color color,int id){
        this.centro=centro;
        this.radio=radio;
        this.color[0]=((color.getRed()+1)/255)-(1/255);
        this.color[1]=((color.getGreen()+1)/255)-(1/255);
        this.color[2]=((color.getBlue()+1)/255)-(1/255);
        this.id=id;
        
    }
    @Override
    public Vector obtenerNormal(Punto punto){
        return new Vector(punto.x-centro.x,punto.y-centro.y,punto.z-centro.z);
    }
    @Override
    public boolean existeInterseccion(Rayo rp){
        boolean res=false;
        Punto r0=rp.r0;
        Vector rd=rp.rd;
        double B=(2*rd.x*(r0.x-centro.x))+(2*rd.y*(r0.y-centro.y))+(2*rd.z*(r0.z-centro.z));
        double C=Math.pow(r0.x-centro.x,2)+Math.pow(r0.y-centro.y,2)+Math.pow(r0.z-centro.z,2)-(radio*radio);
        double t1,t2;
        t1=(-B+Math.sqrt(B*B-4*C))/2;
        t2=(-B-Math.sqrt(B*B-4*C))/2;
        if((B*B)-(4*C)>=0){//&&t1>0||t2>0){
            res=true;
            
        }
        return res;
    }
    double t;
    @Override
    public double getInterseccion(Rayo rayo){
        double res;
        Punto r0=rayo.r0;
        Vector rd=rayo.rd;
        double B=(2*rd.x*(r0.x-centro.x))+(2*rd.y*(r0.y-centro.y))+(2*rd.z*(r0.z-centro.z));
        double C=Math.pow(r0.x-centro.x,2)+Math.pow(r0.y-centro.y,2)+Math.pow(r0.z-centro.z,2)-(radio*radio);
        double t1,t2;
        t1=(-B+Math.sqrt(B*B-4*C))/2;
        t2=(-B-Math.sqrt(B*B-4*C))/2;
        if((B*B)-(4*C)>=0){
        if(t1>=0&&t1<t2)
            res=t1;
        else
            res=t2;
        }else res=-1;
        t=res;
        return res;
    }
    @Override
    public Punto obtenerPuntoInterseccion(Rayo rpixel,double t){
        //Punto res=new double[3];
        Vector direccion=rpixel.rd;
        return new Punto(t*direccion.x+rpixel.r0.x,t*direccion.y+rpixel.r0.y,t*direccion.z+rpixel.r0.z);
        /**res[0] = t*aux[0];
        res[1]=t*aux[1];
        res[2]=t*aux[2];
        
        return Vector.sumar(rpixel.r0,res);*/
    }
    
}
