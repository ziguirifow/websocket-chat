package br.unip.websocketchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "usuario_id")
  private Integer id;

  @Column(name = "user_name")
  @Length(min = 3, message = "*Seu nome de usuário deve ter pelo menos 3 caracteres")
  @NotEmpty(message = "*Forneça um nome de usuário")
  private String userName;

  @Column(name = "email")
  @Email(message = "*Por favor forneça um email válido")
  @NotEmpty(message = "*Por favor, forneça um e-mail")
  private String email;

  @Column(name = "senha")
  @Length(min = 4, message = "*Sua senha deve ter pelo menos 4 caracteres")
  @NotEmpty(message = "*Por favor, forneça sua senha")
  private String senha;

  @Column(name = "ativo")
  private Boolean ativo;

  @ManyToMany(cascade = CascadeType.MERGE)
  @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "permissao_id"))
  private Set<Permissao> permissao;
}
