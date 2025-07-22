package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

/**
 * ユーザー情報 Service
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
  /**
   * ユーザー情報 Repository
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * ユーザー情報 全検索
   * @return 検索結果
   */
  public List<User> searchAll() {
    return userRepository.findAll();
  }

  /**
   * ユーザー情報 新規登録
   * @param userRequest ユーザー情報
   */
  public void create(UserRequest userRequest) {
    Date now = new Date();
    User user = new User();
    user.setName(userRequest.getName());
    user.setAddress(userRequest.getAddress());
    user.setPhone(userRequest.getPhone());
    user.setCreateDate(now);
    user.setUpdateDate(now);
    userRepository.save(user);
  } // <-- createメソッドの閉じ括弧をここに追加

  public void createRandom() {
    Faker faker = new Faker(new Locale("ja")); // 生成日文数据

    UserRequest userRequest = new UserRequest();
    userRequest.setName(faker.name().fullName());
    userRequest.setAddress(faker.address().fullAddress());
    userRequest.setPhone(faker.phoneNumber().cellPhone());

    create(userRequest);
  }

  /**
   * ユーザー情報 主キー検索
   * @param id ユーザーID
   * @return 検索結果
   */
  public User findById(Long id) {
    return userRepository.findById(id).get();
  }

  /**
   * ユーザー情報 物理削除
   * @param id ユーザーID
   */
  public void delete(Long id) {
    User user = findById(id);
    userRepository.delete(user);
  }
}