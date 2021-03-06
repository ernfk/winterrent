package com.winterrent.winterrent.service.itemPropertyDefinition;

import com.winterrent.winterrent.dao.itemPropertyDefinition.ItemPropertyDefinitionDAO;
import com.winterrent.winterrent.entity.ItemPropertyDefinition;
import com.winterrent.winterrent.entity.ItemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPropertyDefinitionServiceImpl implements ItemPropertyDefinitionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemPropertyDefinitionServiceImpl.class);

    private ItemPropertyDefinitionDAO itemPropertyDefinitionDAO;

    @Autowired
    public ItemPropertyDefinitionServiceImpl(ItemPropertyDefinitionDAO theItemPropertyDefinitionDAO) {
        this.itemPropertyDefinitionDAO = theItemPropertyDefinitionDAO;
    }

    @Override
    public List<ItemPropertyDefinition> findAll() {
        LOGGER.info("Find all item property definitions");
        return itemPropertyDefinitionDAO.findAll();
    }

    @Override
    public List<ItemPropertyDefinition> findByItemType(ItemType itemType) {
        LOGGER.info("Find item property definitions by item type: {}", itemType);
        return itemPropertyDefinitionDAO.findByItemType(itemType);
    }
}
