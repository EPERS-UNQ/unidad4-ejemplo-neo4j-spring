package ar.edu.unq.epers.unidad4.persistence.sql;

import ar.edu.unq.epers.unidad4.persistence.sql.entity.ItemSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemSQLDAO extends JpaRepository<ItemSQL, Long> {

    @Query(
            "FROM ItemSQL i where i.peso  > :peso order by i.peso asc"
    )
    Set<ItemSQL> getMasPesados(@Param("peso") int peso);

    @Query(
            "from ItemSQL i "
                    + "where i.owner.vida < ?1 "
                    + "order by i.peso asc"
    )
    Set<ItemSQL> getItemsDePersonajesDebiles(int vida);
}
