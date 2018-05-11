package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import missionarioscanibais.Estado;
 
public class Painel extends JPanel{
    
    //  Dados:
    
    private Estado estado;
    private ArrayList<Estado> caminho;
    private final int tipo;
    
    //  Posições:
    
    private int maxX;
    private int maxY;
    private int profAtual;
    private int xAtual;
    private int yAtual;
    
    private ArrayList<ArrayList<Estado>> niveis;
    
    public Painel(Estado estado){
        this.estado = estado;
        setPositions();
        tipo = 1;
        
        maxX=0;
        maxY=0;
        profAtual=0;
        xAtual=30;
        yAtual=30;
    }
    
    public Painel(ArrayList<Estado> caminho){
        this.caminho = caminho;
        tipo = 2;
        
        maxX=0;
        maxY=0;
        profAtual=0;
        xAtual=30;
        yAtual=30;
    }
    
    /*  Função SetPositions:
     *  -Define posições para cada Estado Árvore
     *      --Profundidade do Estado
     *      --Posição Horizontal
     */
    
    private void setPositions(){
        niveis = new ArrayList();
        
        setProfundidade(estado);
        for(int i=niveis.size()-1; i>=0; i--){
            ArrayList<Estado> nivel = niveis.get(i);
            setLargura(nivel);
        }
    }
    
    /*  Função SetProfundidade:
     *  -Calcula Distância do Estado Inicial
     */
    
    private void setProfundidade(Estado e){
        Estado pai = e.getPai();
        int prof = 0;
        
        if(pai != null)
            prof = pai.getProfundidade()+1;
        
        if(niveis.size()!=prof+1)
            niveis.add(new ArrayList());
        
        niveis.get(prof).add(e);
        e.setProfundidade(prof);
        
        ArrayList<Estado> filhos = e.getFilhos();
        for(int i=0; i<filhos.size(); i++)
            setProfundidade(filhos.get(i));
    }
    
    /*  Função SetLargura:
     *  -Calcula Posição Horizontal dentro da Profundidade Atual
     */
    
    private void setLargura(ArrayList<Estado> nivel){
        for(int i=0; i<nivel.size(); i++){
            Estado e = nivel.get(i);                                            
            ArrayList<Estado> filhos = e.getFilhos();                           
            
            double x = 0;
            if(filhos.size() > 0){
                Estado pf = filhos.get(0);
                Estado uf = filhos.get(filhos.size()-1);
                
                x = ((pf.getPosX()+uf.getPosX())/2);
            }
            
            if(i==0)                                                       
                e.setPosX(x);
            else{
                Estado ant = nivel.get(i-1);
                e.setPosX(ant.getPosX()+x+1);
            }
        }
    }
    
    //Função que chama o desenho
    
    public void desenhar(){
        paint(getGraphics());                                                   
    }
    
    /*  Função Paint
     *  -Desenha Árvore + Legenda caso Frame seja do Tipo 1
     *  -Desenha Caminho caso Frame seja do Tipo 2
     */
   
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        if(tipo == 1){
            drawArvore(estado, g2d);
            drawLegenda(g2d);
        }else
            drawCaminho(g2d);
    }
    
    //  Função que Desenha Árvore
    
    private void drawArvore(Estado e, Graphics2D g2d){
        if(e == null){
            return;                                                             
        }
        g2d.setStroke(new BasicStroke(1f));
        g2d.setColor(Color.black);
            
        g2d.setFont(new Font("default",Font.PLAIN, 12));
        int x = 30+(int) (150*e.getPosX());
        int y = 30+50*e.getProfundidade();
        
        if(x>maxX){
            maxX = x;
        }
        if(y>maxY){
            maxY = y;
        }
        
        g2d.setColor(Color.orange);
        if(e.isFechado()){
            g2d.setColor(Color.red);                                            
        }
        if(e.isAtual()){
            g2d.setColor(Color.YELLOW);
                
            if(e.getProfundidade()>=profAtual){
                profAtual = e.getProfundidade();
                xAtual=x-300;
                if(xAtual<0){
                    xAtual=0;
                }
                yAtual=y-300;
                if(yAtual<0){
                    yAtual=0;
                }
            }
        }
        if(e.isObjetivo()){
            g2d.setColor(Color.GREEN);
        }
        
        g2d.fill3DRect(x-5, y-20, 115, 30, true);
        g2d.setColor(Color.black);
        g2d.draw3DRect(x-5, y-20, 115, 30, true);
        g2d.drawString(e.toString(),x,y);
        
        ArrayList<Estado> filhos = e.getFilhos();
        for(int i=0; i<filhos.size(); i++){
            int xx = 30+(int)(150*filhos.get(i).getPosX());
            g2d.setColor(Color.GRAY);
            int yy = 30+50*filhos.get(i).getProfundidade();
            g2d.drawLine(x+50,y+10,xx+50,yy-20);
            drawArvore(filhos.get(i), g2d);
            
            int mx = (x+xx+100)/2;
            int my = (y+yy-10)/2;
            my+=5;
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("default",Font.BOLD, 12));
            g2d.drawString(filhos.get(i).getMov().toString(),mx,my);
        }
    }
    
    //  Função que Desenha Legenda
    
    private void drawLegenda(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fill3DRect(20, maxY+30, 30, 30, true);
        g2d.setColor(Color.orange);
        g2d.fill3DRect(170, maxY+30, 30, 30, true);
        g2d.setColor(Color.YELLOW);
        g2d.fill3DRect(320, maxY+30, 30, 30, true);
        g2d.setColor(Color.GREEN);
        g2d.fill3DRect(470, maxY+30, 30, 30, true);
        g2d.setColor(Color.black);
        g2d.draw3DRect(20, maxY+30, 30, 30, true);
        g2d.draw3DRect(170, maxY+30, 30, 30, true);
        g2d.draw3DRect(320, maxY+30, 30, 30, true);
        g2d.draw3DRect(470, maxY+30, 30, 30, true);
        g2d.drawString("Visitados",60,maxY+50);
        g2d.drawString("Abertos",210,maxY+50);
        g2d.drawString("Atual",360,maxY+50);
        g2d.drawString("Objetivo",510,maxY+50);
    }
    
    //  Função que Desenha Caminho
    
    private void drawCaminho(Graphics2D g2d){
        int n = 0;
        for(int i=caminho.size()-1; i>=0; i--){
            g2d.setStroke(new BasicStroke(1f));
            g2d.setColor(Color.black);

            g2d.setFont(new Font("default",Font.PLAIN, 12));
            int x = 160;
            int y = 30+50*n;

            if(x>maxX){
                maxX = x;
            }
            if(y>maxY){
                maxY = y;
            }
           
            g2d.setColor(Color.YELLOW);

            g2d.fill3DRect(x-5, y-20, 115, 30, true);
            g2d.setColor(Color.black);
            g2d.draw3DRect(x-5, y-20, 115, 30, true);
            g2d.drawString(caminho.get(i).toString(),x,y);

            if(i>0){
                int xx = 160;
                int yy = 30+50*(n+1);
                g2d.drawLine(x+50,y+10,xx+50,yy-20);
                
                int mx = (x+xx+100)/2;
                int my = (y+yy-10)/2;
                my+=5;
                g2d.setFont(new Font("default",Font.BOLD, 12));
                g2d.drawString(caminho.get(i-1).getMov().toString(),mx,my);
            }
            n++;
        }
    }
    
    
    
    /*Getters & Setters*/

    public int getMaxX() {
        return maxX+150;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY+70;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getxAtual() {
        return xAtual;
    }

    public void setxAtual(int xAtual) {
        this.xAtual = xAtual;
    }

    public int getyAtual() {
        return yAtual;
    }

    public void setyAtual(int yAtual) {
        this.yAtual = yAtual;
    }
    
    
}
