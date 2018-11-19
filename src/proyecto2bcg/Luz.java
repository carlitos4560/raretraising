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
public class Luz {
    Punto posicion;
    double[] color=new double[]{1,1,1};
    public Luz(Punto posicion,Color color){
        this.posicion=new Punto(posicion.x,posicion.y,posicion.z);
        this.color[0]=((color.getRed()+1)/255)-(1/255);
        this.color[1]=((color.getGreen()+1)/255)-(1/255);
        this.color[2]=((color.getBlue()+1)/255)-(1/255);
    }
    public Luz(Punto posicion){
        this.posicion=new Punto(posicion.x,posicion.y,posicion.z);
    }
    
    
    public double[] obtenerComponenteDifuso(Objeto oc,Vector normal,Vector vectorLi){
        double[] resRGB=new double[3];
        //resRGB[0]=color[0]*oc.kd*oc.color[0]*Vector.productoPunto(normal, vectorLi);
        //resRGB[1]=color[1]*oc.kd*oc.color[1]*Vector.productoPunto(normal, vectorLi);
        //resRGB[2]=color[2]*oc.kd*oc.color[2]*Vector.productoPunto(normal, vectorLi);
        resRGB[0]=color[0]*oc.color[0]*Vector.productoPunto(normal, vectorLi);
        resRGB[1]=color[1]*oc.color[1]*Vector.productoPunto(normal, vectorLi);
        resRGB[2]=color[2]*oc.color[2]*Vector.productoPunto(normal, vectorLi);
        return resRGB;
    }
    public double[] obtenerComponenteEspecular(Objeto oc,Vector normal,Vector vectorLi,Rayo rp){
        Vector vectorLi1=Vector.productoEscalar(vectorLi, -1);
        double aux=2*Vector.productoPunto(normal, vectorLi1);
        double[] auxV=new double[]{aux*normal.x,aux*normal.y,aux*normal.z};
        //auxV[0]=vectorLi.x-auxV[0];
        //auxV[1]=vectorLi.y-auxV[1];
        //auxV[2]=vectorLi.z-auxV[2];
        
        //double[] R=Principal.normalizar(auxV);
        
        Vector R=new Vector(vectorLi1.x-auxV[0],vectorLi1.y-auxV[1],vectorLi1.z-auxV[2]);
        //double[] V=new double[3];
        //V[0]=rp.rd[0]*-1;
        //V[1]=rp.rd[1]*-1;
        //V[2]=rp.rd[2]*-1;
        Vector V=Vector.productoEscalar(rp.rd, -1);
        aux=Vector.productoPunto(R, V);
        aux=Math.pow(aux, oc.n);
        //aux=aux*oc.ks;
        
        //V[0]=color[0]*aux;
        //V[1]=color[1]*aux;
        //V[2]=color[2]*aux;
        
        return new double[]{color[0]*aux,color[1]*aux,color[2]*aux};
    }
}
