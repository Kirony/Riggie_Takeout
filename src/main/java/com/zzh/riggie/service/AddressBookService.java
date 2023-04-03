package com.zzh.riggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzh.riggie.entity.AddressBook;

import java.util.List;

public interface AddressBookService extends IService<AddressBook> {

    public List<AddressBook> listById(Long id);

    public AddressBook getDefault();

    public AddressBook setDefault(AddressBook addressBook);

}
