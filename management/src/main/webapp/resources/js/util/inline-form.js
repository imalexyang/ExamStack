$(function() {
	$(":input[placeholder]").placeholder();

	$(".show-form").click(function() {
		$("form.inline-form").show();
		$(this).hide();
		return false;
	});

	$(".cancel-form").click(function() {
		$("form.inline-form").hide();
		$(".show-form").show();
		return false;
	});
});
