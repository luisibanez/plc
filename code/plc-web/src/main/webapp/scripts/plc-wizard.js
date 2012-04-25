/*******************************************************************************
 * Copyright (c) 2012, 5AM Solutions, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the 
 * following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following 
 * disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following 
 * disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
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

/**
 * Sets the handler for the survey link.
 */
function surveyLinkHandler() {
    $('#surveyLink').click(function() {
       $('#surveyForm').submit(); 
    });
}