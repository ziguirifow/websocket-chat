package br.unip.websocketchat.service;

import br.unip.websocketchat.model.Permissao;
import br.unip.websocketchat.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsuarioDetailsServer implements UserDetailsService {

  @Autowired
  private UsuarioService usuarioService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String userName) {
    var usuario = usuarioService.findUsuarioByUserName(userName);
    List<GrantedAuthority> permissoes = getPermissaoDoUsuario(usuario.getPermissao());
    return buildAutenticacaoDoUsuario(usuario, permissoes);
  }

  private List<GrantedAuthority> getPermissaoDoUsuario(Set<Permissao> permissoesDoUsuario) {
    Set<GrantedAuthority> permissoes = new HashSet<>();

    permissoesDoUsuario.forEach(permissao -> permissoes.add(new SimpleGrantedAuthority(permissao.getPermissao())));

    return new ArrayList<>(permissoes);
  }

  private UserDetails buildAutenticacaoDoUsuario(Usuario usuario, List<GrantedAuthority> permissoes) {
    return new User(usuario.getUserName(), usuario.getSenha(), usuario.getAtivo(), true, true, true, permissoes);
  }
}
