package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * ユーザー情報 Mapper
 */
@Mapper
public interface UserMapper {

    /**
     * ユーザー情報全件検索
     * @return 検索結果
     */
    List<User> findAll();

    /**
     * ユーザー情報主キー検索
     * @param id 主キー
     * @return 検索結果
     */
    User findById(Long id);

    /**
     * ユーザー情報検索
     * @param user 検索用リクエストデータ
     * @return 検索結果
     */
    List<User> search(UserSearchRequest user);

    /**
     * ユーザー情報登録
     * @param userRequest 登録用リクエストデータ
     */
    void save(UserAddRequest userRequest);

    /**
     * ユーザー情報更新
     * @param userUpdateRequest 更新用リクエストデータ
     */
    void update(UserUpdateRequest userUpdateRequest);

    /**
     * ユーザー情報の論理削除
     * @param id ID
     */
    void delete(Long id);

    User findByIdIncludingDeleted(Long id);


    @Select("SELECT COUNT(*) FROM user WHERE phone = #{phone} AND delete_date IS NULL")
    boolean existsByPhone(String phone);

}