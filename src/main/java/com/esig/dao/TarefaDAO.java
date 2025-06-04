package com.esig.dao;

import java.util.List;
import com.esig.model.Tarefa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class TarefaDAO {
    // Acesso ao banco de dados
    @PersistenceContext(unitName = "taskPU")
    private EntityManager em;

    public void salvar(Tarefa tarefa) {
        if (tarefa.getId() == null) {
            em.persist(tarefa);
        } else {
            em.merge(tarefa);
        }
    }

    public void remover(Tarefa tarefa) {
        Tarefa t = em.find(Tarefa.class, tarefa.getId());
        if (t != null) {
            em.remove(t);
        }
    }

    public Tarefa findPorId(Long id) {
        return em.find(Tarefa.class, id);
    }

    public List<Tarefa> listarTodas() {
        TypedQuery<Tarefa> query = em.createQuery("SELECT t FROM Tarefa t", Tarefa.class);
        return query.getResultList();
    }
    
    public List<Tarefa> filtrarPorResponsavel(String responsavel) {
        TypedQuery<Tarefa> query = em.createQuery(
            "SELECT t FROM Tarefa t WHERE t.responsavel LIKE :resp", Tarefa.class);
        query.setParameter("resp", "%" + responsavel + "%");
        return query.getResultList();
    }
    
    public List<Tarefa> filtrar(String numero, String titulo, String responsavel, String status) {
        String jpql = "SELECT t FROM Tarefa t WHERE 1=1";
        if (numero != null && !numero.isEmpty()) {
            jpql += " AND t.id = :numero";
        }
        if (titulo != null && !titulo.isEmpty()) {
            jpql += " AND (LOWER(t.titulo) LIKE :titulo OR LOWER(t.descricao) LIKE :titulo)";
        }
        if (responsavel != null && !responsavel.isEmpty()) {
            jpql += " AND t.responsavel = :responsavel";
        }
        if (status != null && !status.isEmpty()) {
            jpql += " AND t.status = :status";
        }

        TypedQuery<Tarefa> query = em.createQuery(jpql, Tarefa.class);
        if (numero != null && !numero.isEmpty()) {
            try {
                query.setParameter("numero", Long.parseLong(numero));
            } catch (NumberFormatException e) {
            }
        }
        if (titulo != null && !titulo.isEmpty()) {
            query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        }
        if (responsavel != null && !responsavel.isEmpty()) {
            query.setParameter("responsavel", responsavel);
        }
        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }
        return query.getResultList();
    }
}
