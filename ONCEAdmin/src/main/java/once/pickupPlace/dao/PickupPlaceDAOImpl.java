package once.pickupPlace.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PickupPlaceDAOImpl implements PickupPlaceDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
}
