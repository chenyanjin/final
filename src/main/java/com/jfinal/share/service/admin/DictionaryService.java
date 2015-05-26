package com.jfinal.share.service.admin;

import java.util.List;

import com.jfinal.share.model.Dictionary;
import com.jfinal.share.util.DateUtils;
import com.jfinal.share.util.ModelEnum;

/**
 * Dictionary 业务逻辑层
 * 
 * @author 風佑兲 (375910297@.qq.com)
 * @date 2014年8月2日 上午11:07:02
 */
public class DictionaryService {
	private String message = "";

	public String getMessage() {
		return message;
	}

	/**
	 * 获得parent列表
	 * 
	 * @return
	 */
	public List<Dictionary> getParentList() {
		return Dictionary.dao.searchParentAll();
	}

	/**
	 * 获得childen列表
	 * 
	 * @return
	 */
	public List<Dictionary> getChildrenList() {
		return Dictionary.dao.searchChildrenAll();
	}

	/**
	 * 组装Dictionary的ztree的json格式
	 * 
	 * @return String
	 */
	public String getTree() {
		List<Dictionary> dictionaryList = Dictionary.dao.searchAll();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");

		for (Dictionary dictionary : dictionaryList) {
			stringBuilder.append("{id:");
			stringBuilder.append(dictionary.getInt(Dictionary.ID));
			stringBuilder.append(",pid:");
			stringBuilder.append(dictionary.getInt(Dictionary.PID));
			stringBuilder.append(",name:\"");
			int enable = "1".equals(dictionary.getStr(Dictionary.ENABLE)) ? 1 : 0;
			if (enable == ModelEnum.EnableOrDisable.DISABLE.ordinal()) {
				stringBuilder.append("<span style='color:red'>");
				stringBuilder.append(dictionary.getStr(Dictionary.NAME));
				stringBuilder.append("</span>");
			} else {
				stringBuilder.append(dictionary.getStr(Dictionary.NAME));
			}
			stringBuilder.append("\"");
			if (enable == ModelEnum.EnableOrDisable.DISABLE.ordinal()) {
				stringBuilder.append(",chkDisabled:true");
			}
			
			stringBuilder.append(",click:\"openDialogToDictionary('/admin/dictionary/toModifyPage/" + dictionary.getInt(Dictionary.ID) + "');\"");
			stringBuilder.append(",open:false");
			stringBuilder.append("}");
			stringBuilder.append(",");
		}

		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	/**
	 * 保存或更新Dictionary
	 */
	public void modify(Dictionary dictionary) {
		if (null == dictionary.get(Dictionary.ID)) {
			dictionary.set(Dictionary.CREATE_TIME, DateUtils.getCurrentTime())
							.save();
		} else {
			dictionary.update();
		}
	}

	/**
	 * 批量启用 - 停用 Dictionary
	 * 
	 * @param ids
	 */
	public void enableOrdisable(String[] ids) {
		int enableCount = 0;
		int disableCount = 0;
		for (String id : ids) {
			Dictionary dictionary = Dictionary.dao.findById(id);
			int disable = "1".equals(dictionary.getStr(Dictionary.ENABLE)) ? 1 : 0 ;
			if (ModelEnum.EnableOrDisable.DISABLE.ordinal() == disable) {
				// 启用
				dictionary.set(Dictionary.ENABLE, ModelEnum.EnableOrDisable.ENABLE.ordinal()).update();
				enableCount ++;
			} else {
				// 停用
				dictionary.set(Dictionary.ENABLE, ModelEnum.EnableOrDisable.DISABLE.ordinal()).update();
				disableCount ++;
			}
		}
		message = "启用[" + enableCount + "]条数据</br>停用[" + disableCount + "]条数据";
	}

	/**
	 * 根据父code查找出字典中的子数据
	 * 
	 * @param code 父字典代码
	 * @return List<Dictionary>
	 */
	public List<Dictionary> getDictionaryByCode(String code) {
		return Dictionary.dao.searchChildrenByCode(code);
	}

}
