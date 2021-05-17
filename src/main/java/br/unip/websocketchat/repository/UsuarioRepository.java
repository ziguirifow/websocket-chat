package br.unip.websocketchat.repository;

import br.unip.websocketchat.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Usuario findUsuarioByEmail(String email);

  Usuario findUsuarioByUserName(String userName);
}