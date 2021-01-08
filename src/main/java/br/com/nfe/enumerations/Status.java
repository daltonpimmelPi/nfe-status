package br.com.nfe.enumerations;

import java.util.Map;

public enum Status {

    VERMELHO("imagens/bola_vermelha_P.png"),
    AMARELO("imagens/bola_amarela_P.png"),
    VERDE("imagens/bola_verde_P.png"),
    SEM_STATUS("");

    Status(String image){
        this.image = image;
    }

    private String image;

    public static Status fromString(String text) {
        for (Status status : Status.values()) {
            if (status.image.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return SEM_STATUS;
    }

    public void isUnavailability(Map<String, Integer> map, String state){
        if(this.image != null && this.image == VERMELHO.image || this.image == AMARELO.image){
            var isState = map.get(state);
            if(isState == null) {
                map.put(state, 1);
            }else{
                Integer qtd = map.get(state);
                map.put(state, (qtd + 1));
            }
        }
    }

}
