function disableNavigationUntilVideoFinishes() {
	var video = $('#ctrvideo')[0];
	$f(video).addEvent('ready', ready);

	function addEvent(element, eventName, callback) {
		if (element.addEventListener) {
			element.addEventListener(eventName, callback, false);
		}
		else {
			element.attachEvent(eventName, callback, false);
		}
	}

	function ready(player_id) {
		var player = $f(player_id);

		function onFinish() {
			player.addEvent('finish', function(data) {
				$(":submit").removeClass('disabled');
				$(":submit").removeAttr('disabled');
				$("#video_instructions").text("Click Next to continue the consent process.");
				$("#video_instructions").addClass("success");
			});
		}
		onFinish();
	}
}

/**
 * Marks steps as complete in the sidebar.
 * @param stepSelector the selector for the current sidebar step
 */
function markProgress(stepSelector) {
	var stopLi = $('#sidebar li.' + stepSelector);
	$('#sidebar li').each(function(index) {
		if ($(this).is(stopLi)) {
			$(this).addClass("currentStep");
			return false;
		} else {
			$(this).addClass("viewed");
		}
	});
}