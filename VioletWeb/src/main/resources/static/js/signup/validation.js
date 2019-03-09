
/*[- --------------------------------------------------------- -]*/
/*[- Validation Init -]*/
function validation_init() {
	
}

/*[- --------------------------------------------------------- -]*/
/*[- STEP1 Validation Check -]*/
function certification_validation() {
	var status = "";
	var content = "";
	/*[- alert Active Event -]*/
	return alertCallEvent(status, content);
}

/*[- --------------------------------------------------------- -]*/
/*[- STEP2 Validation Check -]*/
function agreement_validation() {
	var status = "";
	var content = "";
	if($("#terms-of-service-chkbox").prop("checked") === false) {
		status = "error";
		content = "[VIOLET 이용 약관] 는 필수 체크 항목입니다.";
	} else if($("#personal-information-chkbox").prop("checked") === false) {
		status = "error";
		content = "[VIOLET 개인정보 수집 맟 아용에 대한 안내] 는 필수 체크 항목입니다.";
	} else {
		status = "success";
		$("#terms-of-service-chkbox").val('Y');
		$("#personal-information-chkbox").val('Y');
		if($("#event-alarm-chkbox").prop("checked") === true) {
			$("#event-alarm-chkbox").val('Y');
		}
	}
	/*[- alert Active Event -]*/
	return alertCallEvent(status, content);
}

/*[- --------------------------------------------------------- -]*/
/*[- STEP3 Validation Check -]*/
function details_validation() {
	var status = "";
	var content = "";
	var inputId = "";
	var pwRegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,20}/;
	var phoneRegExp = /(01[016789])([0-9]{1}[0-9]{2,3})([0-9]{4})$/;
	
	if($('#username').val().length < 3 || $('#username').val().length > 50) {
		status = "error";
		content = "이메일을 50자 이내로 작성하여 주세요.";
//		inputId = $('#username').attr("id");
		$('#username').focus();
	} else if($('#usernameInput').hasClass("has-danger")) {
		status = "error";
		content = "잘못된 이메일 형식이거나 이미 등록된 사용자 입니다.";
//		inputId = $('#username').attr("id");
		$('#username').focus();
	} else if(!$("#name").val() || $("#name").val().length > 20) {
		status = "error";
		content = "이름을 20자 이내로 작성하여 주세요.";
//		inputId = $('#name').attr("id");
		$('#name').focus();
	} else if((!pwRegExp.test($("#password").val()))) {
		status = "error";
		content = "패스워드를 20자 이하, 앞자리 대문자, 하나 이상의 숫자 및 특수 문자 포함하여 작성하여 주세요.";
//		inputId = $('#password').attr("id");
		$('#password').focus();
	} else if($("#password2").val().length < 8 || $("#password2").val().length > 20) {
		status = "error";
		content = "패스워드 확인을 20자 이내로 작성하여 주세요.";
//		inputId = $('#password2').attr("id");
		$('#password2').focus();
	} else if($("#password2").val() != $("#password").val()) {
		status = "error";
		content = "패스워드 확인의 패스워드와 기존 작성한 패스워드가 불일치 합니다.";
//		inputId = $('#password2').attr("id");
		$('#password2').focus();
	} else if($("#phone").val().length > 20 || !$("#phone").val() || !phoneRegExp.test($("#phone").val())) {
		status = "error";
		content = "휴대폰 번호를 20자 이내로 작성하여 주세요.";
//		inputId = $('#phone').attr("id");
		$('#phone').focus();
	} else if(!$("#telecom").val()) {
		status = "error";
		content = "통신사를 선택하여 주세요.";
//		inputId = $('#phone').attr("id");
	} else {
		status = "success";
	}
	/*[- input Focus Event -]*/
//	input_focus(inputId, status);
	
	/*[- alert Active Event -]*/
	return alertCallEvent(status, content);
}

/*[- --------------------------------------------------------- -]*/
/*[- STEP3 Validation Check -]*/
function address_validation() {
	var status = "";
	var content = "";
	
	/*[- alert Active Event -]*/
	return alertCallEvent(status, content);
}

/*[- input Focus Event -]*/
function input_focus(_inputId, _status) {
	var $inputId = $('#' + _inputId);
	var $inputParentId = $inputId.parent().attr("id");
	if(_status === "error") {
		if($('#' + $inputParentId).hasClass("has-success")) {
			$('#' + $inputParentId).removeClass("has-success");
		}
		$('#' + $inputParentId).addClass("has-danger");
		$('#' + $inputParentId).find('.form-control-feedback').html('<i class="material-icons">clear</i>');
		$('#' + $inputParentId).find('input').focus();
	} else {
		if($('#' + $inputParentId).hasClass("has-danger")) {
			$('#' + $inputParentId).removeClass("has-danger");
		}
		$('#' + $inputParentId).addClass("has-success");
		$('#' + $inputParentId + '> .form-control-feedback').html('<i class="material-icons">done</i>');
	}
}