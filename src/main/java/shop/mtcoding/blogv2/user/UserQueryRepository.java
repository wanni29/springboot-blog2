package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// 직접 만들어도 돼!
@Repository
public class UserQueryRepository {

    @Autowired
    private EntityManager em;

}
