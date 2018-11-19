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
public class Vector {
    double x,y,z;
    public Vector(double x, double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
        normalizar();
    }
    private void normalizar(){
        double abs=Math.sqrt(x*x+y*y+z*z);
        x=x/abs;
        y=y/abs;
        z=z/abs;
    }
    
    public static double productoPunto(Vector v1,Vector v2){
        return v1.x*v2.x+v1.y*v2.y+v1.z*v2.z;
    }
    /**
    public static Vector sumar(Vector v1,Vector v2){
        return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
    }*/

    /**
     * public static Vector sumar(Vector v1,Vector v2){
        return new Vector(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z);
    }*/
    
    public static Vector productoEscalar(Vector v,double k){
        Vector res=new Vector(v.x,v.y,v.z);
        res.x=v.x*k;
        res.y=v.y*k;
        res.z=v.z*k;
        return res;
    }
    
}
