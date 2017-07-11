
function tableResponseHandler(res) {
	return res.data;
}




$(function(){
	

	$.Tipmsg.s="请{填写|选择}正确的{0|信息}！";
	
	$(".menu-item a").click(function(){

		var $this = $(this);
		
		$(".menu-item").removeClass("active");
		$this.parent().addClass("active");
		
		var href = $this.attr("href");
		
		
		var contentId = $this.attr("data-id");
		$(".contentTab").hide();
		var $contentTab = $("#" + contentId);
		

		if($contentTab.length > 0){
			$contentTab.show();
			//$contentTab.load(href + "?isOnlyContent=true");
		} else {
			$contentTab = $("<div>", {
				  "class": "contentTab",
				  id: contentId,
				});
			$contentTab.append("<div class='loading'></div>");
			$contentTab.appendTo("#content");
			$contentTab.load(href, {isOnlyContent: true, v: new Date().getTime()});
			//$("content").append("<div id='" + contentId + "' class='contentTab'></div>");
		}
		return false;
	});
	
	
	$(".logout").click(function() {
		
		if(!confirm("确定要注销吗？")) {
			return;
		}
		
		$.post(context_ + "/logout.do", {}, function(data){
			if(data.statusCode==0){
				window.location.href = context_;
			} else {
				$.zui.messager.show(data.status);
			}
		});
	});
});
