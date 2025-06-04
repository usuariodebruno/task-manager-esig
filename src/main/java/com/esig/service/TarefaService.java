package com.esig.service;

import com.esig.dao.TarefaDAO;
import com.esig.model.Tarefa;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class TarefaService {
    // Regra de negócio para gerenciar tarefas
    @Inject
    private TarefaDAO tarefaDAO;

    public void salvar(Tarefa tarefa) {
        tarefaDAO.salvar(tarefa);
    }

    public void remover(Tarefa tarefa) {
        tarefaDAO.remover(tarefa);
    }

    public Tarefa findPorId(Long id) {
        return tarefaDAO.findPorId(id);
    }

    public List<Tarefa> listarTodas() {
        return tarefaDAO.listarTodas();
    }

    public List<Tarefa> filtrarPorResponsavel(String responsavel) {
        return tarefaDAO.filtrarPorResponsavel(responsavel);
    }
}
