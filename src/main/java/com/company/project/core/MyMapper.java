package com.company.project.core;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 类名称: MyMapper
 * 类描述:
 *
 * @author:
 * @since: 2018/1/23
 * @version: 1.0
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}