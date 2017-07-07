package com.taotao.manager.service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.mapper.ItemMapper;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.pojo.ItemParamItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ellen on 2017/7/2.
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @Value("${WEB_TAOTAO_URL}")
    private String URL;

    /**
     * 在一个事务中添加item项和itemdesc项
     *
     * @param item
     * @param desc
     */
    public void saveItem(Item item, ItemDesc desc, String itemParams) {
        item.setStatus(1);
        item.setId(null);
        save(item);
        desc.setItemId(item.getId());
        itemDescService.save(desc);

        if (StringUtils.isNotEmpty(itemParams)) {
            ItemParamItem itemParamItem = new ItemParamItem();
            itemParamItem.setItemId(item.getId());
            itemParamItem.setParamData(itemParams);
            itemParamItemService.save(itemParamItem);
        }
    }

    public PageInfo<Item> queryItemList(int page, int rows, Object o) {
        Example example = new Example(Item.class);
        example.setOrderByClause("status ASC");
        return queryPageListByExample(page, rows, example);
    }

    public void updateItems(List<Object> ids, int status) {
        Mapper<Item> mapper = getMapper();
        Example example = new Example(Item.class);
        example.createCriteria().andIn("id", ids);
        Item item = new Item();
        item.setStatus(status);
        mapper.updateByExampleSelective(item, example);
    }

    public void updateItem(Item item, String desc, ItemParamItem itemParamItem) {
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(item.getId());
        itemDesc.setUpdated(new Date());

        updateSelective(item);
        itemDescService.updateSelective(itemDesc);

        if (itemParamItem != null) {
            itemParamItemService.updateSelective(itemParamItem);
        }

        //通知其他系统该商品已经更新
        String url = URL + "/item/cache/" + item.getId() + ".html";

    }


}
