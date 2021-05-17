package br.unip.websocketchat.repository;

import br.unip.websocketchat.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Integer> {

  Permissao findByPermissao(String permissao);
}
