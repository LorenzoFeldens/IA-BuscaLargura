package missionarioscanibais;

import GUI.PainelArvore;
import GUI.PainelEstados;
import java.util.ArrayList;

public class Algoritmo {
    
    //  Nomenclatura Margens:
    
    private final String ESQUERDA = "MargemEsq";
    private final String DIREITA = "MargemDir";
    
    //  Parâmetros Problema:
    
    private final int missionarios;
    private final int canibais;
    private final int barco;
    
    //  Estados e Movimentos:
    
    private Estado inicial;
    private Estado atual;
    private ArrayList<Movimento> movimentos;
    private ArrayList<String> estados;
    private ArrayList<Estado> abertos;
    
    private ArrayList<Estado> caminho;
    
    //  Frames:
    
    private PainelEstados painelEstados;
    private PainelArvore painelArvore;

    /*  Construtor Classe:
     *  - Recebe parâmetros da GUI
     */
    
    public Algoritmo(int missionarios, int canibais, int barco) {
        this.missionarios = missionarios;
        this.canibais = canibais;                                               
        this.barco = barco;
    }
    
    /*  Função Executa:
     *  - Cria estado inicial
     *  - Inicializa Lista Abertos
     *  - Gera Movimentos e Estados Possíveis
     *  - Executa Passo até obter o objetivo ou rejeição do problema
     */
    
    public boolean executa() {
        geraMovimentos();
        geraEstados();
        
        abertos = new ArrayList();
        
        reset();
        
        while(!objetivo())
            if(!passo())
                return false;
        
        return true;
    }
    
    /*  Função GeraMovimentos
     *  -Gera todos os Movimentos Possíveis para o Problema
     */
    
    private void geraMovimentos(){
        movimentos = new ArrayList();
        
        for(int i=0; i<=missionarios; i++)
            for(int j=0; j<=canibais; j++)
                if(((i==0 && j!=0) || (i!=0 && i>=j)) && i+j<=barco)
                    movimentos.add(new Movimento(i,j));
    }
    
    /*  Função GeraEstados
     *  -Gera todos os Estados Possíveis para o Problema
     */
    
    private void geraEstados(){
        estados = new ArrayList();
        
        for(int i=0; i<=missionarios; i++)
            for(int j=0; j<=canibais; j++){
                int mis_o = missionarios-i;
                int can_o = canibais-j;
                if((i==0 && j>0) || (i>=j && mis_o>=can_o) || (mis_o==0 && can_o>0)){
                    estados.add(new Estado(i,j,ESQUERDA).toString());
                    estados.add(new Estado(i,j,DIREITA).toString());                       
                }
            }
    }
    
    /*  Função Reset:
     *  -Retoma estado inicial do problema
     *  -Limpa lista Abertos
     */
    
    public void reset(){
        inicial = new Estado(missionarios, canibais, ESQUERDA);
        
        abertos.removeAll(abertos);
        
        atual = inicial;
        atual.setAtual(true);
        
        if(painelArvore != null && painelArvore.isVisible()){
            painelArvore.atualiza(inicial);
        }
    }
    
    /*  Função Objetivo:
     *  -Verifica se Estado Atual é o Estado Objetivo
     */
    
    private boolean objetivo(){
        int mis = atual.getMissionarios();
        int can = atual.getCanibais();
        String mar = atual.getMargem();
        
        System.out.println("Estado Atual: "+atual.toString());
        
        if(mis == missionarios && can == canibais && mar.equalsIgnoreCase(DIREITA)){
            imprimeObj();
            atual.setObjetivo(true);
            return true;
        }
        return false;
    }
    
    /*  Função ImprimeObjetivo
     *  -Imprime Caminho Estado Inicial até Estado Obejtivo
     */
    
    private void imprimeObj(){
        String ret = "";
        caminho = new ArrayList();
        
        Estado est = atual;
        while(est != null){
            Movimento op = est.getMov();
            if(op != null)
                ret="\n    "+op.toString()+ret;
            
            caminho.add(est);
            est = est.getPai();
        }
        System.out.println("\nCaminho até Objetivo:"+ret);
    }
    
    /*  Função Passo:
     *  -Abre novos Estados
     *  -Define Estado Atual como Fechado
     *  -Escolhe novo Estado Atual
     *      --Caso não existam opções retorna falha
     */
    
    private boolean passo(){
        abreEstados();
        
        atual.setFechado(true);
        atual.setAtual(false);
        
        if(abertos.size()>0){
            atual = abertos.get(0);
            atual.setAtual(true);
            abertos.remove(0);
            return true;
        }
        return false;
    }
    
    /*  Função AbreEstados:
     *  -Para cada movimento Possível
     *      --Verifica se aplicado ao Estado Atual, gera um Estado Possível
     *      --E se este Estado já não foi acessado no Caminho
     *      --Insere Estados Possíveis na Lista de Abertos
     */
    
    private void abreEstados(){
        ArrayList<Estado> novos = new ArrayList();
        
        for(int i=0; i<movimentos.size(); i++){
            Estado novo = verificaPossivel(movimentos.get(i));
            if(novo != null && verificaNovo(novo)){
                atual.getFilhos().add(novo);
                novos.add(novo);
            }
        }
        
        imprimeGerados(novos);
        abertos.addAll(novos);
    }
    
    /*  Função VerificaPossivel
     *  -Dado o Estado Atual e um Movimento
     *      --Retorna um novo Estado se este for Possível
     */
    
    private Estado verificaPossivel(Movimento m){
        int mis_m = m.getMissionarios();
        int can_m = m.getCanibais();
        
        int mis = atual.getMissionarios();
        int can = atual.getCanibais();
        
        int mis_o = missionarios-mis;
        int can_o = canibais-can;
        
        int mis_new = mis_o+mis_m;
        int can_new = can_o+can_m;
        
        String mar_new = ESQUERDA;
        if(atual.getMargem().equalsIgnoreCase(ESQUERDA))
            mar_new = DIREITA;
        
        Estado novo = new Estado(atual, m, mis_new, can_new, mar_new);
        
        if(estados.contains(novo.toString()))
            return novo;
        
        
        return null;
    }
    
    /*  Função VerificaNovo
     *  -Verifica se Estado já foi percorrido neste caminho
     *  -(Estado Inicial até Novo Estado)
     */
    
    private boolean verificaNovo(Estado novo){
        ArrayList<String> cam = new ArrayList();
        Estado e = novo;
        
        while(e.getPai()!=null){
            e=e.getPai();
            cam.add(e.toString());
        }
        
        return !cam.contains(novo.toString());
    }
    
    /*  Função ImprimeGerados
     *  -Imprime Estados Abertos pelo Estado Atual
     */
    
    private void imprimeGerados(ArrayList<Estado> novos){
        String print = "Novos estados gerados: [";
        for(int i=0; i<novos.size(); i++){
            Estado e = novos.get(i);
            print+="("+e.toString()+","+e.getMov().toString()+")";
        }
        print+="]";
        System.out.println(print);
    }
    
    //Funções Botões GUI:
    
    public void mostrarMovimentos(){
        ArrayList<String> ret = new ArrayList();
        
        for(int i=0; i<movimentos.size(); i++){
            ret.add(movimentos.get(i).toString());
        }
        if(painelEstados != null){
            painelEstados.dispose();
        }
        painelEstados = new PainelEstados();
        painelEstados.setVisible(true);
        painelEstados.setText("Operadores Válidos",ret);
    }
    
    public void mostrarEstados(){
        ArrayList<String> ret = new ArrayList();
                
        for(int i=0; i<estados.size(); i++){
            ret.add(estados.get(i));
        }
        if(painelEstados != null){
            painelEstados.dispose();
        }
        painelEstados = new PainelEstados();
        painelEstados.setVisible(true);
        painelEstados.setText("Estados Válidos",ret);
    }
    
    public void mostrarArvore(){
        if(painelArvore != null){
            painelArvore.dispose();
        }
        reset();
        painelArvore = new PainelArvore(inicial, this);
        painelArvore.setVisible(true);
    }
    
    public boolean passos(){
        if(!passo()){
            painelArvore.atualiza(inicial);
            return true;
        }
        painelArvore.atualiza(inicial);
        return(objetivo());
    }
    
    public void mostrarCaminho(){
        if(painelArvore != null){
            painelArvore.dispose();
        }
        painelArvore = new PainelArvore(caminho);
        painelArvore.setVisible(true);
    }
    
}
