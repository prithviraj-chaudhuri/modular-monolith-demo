package com.modular.monolith.inventory.service;

import com.modular.monolith.inventory.entity.Item;
import com.modular.monolith.inventory.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createItem(String name, int count) {
        Item item = new Item(name, count);
        return itemRepository.save(item);
    }

    public Optional<Item> updateItem(Long id, String name, int count) {
        return itemRepository.findById(id).map(item -> {
            item.setName(name);
            item.setCount(count);
            return itemRepository.save(item);
        });
    }

    public boolean deleteItem(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
