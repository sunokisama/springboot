package com.example.demo.controller;

import java.util.List;
import java.util.Locale;

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.mapper.CountryCodeMapper;
import jakarta.persistence.OptimisticLockException;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserController {
  @Autowired
  private MessageSource messageSource;

  public String getGreeting(Locale locale) {
    return messageSource.getMessage("greeting", null, locale);
  }


  @Autowired
  private UserService userService;

  /** 一覧画面表示 + 初期検索フォーム */


  @GetMapping("/user/list")
  public String displayList(@ModelAttribute("searchForm") UserSearchRequest request, Model model) {
    List<User> userlist = userService.search(request);
    model.addAttribute("userlist", userlist);
    model.addAttribute("userSearchRequest", new UserSearchRequest());
    model.addAttribute("countries", countryCodeMapper.findAll());
    return "user/list";
  }

  /** 検索処理 */
  @PostMapping("/user/list")
  public String search(@ModelAttribute UserSearchRequest request, Model model) {
    List<User> userlist = userService.search(request);
    model.addAttribute("userlist", userlist);
    model.addAttribute("userSearchRequest", request);
    model.addAttribute("countries", countryCodeMapper.findAll());
    return "user/list";
  }

  /** 詳細表示 */
  @GetMapping("/user/{id}")
  public String view(@PathVariable Long id, Model model) {
    User user = userService.findById(id);
    model.addAttribute("userData", user);
    return "user/view";
  }

  /** 編集画面表示 */
  @GetMapping("/user/{id}/edit")
  public String displayEdit(@PathVariable Long id, Model model) {
    User user = userService.findById(id);
    UserUpdateRequest request = new UserUpdateRequest();
    request.setId(user.getId());
    request.setName(user.getName());
    request.setPhone(user.getPhone());
    request.setAddress(user.getAddress());
    request.setVersion(user.getVersion());
    model.addAttribute("userUpdateRequest", request);
    model.addAttribute("countries", countryCodeMapper.findAll());

    return "user/edit";
  }

  /** 更新処理 */
  @PostMapping("/user/update")
  public String update(@Validated @ModelAttribute UserUpdateRequest request,
                       BindingResult result,
                       Model model) {
    if (result.hasErrors()) {
      List<String> errors = result.getAllErrors().stream()
              .map(ObjectError::getDefaultMessage).toList();
      model.addAttribute("validationError", errors);
      model.addAttribute("countries", countryCodeMapper.findAll()); // 🔧 修复点
      return "user/edit";
    }
    try {
      userService.update(request);
    } catch (OptimisticLockException e) {
      model.addAttribute("errorMessage", "他のユーザーにより更新されました");
      model.addAttribute("countries", countryCodeMapper.findAll()); // 🔧 修复点
      return "user/edit";
    }
    return "redirect:/user/list";
  }

  /** 削除処理 */
  @GetMapping("/user/{id}/delete")
  public String delete(@PathVariable Long id) {
    userService.delete(id);
    return "redirect:/user/list";
  }


  /** 通常の新規登録 */
  @PostMapping("/user/create")
  public String create(@Validated @ModelAttribute UserAddRequest request,
                       BindingResult result,
                       Model model) {
    if (result.hasErrors()) {
      List<String> errors = result.getAllErrors().stream()
              .map(ObjectError::getDefaultMessage).toList();
      model.addAttribute("validationError", errors);
      model.addAttribute("userRequest", request);
      model.addAttribute("countries", countryCodeMapper.findAll());
      return "user/add";
    }
    try {
      userService.save(request);
    } catch (IllegalArgumentException e) {
      model.addAttribute("validationError", List.of(e.getMessage()));
      model.addAttribute("userRequest", request);
      model.addAttribute("countries", countryCodeMapper.findAll());
      return "user/add";
    }

    return "redirect:/user/list";
  }

  /** ランダム登録（既存機能） */
  @PostMapping("/user/create/random")
  public String createRandom() {
    userService.createRandom();
    return "redirect:/user/list";
  }

  @Autowired
  private CountryCodeMapper countryCodeMapper;

  @GetMapping("/user/add")
  public String showAddForm(Model model) {
    model.addAttribute("userRequest", new UserAddRequest());
    model.addAttribute("countries", countryCodeMapper.findAll());
    return "user/add";
  }
}
