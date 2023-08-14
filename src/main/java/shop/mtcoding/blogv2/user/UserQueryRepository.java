package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// 직접 만들어도 돼!
@Repository
public class UserQueryRepository {

    @Autowired
    private EntityManager em;

    public void save(User user) {

        em.persist(user); // 영속화 (영속성 컨텍스트)
    }

    // JPA findById의 내부에서는 이게 작동하고 있는거임
    public User findById(Integer id) {
        return em.find(User.class, id);
    }

}
