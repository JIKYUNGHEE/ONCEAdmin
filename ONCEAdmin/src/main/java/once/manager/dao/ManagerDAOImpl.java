package once.manager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import once.manager.vo.ManagerVO;
import once.pickupPlace.vo.PickupPlaceVO;
import once.store.vo.StoreVO;

@Repository
public class ManagerDAOImpl implements ManagerDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	/**
	 * manger 로그인
	 * @param manager
	 * @return managerVO
	 */
	@Override
	public ManagerVO login(ManagerVO manager) {
		ManagerVO managerVO = sqlSession.selectOne("once.manager.dao.ManagerDAO.login", manager);
		System.out.println("loginDao 성공");
		System.out.println(managerVO);
		return managerVO;
	}
	
	@Override
	public String checkType(String id) {
		return sqlSession.selectOne("once.manager.dao.ManagerDAO.checkType", id);
	}

	@Override
	public boolean checkPassword(String managerId, String password) {
		boolean result = false;
		
        Map<String, String> map = new HashMap<String, String>();
        map.put("managerId", managerId);
        map.put("password", password);
        
        int count = sqlSession.selectOne("once.manager.dao.ManagerDAO.checkPassword", map);
        if(count == 1) {
        	result= true;
        }
        
        return result;
	}

	@Override
	public ManagerVO selectById(String managerId) {
		return sqlSession.selectOne("once.manager.dao.ManagerDAO.selectOneManager", managerId);
	}

	@Override
	public StoreVO selectByNo(String storeNo) {
		return sqlSession.selectOne("once.manager.dao.ManagerDAO.selectByNo", storeNo);
	}

	@Override
	public void modifyManager(String managerId, String password, String telephone) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("managerId", managerId);
		map.put("password", password);
		map.put("telephone", telephone);
		
		sqlSession.update("once.manager.dao.ManagerDAO.modifyManager", map);
	}
	
	//매니저 관리 전체 조회
	@Override
	public List<ManagerVO> selectAll() {
		
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectAll");
		
		return list;
	}
	
	//매니저 삭제
	@Override
	public void delete(String managerId) {
		sqlSession.delete("once.manager.dao.ManagerDAO.delete", managerId);
	}
	
	//매니저 수정
	@Override
	public void update(String managerId, String telephone) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("managerId", managerId);
		map.put("telephone", telephone);
		
		sqlSession.update("once.manager.dao.ManagerDAO.update", map);
	}
	
	//매니저 추가
	@Override
	public void insert(ManagerVO manager) {
		
		sqlSession.insert("once.manager.dao.ManagerDAO.insert", manager);
	}
	
	//매니저 검색
	@Override
	public List<ManagerVO> search(ManagerVO manager) {
		
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.search", manager);
		
		return list;
	}

	//ID 중복 체크
	@Override
	public boolean checkId(String managerId) {
		
		ManagerVO manager = sqlSession.selectOne("once.manager.dao.ManagerDAO.selectOneManager", managerId);
		
		if(manager==null)
			return false;
		else
			return true;
	}
	
	//페이징
	@Override
	public List<ManagerVO> selectPage(List page) {
		
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectPage", page);
		
		return list;
	}

	@Override
	public List<ManagerVO> selectInfoStaff() {
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectInfoStaff");
		return list;
	}

	@Override
	public List<ManagerVO> selectInfoStaffPage(List<Integer> page) {
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectInfoStaffPage", page);
		return list;
	}

	@Override
	public List<ManagerVO> searchInfoStaff(ManagerVO manager) {
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.searchInfoStaff", manager);
		return list;
	}


	@Override
	public ManagerVO selectOnestaffNo(int staffNo) {
		return sqlSession.selectOne("once.manager.dao.ManagerDAO.selectOnestaffNo", staffNo);
	}

	//해당 매장 직원 목록
	@Override
	public List<ManagerVO> selectByStore(String storeNo) {
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectByStore", storeNo);
		
		return list;
	}
	
	@Override
	public List<ManagerVO> selectByStorePage(List<Object> page) {
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectByStorePage", page);
		return list;
	}

	@Override
	public List<ManagerVO> searchByStore(ManagerVO manager) {
		List<ManagerVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.searchByStore", manager);
		return list;
	}

	@Override
	public void insert1(ManagerVO manager) {
		
		sqlSession.insert("once.manager.dao.ManagerDAO.insert1", manager);
	}
	
	@Override
	public List<StoreVO> selectStoreByFloor(String floor) {
		List<StoreVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectStoreByFloor", floor);
		return list;
	}

	@Override
	public List<StoreVO> selectInfoByFloor() {
		List<StoreVO> list = sqlSession.selectList("once.manager.dao.ManagerDAO.selectInfoByFloor");
		return list;
	}
}
