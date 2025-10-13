package ar.edu.unq.epers.unidad4.persistence.repository.mapper;

import ar.edu.unq.epers.unidad4.model.Item;
import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    private ModelMapper modelMapper;

    public ItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public ItemSQL toSql(Item item) {
        return modelMapper.map(item, ItemSQL.class);
    }

    public Item toModel(ItemSQL itemSQL) {
        return modelMapper.map(itemSQL, Item.class);
    }
}
