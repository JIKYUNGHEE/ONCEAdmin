package once.manager.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import once.manager.service.ManagerService;
import once.notice.service.NoticeService;
import once.manager.vo.ManagerVO;
import once.notice.vo.NoticeVO;
import once.store.vo.StoreVO;

@SessionAttributes("loginVO")
@Controller
public class ManagerController {

	@Autowired
	private ManagerService service;
	
	@Autowired
	private NoticeService nService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("id")String id, @RequestParam("password")String password, Model model, HttpServletRequest request) {
		
		ManagerVO manager = new ManagerVO();
		manager.setManagerId(id);
		manager.setPassword(password);
		
		ManagerVO loginVO = service.login(manager);
				
		if(loginVO==null) {
			model.addAttribute("message", "Please check your ID or Password");
			
			return "login/loginFail";
		}else {
			model.addAttribute("loginVO", loginVO);
			System.out.println(loginVO);
			if(loginVO.getType().equals("admin")) {
				
				// 현재 페이지 번호 저장 변수
				int pageNo = 1;
				if (request.getParameter("pageNo") != null) {
					// 페이지 파라미터가 있는 경우 현재 페이지 번호를 설정
					pageNo = Integer.parseInt(request.getParameter("pageNo"));
				} 
				// 한페이지에 보여질 목록 수
				int listSize = 10;
				
				// 전체 게시글 카운트
				int totalCount = nService.selectAllNotice().size();

				// 마지막 페이지 구하기
				int lastPage = (totalCount % listSize == 0) ? totalCount / listSize 
						                                    : totalCount / listSize + 1;	

				request.setAttribute("pageNo"  , pageNo);
				request.setAttribute("lastPage", lastPage);


				// ======================================================================
				// 탭 관련 작업 추가 파트
				// ======================================================================
				// 목록에 보여질 탭 사이즈
				int tabSize  = 5;
				// 현재 페이지에 해당하는 탭 위치 
				int currTab   = (pageNo  -1) / tabSize + 1;
				int beginPage = (currTab -1) * tabSize + 1;
				int endPage   = (currTab * tabSize < lastPage) ? currTab * tabSize 
						                                       : lastPage;
				
				request.setAttribute("beginPage", beginPage);
				request.setAttribute("endPage"  , endPage);
				// ======================================================================
				
				// 해당 페이지의 게시글 목록
				List<Integer> page = new ArrayList<>();
				page.add( (pageNo - 1) * listSize );
				page.add( listSize );
				List<NoticeVO> list = nService.selectPage(page);
				
				model.addAttribute("list", list);
				return "admin/notice/list";
			}else if(loginVO.getType().equals("infoManger")) {
				
				return "infoManager/itemManage/addItem";
			}else if(loginVO.getType().equals("storeManager")) {
				
				return "storeManager/itemManage/register";
			}else {
				model.addAttribute("message", "type이 이상합니다");
				
				return "login/loginFail";
			}
		}
	}
	
	//패스워드 체크 페이지
	@RequestMapping(value = "/manager/check", method = RequestMethod.GET)
	public String checkForm() {
		return "manager/check";
	}

	//패스워드 체크 처리
	@RequestMapping(value = "/manager/check", method = RequestMethod.POST)
	public String check(@ModelAttribute("loginVO") ManagerVO manager, Model model) {
		String managerId = manager.getManagerId();
		boolean result = service.checkPassword(managerId, manager.getPassword());
		if(result == false) {
			model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
			return "manager/checkProcess";
		}
		
		//회원 정보 상세 페이지
		service.selectById(managerId);
		StoreVO store = service.selectByNo(manager.getStoreNo());
		
		model.addAttribute("managerVO", manager);
		model.addAttribute("storeVO", store);
		
		return "manager/detail";
	}
	
	//회원 정보 수정 처리
	@RequestMapping(value = "/manager/detail/{managerId}", method = RequestMethod.GET)
	public String modify(@PathVariable String managerId, @ModelAttribute @Valid ManagerVO manager, Model model) {
		
		service.modifyManager(manager.getManagerId(), manager.getPassword(), manager.getTelephone());
		
		model.addAttribute("managerVO", manager);
		model.addAttribute("message", "회원 정보 수정이 성공적으로 완료되었습니다.");
		
		return "manager/modifyProcess";
	}
	
	//매니저 관리 조회
	@RequestMapping(value="/manager/list")
	public ModelAndView list() {
		
		List<ManagerVO> managerList = service.selectAll();
		List<StoreVO> storeList = new ArrayList<>();
		
		ModelAndView mav = new ModelAndView();
		String DiffNo = null; // 기존에 없던 새로운 storeNo
		String ExNo = null; // 비교 기준 storeNo
		int count;

		
		for(int i=0; i<=managerList.size()-1; i++) {
			
			count=0; //storeNo에 해당하는 storeName이 존재하는 것을 비교하기 위한 count
			
			ExNo = managerList.get(i).getStoreNo(); //ExNo : 비교 기준 storeNo
			
			if(i==0) {
				storeList.add(service.selectByNo(ExNo));
			}else {
				for(int j=0; j<=i-1; j++) {
					if(ExNo.equals(managerList.get(j).getStoreNo())) //기존 storeList에 추가된 storeNo가 존재하는 경우
						++count;
					else //기존 storeList에 추가된 storeNo가 없을 경우
						DiffNo = ExNo;   
				}
				if(count==0)
					storeList.add(service.selectByNo(DiffNo)); //새로운  storeNo에 해당하는 storeVO객체를 List에 추가
			}
		}
		
		mav.addObject("managerList", managerList);
		mav.addObject("storeList", storeList);
		
		mav.setViewName("admin/manager/list");
		
		return mav;
	}
	
	//매니저 삭제
	@RequestMapping(value="/manager/list", method=RequestMethod.DELETE)
	public String delete(HttpServletRequest request) {
		
		String [] staffNos = request.getParameterValues("managerId");
		
		for(int i=0; i<staffNos.length; i++) {
			service.delete(staffNos[i]);
		}
		
		return "redirect:/manager/list";
	}
	
	//매니저 정보 수정
	@RequestMapping(value = "/manager/modify/{managerId}", method = RequestMethod.GET)
	public ModelAndView modify(@PathVariable String managerId) {
	
		ManagerVO manager = service.selectById(managerId);
		StoreVO store = service.selectByNo(manager.getStoreNo());
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("managerVO", manager);
		
		mav.addObject("storeVO", store);
		mav.setViewName("admin/manager/modify");
		
		return mav;
	}
	
	//매니저 정보 수정 완료 
	@RequestMapping(value ="/manager/update/{managerId}", method=RequestMethod.PUT )
	public String update(@PathVariable String managerId, @ModelAttribute @Valid ManagerVO manager) {
		
		service.update(manager.getManagerId(), manager.getTelephone());
		
		return "redirect:/manager/list";
	}
}
