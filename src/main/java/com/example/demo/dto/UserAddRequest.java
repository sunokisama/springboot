package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class UserAddRequest implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  /**
   * 名前
   */
  @NotBlank(message = "名前を入力してください")
  @Size(max = 100, message = "名前は100桁以内で入力してください")
  private String name;


  /**
   * 住所
   */
  @Size(max = 255, message = "住所は255桁以内で入力してください")
  private String address;
  /**
   * 電話番号
   */
  @NotBlank(message = "電話番号は必須です")
  @Pattern(
          regexp = "^(0\\d{1,4}-\\d{1,4}-\\d{4}|\\+\\d{1,14})$",
          message = "電話番号は「0から始まるハイフン付き」または「+で始まる国際番号」で入力してください"
  )
  private String phone;


  /** バージョン（楽観ロック用） */
  private Integer version;

  private String country;

}