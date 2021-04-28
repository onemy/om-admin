package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.repository.dao.ResourceDao;

/**
 * @author zongshuo
 * 
 */
public interface MenuRepository extends JpaRepository<Menu, String> {
	List<Menu> findByParentIsNull();
	
    Menu findByName(String name);
    
    Menu findByCode(String Code);
	
    @Modifying
    @Query("delete from Menu where id in ?1")
    void deleteByIdIn(List<String> ids);
    

    @Modifying
    @Query("update Menu g set g.status=?2  where id=?1")
    void upateStatus(String id,String status);
}
