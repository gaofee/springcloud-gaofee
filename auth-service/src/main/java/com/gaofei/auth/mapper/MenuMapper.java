package com.gaofei.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaofei.auth.domain.Menu;

import java.util.List;

/**
 * @author : gaofee
 * @date : 11:37 2022/5/27
 * @码云地址 : https://feege.gitee.io
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
