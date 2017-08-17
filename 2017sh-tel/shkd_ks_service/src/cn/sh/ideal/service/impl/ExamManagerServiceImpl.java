package cn.sh.ideal.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.sh.ideal.dao.ItExampaperDao;
import cn.sh.ideal.dao.ItExampaperQuestionsDao;
import cn.sh.ideal.dao.ItQuestionsAreaDao;
import cn.sh.ideal.dao.ItQuestionsDao;
import cn.sh.ideal.dao.ItSysCodeDao;
import cn.sh.ideal.model.ExamManualScore;
import cn.sh.ideal.model.TQuestionsArea;
import cn.sh.ideal.model.tExampaper;
import cn.sh.ideal.model.tExampaperQuestions;
import cn.sh.ideal.model.tQuestions;
import cn.sh.ideal.model.tSysCode;
import cn.sh.ideal.model.vSysuser;
import cn.sh.ideal.service.ExamManagerService;
import cn.sh.ideal.util.Constans;
import cn.sh.ideal.util.DateUtil;
import cn.sh.ideal.util.FileUtil;
import cn.sh.ideal.util.PoiExcelUtil;
@Service
public class ExamManagerServiceImpl implements ExamManagerService {
	@Resource
	private ItExampaperDao  paperDao;
	@Resource
	private ItQuestionsDao questionsDao;
	@Resource
	private ItSysCodeDao sysCodeDao;
	@Resource
	private ItExampaperDao exampaperDao;
	@Resource
	private ItExampaperQuestionsDao exampaperQuestionsDao;
	@Autowired
	private ItQuestionsAreaDao questionsAreaMapper; 

	@Override
	public List<ExamManualScore> queryExampapersList(tExampaper exampaper) {
		
		return paperDao.queryExampapersList(exampaper);
	}

	@Override
	public int queryExampapersCount(tExampaper exampaper) {
		
		return paperDao.queryExampapersCount(exampaper);
	}

	@Override
	public tExampaper getExampaperById(Integer pkAutoId) {
		return paperDao.selectByPrimaryKey(pkAutoId);
	}

	@Override
	public List<tQuestions> getQuesByExampaperId(Integer pkAutoId) {
		
		return questionsDao.getQuesByExampaperId(pkAutoId);
	}

	@Override
	public Map<String, Object> checkExamQuestion(List<List<Object>> excelList,
			Map<Object, Object> paramMap, List<List<Object>> titlelList) {
		int rowNum = 3; // 从第3行开始读取数据
		String lineErrorMsg = ""; // 行错误信息
		Integer quesTionType = 0;
		long errorColumnCnt = 0; // 列错误数
		boolean lineError = false; // 行错误标识
		StringBuffer errorRowStr = new StringBuffer(); // 行列错误信息
		Map<String, Object> map = new HashMap<String, Object>();// 返回值，用来存入错误个数及错误信息
		List<List<Object>> errorList = new ArrayList<List<Object>>();// 错误数据集合
		List<List<Object>> correctList = new ArrayList<List<Object>>();// 正确数据集合
		List<String> errormslist = new ArrayList<String>();
		List<Map<String, Integer>> errorRegionList = new ArrayList<Map<String, Integer>>();// 格式错误的单元格（正确数据导入）
		List<Map<String, Integer>> regionList = new ArrayList<Map<String, Integer>>();// 格式错误的单元格（正确数据不导入）
		List<Map<String, Integer>> errorColorList = new ArrayList<Map<String, Integer>>();// 与数据库客户号码重复（正确数据导入）
		try {

			Map<Object, Object> mapPutkeyValueMap = new HashMap<Object, Object>();
			if (excelList.isEmpty()) {
				errorColumnCnt += 1;
				lineErrorMsg = "第3行以后(包括第3行)没有数据源信息数据！";
				map.put("errorCnt", errorColumnCnt);
				map.put("errorRowStr", errorRowStr);
				errormslist.add(lineErrorMsg);
			} else {
				// 获取excel列的数目
				int cellNum = excelList.get(0).size();
				if (cellNum < 8) {
					errorColumnCnt += 1;
					lineErrorMsg = "请检查excel列数与模板列数是否一致！";
					errormslist.add(lineErrorMsg);
				} else {
					List<Object> xtitleList = titlelList.get(0);
					String titlquestype = (String) xtitleList.get(0);// 题目类型
					String titlquesScore = (String) xtitleList.get(1);// 题目分数
					String titlquesContent = (String) xtitleList.get(2);// 题目内容
					String titlquesPick = (String) xtitleList.get(3);// 题目可选项
					String titlquesAnswer = (String) xtitleList.get(4);// 题目答案
					String titlquesCorrectAnswer = (String) xtitleList.get(5);// 改错信息答案
					String titlfkCodetypeId = (String) xtitleList.get(6);// 所属业务分类
					String titlquesNandu = (String) xtitleList.get(7);// 题目难度

					if (!"题型".equals(titlquestype)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"默认分数".equals(titlquesScore)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"题目内容".equals(titlquesContent)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"可选项".equals(titlquesPick)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"参考答案".equals(titlquesAnswer)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"改错信息答案".equals(titlquesCorrectAnswer)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"业务分类".equals(titlfkCodetypeId)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					} else if (!"题目难度".equals(titlquesNandu)) {
						lineErrorMsg = "请检查excel列名称是否与模板一致";
						errormslist.add(lineErrorMsg);
					}
					if ("".equals(lineErrorMsg)) {

						int rowSize = excelList.size();
						for (int i = 0; i < rowSize; i++) {
							lineError = false;
							List<Object> rowObjList = excelList.get(i); // 行数据
							int errorRowNum = errorList.size() + 3;// 出错的行数
							rowNum = rowNum + 1;// 读取的行数
							String questype = (String) rowObjList.get(0);// 题目类型
							String quesScore = (String) rowObjList.get(1);// 题目分数
							String quesContent = (String) rowObjList.get(2);// 题目内容
							String quesPick = (String) rowObjList.get(3);// 题目可选项
							String quesAnswer = (String) rowObjList.get(4);// 题目答案
							String quesCorrectAnswer = (String) rowObjList.get(5);// 改错信息答案
							String fkCodetypeId = (String) rowObjList.get(6);// 所属业务分类
							String quesNandu = (String) rowObjList.get(7);// 题目难度
							if (quesNandu == null || "".equals(quesNandu)) {
								quesNandu = "2";// 2:表示难度为“中”
							} else if ("难".equals(quesNandu)) {
								quesNandu = "1";// 1:表示难度为“中”
							} else if ("中".equals(quesNandu)) {
								quesNandu = "2";// 2:表示难度为“中”
							} else if ("易".equals(quesNandu)) {
								quesNandu = "3";// 3:表示难度为“易”
							}
							rowObjList.set(7, quesNandu);
							// 题目内容判断是否为空
							if (!"".equals(quesContent.trim())&&null==quesContent.trim()) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum)
										+ "条信息导入失败,失败原因:试题内容为空";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 0);
								addElement(regionList, rowNum, 0);
							}
							// 判断题目难度是否正确
							if (!quesNandu.equals("1")
									&& !quesNandu.equals("2")
									&& !quesNandu.equals("3")) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 3)
										+ "条信息导入失败,失败原因:题目难度信息不正确";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 1);
								addElement(regionList, rowNum, 1);
							}
							// 判断业务分类是否正确
							if (fkCodetypeId == null) {
								lineError = true;
								lineErrorMsg = "第" + (rowNum - 3)
										+ "条信息导入失败,失败原因:业务分类名称有误，系统中无此业务分类";
								errorColumnCnt += 1;
								addElement(errorRegionList, errorRowNum, 2);
								addElement(regionList, rowNum, 2);
							} else {
								tSysCode sysCode = sysCodeDao
										.selectByAutoId(fkCodetypeId);// 是否有该名称的业务分类
								if (sysCode == null) {
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因:业务分类名称有误，系统中无此业务分类";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 2);
									addElement(regionList, rowNum, 2);

								} else {// 不为空，刚获得它的主键
									fkCodetypeId = sysCode.getPkAutoId()
											.toString();
									rowObjList.set(6, fkCodetypeId);
								}
							}
							if ("判断题".equals(questype)) {
								quesTionType = 1;
								if (quesAnswer != null) {
									// 1，判断题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因:判断题答案不能为空";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 3);
										addElement(regionList, rowNum, 3);

									}
									// 2，判断题答案必须输入'Y'或者'N'
									if (!"Y".equals(quesAnswer.toUpperCase())
											&& !"N".equals(quesAnswer
													.toUpperCase())) {
										lineError = true;
										lineErrorMsg = "第"
												+ (rowNum - 3)
												+ "条信息导入失败,失败原因:判断题答案必须输入\'Y\'或者\'N\'.";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 3);
										addElement(regionList, rowNum, 3);
									}
								}

							} else if ("单选题".equals(questype)) {
								quesTionType = 3;
								// 单选题可选项不能为空！
								if (quesPick == null
										|| "".equals(quesPick.trim())) {
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因：单选题可选项不能为空";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 4);
									addElement(regionList, rowNum, 4);
								}

								// add by Neo,20110228 限制单择题选择项必须在2-4之间
								if (quesPick != null&& (quesPick.trim().split(";").length < 2) && (quesPick.trim().split(";").length > 4)) {
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因：单选题可选项数量限制为2-4个";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 4);
									addElement(regionList, rowNum, 4);
								}
								if (quesAnswer != null) {
									// 1，单选题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：单选题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 4);
										addElement(regionList, rowNum, 4);
									}
									if(quesPick!=null&&!quesPick.equals("")){
										quesPick=ExamManagerServiceImpl.titleNum(quesPick.split(";"));
									}
									// 2，单选题答案必须输入字母
									quesAnswer = quesAnswer.toUpperCase();
									if (!quesAnswer.matches("^[a-zA-Z]*")) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：单选题答案必须输入字母!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 4);
										addElement(regionList, rowNum, 4);
									}
									if (quesAnswer.toUpperCase().length() > 1) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：单选题答案必须只能有一项!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 4);
										addElement(regionList, rowNum, 4);
									}
								}
								if(quesAnswer!=null&&quesPick!=null){
									int pickLenth=quesPick.trim().split(";").length;
									int ansertLenth=quesAnswer.length();
									if(ansertLenth>pickLenth){
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：可选项个数和答案个数不匹配";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 5);
										addElement(regionList, rowNum, 5);
									}
								}

							} else if ("多选题".equals(questype)) {
								quesTionType = 4;
								// 多选题可选项不能为空！
								if (quesPick == null
										|| "".equals(quesPick.trim())) {
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因：多选题不能为空";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 5);
									addElement(regionList, rowNum, 5);
								}
								if (quesPick != null&& (quesPick.trim().split(";").length < 2) && (quesPick.trim().split(";").length > 10)) {
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因：多选题可选项数量限制为2-9个!";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 5);
									addElement(regionList, rowNum, 5);
								}
								if(quesPick!=null&&!quesPick.equals("")){
									quesPick=ExamManagerServiceImpl.titleNum(quesPick.split(";"));
								}
								if (quesAnswer != null) {
									// 1，多选题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：多选题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 5);
										addElement(regionList, rowNum, 5);
									}
									// 2，多选题答案必须输入字母
									quesAnswer = quesAnswer.toUpperCase();
									if (!quesAnswer.matches("^[a-zA-Z]*")) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：多选题答案必须输入字母!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 5);
										addElement(regionList, rowNum, 5);
									}
								}
								if(quesAnswer!=null&&quesPick!=null){
									int pickLenth=quesPick.trim().split(";").length;
									int ansertLenth=quesAnswer.length();
									if(ansertLenth>pickLenth){
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：可选项个数和答案个数不匹配";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 5);
										addElement(regionList, rowNum, 5);
									}
								}

							} else if ("填空题".equals(questype)) {
								quesTionType = 6;
								if (quesContent.trim().indexOf("____") <= 0) {// add
																				// by
																				// gaoweian
																				// 2010-10-15
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因：填空题考题中没有提供答题框!";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 6);
									addElement(regionList, rowNum, 6);
								} else {
									// add by Neo，验证填空数量和答案分割数量一致
									if (("*" + quesAnswer + "*").split("#").length != ("*"
											+ quesContent.trim() + "*")
												.split("____").length - 1) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：填空数量和答案数量不一致!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 6);
										addElement(regionList, rowNum, 6);
									}
								}
								if (quesAnswer != null) {
									// 1，填空题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：填空题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 6);
										addElement(regionList, rowNum, 6);
									}
								}

							} else if ("判断改错题".equals(questype)) {
								quesTionType = 2;

								if (quesAnswer != null) {
									// 1，判断改错题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：判断改错题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 7);
										addElement(regionList, rowNum, 7);
									}
								}

							} else if ("问答题".equals(questype)) {
								quesTionType = 7;

								if (quesAnswer != null) {
									// 1，问答题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：问题题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 8);
										addElement(regionList, rowNum, 8);
									}
								}

							} else if ("案例分析题".equals(questype)) {
								quesTionType = 8;

								if (quesAnswer != null) {
									// 1，案例分析题答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：案例分析题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 9);
										addElement(regionList, rowNum, 9);
									}
								}

							} else if ("不定项选择题".equals(questype)) {
								quesTionType = 5;

								// 不定向选择题可选项不能为空！
								if (quesPick == null
										|| "".equals(quesPick.trim())) {
									lineError = true;
									lineErrorMsg = "第" + (rowNum - 3)
											+ "条信息导入失败,失败原因：不定项选择题选项不能为空!";
									errorColumnCnt += 1;
									addElement(errorRegionList, errorRowNum, 10);
									addElement(regionList, rowNum, 10);
								}
								quesPick=ExamManagerServiceImpl.titleNum(quesPick.split(";"));
								if (quesAnswer != null) {
									// 1，不定向选择答案不能为空
									if ("".equals(quesAnswer.trim())) {
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：不定项选择题答案不能为空!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 10);
										addElement(regionList, rowNum, 10);
									}
									// 2，不定向选择题答案必须输入字母
									if (!quesAnswer.toUpperCase().matches(
											"^[a-zA-Z]*")) {
										lineError = true;
										lineErrorMsg = "第"
												+ (rowNum - 3)
												+ "条信息导入失败,失败原因：不定项选择题答案必须输入字母!";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 10);
										addElement(regionList, rowNum, 10);
									}

								}
								if(quesAnswer!=null&&quesPick!=null){
									int pickLenth=quesPick.trim().split(";").length;
									int ansertLenth=quesAnswer.length();
									if(ansertLenth>pickLenth){
										lineError = true;
										lineErrorMsg = "第" + (rowNum - 3)
												+ "条信息导入失败,失败原因：可选项个数和答案个数不匹配";
										errorColumnCnt += 1;
										addElement(errorRegionList,
												errorRowNum, 5);
										addElement(regionList, rowNum, 5);
									}
								}
							}
							rowObjList.set(0, quesTionType);
							rowObjList.set(3, quesPick);
							if (lineError) {
								errormslist.add(lineErrorMsg);
								errorList.add(rowObjList);
							} else {
								correctList.add(rowObjList);
							}
						}

//						// 正确信息导入到数据库
//						if (!correctList.isEmpty())
//							insertQuestionToDataBase(correctList);

					}
				}
			}
		} catch (Exception e) {
			map.put("errorCnt", errorList.size());
			map.put("successCnt", correctList.size());
			map.put("errorRowStr", errormslist);
			return map;
		}
		map.put("correctList", correctList);
		map.put("successCnt", correctList.size());
		map.put("errorCnt", errorList.size());
		map.put("errorRowStr", errormslist);
		map.put("dataCnt", excelList.size() - 1);
		return map;
	}
	protected static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}
	
	private static String titleNum(String[] quesPick) {
		String zimu[] = new String[] { "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z" };
		String QuesPick = "";
		for (int i = 0; i < quesPick.length; i++) {
			if(quesPick[i].length()>1){
				if((zimu[i].concat(".")).equals(quesPick[i].substring(0, 2))){
					return quesPick[i];
				}
			}
			QuesPick += zimu[i] + "." + quesPick[i] + ";";
		}
		QuesPick = QuesPick.substring(0, QuesPick.length() - 1);
		return QuesPick;
	}

	private List<Map<String, Integer>> addElement(
			List<Map<String, Integer>> pramaList, int rowNum, int columnNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("rowNum", rowNum);
		map.put("columnNum", columnNum);
		pramaList.add(map);
		return pramaList;
	}

	@Override
	public void insertBatCode(Map<String, Object> map) {
		int scoreTotal = 0;// 总分
		List newKeys = new ArrayList();// 考题ID集合
		List quesScores = new ArrayList();// 考题分数集合
		List<List<Object>> lists =(List<List<Object>>) map.get("correctList");
		vSysuser sysuser=(vSysuser) map.get("user");
		//保存试题
		for (int j = 0; j < lists.size(); j++) {
			List<Object> rowObjList = lists.get(j); // 行数据
			quesScores.add(Integer.parseInt((String)rowObjList.get(1)));
			scoreTotal+=Integer.parseInt((String)rowObjList.get(1));
			tQuestions questions = new tQuestions();
			questions.setQuesType((Integer)rowObjList.get(0));// 题目类型
			questions.setQuesScore(Integer.parseInt((String)rowObjList.get(1)));// 题目分数
			questions.setQuesContent((String) rowObjList.get(2));// 题目内容
			questions.setQuesPick((String) rowObjList.get(3));// 题目可选项
			questions.setQuesAnswer((String) rowObjList.get(4));// 题目答案
			questions.setQuesCorrectAnswer((String) rowObjList.get(5));// 改错信息答案
			questions.setFkCodetypeId(Integer.parseInt((String)rowObjList.get(6)));// 所属 业务分类
			questions.setQuesNandu((String) rowObjList.get(7));//难度选择
			questions.setZuhuId(1);//租户ID
			questions.setQuesStatus("1");//考题状态
			questions.setFkInsertUserId(sysuser.getUserId());//用户id
			questions.setInsertTime(DateUtil.getDateTimeStr(new Date()));//增加考题录入时间
			
			Integer pkautoId=questionsDao.checkQuestion(questions);
			if(null!=pkautoId){
				newKeys.add(pkautoId);
			}else{
				questionsDao.insertSelective(questions);
				newKeys.add(questions.getPkAutoId());
			}
		}
		//保存考卷
		try {
			tExampaper exampaper=new tExampaper();
			exampaper.setExamPaperRemark(map.get("examPaperRemark").toString());
			exampaper.setExamPaperName(map.get("examPaperName").toString());
			exampaper.setExamPaperScore(scoreTotal);
			exampaper.setExamPaperStatus(Constans.SUCCESS);
			exampaper.setIsindex(map.get("isIndex").toString());
			exampaper.setExamPaperFlag(Constans.EXAMPAPERFLAG);//3：整张试卷导入
			exampaper.setExamPaperType(map.get("examPaperType").toString());
			exampaper.setFkInsertUserId(sysuser.getUserId());//先写死
			exampaper.setInsertTime(DateUtil.getDateTimeStr(new Date()));
			exampaper.setZuhuId(1);
			exampaperDao.insertSelective(exampaper);
			Integer ExampaperId=exampaper.getPkAutoId();
			for(int i=0;i<newKeys.size();i++){
				tExampaperQuestions exampaperQuestions=new tExampaperQuestions();
				exampaperQuestions.setFkExampaperId(ExampaperId);
				exampaperQuestions.setFkQuestionsId((Integer) newKeys.get(i));
				exampaperQuestions.setQuesScore((Integer) quesScores.get(i));
				exampaperQuestions.setFkInsertUserId(sysuser.getUserId());
				exampaperQuestions.setInsertTime(DateUtil.getDateTimeStr(new Date()));
				exampaperQuestions.setExampaperFlag(Constans.FAILE);//试卷模板 0 表示与考题表关联 1 表示与考题模板表关联
				exampaperQuestions.setEnabled(Constans.SUCCESS);
				exampaperQuestions.setZuhuId(1);
				exampaperQuestionsDao.insertSelective(exampaperQuestions);
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void deleteExamById(tExampaper exampaper) {
		exampaperDao.updateByPrimaryKeySelective(exampaper);
	}

	@Override
	public int checkExampaperExaminee(Integer pkAutoId) {
		return exampaperDao.checkExampaperExaminee(pkAutoId);
	}

	@Override
	public int getQuestionListCount(tQuestions questions) {
		return questionsDao.getQuestionListCount(questions);
	}

	@Override
	public List<tQuestions> getQuestionList(tQuestions questions) {
		return questionsDao.queryQuestionList(questions);
	}

	@Override
	public List<tQuestions> queryQeusByExamId(tQuestions questions) {
		return questionsDao.queryQeusByExamId(questions);
	}

	@Override
	public int queryQeusByExamIdCount(tQuestions questions) {
		return questionsDao.queryQeusByExamIdCount(questions);
	}

	@Override
	public TQuestionsArea selectUpdateQuestionAreaById(String userId) {
		return questionsAreaMapper.selectUpdateQuestionAreaById(userId);
	}

	@Override
	public tExampaper selectExampaperById(Integer exampaperId) {
		return paperDao.selectByPrimaryKey(exampaperId);
	}

	@Override
	public int checkExampaperName(String examPaperName) {
		return paperDao.checkExampaperName(examPaperName);
	}

	@Override
	public List<ExamManualScore> selectExampapersList(tExampaper exampaper) {
		return paperDao.selectExampapersList(exampaper);
	}

	@Override
	public int selectExampapersCount(tExampaper exampaper) {
		return paperDao.selectExampapersCount(exampaper);
	}

	@Override
	public List<ExamManualScore> selectRandomExampapersList(tExampaper exampaper) {
		// TODO Auto-generated method stub
		return exampaperDao.selectRandomExampapersList(exampaper);
	}

	@Override
	public int selectRandomExampapersCount(tExampaper exampaper) {
		// TODO Auto-generated method stub
		return exampaperDao.selectRandomExampapersCount(exampaper);
	}
}
