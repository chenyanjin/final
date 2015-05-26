var setting = {
	view : {
		nameIsHTML : true
	},
	check : {
		enable : true
	},
	data : {
		simpleData : {
			enable : true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: null,
		}
	},
	async : {
		enable : true,
		type : "post",
		url : $("#ctx").val() + "/admin/dictionary/getTree"
	},
	callback : {
		onAsyncSuccess : zTreeOnAsyncSuccess,
		onAsyncError : zTreeOnAsyncError
	}
};
function zTreeOnAsyncError(event, treeId, treeNode) {
	alertMsg.error('加载权限信息失败，请重试...')
}
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	$("#loading").remove();
}

var url;
function openDialogToDictionary(url) {
	$.pdialog.open($("#ctx").val() + url, "modifyDictionaryDialogs", "编辑字典数据", {
		width : 520,
		height : 255,
		mask : true
	});
}

$(document).ready(function() {
	// init ztree
	$.fn.zTree.init($("#dictionaryTree"), setting);
	$("#delBut").click(function() {
		zTree = $.fn.zTree.getZTreeObj("dictionaryTree");
		var checkNode = zTree.getCheckedNodes();
		var ids = "";
		for (i = 0; i < checkNode.length; i++) {
			if (ids == ""){
				ids = checkNode[i].id;
			} else {
				ids += "," + checkNode[i].id;
			}
		}
		if (ids == "") {
			alertMsg.info('请选择要启用或停用的数据!')
		} else {
			$.post($("#ctx").val() + "/admin/dictionary/enableOrdisableByTree", {
				'ids' : ids,
			}, function(data) {
				alertMsg.correct(data);
				// 刷新当前navTab
				navTabPageBreak(); 
//				dwzPageBreak();
			});
		}
	});
});