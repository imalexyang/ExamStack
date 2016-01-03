(function($) {
	$.tree = {
		defaults : {
			domObject : null,
			treeData : null,
			checkBox : true,
			expanded : true,
			clickNode : null,
		},

		initial : function initial(config) {
			_config = $.extend({}, $.tree.defaults, config);
			$.tree.DrawTree();
			$.tree.closeChildren();
			$.tree.unCheckAllNode();
			$.tree.bindClickNodeAction();
			$.tree.bindExpandNodeAction();
			$.tree.bindCheckAction();
		},

		DrawTree : function DrawTree() {
			var treeHtml = "<ul><li>";
			var enableCheckBox = _config.checkBox;
			var checkboxhtml = enableCheckBox ? "<span class=\"tree-checkbox\"> </span>" : "";
			treeHtml += "<span class=\"expander\"> </span>" + checkboxhtml + "<span class=\"tree-node-text\">" + _config.treeData.name + "</span><span class=\"data-value\">" + _config.treeData.id + "</span><span class=\"group-level-id\">" + _config.treeData.group_level_id + "</span>";
			treeHtml += "<ul>";
			if (_config.treeData.children != null) {
				for (var i = 0; i < _config.treeData.children.length; i++) {
					var expanderHtml_i = "<span class=\"leaf\"> </span>";
					if (_config.treeData.children[i].children != null) {
						expanderHtml_i = "<span class=\"expander\"> </span>";
					}

					treeHtml += "<li>" + expanderHtml_i + checkboxhtml + "<span class=\"tree-node-text\">" + _config.treeData.children[i].name + "</span><span class=\"data-value\">" + _config.treeData.children[i].id + "</span><span class=\"group-level-id\">" + _config.treeData.children[i].group_level_id + "</span>";
					treeHtml += "<ul>";

					if (_config.treeData.children[i].children != null) {
						for (var l = 0; l < _config.treeData.children[i].children.length; l++) {
							treeHtml += "<li><span class=\"leaf\"> </span>" + checkboxhtml + "<span class=\"tree-node-text\">" + _config.treeData.children[i].children[l].name + "</span><span class=\"data-value\">" + _config.treeData.children[i].children[l].id + "</span><span class=\"group-level-id\">" + _config.treeData.children[i].children[l].group_level_id + "</span>";
							treeHtml += "</li>";
						}
					}
					treeHtml += "</ul>";
					treeHtml += "</li>";
				}
			}

			treeHtml += "</li></ul>";
			_config.domObject.html(treeHtml);
		},
		bindClickNodeAction : function bindClickNodeAction() {
			_config.domObject.delegate(".tree-node-text", "click", function() {
				if ($(this).hasClass("node-selected")) {
					$(this).removeClass("node-selected");
				} else {
					$(".node-selected").removeClass("node-selected");
					$(this).addClass("node-selected");
				}
				$.tree.clickNodeEvent();
			});
		},
		
		clickNodeEvent : function clickNodeEvent(){
			_config.clickNode();
		},

		bindExpandNodeAction : function bindExpandNodeAction() {
			_config.domObject.delegate(".expander", "click", function() {
				if ($(this).hasClass("expander-close")) {
					$(this).removeClass("expander-close");
					$(this).addClass("expander-open");
					$(this).parent().children("ul").slideDown("fast");
				} else if ($(this).hasClass("expander-open")) {
					$(this).removeClass("expander-open");
					$(this).addClass("expander-close");
					$(this).parent().children("ul").slideUp("fast");
				}
			});
		},

		bindCheckAction : function bindCheckAction() {
			_config.domObject.delegate(".tree-checkbox", "click", function() {
				if ($(this).hasClass("tree-checkbox-checked")) {
					$(this).removeClass("tree-checkbox-checked");
					$(this).removeClass("tree-checkbox-halfchecked");
					$(this).parent().children("ul").find(".tree-checkbox").removeClass("tree-checkbox-checked");
					$(this).parent().children("ul").find(".tree-checkbox").removeClass("tree-checkbox-halfchecked");

					$(this).addClass("tree-checkbox-unchecked");
					$(this).parent().children("ul").find(".tree-checkbox").addClass("tree-checkbox-unchecked");
				} else if ($(this).hasClass("tree-checkbox-halfchecked")) {
					$(this).removeClass("tree-checkbox-checked");
					$(this).removeClass("tree-checkbox-halfchecked");
					$(this).parent().children("ul").find(".tree-checkbox").removeClass("tree-checkbox-checked");
					$(this).parent().children("ul").find(".tree-checkbox").removeClass("tree-checkbox-halfchecked");

					$(this).addClass("tree-checkbox-unchecked");
					$(this).parent().children("ul").find(".tree-checkbox").addClass("tree-checkbox-unchecked");
				} else if ($(this).hasClass("tree-checkbox-unchecked")) {
					$(this).removeClass("tree-checkbox-unchecked");
					$(this).parent().children("ul").find(".tree-checkbox").removeClass("tree-checkbox-unchecked");

					$(this).addClass("tree-checkbox-checked");
					$(this).parent().children("ul").find(".tree-checkbox").addClass("tree-checkbox-checked");
				}

				var this_ul = $(this).parent().parent();

				var checked_num = this_ul.find(".tree-checkbox-checked").length;
				var unchecked_num = this_ul.find(".tree-checkbox-unchecked").length;

				if (checked_num > 0 && unchecked_num > 0) {
					var parent_li = this_ul.parents("li");
					parent_li.each(function() {
					});
					parent_li.children(".tree-checkbox").removeClass("tree-checkbox-checked");
					parent_li.children(".tree-checkbox").removeClass("tree-checkbox-unchecked");
					parent_li.children(".tree-checkbox").addClass("tree-checkbox-halfchecked");
				} else if (checked_num > 0 && unchecked_num == 0) {
					var parent_li = this_ul.parents("li");
					parent_li.children(".tree-checkbox").removeClass("tree-checkbox-halfchecked");
					parent_li.children(".tree-checkbox").removeClass("tree-checkbox-unchecked");
					parent_li.children(".tree-checkbox").addClass("tree-checkbox-checked");
				} else if (checked_num == 0) {
					var parent_li = this_ul.parents("li");
					parent_li.children(".tree-checkbox").removeClass("tree-checkbox-halfchecked");
					parent_li.children(".tree-checkbox").removeClass("tree-checkbox-checked");
					parent_li.children(".tree-checkbox").addClass("tree-checkbox-unchecked");
				}

			});
		},

		closeChildren : function closeChildren() {
			if (_config.expanded) {
				_config.domObject.find("ul").show();

				$(".expander").removeClass("expander-close");
				$(".expander").addClass("expander-open");
			} else {
				_config.domObject.find("ul").hide();
				_config.domObject.children("ul").show();

				$(".expander").removeClass("expander-open");
				$(".expander").addClass("expander-close");
			}
		},

		unCheckAllNode : function unCheckAllNode() {
			$(".tree-checkbox-checked").removeClass("tree-checkbox-checked");
			$(".tree-checkbox-halfchecked").removeClass("tree-checkbox-halfchecked");
			$(".tree-checkbox").addClass("tree-checkbox-unchecked");
		}
	};
})(jQuery);
