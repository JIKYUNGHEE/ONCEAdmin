package once.customer.dao;

import java.util.List;

import once.customer.vo.CustomerVO;

public interface CustomerDAO {
	
	List<CustomerVO> selectAll();
	void delete(int memNo);
	List<CustomerVO> search(String id);
	List<CustomerVO> selectPage(List page);
}
