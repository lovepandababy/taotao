package com.taotao.manager.controller;

import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Ellen on 2017/7/6.
 */
@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentCategoryController.class);

    @Autowired
    private ContentCategoryService contenCategoryService;

    /**
     * 查询广告
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryCat(
            @RequestParam(value = "id", defaultValue = "0") long id) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("查询广告商品");
            }

            ContentCategory param = new ContentCategory();
            param.setParentId(id);
            List<ContentCategory> list = contenCategoryService.queryListByWhere(param);
            if (list == null || list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("查询广告商品成功，contentCatgory = {}", list);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("查询广告失败");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 保存新的商品分类广告
     *
     * @param contentCategory
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCatgory(ContentCategory contentCategory) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("保存广告商品，contentCategory = {}", contentCategory);
            }

            contenCategoryService.saveContentCatgory(contentCategory);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("保存广告商品成功，contentCategory = {}", contentCategory);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
        } catch (Exception e) {
            LOGGER.error("保存广告商品失败，contentCategory = {}", contentCategory);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 更新商品广告分类
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("更新广告商品，contentCategory = {}", contentCategory);
            }
            contenCategoryService.updateSelective(contentCategory);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("更新广告商品成功，contentCategory = {}", contentCategory);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("更新广告商品失败，contentCategory = {}", contentCategory);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCatgory(ContentCategory contentCategory) {
        try {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("更新广告商品，contentCategory = {}", contentCategory);
            }

            contenCategoryService.deleteContentCatgory(contentCategory);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("更新广告商品成功，contentCategory = {}", contentCategory);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            LOGGER.error("更新广告商品失败，contentCategory = {}", contentCategory);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
