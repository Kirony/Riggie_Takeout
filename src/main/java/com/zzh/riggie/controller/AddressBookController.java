package com.zzh.riggie.controller;

import com.zzh.riggie.common.BaseContext;
import com.zzh.riggie.common.R;
import com.zzh.riggie.entity.AddressBook;
import com.zzh.riggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 获取所有地址
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        Long userId = BaseContext.getThreadLocal();
        List<AddressBook> addressBookList = addressBookService.listById(userId);
        return R.success(addressBookList);
    }

    /**
     * 获取最新地址
     *
     * @return
     */
    @GetMapping("/lastUpadate")
    public R<AddressBook> lastUpdate() {
        return null;
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook) {
        Long userId = BaseContext.getThreadLocal();
        addressBook.setUserId(userId);
        addressBookService.save(addressBook);
        return R.success("新增地址成功");
    }

    /**
     * 更改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook){
        addressBookService.updateById(addressBook);
        return R.success("更改地址成功");
    }

    /**
     * 删除地址
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        addressBookService.removeByIds(ids);
        return R.success("/删除地址成功");
    }

    /**
     * 查询单个地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id){
        AddressBook byId = addressBookService.getById(id);
        return R.success(byId);
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> defaultAddress(@RequestBody AddressBook addressBook){
        AddressBook addressBook1 = addressBookService.setDefault(addressBook);
        return R.success(addressBook1);
    }

    /**
     * 获取默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        AddressBook aDefault = addressBookService.getDefault();
        return R.success(aDefault);
    }
}
