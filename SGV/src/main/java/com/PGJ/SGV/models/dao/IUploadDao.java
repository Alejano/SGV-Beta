package com.PGJ.SGV.models.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.PGJ.SGV.models.entity.CatalogoServicio;

public interface IUploadDao extends CrudRepository<CatalogoServicio, Long> {
	/*@Modifying
    @Transactional
    @Query (value="LOAD DATA LOCAL INFILE 'C:/Users/Tester/Documents/transaction_data.txt' INTO TABLE tbl_fin FIELDS TERMINATED BY ',' IGNORE 1 LINES", nativeQuery = true)
    public void bulkLoadData();*/
	
	@Modifying
    @Transactional
	@Query (value="LOAD DATA LOCAL INFILE '/opt/import/catalogo_servicios.csv' INTO TABLE catalogo_servicios FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS", nativeQuery = true)
	public void importarServicios();
}
