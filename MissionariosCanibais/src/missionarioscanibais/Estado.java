package missionarioscanibais;

import java.util.ArrayList;

public class Estado {
    private int missionarios;
    private int canibais;
    private String margem;
    
    //  Variáveis para obtenção do Caminho Solução:
    
    private Estado pai;
    private Movimento mov;
    
    //  Variáveis para ajuste parte Gráfica:
    
    private ArrayList<Estado> filhos;
    
    private int profundidade;
    private double posX;
    
    private boolean fechado;
    private boolean atual;
    private boolean objetivo;
    
    /*  Construtor para Estado Inicial
     *  -E Lista Estados Válidos
     */
    
    public Estado(int missionarios, int canibais, String margem) {
        this.missionarios = missionarios;
        this.canibais = canibais;
        this.margem = margem;
        
        pai = null;
        mov = null;
        
        filhos = new ArrayList();
        
        fechado = false;
        atual = false;
        objetivo = false;
    }
    
    //  Construtor para Estados com Pai e Movimento

    public Estado(Estado pai, Movimento mov, int missionarios, int canibais, String margem) {
        this.pai = pai;
        this.mov = mov;
        this.missionarios = missionarios;
        this.canibais = canibais;
        this.margem = margem;
        
        filhos = new ArrayList();
        
        fechado = false;
        atual = false;
        objetivo = false;
    }
    
    @Override
    public String toString(){                                                   //Retorna Notação do Estado
        return "<"+missionarios+" ,"+canibais+" ,"+margem+">";
    }
    
    //  Getter e Setters da Classe:
    
    public Estado getPai() {
        return pai;
    }

    public void setPai(Estado pai) {
        this.pai = pai;
    }

    public Movimento getMov() {
        return mov;
    }

    public void setMov(Movimento op) {
        this.mov = op;
    }

    public ArrayList<Estado> getFilhos() {
        return filhos;
    }

    public void setFilhos(ArrayList<Estado> filhos) {
        this.filhos = filhos;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public void setProfundidade(int profundidade) {
        this.profundidade = profundidade;
    }

    public int getMissionarios() {
        return missionarios;
    }

    public void setMissionarios(int missionarios) {
        this.missionarios = missionarios;
    }

    public int getCanibais() {
        return canibais;
    }

    public void setCanibais(int canibais) {
        this.canibais = canibais;
    }

    public String getMargem() {
        return margem;
    }

    public void setMargem(String margem) {
        this.margem = margem;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }   

    public boolean isAtual() {
        return atual;
    }

    public void setAtual(boolean atual) {
        this.atual = atual;
    }

    public boolean isObjetivo() {
        return objetivo;
    }

    public void setObjetivo(boolean objetivo) {
        this.objetivo = objetivo;
    }
    
    
}
