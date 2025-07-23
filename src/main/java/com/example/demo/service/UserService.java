package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.Country;
import com.example.demo.mapper.CountryCodeMapper;
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

  @Autowired
  private CountryCodeMapper countryCodeMapper;
  public void createRandom() {
    Faker faker = new Faker(new Locale("ja")); // 生成日文数据
    Country randomCountry = countryCodeMapper.findRandom();

    UserAddRequest userRequest = new UserAddRequest();
    userRequest.setName(faker.name().fullName());
    userRequest.setAddress(faker.address().fullAddress());
    userRequest.setPhone(faker.phoneNumber().cellPhone());
    userRequest.setCountry(randomCountry.getAlpha3());


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
    // 1. 電話番号を一度だけフォーマットする
    String formattedPhone = formatPhone(userAddRequest.getPhone());

    // 2. フォーマットした電話番号で重複チェックを先に行う
    if (userMapper.existsByPhone(formattedPhone)) {
      throw new IllegalArgumentException("既に同じ電話番号が登録されています。");
    }

    // 3. 重複がなければ、フォーマット済みの電話番号をセットして保存する
    userAddRequest.setPhone(formattedPhone);
    userMapper.save(userAddRequest); // 保存処理は一度だけ
  }

  private String formatPhone(String rawPhone) {
    if (rawPhone == null || rawPhone.trim().isEmpty()) {
      // 或者根据业务要求抛出异常
      return null;
    }

    // 步骤 A: 清理所有非数字字符 (例如空格、括号、连字符)
    String cleanedPhone = rawPhone.replaceAll("[\\s\\-()]", "");

    // 步骤 B: 处理国际区号 "+81"
    if (cleanedPhone.startsWith("+81")) {
      cleanedPhone = "0" + cleanedPhone.substring(3);
    }

    // 步骤 C: 验证清理后的号码是否为11位数字
    if (!cleanedPhone.matches("^\\d{11}$")) {
      // 如果不是11位，说明格式不正确
      throw new IllegalArgumentException("電話番号の形式が正しくありません。11桁の数字を入力してください。");
    }

    // 步骤 D: 格式化为 "XXX-XXXX-XXXX"
    return cleanedPhone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
  }


  /**
   * ユーザ情報更新
   * @param userUpdateRequest リクエストデータ
   */
  public void update(UserUpdateRequest userUpdateRequest) {
    userUpdateRequest.setPhone(formatPhone(userUpdateRequest.getPhone()));
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