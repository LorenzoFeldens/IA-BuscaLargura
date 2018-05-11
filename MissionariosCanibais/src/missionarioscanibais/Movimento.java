package missionarioscanibais;

public class Movimento {
    private int missionarios;
    private int canibais;
    
    //  Construtor Classe
    
    public Movimento(int missionarios, int canibais) {
        this.missionarios = missionarios;
        this.canibais = canibais;
    }
    
    @Override
    public String toString(){
        return "<"+missionarios+","+canibais+">";
    }
    
    //  Getter e Setters:

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
    
    
}
