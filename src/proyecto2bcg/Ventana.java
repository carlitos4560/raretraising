/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2bcg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

/**
 *
 * @author ariel
 */
public class Ventana extends JFrame implements MouseListener,KeyListener{
    Escena escena;
    int ids=0;
    int ancho=800;
    int alto=600;
    public Ventana(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(100, 0, ancho, alto);
        setTitle("RAY-TRACING");
        
        addMouseListener(this);
        addKeyListener(this);
        
        escena=new Escena();
        init();
    }
    private void init(){
        escena.addObjeto(new Esfera(new Punto(0,0,300),100,Color.RED,ids++));
        escena.addObjeto(new Esfera(new Punto(0,120,300),25,Color.YELLOW,ids++));
        //cabeza
        escena.addObjeto(new Esfera(new Punto(0,163,299-3),48,new Color(0,0,0),ids++));
        escena.addObjeto(new Esfera(new Punto(0,160,300-3),50,Color.YELLOW,ids++));
        
        
        escena.addObjeto(new Esfera(new Punto(20-2,155,260-3),8,Color.BLACK,ids++));
        escena.addObjeto(new Esfera(new Punto(-20+2,155,260-3),8,Color.BLACK,ids++));
        escena.addObjeto(new Esfera(new Punto(0,140,260-3),10,Color.RED,ids++));
        //pata
        escena.addObjeto(new Esfera(new Punto(0,-5,300),98,Color.BLUE,ids++));
        escena.addObjeto(new Esfera(new Punto(50,-100,300),25,Color.BLUE,ids++));
        escena.addObjeto(new Esfera(new Punto(-50,-100,300),25,Color.BLUE,ids++));
        escena.addObjeto(new Esfera(new Punto(52,-130,297),25,Color.yellow,ids++));
        escena.addObjeto(new Esfera(new Punto(-52,-130,297),25,Color.yellow,ids++));
        escena.addObjeto(new Esfera(new Punto(52,-160,300),25,Color.yellow,ids++));
        escena.addObjeto(new Esfera(new Punto(-52,-160,300),25,Color.yellow,ids++));
        escena.addObjeto(new Esfera(new Punto(50,-210+10,300),27,Color.WHITE,ids++));
        escena.addObjeto(new Esfera(new Punto(-50,-210+10,300),27,Color.WHITE,ids++));
        
        //hand
        escena.addObjeto(new Esfera(new Punto(80,70,280),25,Color.RED,ids++));
        escena.addObjeto(new Esfera(new Punto(110,40,280),25,Color.RED,ids++));
        escena.addObjeto(new Esfera(new Punto(120,10,270),25,Color.YELLOW,ids++));
        escena.addObjeto(new Esfera(new Punto(110,-15,260),27,Color.YELLOW,ids++));
        
        escena.addObjeto(new Esfera(new Punto(-80,70,280),25,Color.RED,ids++));
        escena.addObjeto(new Esfera(new Punto(-110,40,280),25,Color.RED,ids++));
        escena.addObjeto(new Esfera(new Punto(-120,10,270),25,Color.YELLOW,ids++));
        escena.addObjeto(new Esfera(new Punto(-110,-15,260),27,Color.YELLOW,ids++));
        
        
        //earth
        escena.addObjeto(new Esfera(new Punto(0,-720,300),500,Color.green,ids++));
        //escena.addObjeto(new Esfera(new Punto(100,0,100),25,Color.YELLOW,ids++));
        
        //escena.addObjeto(new Esfera(new Punto(-100,200,250),150,Color.YELLOW,ids++));
        
        
        //escena.addLuz(new Luz(new Punto(-200,500,50),Color.WHITE));
        //luces.add(new Luz(700,0,100),Color.WHITE));
        //escena.addLuz(new Luz(new Punto(400,400,10),Color.WHITE));
        escena.addLuz(new Luz(new Punto(-50,600,250),Color.WHITE));
        escena.addLuz(new Luz(new Punto(-50,-500,-550),Color.WHITE));
        //luces.add(new Luz(-100,200,100,Color.BLUE));
        
        //luces.add(new Luz(700,-300,800,Color.WHITE));
        objetoCercano=escena.objetos.get(0);
        luzCercano = escena.luces.get(0);
        
    }
    @Override
    public void paint(Graphics g){
        //update(g);
        escena.dibujar(g, ancho, alto);
    }
    @Override
    public void update(Graphics g){
        Image img=this.createImage(ancho,alto);
        Graphics buffer=img.getGraphics();
        
        escena.dibujar(buffer,ancho,alto);
        
        g.drawImage(img, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    
    }
    Objeto objetoCercano=null;
    Luz luzCercano=null;
    @Override
    public void mousePressed(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        if(e.getButton()==MouseEvent.BUTTON1){
            luzActivo=false;
            Rayo RPixel=new Rayo(escena.observer,new Vector((x-ancho/2)-escena.observer.x,(alto/2-y)-escena.observer.y,0-escena.observer.z));
            double tmin=Double.MAX_VALUE;
            //Objeto objetoCercano=null;
            for(Objeto o:escena.objetos){
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
        }else if(e.getButton()==MouseEvent.BUTTON3){
            luzCercano = escena.luces.get(0);
            luzActivo=true;
        }
    }
    boolean luzActivo=false;
    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(!luzActivo){
            Esfera es=(Esfera)objetoCercano;
            //System.out.println(e.getKeyCode());

            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                es.centro.y=es.centro.y-10;
            }else if(e.getKeyCode()==KeyEvent.VK_UP){
                es.centro.y=es.centro.y+10;
            }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                es.centro.x=es.centro.x+10;
            }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
                es.centro.x=es.centro.x-10;
            }else if(e.getKeyCode()==KeyEvent.VK_E){
                es.centro.z=es.centro.z+10;
            }else if(e.getKeyCode()==KeyEvent.VK_S){
                es.centro.z=es.centro.z-10;
            }else if(e.getKeyCode()==KeyEvent.VK_ADD){
                es.radio=es.radio+10;
            }else if(e.getKeyCode()==KeyEvent.VK_SUBTRACT){
                es.radio=es.radio-10;
            }else if(e.getKeyCode()==KeyEvent.VK_4){
                ancho=ancho+ancho/4;
                alto=alto+alto/4;
                this.setBounds(100, 0, ancho, alto);
            }else if(e.getKeyCode()==KeyEvent.VK_3){
                ancho=ancho-ancho/4;
                alto=alto-alto/4;
                this.setBounds(100, 0, ancho, alto);
            }
        }else{
            int keyCode=e.getKeyCode();

            Luz lc=luzCercano;

            if(keyCode == KeyEvent.VK_LEFT){
                lc.posicion.x=lc.posicion.x-100;
            }else if(keyCode == KeyEvent.VK_RIGHT){
                lc.posicion.x=lc.posicion.x+100;
            }else if(keyCode == KeyEvent.VK_UP){
                lc.posicion.y=lc.posicion.y+100;
            }else if(keyCode == KeyEvent.VK_DOWN){
                lc.posicion.y=lc.posicion.y-100;
            }else if(keyCode == KeyEvent.VK_E){
                lc.posicion.z=lc.posicion.z+100;
            }else if(keyCode == KeyEvent.VK_S){
                lc.posicion.z=lc.posicion.z-100;
            }else if(keyCode <= KeyEvent.VK_9 && keyCode >= KeyEvent.VK_0){
                int num=keyCode-KeyEvent.VK_0;
                if(num>=escena.luces.size()){
                    luzCercano = escena.luces.get(escena.luces.size()-1);
                }else{
                    luzCercano = escena.luces.get(num);
                }
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
