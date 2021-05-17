package br.unip.websocketchat.controller;

import br.unip.websocketchat.model.Usuario;
import br.unip.websocketchat.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping(value = "/")
  public ModelAndView login() {
    var modelAndView = new ModelAndView();
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @GetMapping(value = "/registration")
  public ModelAndView registration() {
    var modelAndView = new ModelAndView();
    var usuario = new Usuario();
    modelAndView.addObject("usuario", usuario);
    modelAndView.setViewName("registration");
    return modelAndView;
  }

  @PostMapping(value = "/registration")
  public ModelAndView criarUsuario(@Valid Usuario usuario, BindingResult bindingResult) {
    var modelAndView = new ModelAndView();
    var usuarioExiste = usuarioService.findUsuarioByUserName(usuario.getUserName());
    if (usuarioExiste != null) {
      bindingResult.rejectValue("userName", "error.usuario", "Já existe um usuário registrado com o nome de usuário fornecido");
    }
    if (!bindingResult.hasErrors()) {
      usuarioService.persisteUsuario(usuario);
      modelAndView.addObject("successMessage", "O usuário foi registrado com sucesso");
      modelAndView.addObject("usuario", new Usuario());

    }
    modelAndView.setViewName("registration");
    return modelAndView;
  }

  @GetMapping(value = "/chat")
  public ModelAndView chat() {
    var modelAndView = new ModelAndView();
    modelAndView.setViewName("index");
    return modelAndView;
  }

}
