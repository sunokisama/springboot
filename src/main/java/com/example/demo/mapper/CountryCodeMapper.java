package com.example.demo.mapper;

import com.example.demo.entity.Country;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CountryCodeMapper {

    @Select("SELECT alpha3, ja_name, en_name FROM country ORDER BY ja_name")
    List<Country> findAll();

    @Select("SELECT alpha3, ja_name, en_name FROM country ORDER BY RAND() LIMIT 1")
    Country findRandom();

    @Select("SELECT alpha3, ja_name, en_name FROM country WHERE alpha3 = #{alpha3}")
    Country findByAlpha3(String alpha3);

    @Insert("INSERT INTO country (alpha3, ja_name, en_name) VALUES (#{alpha3}, #{jaName}, #{enName})")
    void insert(Country country);

    @Update("UPDATE country SET ja_name = #{jaName}, en_name = #{enName} WHERE alpha3 = #{alpha3}")
    void update(Country country);

    @Delete("DELETE FROM country WHERE alpha3 = #{alpha3}")
    void delete(String alpha3);
}

