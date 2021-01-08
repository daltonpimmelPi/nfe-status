package br.com.nfe.util;

import java.util.HashMap;
import java.util.Map;

public class Columns {

    public static Map<Integer, String> create(){

        Map<Integer,String> map = new HashMap<Integer,String>();
        map.put(1, "autorizacao");
        map.put(2, "retornoAutorizacao");
        map.put(3, "inutilizacao");
        map.put(4, "consultaProtocolo");
        map.put(5, "servico");
        map.put(7, "consultaCadastro");
        map.put(8, "recepcaoEvento");

        return map;

    }

}
