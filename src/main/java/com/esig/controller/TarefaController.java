package com.esig.controller;

import java.io.Serializable;
import java.util.List;

import com.esig.dao.TarefaDAO;
import com.esig.model.Tarefa;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
@Named("TarefaController")
@SessionScoped
public class TarefaController implements Serializable {
    // Gerencia o fluxo entre view e modelo
    private static final long serialVersionUID = 1L;

    @EJB
    private TarefaDAO tarefaDAO;

    private List<Tarefa> tarefas;
    private Tarefa tarefaSelecionada;
    private String filtroResponsavel;
    private String filtroNumero;
    private String filtroTitulo;
    private String filtroStatus;

    @PostConstruct
    public void init() {
        this.filtroStatus = "Em andamento";
        carregarTarefas();
        tarefaSelecionada = new Tarefa();
    }

    public void carregarTarefas() {
        tarefas = tarefaDAO.filtrar(filtroNumero, filtroTitulo, filtroResponsavel, filtroStatus);
    }

    public String salvar() {
        tarefaDAO.salvar(tarefaSelecionada);
        limparFormulario();
        carregarTarefas();
        return "lista.xhtml?faces-redirect=true";
    }

    public void editar(Tarefa t) {
        this.tarefaSelecionada = t;
    }

    public void remover(Tarefa t) {
        tarefaDAO.remover(t);
        carregarTarefas();
    }

    public void concluir(Tarefa t) {
        t.setStatus("Concluída");
        tarefaDAO.salvar(t);
        carregarTarefas();
    }

    private void limparFormulario() {
        tarefaSelecionada = new Tarefa();
    }

    public List<Tarefa> getTarefas() { return tarefas; }
    public void setTarefas(List<Tarefa> tarefas) { this.tarefas = tarefas; }

    public Tarefa getTarefaSelecionada() { return tarefaSelecionada; }
    public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
        this.tarefaSelecionada = tarefaSelecionada;
    }

    public String getFiltroResponsavel() { return filtroResponsavel; }
    public void setFiltroResponsavel(String filtroResponsavel) {
        this.filtroResponsavel = filtroResponsavel;
    }

    public String getFiltroNumero() {
        return filtroNumero;
    }

    public void setFiltroNumero(String filtroNumero) {
        this.filtroNumero = filtroNumero;
    }

    public String getFiltroTitulo() {
        return filtroTitulo;
    }

    public void setFiltroTitulo(String filtroTitulo) {
        this.filtroTitulo = filtroTitulo;
    }

    public String getFiltroStatus() {
        return filtroStatus;
    }

    public void setFiltroStatus(String filtroStatus) {
        this.filtroStatus = filtroStatus;
    }
}
