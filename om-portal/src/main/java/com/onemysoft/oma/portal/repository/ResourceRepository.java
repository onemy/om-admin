package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.repository.dao.ResourceDao;

/**
 * @author zongshuo
 * 
 */
public interface ResourceRepository extends JpaRepository<Menu, String>,ResourceDao {

}
