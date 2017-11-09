package once.manager.dao;

import once.manager.vo.ManagerVO;

public interface ManagerDAO {

	boolean checkPassword(String managerId, String password);

	ManagerVO selectById(String managerId);

}
