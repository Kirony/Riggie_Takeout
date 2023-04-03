package com.zzh.riggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzh.riggie.common.CustomException;
import com.zzh.riggie.common.R;
import com.zzh.riggie.entity.AddressBook;
import com.zzh.riggie.mapper.AddressBookMapper;
import com.zzh.riggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    /**
     * 获取所有地址
     * @param id
     * @return
     */
    @Override
    public List<AddressBook> listById(Long id) {

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null , AddressBook::getUserId,id);
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> list = this.list(queryWrapper);
        return list;
    }

    /**
     * 获取默认地址
     * @return
     */
    @Override
    public AddressBook getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook one = this.getOne(queryWrapper);
        return one;
    }

    /**
     * 设置默认地址
     * @param addressBook
     */
    @Override
    public AddressBook setDefault(AddressBook addressBook) {
        log.info("addressBook:"+ addressBook.toString());
        AddressBook byId = this.getById(addressBook.getId());
        log.info("byId:"+ byId.toString());
        if(byId.getIsDefault() == 0) {
            LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(addressBook.getUserId() != null,AddressBook::getUserId,addressBook.getUserId());
            updateWrapper.set(AddressBook::getIsDefault,0);

            this.update(updateWrapper);

            addressBook.setIsDefault(1);

            this.updateById(addressBook);
        }else if(byId.getIsDefault() == 1) {
            LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(addressBook.getUserId() != null,AddressBook::getUserId,addressBook.getUserId());
            updateWrapper.set(AddressBook::getIsDefault,0);

            this.update(updateWrapper);
        }
        return addressBook;
    }
}
