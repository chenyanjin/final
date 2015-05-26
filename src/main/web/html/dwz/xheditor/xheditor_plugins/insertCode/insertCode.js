 var plugins = {
		Code:{c:'btnCode',t:'插入代码',h:1,e:function(){
		var _this=this;
		var htmlCode="<div>编程语言<select id='xheCodeType'>";
			htmlCode+="<option value='html'>HTML/XML</option>";
			htmlCode+="<option value='js'>Javascript</option>";
			htmlCode+="<option value='css'>CSS</option>";
			htmlCode+="<option value='php'>PHP</option>";
			htmlCode+="<option value='java'>Java</option>";
			htmlCode+="<option value='sql'>Sql</option>";
			htmlCode+="<option value='shell'>Shell</option>";
			htmlCode+="<option value='py'>Python</option>";
			htmlCode+="<option value='pl'>Perl</option>";
			htmlCode+="<option value='rb'>Ruby</option>";
			htmlCode+="<option value='cs'>C#</option>";
			htmlCode+="<option value='c'>C++/C</option>";
			htmlCode+="<option value='vb'>VB/ASP</option>";
			htmlCode+="<option value=''>其它</option>";
			htmlCode+="</select></div><div>";
			htmlCode+="<textarea id='xheCodeValue' wrap='soft' spellcheck='false' style='width:300px;height:100px;' />";
			htmlCode+="</div><div style='text-align:right;'><input type='button' id='xheSave' value='确定' /></div>";			
		var jCode=$(htmlCode),jType=$('#xheCodeType',jCode),jValue=$('#xheCodeValue',jCode),jSave=$('#xheSave',jCode);
		jSave.click(function(){
			_this.loadBookmark();
			_this.pasteHTML('<pre class="brush: '+jType.val()+'">'+_this.domEncode(jValue.val())+'</pre>&nbsp;');
			_this.hidePanel();
			return false;	
		});
		_this.saveBookmark();
		_this.showDialog(jCode);
	}},
};