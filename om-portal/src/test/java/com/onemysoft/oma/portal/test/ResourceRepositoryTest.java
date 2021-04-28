package com.onemysoft.oma.portal.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.mapper.ResourceMapper;
import com.onemysoft.oma.portal.repository.AclRepository;
import com.onemysoft.oma.portal.repository.ResourceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.onemysoft.oma.portal.mapper")
public class ResourceRepositoryTest {
	
    @Autowired
    private ResourceMapper resourceMapper;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private AclRepository aclRepository;
	
	@Test
    public void test_findPermissionsByUser(){
		//List<Permission> list=resourceRepository.findPermissionsByUser("admin");
		
//		resourceMapper.findMenusByUserAndMoudule("1", "1");
		List<Permission> list=resourceMapper.findPermissionsByUser("");
        Assert.notEmpty(list,"测试查询到功能权限");
//		Role role=new Role();
//		role.setId("1");
//		
//		List<Menu> r=aclRepository.findMenuByPrincipal(role);
//		
//		Assert.notEmpty(r,"测试查询到功能权限");
    }

}
