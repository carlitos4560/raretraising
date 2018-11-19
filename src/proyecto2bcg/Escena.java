/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2bcg;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class Escena {
    Punto observer;
    ArrayList<Objeto> objetos;
    ArrayList<Luz> luces;
    double ka;
    double[] intensidadAmbiental;
    public Escena(){
        objetos=new ArrayList();
        luces=new ArrayList();
        ka=0.1;
        intensidadAmbiental=new double[]{0.4,0.4,0.4};
        observer=new Punto(0,0,-380);
    }
    public void addObjeto(Objeto o){
        objetos.add(o);
    }
    public void addLuz(Luz l){
        luces.add(l);
    }
    
    
    public void dibujar(Graphics g,int ancho,int alto){
        for(int f=0;f<=alto;f++){
            //long n1=System.currentTimeMillis();
            for(int c=0;c<=ancho;c++){
                
                Rayo RPixel=new Rayo(observer,new Vector((c-ancho/2)-observer.x,(alto/2-f)-observer.y,0-observer.z));
                double[] co=rayTracing(RPixel);
                
                Color aux=g.getColor();
                g.setColor(new Color((int)(co[0]*255),(int)(co[1]*255),(int)(co[2]*255)));
                g.drawLine(c, f, c, f);
                g.setColor(aux);
                
            }
            //long n2=System.currentTimeMillis();
            //System.out.println(n2-n1);
        }
    }
    
    private double[] rayTracing(Rayo RPixel){
        double[] res=new double[]{0,0,0};
        double tmin=Double.MAX_VALUE;
        Objeto objetoCercano=null;
        
        for(Objeto o:objetos){
            //boolean existe=o.existeInterseccion(RPixel);
            double t=o.getInterseccion(RPixel);
            if(t>=0){
                //double t=((Esfera)o).t;
                
                //double t=o.getInterseccion(RPixel);
                
                if(t<tmin){
                    tmin=t;
                    objetoCercano=o;
                }
            }
            
        }
        if(objetoCercano!=null){
            res=aplicarPhong(objetoCercano,tmin,RPixel);
            //res=objetoCercano.color;
        }
        return res;
    }
    
    private double[] aplicarPhong(Objeto oc,double tmin,Rayo rpixel){
        double[] res;
        double[] compDifuso=new double[3],compEspecular=new double[3];
        for(Luz li:luces){
            Punto puntoI=oc.obtenerPuntoInterseccion(rpixel,tmin);
            Vector vectorLi=new Vector(li.posicion.x-puntoI.x,li.posicion.y-puntoI.y,li.posicion.z-puntoI.z);
            
            Vector normal=oc.obtenerNormal(puntoI);
            if(Vector.productoPunto(normal,vectorLi)>0){
                //Deteccion de sombra
                boolean haySombra=false;
                Rayo sombra=new Rayo(puntoI,new Vector(li.posicion.x-puntoI.x,li.posicion.y-puntoI.y,li.posicion.z-puntoI.z));
                for(Objeto o:objetos){
                    if(o.id!=oc.id){
                        if(o.existeInterseccion(sombra)){
                            if(o.getInterseccion(sombra)<=tmin&&o.getInterseccion(sombra)>=0){
                                haySombra=true;
                                break;
                            }   
                        }
                    }
                }
                if(!haySombra){
                    compDifuso=sumar(compDifuso, li.obtenerComponenteDifuso(oc,normal,vectorLi));
                    compEspecular=sumar(compEspecular, li.obtenerComponenteEspecular(oc,normal,vectorLi,rpixel));
                
                }
            }
        }
        compDifuso[0]=compDifuso[0]*oc.kd;
        compDifuso[1]=compDifuso[1]*oc.kd;
        compDifuso[2]=compDifuso[2]*oc.kd;
        
        compEspecular[0]=compEspecular[0]*oc.ks;
        compEspecular[1]=compEspecular[1]*oc.ks;
        compEspecular[2]=compEspecular[2]*oc.ks;
        double[] ia=new double[3];
        ia[0]=ka*intensidadAmbiental[0]*oc.color[0];
        ia[1]=ka*intensidadAmbiental[1]*oc.color[1];
        ia[2]=ka*intensidadAmbiental[2]*oc.color[2];
        res=sumar(sumar(compDifuso, compEspecular),ia);
        
        if(res[0]>=1)
            res[0]=1;
        if(res[1]>=1)
            res[1]=1;
        if(res[2]>=1)
            res[2]=1;
        
        if(res[0]<=0)
            res[0]=ia[0];
        if(res[1]<=0)
            res[1]=ia[1];
        if(res[2]<=0)
            res[2]=ia[2];
        return res;
        
    }
    private double[] sumar(double[] a1,double[] a2){
        return new double[]{a1[0]+a2[0],a1[1]+a2[1],a1[2]+a2[2]};
    }
}
