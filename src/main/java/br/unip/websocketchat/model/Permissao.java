package br.unip.websocketchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissoes")
public class Permissao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "permissao_id")
  private int id;

  @Column(name = "permissao")
  private String permissao;
}
