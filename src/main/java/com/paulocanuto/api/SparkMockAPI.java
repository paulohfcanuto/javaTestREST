package com.paulocanuto.api;

import com.paulocanuto.api.model.Pessoa;
import com.google.gson.Gson;
import java.util.ArrayList;
import spark.Request;
import static spark.Spark.*;


public class SparkMockAPI {

    private static ArrayList<Pessoa> pessoas = null;
    private static Gson gson = null;

    public static void main(String[] args) {
        gson = new Gson();

        pessoas = initData();

        post("/api/pessoa", "application/json", (req, res) -> {
            Pessoa pessoa = gson.fromJson(req.body(), Pessoa.class);

            pessoa.setId(getNextID());
            pessoas.add(pessoa);

            res.type("application/json");
            return gson.toJson(pessoa);
        });

        get("/api/pessoa/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(findPessoaById(id));
        });

        get("/api/pessoa", (req, res) -> pessoas, gson::toJson);

        put("/api/pessoa/:id", (req, res) -> {
            System.out.println("Body: " + req.body());
            
            res.type("application/json");
            return gson.toJson(updatePessoa(req));
        });

        delete("/api/pessoa/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));

            pessoas.remove(findPessoaById(id));
            
            res.type("application/json");
            return gson.toJson(new Status("success"));
        });
    }

    private static ArrayList<Pessoa> initData() {
        pessoas = new ArrayList<>();

        pessoas.add(new Pessoa(1, "Paulo Henrique", "SC", "Analista de Testes"));
        pessoas.add(new Pessoa(2, "Rômulo Pereira", "RS", "Auxiliar de betoneira"));
        pessoas.add(new Pessoa(3, "Jequi", "RS", "Geólogo"));

        return pessoas;
    }

    private static int getNextID() {
        return pessoas.get(pessoas.size() - 1).getId() + 1;
    }

    private static Pessoa findPessoaById(int id) {
        Pessoa pessoaReturn = null;

        for (Pessoa pessoa : pessoas) {
            if (id == pessoa.getId()) {
                pessoaReturn = pessoa;
            }
        }
        return pessoaReturn;
    }

    private static Pessoa updatePessoa(Request req) {
        int id = Integer.parseInt(req.params("id"));
        Pessoa pessoaRequested = gson.fromJson(req.body(), Pessoa.class);
        
        System.out.println("Corpo do update" + req.body());

        Pessoa pessoaFound = findPessoaById(id);
        int index = pessoas.lastIndexOf(pessoaFound);

        pessoaFound = pessoaRequested;
        pessoaFound.setId(id);

        pessoas.set(index, pessoaRequested);

        System.out.println(pessoaRequested);
        return pessoaRequested;
    }
    
    static class Status {
        private String status;
        
        public Status(String status) {
            this.status = status;
        }
    }
}
