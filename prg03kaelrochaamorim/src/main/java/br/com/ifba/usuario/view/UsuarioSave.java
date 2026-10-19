package br.com.ifba.usuario.view;

import br.com.ifba.usuario.entity.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//Testar se o caminho do banco ta certo
public class UsuarioSave {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prg03PU");
        EntityManager em = emf.createEntityManager();

        try {
            Usuario u = new Usuario("Teste", "12345678900", "teste" + System.currentTimeMillis(), "123");

            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();

            System.out.println("Salvo com id: " + u.getId());
        } finally {
            em.close();
            emf.close();
        }
    }
}