package com.xlb.ims.app.services.resume.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xlb.ims.alipay.util.DateUtil;
import com.xlb.ims.app.dao.classify.JobIndustryMapper;
import com.xlb.ims.app.dao.classify.JobPositionMapper;
import com.xlb.ims.app.dao.headHunter.MemberEnterpriseMapper;
import com.xlb.ims.app.dao.orderbill.PurchaseRecordMapper;
import com.xlb.ims.app.dao.system.SysCityMapper;
import com.xlb.ims.app.dao.talent.TalentResumeMapper;
import com.xlb.ims.app.entity.classify.JobIndustry;
import com.xlb.ims.app.entity.classify.JobPosition;
import com.xlb.ims.app.entity.resume.Resume;
import com.xlb.ims.app.entity.system.SysCity;
import com.xlb.ims.app.entity.talent.Talent;
import com.xlb.ims.app.entity.talent.TalentEducationExperience;
import com.xlb.ims.app.entity.talent.TalentInfo;
import com.xlb.ims.app.entity.talent.TalentLanguageAbility;
import com.xlb.ims.app.entity.talent.TalentOccupationIndustry;
import com.xlb.ims.app.entity.talent.TalentOccupationIntention;
import com.xlb.ims.app.entity.talent.TalentOccupationPosition;
import com.xlb.ims.app.entity.talent.TalentOccupationWorkPlace;
import com.xlb.ims.app.entity.talent.TalentProjectExperience;
import com.xlb.ims.app.entity.talent.TalentSelfEvaluation;
import com.xlb.ims.app.entity.talent.TalentSpecialSkill;
import com.xlb.ims.app.entity.talent.TalentSupplement;
import com.xlb.ims.app.entity.talent.TalentTrainingExperience;
import com.xlb.ims.app.entity.talent.TalentWorkExperience;
import com.xlb.ims.app.entity.talent.TalentWorkExperienceCompany;
import com.xlb.ims.app.services.resume.TalentResumeService;
import com.xlb.ims.util.StringUtil;
import com.xlb.ims.util.UUIDGenerator;



@Service("talentResumeService")
@Transactional
public class TalentResumeServiceImpl implements TalentResumeService {

	
	@Autowired
	TalentResumeMapper talentResumeMapper;
	@Autowired
	PurchaseRecordMapper purchaseRecordMapper;
	@Autowired
	MemberEnterpriseMapper memberEnterpriseMapper;
	@Autowired
	JobPositionMapper jobPositionMapper;
	@Autowired
	SysCityMapper sysCityMapper;
	@Autowired
	JobIndustryMapper jobIndustryMapper;
	
	
	
	@Autowired  
    private SqlSessionTemplate sqlSessionTemplate; 
	
	@Override
	public void saveTalent(Talent t) {
		talentResumeMapper.saveTalent(t);
		
//		PurchaseRecord pr = new PurchaseRecord();
//		UUIDGenerator uuid = new UUIDGenerator();
//		pr.setId(uuid.generate().toString());
//		pr.setUmid(t.getId());
//		String mid = t.getFillMid();
//		pr.setBuyId(mid);
//		MemberEnterprise me = memberEnterpriseMapper.queryMemberEnterpriseByState(mid);
//		pr.setBuyEid(me.getEid());
//		pr.setAmount(new BigDecimal(0));
//		purchaseRecordMapper.savePurchaseRecord(pr);
	}

	@Override
	public void updateTalent(Talent t) {
		talentResumeMapper.updateTalent(t);
	}

	@Override
	public Talent getTalentById(String id) {
		return talentResumeMapper.getTalentById(id);
	}

	@Override
	public void saveTalentInfo(TalentInfo ti) {
		
		if(null == ti.getResumeNo() || "".equals(ti.getResumeNo())){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String code = "RE"+sdf.format(new Date())+StringUtil.getResumeCode(talentResumeMapper.queryResumeSequence());
		ti.setResumeNo(code);
		}
		talentResumeMapper.saveTalentInfo(ti);
		
		Talent t = new Talent();
		t.setId(ti.getTid());
		talentResumeMapper.updateTalent(t);
		
	}

	@Override
	public void editTalentInfo(TalentInfo ti) {
		talentResumeMapper.editTalentInfo(ti);
//		Talent t = talentResumeMapper.getTalentById(ti.getTid());
//		t.setPhoneNO(ti.getPhoneNO());
//		talentResumeMapper.updateTalent(t);
		Talent t = new Talent();
		t.setId(ti.getTid());
		talentResumeMapper.updateTalent(t);
	}

	@Override
	public TalentInfo getTalentInfoByTid(String tid) {
		return talentResumeMapper.getTalentInfoByTid(tid);
	}

	@Override
	public TalentOccupationIntention getTalentOccupationIntentionByTid(
			String tid) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tid", tid);
		TalentOccupationIntention toi = new TalentOccupationIntention();
		toi.setIndustry(talentResumeMapper.queryTalentOccuptionIndustryByTid(map)); 
		toi.setPosition(talentResumeMapper.queryTalentOccuptionPositionByTid(map)); 
		toi.setWorkingPlace(talentResumeMapper.queryTalentOccuptionWorkPlaceByTid(map));
		toi.setSalary(talentResumeMapper.getTalentInfoByTid(tid).getSalary());
		return toi;
	}

	@Override
	public void setTalentOccupationIntention(TalentOccupationIntention toi) {
		UUIDGenerator uuid = new UUIDGenerator();
		List<TalentOccupationIndustry> toi_arr = new ArrayList<TalentOccupationIndustry>();
		List<TalentOccupationPosition> top_arr = new ArrayList<TalentOccupationPosition>();
		List<TalentOccupationWorkPlace> tow_arr = new ArrayList<TalentOccupationWorkPlace>();
		String tid = toi.getTid();
		String resumeNo = toi.getResumeNo();
		String [] industry_arr = toi.getIndustry().split(",");
		for (String string : industry_arr) {
			TalentOccupationIndustry industry_to = new TalentOccupationIndustry();
			industry_to.setId(uuid.generate().toString());
			industry_to.setIndustry(string);
			JobIndustry ji =
					jobIndustryMapper.queryIndustryById(string);
			industry_to.setFid(ji.getParentNo());
			industry_to.setTid(tid);
			industry_to.setResumeNo(resumeNo);
			toi_arr.add(industry_to);
		}
		String [] position_arr = toi.getPosition().split(",");
		for (String string : position_arr) {
			TalentOccupationPosition position_to = new TalentOccupationPosition();
			position_to.setId(uuid.generate().toString());
			position_to.setPosition(string);
			JobPosition jp =
					jobPositionMapper.queryPositionByID(string);
			position_to.setFid(jp.getParentNo());
			position_to.setTid(tid);
			position_to.setResumeNo(resumeNo);
			top_arr.add(position_to);
		}
		String [] workPlace_arr = toi.getWorkingPlace().split(",");
		for (String string : workPlace_arr) {
			TalentOccupationWorkPlace workplace_to = new TalentOccupationWorkPlace();
			workplace_to.setId(uuid.generate().toString());
			workplace_to.setWorkPlace(string);
			SysCity sc =
				sysCityMapper.queryCityById(string);
			workplace_to.setFid(sc.getFid().toString());
			workplace_to.setTid(tid);
			workplace_to.setResumeNo(resumeNo);
			tow_arr.add(workplace_to);
		}
		
		
		talentResumeMapper.saveTalentOccuptionIndustry(toi_arr);
		talentResumeMapper.saveTalentOccuptionPosition(top_arr);
		talentResumeMapper.saveTalentOccuptionWorkPlace(tow_arr);
		
		TalentInfo ti = new TalentInfo();
		ti.setTid(tid);
		ti.setSalary(toi.getSalary());
		talentResumeMapper.editTalentInfo(ti);
		
		Talent t = new Talent();
		t.setId(toi.getTid());
		talentResumeMapper.updateTalent(t);
	}

	@Override
	public void editTalentOccupationIntention(TalentOccupationIntention toi) {
		String tid = toi.getTid();
		String resumeNo = toi.getResumeNo();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tid", tid);
		talentResumeMapper.deleteTalentOccuptionIndustryByTid(map);
		talentResumeMapper.deleteTalentOccuptionPositionByTid(map);
		talentResumeMapper.deleteTalentOccuptionWorkPlaceByTid(map);
		
		UUIDGenerator uuid = new UUIDGenerator();
		List<TalentOccupationIndustry> toi_arr = new ArrayList<TalentOccupationIndustry>();
		List<TalentOccupationPosition> top_arr = new ArrayList<TalentOccupationPosition>();
		List<TalentOccupationWorkPlace> tow_arr = new ArrayList<TalentOccupationWorkPlace>();
		
		String [] industry_arr = toi.getIndustry().split(",");
		for (String string : industry_arr) {
			TalentOccupationIndustry industry_to = new TalentOccupationIndustry();
			industry_to.setId(uuid.generate().toString());
			industry_to.setIndustry(string);
			JobIndustry ji =
					jobIndustryMapper.queryIndustryById(string);
			industry_to.setFid(ji.getParentNo());
			industry_to.setTid(tid);
			industry_to.setResumeNo(resumeNo);
			toi_arr.add(industry_to);
		}
		String [] position_arr = toi.getPosition().split(",");
		for (String string : position_arr) {
			TalentOccupationPosition position_to = new TalentOccupationPosition();
			position_to.setId(uuid.generate().toString());
			position_to.setPosition(string);
			JobPosition jp =
					jobPositionMapper.queryPositionByID(string);
			position_to.setFid(jp.getParentNo());
			position_to.setTid(tid);
			position_to.setResumeNo(resumeNo);
			top_arr.add(position_to);
		}
		String [] workPlace_arr = toi.getWorkingPlace().split(",");
		for (String string : workPlace_arr) {
			TalentOccupationWorkPlace workplace_to = new TalentOccupationWorkPlace();
			workplace_to.setId(uuid.generate().toString());
			workplace_to.setWorkPlace(string);
			SysCity sc =
					sysCityMapper.queryCityById(string);
			workplace_to.setFid(sc.getFid().toString());
			workplace_to.setTid(tid);
			workplace_to.setResumeNo(resumeNo);
			tow_arr.add(workplace_to);
		}
		
		
		talentResumeMapper.saveTalentOccuptionIndustry(toi_arr);
		talentResumeMapper.saveTalentOccuptionPosition(top_arr);
		talentResumeMapper.saveTalentOccuptionWorkPlace(tow_arr);
		
		TalentInfo ti = new TalentInfo();
		ti.setTid(tid);
		ti.setSalary(toi.getSalary());
		talentResumeMapper.editTalentInfo(ti);
		
		Talent t = new Talent();
		t.setId(toi.getTid());
		talentResumeMapper.updateTalent(t);
	}

	/**
	 * TODO 添加工作经历方法
	 * 添加时计算工作时间并存入基本信息中
	 * 工作经历公司表整合入工作经历表
	 * 将工作经历的企业名称、职位名称、工作职责、工作业绩存入综合信息中
	 * @Function: setTalentWorkExperience 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年4月13日      李彦军                          v1.0.3         create
	 * -----------------------------------------------------------------
	 * @param twe
	 */
	@Override
	public void setTalentWorkExperience(TalentWorkExperience twe) {
		UUIDGenerator uuid = new UUIDGenerator();
		twe.setId(uuid.generate().toString());
		twe.setType("0"); //简历类型（0中文 1英文）
		twe.setWorkDuty(twe.getWorkDuty().replace("\n", "<br>"));
		twe.setWorkPerformance(twe.getWorkPerformance().replace("\n", "<br>")); 
		if(null != twe.getResume()){
		twe.setResume(twe.getResume().replace("\n", "<br>"));
		}
		talentResumeMapper.setTalentWorkExperience(twe);
		
		//**********将工作经历的企业名称、职位名称、工作职责、工作业绩存入综合信息中***********
		List<String> list = new ArrayList<String>();
		String e_name = twe.getEnterpriseName();
		String p_name = twe.getPositionName();
		String duty = twe.getWorkDuty();
		String performance = twe.getWorkPerformance();
		list.add(e_name);
		list.add(p_name);
		list.add(duty);
		list.add(performance);
		markIntegration(twe.getTid(),list,"0");
		
		
		
		Talent t = new Talent();
		t.setId(twe.getTid());
		talentResumeMapper.updateTalent(t);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		if(sdf.format(twe.getEntryEndDate()).equals("1000.01.01")){
			twe.setEntryEndDate(new Date());
		}
		
		//查询最早工作经历时间并存入TalentInfo
		TalentWorkExperience min_date_twe =
		talentResumeMapper.getMinDateTalentWorkExperienceByTId(twe.getTid());
		TalentInfo ti =
				talentResumeMapper.getTalentInfoByTid(twe.getTid());
		ti.setStartWorkDate(min_date_twe.getEntryBeginDate());
		talentResumeMapper.editTalentInfo(ti);
		
	}

	
	/**
	 * TODO 将搜索条件整合存入TalentInfo talentMark字段
	 * @Function: markIntegration 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年5月12日      李彦军                          v1.0.0         create
	 * -----------------------------------------------------------------
	 * @param list 需整合的信息集合
	 * @param tid
	 * @param type  0.新增  1.修改OR删除
	 */
	private void markIntegration(String tid,List<String> list,String type){
		TalentInfo ti =
				talentResumeMapper.getTalentInfoByTid(tid);
		
		String mark = ""; 
		
		if("0".equals(type)){ //新增信息直接添加入Mark信息中
			mark = addMark(ti.getTalentMark(),list); //整合Mark信息
		}else if("1".equals(type)){ //删除旧的Mark信息 
			mark = removeMark(ti.getTalentMark(),list); //删除Mark信息
		}
				
		ti.setTalentMark(mark);
		talentResumeMapper.editTalentInfo(ti);
	}
	
	/**
	 * TODO 整合Mark信息
	 * @Function: addMark 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年5月12日      李彦军                          v1.0.0         create
	 * -----------------------------------------------------------------
	 * @param mark
	 * @param list
	 * @return
	 */
	private String addMark(String mark,List<String> list){
		if(null == mark){
			mark = "";
		}
		for (String string : list) {
			if(!"".equals(string) || null != string){
			string = string.replaceAll("\\(", "（");
			string = string.replaceAll("\\)", "）");
			string = string.replaceAll("\\\\", "/");
			mark += "___分隔符___" + string;
			}
		}
		return mark;
	}
	
	
	/**
	 * TODO 删除Mark信息
	 * @Function: removeMark 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年5月12日      李彦军                          v1.0.0         create
	 * -----------------------------------------------------------------
	 * @param mark
	 * @param list
	 * @return
	 */
	private String removeMark(String mark,List<String> list){
		if(null == mark){
			mark = "";
		}else{
			mark = mark.replaceAll("\\(", "（");
			mark = mark.replaceAll("\\)", "）");
			mark = mark.replaceAll("\\\\", "/");
		}
		for (String string : list) {
			if(null != string && !"".equals(string)){
				string = string.replaceAll("\\(", "（");
				string = string.replaceAll("\\)", "）");
				string = string.replaceAll("\\\\", "/");
				try {
					mark = mark.replaceFirst("___分隔符___" + string, "");
				} catch (Exception e) {
					System.out.println("异常字符串:"+string);
					e.printStackTrace();
				}
			}
		}
		return mark;
	}
	
	
	public static void main(String[] args) {
		String mark = "";
		List<String> list = new ArrayList<String>();
		list.add("___分隔符___"+
		"<br>一、采购模块"+
		"<br>1、负责供应商体系的建立、开发；"+
		"<br>2、负责供应商合同的审定；"+
		"<br>3、负责关键零部件商务谈判；"+
		"<br>4、负责组织零部件、机械、设备的采购及招标；"+
		"<br>5、供应商二方审核的组织和实施；"+
		"<br>6、新开发车型供应商开发进度的跟踪。"+
		"<br>二、研发模块："+
		"<br>1、车辆选型、新产品研发的策划和规划； "+
		"<br>2、公司新产品研发的研发组织和实施； "+
		"<br>3、负责集团研究院组建、项目管理的实施； "+
		"<br>4、新产品的试制和检验试验、公告的申报组织； "+
		"<br>5、新产品的试制、的组织和实施；"+ 
		"<br>6、新年能源车辆推荐目录的组织和实施。");
		list.add("AAAAABBBBB1");
		list.add("SSSSSSSSSSS2");
		for (String string : list) {
			string = string.replaceAll("\\(", "");
			string = string.replaceAll("\\)", "");
			System.out.println("aa= "+string);
			mark = mark.replaceFirst("___分隔符___" + string, "");
		}
		System.out.println("kkk =  "+mark);
	}
	
	
	/**
	 * TODO 修改工作经历方法
	 * 修改时计算原工作经历的工作天数与修改后工作经历的工作天数比较并计入基本信息
	 * 工作经历公司表整合入工作经历表
	 * @Function: editTalentWorkExperience 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年4月13日      李彦军                          v1.0.2         create
	 * -----------------------------------------------------------------
	 * @param twe
	 */
	@Override
	public void editTalentWorkExperience(TalentWorkExperience twe) {
		
		//**********将工作经历的企业名称、职位名称、工作职责、工作业绩存入综合信息中***********
		List<String> list = new ArrayList<String>();
		
		TalentWorkExperience old_twe =
				talentResumeMapper.getWorkExperienceById(twe.getId());
		String old_e_name = old_twe.getEnterpriseName();
		String old_p_name = old_twe.getPositionName();
		String old_duty = old_twe.getWorkDuty();
		String old_performance = old_twe.getWorkPerformance();
		list.add(old_e_name);
		list.add(old_p_name);
		list.add(old_duty);
		list.add(old_performance);
		markIntegration(twe.getTid(),list,"1");
		
		twe.setWorkDuty(twe.getWorkDuty().replace("\n", "<br>"));
		twe.setWorkPerformance(twe.getWorkPerformance().replace("\n", "<br>"));
		if(null != twe.getResume()){
			twe.setResume(twe.getResume().replace("\n", "<br>"));
		}
		talentResumeMapper.editTalentWorkExperience(twe);
		
		
		//**********将工作经历的企业名称、职位名称、工作职责、工作业绩存入综合信息中***********
		list.clear();
		String e_name = twe.getEnterpriseName();
		String p_name = twe.getPositionName();
		String duty = twe.getWorkDuty();
		String performance = twe.getWorkPerformance();
		list.add(e_name);
		list.add(p_name);
		list.add(duty);
		list.add(performance);
		markIntegration(twe.getTid(),list,"0");
		
		
		
		Talent t = new Talent();
		t.setId(twe.getTid());
		talentResumeMapper.updateTalent(t);
		
		//查询最早工作经历时间并存入TalentInfo
		TalentWorkExperience min_date_twe =
		talentResumeMapper.getMinDateTalentWorkExperienceByTId(twe.getTid());
		TalentInfo ti =
				talentResumeMapper.getTalentInfoByTid(twe.getTid());
		ti.setStartWorkDate(min_date_twe.getEntryBeginDate());
		talentResumeMapper.editTalentInfo(ti);
		
	}

	
	
	@Override
	public TalentWorkExperience getWorkExperienceById(String id) {
		return talentResumeMapper.getWorkExperienceById(id);
	}

	@Override
	public void deleteWorkExperience(String id) {
		
		List<String> list = new ArrayList<String>();
		TalentWorkExperience old_twe =
				talentResumeMapper.getWorkExperienceById(id);
		String old_e_name = old_twe.getEnterpriseName();
		String old_p_name = old_twe.getPositionName();
		String old_duty = old_twe.getWorkDuty();
		String old_performance = old_twe.getWorkPerformance();
		list.add(old_e_name);
		list.add(old_p_name);
		list.add(old_duty);
		list.add(old_performance);
		markIntegration(old_twe.getTid(),list,"1");
		
		talentResumeMapper.deleteWorkExperience(id);
		
	}

	@Override
	public TalentProjectExperience getProjectExperienceById(String id) {
		return talentResumeMapper.getProjectExperienceById(id);
	}

	/**
	 * TODO 项目经验添加
	 * 将 company、post、description、duty、performance 整合存入综合信息中
	 * @Function: setTalentProjectExperience 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年5月12日      李彦军                          v1.0.1         update
	 * -----------------------------------------------------------------
	 * @param tpe
	 */
	@Override
	public void setTalentProjectExperience(TalentProjectExperience tpe) {
		UUIDGenerator uuid = new UUIDGenerator();
		tpe.setId(uuid.generate().toString());
		tpe.setType("0"); //简历类型（0中文 1英文）
		tpe.setDuty(tpe.getDuty().replace("\n", "<br>"));
		tpe.setPerformance(tpe.getPerformance().replace("\n", "<br>"));
		talentResumeMapper.setTalentProjectExperience(tpe);
		
		//将 company、post、description、duty、performance 整合存入综合信息中
				List<String> list = new ArrayList<String>();
				list.add(tpe.getCompany());
				list.add(tpe.getPost());
				list.add(tpe.getDescription());
				list.add(tpe.getPerformance());
				list.add(tpe.getDuty());
				markIntegration(tpe.getTid(),list,"0");
		
		Talent t = new Talent();
		t.setId(tpe.getTid());
		talentResumeMapper.updateTalent(t);
	}

	@Override
	public void editProjectExperience(TalentProjectExperience tpe) {
		//将 company、post、description、duty、performance 整合存入综合信息中
				List<String> list = new ArrayList<String>();
				TalentProjectExperience old_tpe =
						talentResumeMapper.getProjectExperienceById(tpe.getId());
				list.add(old_tpe.getCompany());
				list.add(old_tpe.getPost());
				list.add(old_tpe.getDescription());
				list.add(old_tpe.getPerformance());
				list.add(old_tpe.getDuty());
				markIntegration(tpe.getTid(),list,"1");
		
		
		tpe.setDuty(tpe.getDuty().replace("\n", "<br>"));
		tpe.setPerformance(tpe.getPerformance().replace("\n", "<br>"));
		talentResumeMapper.editProjectExperience(tpe);
		
		
		//将 company、post、description、duty、performance 整合存入综合信息中
		list.clear();
		list.add(tpe.getCompany());
		list.add(tpe.getPost());
		list.add(tpe.getDescription());
		list.add(tpe.getPerformance());
		list.add(tpe.getDuty());
		markIntegration(tpe.getTid(),list,"0");
				
		Talent t = new Talent();
		t.setId(tpe.getTid());
		talentResumeMapper.updateTalent(t);
		
	}

	@Override
	public void deleteProjectExperience(String id) {
		//将 company、post、description、duty、performance 整合存入综合信息中
		List<String> list = new ArrayList<String>();
		TalentProjectExperience old_tpe =
				talentResumeMapper.getProjectExperienceById(id);
		list.add(old_tpe.getCompany());
		list.add(old_tpe.getPost());
		list.add(old_tpe.getDescription());
		list.add(old_tpe.getPerformance());
		list.add(old_tpe.getDuty());
		markIntegration(old_tpe.getTid(),list,"1");
		
		talentResumeMapper.deleteProjectExperience(id);
		
	}

	/**
	 * TODO 存入一条教育经历
	 * 检查该教育经历的学历是否为最高学历（若是最高学历则存入基本信息）
	 * @Function: setTalentEducationExperience 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年4月13日      李彦军                          v1.0.0         create
	 * -----------------------------------------------------------------
	 * @param tee
	 */
	@Override
	public void setTalentEducationExperience(TalentEducationExperience tee) {
		UUIDGenerator uuid = new UUIDGenerator();
		tee.setId(uuid.generate().toString());
		tee.setType("0"); //简历类型（0中文 1英文）
		talentResumeMapper.setTalentEducationExperience(tee);
		
		Talent t = new Talent();
		t.setId(tee.getTid());
		talentResumeMapper.updateTalent(t);
		
		TalentInfo ti =
				talentResumeMapper.getTalentInfoByTid(tee.getTid());
		if("".equals(ti.getEducation()) || null == ti.getEducation()){
			ti.setEducation(tee.getDegrees());
			talentResumeMapper.editTalentInfo(ti);
		}else{
			String degrees = tee.getDegrees();
			String old_degrees = ti.getEducation();
			if(Integer.parseInt(degrees) - Integer.parseInt(old_degrees) > 0){ //新学历高于老学历
				ti.setEducation(degrees);
				talentResumeMapper.editTalentInfo(ti);
			}
			
		}
		
		
	}

	@Override
	public TalentEducationExperience getEducationExperienceById(String id) {
		return talentResumeMapper.getEducationExperienceById(id);
	}

	/**
	 * TODO 修改教育经历
	 * 检查该教育经历的学历是否为最高学历（若是最高学历则存入基本信息）
	 * @Function: editEducationExperience 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年4月13日      李彦军                          v1.0.0         create
	 * -----------------------------------------------------------------
	 * @param tee
	 */
	@Override
	public void editEducationExperience(TalentEducationExperience tee) {
		talentResumeMapper.editEducationExperience(tee);
		
		Talent t = new Talent();
		t.setId(tee.getTid());
		talentResumeMapper.updateTalent(t);
		
		TalentInfo ti =
				talentResumeMapper.getTalentInfoByTid(tee.getTid());
		if("".equals(ti.getEducation()) || null == ti.getEducation()){
			ti.setEducation(tee.getDegrees());
			talentResumeMapper.editTalentInfo(ti);
		}else{
			String degrees = tee.getDegrees();
			String old_degrees = ti.getEducation();
			if(Integer.parseInt(degrees) - Integer.parseInt(old_degrees) > 0){ //新学历高于老学历
				ti.setEducation(degrees);
				talentResumeMapper.editTalentInfo(ti);
			}
			
		}
	}

	@Override
	public void deleteEducationExperience(String id) {
		talentResumeMapper.deleteEducationExperience(id);
		
	}

	@Override
	public void setTalentTrainingExperience(TalentTrainingExperience tte) {
		UUIDGenerator uuid = new UUIDGenerator();
		tte.setId(uuid.generate().toString());
		tte.setType("0"); //简历类型（0中文 1英文）
		talentResumeMapper.setTalentTrainingExperience(tte);
		
		Talent t = new Talent();
		t.setId(tte.getTid());
		talentResumeMapper.updateTalent(t);
	}

	@Override
	public void editTalentTrainingExperience(TalentTrainingExperience tte) {
		talentResumeMapper.editTalentTrainingExperience(tte);
		
		Talent t = new Talent();
		t.setId(tte.getTid());
		talentResumeMapper.updateTalent(t);
		
	}

	@Override
	public TalentTrainingExperience getTrainingExperienceById(String id) {
		return talentResumeMapper.getTrainingExperienceById(id);
	}

	@Override
	public void deleteTrainingExperience(String id) {
		talentResumeMapper.deleteTrainingExperience(id);
		
	}

	@Override
	public TalentSpecialSkill setTalentSpecialSkill(TalentSpecialSkill tss) {
		UUIDGenerator uuid = new UUIDGenerator();
		tss.setId(uuid.generate().toString());
		tss.setType("0"); //简历类型（0中文 1英文）
		talentResumeMapper.setTalentSpecialSkill(tss);
		
		
		Talent t = new Talent();
		t.setId(tss.getTid());
		talentResumeMapper.updateTalent(t);
		
		return tss;
	}

	@Override
	public TalentSpecialSkill getSpecialSkillById(String id) {
		return talentResumeMapper.getSpecialSkillById(id);
	}

	@Override
	public void editTalentSpecialSkill(TalentSpecialSkill tss) {
		talentResumeMapper.editTalentSpecialSkill(tss);
		
		Talent t = new Talent();
		t.setId(tss.getTid());
		talentResumeMapper.updateTalent(t);
	}

	@Override
	public void deleteTalentSpecialSkill(String id) {
		talentResumeMapper.deleteTalentSpecialSkill(id);
		
	}

	@Override
	public TalentLanguageAbility setTalentLanguageAbilityByLA(TalentLanguageAbility tla) {
		UUIDGenerator uuid = new UUIDGenerator();
		tla.setId(uuid.generate().toString());
		tla.setType("0"); //简历类型（0中文 1英文）
		talentResumeMapper.setTalentLanguageAbilityByLA(tla);
		
		Talent t = new Talent();
		t.setId(tla.getTid());
		talentResumeMapper.updateTalent(t);
		
		return tla;
	}

	@Override
	public TalentLanguageAbility getLanguageAbilityById(String id) {
		return talentResumeMapper.getLanguageAbilityById(id);
	}

	@Override
	public void editTalentLanguageAbility(TalentLanguageAbility tla) {
		talentResumeMapper.editTalentLanguageAbility(tla);
		
		Talent t = new Talent();
		t.setId(tla.getTid());
		talentResumeMapper.updateTalent(t);
		
	}

	@Override
	public void deleteTalentLanguageAbility(String id) {
		talentResumeMapper.deleteTalentLanguageAbility(id);
		
	}

	@Override
	public TalentInfo saveTalentSelfEvaluation(TalentSelfEvaluation tse) {
		TalentInfo ti = new TalentInfo();
		ti.setEvaluation(tse.getEvaluation().replace("\n", "<br>"));
		ti.setTid(tse.getTid());
		talentResumeMapper.editTalentInfo(ti);
		
		Talent t = new Talent();
		t.setId(tse.getTid());
		talentResumeMapper.updateTalent(t);
		
		return ti;
	}

	@Override
	public TalentSelfEvaluation getSelfEvaluationById(String id) {
		return talentResumeMapper.getSelfEvaluationById(id);
	}

	@Override
	public void editTalentSelfEvaluation(TalentSelfEvaluation tse) {
		TalentInfo ti = new TalentInfo();
		ti.setEvaluation(tse.getEvaluation().replace("\n", "<br>"));
		ti.setTid(tse.getTid());
		talentResumeMapper.editTalentInfo(ti);
		
		Talent t = new Talent();
		t.setId(tse.getTid());
		talentResumeMapper.updateTalent(t);
		
	}

	@Override
	public TalentSupplement setTalentSupplement(TalentSupplement ts) {
		UUIDGenerator uuid = new UUIDGenerator();
		ts.setId(uuid.generate().toString());
		ts.setType("0"); //简历类型（0中文 1英文）
		ts.setContent(ts.getContent().replace("\n", "<br>"));
		talentResumeMapper.setTalentSupplement(ts);
		
		Talent t = new Talent();
		t.setId(ts.getTid());
		talentResumeMapper.updateTalent(t);
		return ts;
	}

	@Override
	public void editTalentSupplement(TalentSupplement ts) {
		TalentInfo ti = new TalentInfo();
		ti.setSupplementContent(ts.getContent().replace("\n", "<br>"));
		ti.setTid(ts.getTid());
		talentResumeMapper.editTalentInfo(ti);
		
		Talent t = new Talent();
		t.setId(ts.getTid());
		talentResumeMapper.updateTalent(t);
	}


	@Override
	public TalentSupplement getSupplementByTid(String id) {
		return talentResumeMapper.getSupplementByTid(id);
	}
	
	@Override
	public long getResumeListNum(Map<String, Object> map) {
		return talentResumeMapper.getResumeListNum(map);
	}

	@Override
	public List<Resume> getResumeList(Map<String, Object> map) {
		return talentResumeMapper.getResumeList(map);
	}

	@Override
	public long queryPersonalResourceResumeNum(Map<String, Object> map) {
		return talentResumeMapper.queryPersonalResourceResumeNum(map);
	}

	@Override
	public List<Resume> queryPersonalResourceResume(Map<String, Object> map) {
		List<Resume> resumeList = talentResumeMapper.queryPersonalResourceResume(map);
		for (Resume resume : resumeList) {
			if(null != resume.getTalentInfo().getPosition()){
			resume.getTalentInfo().setPositionFullName(jobPositionMapper.queryJobPositionByArr(resume.getTalentInfo().getPosition().split(",")));
			}
		}
		return resumeList;
	}


	@Override
	public long queryEnterpriseResourceResumeNum(Map<String, Object> map) {
		return talentResumeMapper.queryEnterpriseResourceResumeNum(map);
	}

	@Override
	public List<Resume> queryEnterpriseResourceResume(Map<String, Object> map) {
		List<Resume> resumeList = talentResumeMapper.queryEnterpriseResourceResume(map);
		for (Resume resume : resumeList) {
			if(null != resume.getTalentInfo().getPosition()){
			resume.getTalentInfo().setPositionFullName(jobPositionMapper.queryJobPositionByArr(resume.getTalentInfo().getPosition().split(",")));
			}
		}
		return resumeList;
	}

	@Override
	public long queryZNResourceResumeNum(Map<String, Object> map) {
		return talentResumeMapper.queryZNResourceResumeNum(map);
	}

	@Override
	public List<Resume> queryZNResourceResume(Map<String, Object> map) {
		List<Resume> resumeList = talentResumeMapper.queryZNResourceResume(map);
		for (Resume resume : resumeList) {
			if(null != resume.getTalentInfo().getPosition()){
			resume.getTalentInfo().setPositionFullName(jobPositionMapper.queryJobPositionByArr(resume.getTalentInfo().getPosition().split(",")));
			}
		}
		return resumeList;
	}

	/**
	 * TODO 根据ID获取简历内容
	 * 修改工作年限显示到年
	 * @Function: getResume 
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2017年5月23日      李彦军                          v1.0.1         update
	 * -----------------------------------------------------------------
	 * @param id
	 * @return
	 */
	@Override
	public Resume getResume(String id) {
		Resume resume = new Resume();
		
		Talent t = talentResumeMapper.getTalentById(id);
		resume.setTalent(t);
		TalentInfo ti = talentResumeMapper.getTalentInfoByTid(id);
		if(null != ti){
		ti.setLocationFullName(giveCityName(ti.getLocation())) ;
		ti.setPositionFullName(givePositionName(ti.getPosition()));
		ti.setInIndustryFullName(giveIndustryName(ti.getInIndustry()));
		}
		resume.setTalentInfo(ti);
		TalentOccupationIntention toi = new TalentOccupationIntention();
		if(null != ti){
		toi.setSalary(ti.getSalary());
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tid", id);
		String industry_id =
		talentResumeMapper.queryTalentOccuptionIndustryByTid(map);
		String position_id =
		talentResumeMapper.queryTalentOccuptionPositionByTid(map);
		String workPlace_id =
		talentResumeMapper.queryTalentOccuptionWorkPlaceByTid(map);
		
		
		if(null != toi){
		toi.setIndustryName(giveIndustryName(industry_id));
		toi.setPositionName(givePositionName(position_id));
		toi.setWorkingPlaceName(giveCityName(workPlace_id));
		}
		resume.setTalentOccupationIntention(toi);
		List<TalentWorkExperience> twe = talentResumeMapper.getTalentWorkExperienceByTId(id);
		resume.setTalentWorkExperienceList(isList(twe));
//		resume.setTalentWorkExperienceList((twe.size()>0)?twe:null);
		resume.setTalentLanguageAbilityList(isList(talentResumeMapper.getTalentLanguageAbilityByTid(id)));
		resume.setTalentEducationExperienceList(isList(talentResumeMapper.getTalentEducationExperienceByTid(id)));
		resume.setTalentTrainingExperiencesList(isList(talentResumeMapper.getTalentTrainingExperienceByTid(id)));
		resume.setTalentSpecialSkillList(isList(talentResumeMapper.getTalentSpecialSkillByTid(id)));
		resume.setTalentProjectExperienceList(isList(talentResumeMapper.getTalentProjectExperienceByTid(id)));
		
		 if(null != ti && null != ti.getStartWorkDate()){
			 long time =  DateUtil.getDaysBetweenTwoDate(new Date(),ti.getStartWorkDate());
			 long year = time / 365;
			// long month = time % 365 / 30;
			 resume.setWorkDate(year + "年");
		 }else{
			 resume.setWorkDate("---");
		 }
		 
		return resume;
	}

	
	
	private String giveCityName(String cid){
		String cityName = " ";
		if(null != cid){
			cityName = sysCityMapper.queryCityNameByArr(cid.split(","));
		}
		return cityName;
	}
	
	private String giveIndustryName(String industry){
		String industryName = "";
		if(null != industry){
		String [] industryArr = industry.split(",");
		industryName = jobIndustryMapper.queryJobIndustyByArr(industryArr);
		}
		return industryName;
	}
	
	private String givePositionName(String position){
		String positionName = "";
		if(null != position){
			String [] positionArr = position.split(",");
			positionName = jobPositionMapper.queryJobPositionByArr(positionArr);
		}
		return positionName;
		
	}

	@Override
	public Integer queryResumeSequence() {
		return talentResumeMapper.queryResumeSequence();
	}

	@Override
	public List<Resume> querySimilarResume(Map<String, Object> map) {
		return talentResumeMapper.querySimilarResume(map);
	}


	@Override
	public void deleteTalentOccuptionIndustryByTid(Map<String, Object> map) {
		talentResumeMapper.deleteTalentOccuptionIndustryByTid(map);
		
	}


	@Override
	public void deleteTalentOccuptionPositionByTid(Map<String, Object> map) {
		talentResumeMapper.deleteTalentOccuptionPositionByTid(map);
		
	}


	@Override
	public void deleteTalentOccuptionWorkPlaceByTid(Map<String, Object> map) {
		talentResumeMapper.deleteTalentOccuptionWorkPlaceByTid(map);
		
	}

	@Override
	public void markImport() {
		BeanFactory factory = new ClassPathXmlApplicationContext(new String[] {"classpath:config/spring/applicationContext.xml"}); 
		TalentResumeMapper  talentResumeMapper = (TalentResumeMapper) factory.getBean("talentResumeMapper"); 
		List<TalentInfo> ti =
				talentResumeMapper.queryAllTalentInfo();
		List<String> list = new ArrayList<String>();
		int n = 0;
		for (TalentInfo talentInfo : ti) {
			System.out.println(++n);
			list.clear();
			String tid = talentInfo.getTid();
			List<TalentWorkExperience> twe_list =
					talentResumeMapper.getTalentWorkExperienceByTId(tid);
			for (TalentWorkExperience talentWorkExperience : twe_list) {
				list.add(talentWorkExperience.getEnterpriseName());
				list.add(talentWorkExperience.getPositionName());
				list.add(talentWorkExperience.getWorkDuty());
				list.add(talentWorkExperience.getWorkPerformance());
			}
			List<TalentProjectExperience> tpe_list =
					talentResumeMapper.getTalentProjectExperienceByTid(tid);
			for (TalentProjectExperience talentProjectExperience : tpe_list) {
				list.add(talentProjectExperience.getCompany());
				list.add(talentProjectExperience.getPost());
				list.add(talentProjectExperience.getDescription());
				list.add(talentProjectExperience.getPerformance());
				list.add(talentProjectExperience.getDuty());
			}
			
			 String mark =  addMark("",list);
			 talentInfo.setTalentMark(mark);
			talentResumeMapper.editTalentInfoTalentMark(talentInfo);
		}
	}

	@Override
	public void talentOccuptionSeparate() {
		BeanFactory factory = new ClassPathXmlApplicationContext(new String[] {"classpath:config/spring/applicationContext.xml"}); 
		TalentResumeMapper  talentResumeMapper = (TalentResumeMapper) factory.getBean("talentResumeMapper"); 
		List<TalentOccupationIntention> toi_list =
				talentResumeMapper.queryAllTalentOccuptionIntention();
		TalentOccupationIndustry toi = null;
		TalentOccupationPosition top = null;
		TalentOccupationWorkPlace tow = null;
		UUIDGenerator uuid = new UUIDGenerator();
		int n = 0;
		for (TalentOccupationIntention talentOccupationIntention : toi_list) {
			System.out.println(++n);
			String id = talentOccupationIntention.getId();
			String tid =  talentOccupationIntention.getTid();
			String resumeNo = talentOccupationIntention.getResumeNo();
			String industry = talentOccupationIntention.getIndustry();
			String position = talentOccupationIntention.getPosition();
			String workPlace = talentOccupationIntention.getWorkingPlace();
			String salary = talentOccupationIntention.getSalary();
			TalentInfo ti =
					talentResumeMapper.getTalentInfoByTid(tid);
			if(null != ti){
			
			if(null == salary || "".equals(salary)){
				salary = "0";
			}
			ti.setSalary(salary);
			talentResumeMapper.editTalentInfo(ti);
			if(null != industry){
			String [] arr =  industry.split(",");
			List<TalentOccupationIndustry> list = new ArrayList<TalentOccupationIndustry>();
				for (String string : arr) {
					JobIndustry ji =
							jobIndustryMapper.queryIndustryById(string);
					if(null != ji){
					toi = new TalentOccupationIndustry();
					toi.setFid(ji.getParentNo());
					toi.setId(uuid.generate().toString());
					toi.setIndustry(string);
					toi.setTid(tid);
					toi.setResumeNo(resumeNo);
					list.add(toi);
					}
				}
				if(list.size() > 0){
				talentResumeMapper.saveTalentOccuptionIndustry(list);
				}
			}
			if(null != position){
			String [] arr = position.split(",");
			List<TalentOccupationPosition> list = new ArrayList<TalentOccupationPosition>();
				for (String string : arr) {
					JobPosition jp =
							jobPositionMapper.queryPositionByID(string);
					if(null != jp){
					top = new TalentOccupationPosition();
					top.setId(uuid.generate().toString());
					top.setPosition(string);
					top.setTid(tid);
					top.setResumeNo(resumeNo);
					top.setFid(jp.getParentNo());
					list.add(top);
					}
				}
				if(list.size() > 0){
				talentResumeMapper.saveTalentOccuptionPosition(list);
				}
			}
			
			if(null != workPlace){
			String [] arr = workPlace.split(",");
			List<TalentOccupationWorkPlace> list = new ArrayList<TalentOccupationWorkPlace>();
				for (String string : arr) {
					SysCity sc =
							sysCityMapper.queryCityById(string);
					if(null != sc){
					tow = new TalentOccupationWorkPlace();
					tow.setId(uuid.generate().toString());
					tow.setWorkPlace(string);
					tow.setTid(tid);
					tow.setResumeNo(resumeNo);
					tow.setFid(sc.getFid().toString());
					list.add(tow);
					}
				}
				if(list.size() > 0){
				talentResumeMapper.saveTalentOccuptionWorkPlace(list);
				}
			}
			
			talentResumeMapper.editTalentOccuptionIntentionUpdateType(id);
			
		}
		}
	}

	@Override
	public long queryTalentInfoUpdateType() {
		return talentResumeMapper.queryTalentInfoUpdateType();
	}

	@Override
	public long queryAllTalentOccuptionIntentionUpdateTypeNum() {
		return talentResumeMapper.queryAllTalentOccuptionIntentionUpdateTypeNum();
	}

	@Override
	public void talentWorkExperienceMerge() {
		
		BeanFactory factory = new ClassPathXmlApplicationContext(new String[] {"classpath:config/spring/applicationContext.xml"}); 
		TalentResumeMapper  talentResumeMapper = (TalentResumeMapper) factory.getBean("talentResumeMapper"); 
		
		List<TalentWorkExperience> twe_list =
				talentResumeMapper.queryAllTalentWorkExperience();
		int n = 0;
		for (TalentWorkExperience talentWorkExperience : twe_list) {
			
			System.out.println(++n);
			TalentWorkExperienceCompany twec =
					talentResumeMapper.queryTalentWorkExperienceCompanyByweid(talentWorkExperience.getId());
			if(null != twec){
				talentWorkExperience.setNature(twec.getNature());
				talentWorkExperience.setScale(twec.getScale());
				talentWorkExperience.setSector(twec.getSector());
				talentWorkExperience.setSuperior(twec.getSuperior());
				talentWorkExperience.setResume(twec.getResume());
			}
			talentResumeMapper.talentWorkExperienceMerge(talentWorkExperience);
		}
		
		
	}

	@Override
	public long queryAllTalentWorkExperienceByUpdateType() {
		return talentResumeMapper.queryAllTalentWorkExperienceByUpdateType();
	}

	@Override
	public List<TalentEducationExperience> getTalentEducationExperienceByTid(
			String tid) {
		return talentResumeMapper.getTalentEducationExperienceByTid(tid);
	}

	@Override
	public List<TalentWorkExperience> getTalentWorkExperienceByTId(String id) {
		return talentResumeMapper.getTalentWorkExperienceByTId(id);
	}

	@Override
	public List<TalentWorkExperience> queryTalentWorkExperienceByTId(String id) {
		return talentResumeMapper.queryTalentWorkExperienceByTId(id);
	}
	
	/**
	 * 判断集合数据是否为空，如果为空，就设置为null
	 * @param <E>
	 */
	public <E> List<E> isList(List<E> list){
		return (list.size()>0)?list:null;
	}

}
