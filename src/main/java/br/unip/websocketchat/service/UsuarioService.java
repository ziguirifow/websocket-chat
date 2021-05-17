package br.unip.websocketchat.service;


import br.unip.websocketchat.model.Usuario;
import br.unip.websocketchat.repository.PermissaoRepository;
import br.unip.websocketchat.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static java.util.Collections.singletonList;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PermissaoRepository permissaoRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;


  public Usuario findUsuarioByEmail(String email) {
    return usuarioRepository.findUsuarioByEmail(email);
  }

  public Usuario findUsuarioByUserName(String userName) {
    return usuarioRepository.findUsuarioByUserName(userName);
  }

  public void persisteUsuario(Usuario usuario) {
    usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
    usuario.setAtivo(true);
    var permissaoDoUsuario = permissaoRepository.findByPermissao("ADMIN");
    usuario.setPermissao(new HashSet<>(singletonList(permissaoDoUsuario)));
    usuarioRepository.save(usuario);
  }
}
