package ar.edu.unq.epers.unidad4.persistence.sql;

import ar.edu.unq.epers.unidad4.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemSQLDAO extends JpaRepository<Item, Long> {

    @Query(
            "FROM Item i where i.peso  > :peso order by i.peso asc"
    )
    Set<Item> getMasPesados(@Param("peso") int peso);

    @Query(
            "from Item i "
                    + "where i.owner.vida < ?1 "
                    + "order by i.peso asc"
    )
    Set<Item> getItemsDePersonajesDebiles(int vida);
}
