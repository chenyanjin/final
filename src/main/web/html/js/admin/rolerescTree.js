var rid
var ztree
function binResc(rid) {
	if ('' == rid) {
		alertMsg.error('角色不能为空，请重试...');
	} else {
		$.post($("#ctx").val() + "/admin/roleresc/getRescByRole/" + rid, function(
				data) {
			if ('' != data) {
				// alert(data);
				$("#saveBut").removeAttr("disabled");
				$("#roleid").val(rid);
				zTree = $.fn.zTree.getZTreeObj("tree");
				// 设置菜单选中状态
				if (data != 'null') {
					zTree.checkAllNodes(false);
					var cNode = data.split("-");
					for (i = 0; i < cNode.length; i++) {
						if (cNode[i] != '') {
							// alert(cNode[i]);
							var node = zTree.getNodeByParam("id", cNode[i], null);// 得到选中节点
							if (!node.isParent) {
								zTree.checkNode(node, true, true); // 显示选中节点
							}
							// zTree.selectNode(node); //打开选中节点的父节点
						}
					}
				} else {
					// 设置菜单全部未选中
					zTree.checkAllNodes(false);
				}
			}
		});
	}
}

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
			rootPId: -1,
		}
	},
	async : {
		enable : true,
		type : "post",
		url : $("#ctx").val() + "/admin/roleresc/getTree"
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
function openDialogToResc(url) {
	$.pdialog.open($("#ctx").val() + url, "modifyRescDialogs", "编辑权限", {
		width : 520,
		height : 530,
		mask : true
	});
}

$(document).ready(function() {
	// init ztree
	$.fn.zTree.init($("#tree"), setting);
	// blind save button
	$("#saveBut").click(function() {
		zTree = $.fn.zTree.getZTreeObj("tree");
		var checkNode = zTree.getCheckedNodes();
		var rescId = '';
		for (i = 0; i < checkNode.length; i++) {
			rescId = rescId + "-" + checkNode[i].id;
		}
		rescId = rescId + "-";
		if (rescId == '-' || $("#roleid").val() == '') {
			alertMsg.info('请选择要绑定的角色和系统菜单!')
		} else {
			$.post($("#ctx").val() + "/admin/roleresc/saveResc", {
				'rescId' : rescId,
				'roleId' : $("#roleid").val()
			}, function(data) {
				if (data == '1') {
					alertMsg.correct('更新成功!');
				} else {
					alertMsg.error('更新失败!');
				}
			});
		}
	});
});