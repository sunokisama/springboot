package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "country")
public class Country implements Serializable {

    /**
     * ISO3166-1 alpha-3 国コード（主キー）
     */
    @Id
    @Column(name = "alpha3")
    private String alpha3;

    /**
     * 日本語の国名
     */
    @Column(name = "ja_name")
    private String jaName;

    /**
     * 英語の国名
     */
    @Column(name = "en_name")
    private String enName;

    /**
     * 数値コード（ISO numeric）
     */
    @Column(name = "numeric")
    private Integer numeric;

}
