package com.zzh.riggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzh.riggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
