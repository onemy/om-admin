package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.UserTable;

/**
 * @author zongshuo
 * 
 */
public interface UserRepository extends JpaRepository<User, String> {
	
    User findByUsername(String username);
    

    @Modifying
    @Query("update User u set u.password=?2  where id=?1")
    void upatePwd(String id,String password);
    

    @Modifying
    @Query("update User u set u.status=?2  where id=?1")
    void upateStatus(String id,String status);
    
    

    @Modifying
    @Query("delete from User where id in ?1")
    void deleteByIdIn(List<String> ids);
}
