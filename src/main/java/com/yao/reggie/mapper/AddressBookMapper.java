package com.yao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.reggie.bean.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yao
 * @create 2022-11-14 15:33
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
