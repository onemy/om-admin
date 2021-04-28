package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.onemysoft.oma.portal.entity.Group;

/**
 * @author zongshuo
 * 
 */
public interface GroupRepository extends JpaRepository<Group, String> {
	
	List<Group> findByParentIsNull();
	
    Group findByGroupName(String groupname);
    
    Group findByGroupCode(String groupCode);
	

    @Modifying
    @Query("delete from Group where id in ?1")
    void deleteByIdIn(List<String> ids);
    

    @Modifying
    @Query("update Group g set g.status=?2  where id=?1")
    void upateStatus(String id,String status);
}
