package dam.JosantVarona.Model.Enum;

public enum Dia {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado"),
    DOMINGO("Domingo"),
    NINGUNO("Ninguno");
    private final String Dia;
    Dia (String dia){
        this.Dia = dia;
    }
    public String getDia(){
        return Dia;
    }
}
