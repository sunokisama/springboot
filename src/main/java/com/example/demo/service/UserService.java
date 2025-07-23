package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.mapper.UserMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

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
  private UserMapper userMapper;;

  /**
   * ユーザー情報 全検索
   * @return 検索結果
   */
  public List<User> findAll() {
    return userMapper.findAll();
  }


  public void createRandom() {
    Faker faker = new Faker(new Locale("ja")); // 生成日文数据

    UserAddRequest userRequest = new UserAddRequest();
    userRequest.setName(faker.name().fullName());
    userRequest.setAddress(faker.address().fullAddress());
    userRequest.setPhone(faker.phoneNumber().cellPhone());

    save(userRequest);
  }

  /**
   * ユーザー情報主キー検索
   * @return 検索結果
   */
  public User findById(Long id) {
    return userMapper.findById(id);
  }

  /**
   * ユーザー情報検索
   * @param userSearchRequest リクエストデータ
   * @return 検索結果
   */
  public List<User> search(UserSearchRequest userSearchRequest) {
    return userMapper.search(userSearchRequest);
  }


  /**
   * ユーザ情報登録
   * @param userAddRequest リクエストデータ
   */
  public void save(UserAddRequest userAddRequest) {
    userMapper.save(userAddRequest);
  }

  /**
   * ユーザ情報更新
   * @param userUpdateRequest リクエストデータ
   */
  public void update(UserUpdateRequest userUpdateRequest) {
    userMapper.update(userUpdateRequest);
  }

  /**
   * ユーザー情報論理削除
   * @param id
   */
  public void delete(Long id) {
    userMapper.delete(id);
  }
}