package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public  class Outil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String map(Map<String, String[]> map) {
        StringBuilder str = new StringBuilder();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = (entry.getValue())[0];
            str.append(key);
            str.append("=");
            str.append(value);
            str.append("&");
        }
        return str.toString();
    }
    public static Produit readProduit(JsonNode node) throws JsonProcessingException {
        Produit produit = mapper.treeToValue(node, Produit.class);
        return produit;
    }

    public static Boutique readBoutique(JsonNode node) throws JsonProcessingException {
        Boutique boutique = mapper.treeToValue(node, Boutique.class);
        return boutique;
    }

    public static Franchise readFranchise(JsonNode node) throws JsonProcessingException {
        Franchise franchise = mapper.treeToValue(node, Franchise.class);
        return franchise;
    }

    public static Distributeur readDistributeur(JsonNode node) throws JsonProcessingException {
        Distributeur distributeur = mapper.treeToValue(node, Distributeur.class);
        return distributeur;
    }

    public static Pdv readPdv(JsonNode node) throws JsonProcessingException {
        Pdv pdv = mapper.treeToValue(node, Pdv.class);
        return pdv;
    }
}