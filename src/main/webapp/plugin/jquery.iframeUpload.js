
(function($) {
	var noop = function(){ return true; };
	var frameCount = 0;
	var pluginName = "iframeUpload";

	var defaults = {
			url: '',
			fileName: 'filedata',
			dataType: 'json',
			onSend: noop,
			onComplate: noop
	};



	//从节点获取Options
	var getElementOptions = function($this) {
		var options = {};
		for(var i in defaults) {
			var data = $this.attr("data-" + i);
			if(!data){
				options[i] = data;
			}
		}
		return options;
	};


	var updateOptions = function($this, options) {
		var elementOptions = getElementOptions($this);
		var newOptions = $.extend({}, defaults, elementOptions, options);
		$this.data(pluginName, newOptions);
		return newOptions;
	};



	// 创造一个对象，存储我们需要的共有方法
	var methods = {
			init: function(options) {
				// 在每个元素上执行方法
				return this.each(function() {
					var $this = $(this);
					var newOptions = updateOptions($this, options);
					
					//截获submit事件
					$this.submit(function(){
						var ret = $this.data("isSubmit_");
						
						//防止循环调用
						if(!ret){
							$this.iframeUpload("submit");
						}
						
						$this.data("isSubmit_", false);
						
						return ret;
					});

					
				});
			},
			submit: function() {// 在每个元素上执行方法
				return this.each(function() {
					
					
					
					var $this = $(this);
					var opts = $this.data(pluginName);
					if($this.find("input[type=file]").length > 0){
						if($this.attr("method") == null){
							$this.attr("method", "post");
						}
					}
					
					//创建iframe
					var frameName = 'uploadFrame' + frameCount;
					++frameCount;
					var iframe = $('<iframe style="display: none!important;" />')
					.attr('id', frameName)
					.attr('name', frameName);
					iframe.appendTo("body");
					// iframe 在提交完成之后
					iframe.load(function() {
						var contents = $(this).contents().get(0);
						var data = $(contents).find('body').text();
						//低版本IE下创建时就会触发load事件，在这判断
						if(data == null || data == ""){
							return;
						}
						try{
							if ('json' == opts.dataType) {
								data = $.parseJSON(data);
							} else if('xml' == opts.dataType) {
								data = $.parseXML(data);
							}
							opts.onComplate(data);
						} catch (e) {
							//出错了
						}
						
						
						setTimeout(function() {
							iframe.remove();
						}, 5000);
					});
					
					
					
					$this.attr("target", frameName);
					
					
					if (opts.onSend() === false) {
						return;
					}
					$this.data("isSubmit_", true);
					$this.submit();
				});
			},
			destroy: function(options) {
				// 在每个元素中执行代码
				return $(this).each(function() {
					var $this = $(this);

					setTimeout(function() {
						iframe.remove();
					}, 5000);
					
					//TODO
					$.error('destroy方法还没写！！！' );
					
					// 删除元素对应的数据
					$this.removeData(pluginName);
					$this.removeData("isSubmit_");
				});
			}
	};




	$.fn.iframeUpload = function() {
		var method = arguments[0];
		if(methods[method]) {
			method = methods[method];
		} else if( typeof(method) == 'object' || !method ) {
			method = methods.init;
		} else {

			$.error( 'Method ' +  method + ' does not exist on jQuery.iframeUpload' );
			return this;
		}
		return method.apply(this, arguments);
	};

	
	
	
	$(function () {
		$('[data-toggle="upload"]').iframeUpload();
	});

})(jQuery);

