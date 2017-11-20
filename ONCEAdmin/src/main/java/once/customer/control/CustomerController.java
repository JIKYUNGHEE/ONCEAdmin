package once.customer.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import once.customer.service.CustomerService;
import once.customer.vo.CustomerVO;

@RequestMapping("/customer")
@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	//고객 전체 리스트 조회
	@RequestMapping("/list")
	public ModelAndView list() {
		List<CustomerVO> customerList = service.selectAllBoard();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/customer/list");
		mav.addObject("customerList", customerList);
		
		return mav;
	}
	
	//고객 삭제
	@RequestMapping(value="/list", method=RequestMethod.DELETE)
	public String deleteList(HttpServletRequest request) {
	   
	    String [] mem = request.getParameterValues("memNo");
	      
	    int [] memNo = new int[mem.length];
	     
	    for(int i=0; i<mem.length; i++) {
	       memNo[i] = Integer.parseInt(mem[i]);
	       service.deleteBoard(memNo[i]);
	    }
	    return "redirect:/customer/list";
	}
	
	//고객 조회
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public ModelAndView searchList(HttpServletRequest request) {
	   
	    String customerId = request.getParameter("customerId");
	      
	   
	    List<CustomerVO> customerList = service.searchBoard(customerId);
		
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("admin/customer/list");
		mav.addObject("customerList", customerList);
	    
		return mav;
	}
}
