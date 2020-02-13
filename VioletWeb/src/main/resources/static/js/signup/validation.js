
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
//	var content = "";
	if($("#terms-of-service-chkbox").prop("checked") === false) {
//		content = "[VIOLET 이용 약관] 는 필수 체크 항목입니다.";
		$("#terms-of-service-chkbox ~ b.form-text").css("margin-bottom", ".1rem");
		$("#terms-of-service-chkbox ~ b.form-text").css("border-bottom", "2px dashed #f4361e");
		$("#terms-of-service-chkbox").focus();
		return false;
	} else if($("#personal-information-chkbox").prop("checked") === false) {
//		content = "[VIOLET 개인정보 수집 맟 아용에 대한 안내] 는 필수 체크 항목입니다.";
		$("#personal-information-chkbox ~ b.form-text").css("margin-bottom", ".1rem");
		$("#personal-information-chkbox ~ b.form-text").css("border-bottom", "2px dashed #f4361e");
		$("#personal-information-chkbox").focus();
		return false;
	} else {
		$("#terms-of-service-chkbox ~ b.form-text").css("margin-bottom", "0");
		$("#terms-of-service-chkbox ~ b.form-text").css("border-bottom", "none");
		$("#personal-information-chkbox ~ b.form-text").css("margin-bottom", "0");
		$("#personal-information-chkbox ~ b.form-text").css("border-bottom", "none");
		
		$("#terms-of-service-chkbox").val('Y');
		$("#personal-information-chkbox").val('Y');
		if($("#event-alarm-chkbox").prop("checked") === true) {
			$("#event-alarm-chkbox").val('Y');
		}
		return true;
	}
	/*[- alert Active Event -]*/
//	return alertCallEvent(status, content);
}

/*[- --------------------------------------------------------- -]*/
/*[- STEP3 Validation Check -]*/
var invalid = [];

function checkId() {
	var username = $('#username').val();
	var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

	if(regExp.test(username)) {
		setTimeout(function () {
			$.ajax({
				url: '/signup/userChk.violet'
				,type: 'post'
				,data: {username:username}
				,success: function(data) {
					if($.trim(data) == 0) {
						input_focus('username', 'success', '');
//						$('label[for=username]').nextAll('.input-help').html('');
//						$('label[for=username] > .input-border').css('transform', 'scaleX(1)');
//						$('label[for=username] > .input-border').css('background', '#28a745');
//						$('label[for=username] > .input-label').css('color', '#28a745');
//						$('label[for=username] + .input-help').css('color', '#A9A9A9');
					} else {
						content = "* 등록되어 있는 이메일 입니다. 다른 이메일로 작성해 주세요.";
						input_focus('username', 'error', content);
//						$('label[for=username]').nextAll('.input-help').html(content);
//						$('label[for=username] > .input-border').css('transform', 'scaleX(0)');
//						$('label[for=username] > .input-border').css('background', '#f4361e');
//						$('label[for=username] > .input-label').css('color', '#f4361e');
//						$('label[for=username] + .input-help').css('color', '#f4361e');
//						$('#username').focus();
					}
				}
				,error: function(msg) {
// 					$('.modal-domain').add('<div th:replace="menu/modal :: modal" th:remove="tag"></div>');
				}
			})
		}, 500);
		
	} else {
		content = "* 잘못된 형식의 이메일 입니다. 다시 입력해 주세요."
		input_focus('username', 'error', content);
//		$('label[for=username]').nextAll('.input-help').html(content);
//		$('label[for=username] > .input-border').css('transform', 'scaleX(0)');
//		$('label[for=username] > .input-border').css('background', '#f4361e');
//		$('label[for=username] > .input-label').css('color', '#f4361e');
//		$('label[for=username] + .input-help').css('color', '#f4361e');
//		$('#username').focus();
	}
}

function checkName() {
	var content = "";
	
	if($("#name").val().length < 1 || $("#name").val().length > 20) {
		content = "* 이름을 20자 이내로 작성하여 주세요.";
		input_focus('name', 'error', content);
//		$('label[for=name]').nextAll('.input-help').html(content);
//		$('label[for=name] > .input-border').css('background', '#f4361e');
//		$('label[for=name] > .input-label').css('color', '#f4361e');
//		$('label[for=name] + .input-help').css('color', '#f4361e');
//		$('#name').focus();
		return false;
	} else {
		input_focus('name', 'success', '');
//		$('label[for=name]').nextAll('.input-help').html('');
//		$('label[for=name] > .input-border').css('background', '#28a745');
//		$('label[for=name] > .input-label').css('color', '#28a745');
//		$('label[for=name] + .input-help').css('color', '#A9A9A9');
		return true;
	}
}

function checkPassword() {
	var content = "";
	var pwRegExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	
	if($("#password").val().length < 8 || $("#password").val().length > 15) {
		content = "* 암호를 최소 8자 이상 최대 15자 이내로 작성하여 주세요.";
		input_focus('password', 'error', content);
//		$('label[for=password]').nextAll('.input-help').html(content);
//		$('label[for=password] > .input-border').css('background', '#f4361e');
//		$('label[for=password] > .input-label').css('color', '#f4361e');
//		$('label[for=password] + .input-help').css('color', '#f4361e');
//		$('#password').focus();
		return false;
		
	} else if((!pwRegExp.test($("#password").val()))) {
		content = "* 암호를 하나 이상의 숫자 및 특수 문자를 포함하여 작성하여 주세요.";
		input_focus('password', 'error', content);
//		$('label[for=password]').nextAll('.input-help').html(content);
//		$('label[for=password] > .input-border').css('background', '#f4361e');
//		$('label[for=password] > .input-label').css('color', '#f4361e');
//		$('label[for=password] + .input-help').css('color', '#f4361e');
//		$('#password').focus();
		return false;
		
	} else {
		input_focus('password', 'success', '');
//		$('label[for=password]').nextAll('.input-help').html('');
//		$('label[for=password] > .input-border').css('background', '#28a745');
//		$('label[for=password] > .input-label').css('color', '#28a745');
//		$('label[for=password] + .input-help').css('color', '#A9A9A9');
		return true;
	}
}

function checkPassword2() {
	var content = "";
	
	if($("#passwordChk").val() != $("#password").val()) {
		content = "* 암호 확인란의 내용과 기존 작성한 암호의 내용이 불일치 합니다.";
		input_focus('passwordChk', 'error', content);
//		$('label[for=passowrdChk]').nextAll('.input-help').html(content);
//		$('label[for=passwordChk] > .input-border').css('background', '#f4361e');
//		$('label[for=passwordChk] > .input-label').css('color', '#f4361e');
//		$('label[for=passwordChk] + .input-help').css('color', '#f4361e');
//		$('#passwordChk').focus();
		return false;
		
	} else {
		input_focus('passwordChk', 'success', '');
//		$('label[for=passwordChk]').nextAll('.input-help').html('');
//		$('label[for=passwordChk] > .input-border').css('background', '#28a745');
//		$('label[for=passwordChk] > .input-label').css('color', '#28a745');
//		$('label[for=passwordChk] + .input-help').css('color', '#A9A9A9');
		return true;
	}
}

$("#phone").inputmask("999-999[9]-9999",{ placeholder:" ", clearMaskOnLostFocus: true,  keepStatic: true });
function checkPhone() {
	var content = "";
	var _phoneNum = $("#phone").val();
	var phoneRegExp = /(01[016789])-([0-9]{1}[0-9]{2,3})-([0-9]{4})$/;
	
	if(_phoneNum.length < 11 || _phoneNum.length > 14 || phoneRegExp.test(_phoneNum) == false) {
		content = "휴대폰 번호를 13자리 이내로 작성하여 주세요.";
		input_focus('phone', 'error', content);
//		$('label[for=phone]').nextAll('.input-help').html(content);
//		$('label[for=phone] > .input-border').css('background', '#f4361e');
//		$('label[for=phone] > .input-label').css('color', '#f4361e');
//		$('label[for=phone] + .input-help').css('color', '#f4361e');
//		$('#phone').focus();
		return false;
		
	} else {
		input_focus('phone', 'success', '');		
//		$('label[for=phone]').nextAll('.input-help').html('');
//		$('label[for=phone] > .input-border').css('background', '#28a745');
//		$('label[for=phone] > .input-label').css('color', '#28a745');
//		$('label[for=phone] + .input-help').css('color', '#A9A9A9');
		return true;
	}
}

function details_validation() {
	checkId();
	if(document.activeElement.id == 'username') {
		return false;
	} else if(checkName() == false ){
		return false;
	} else if(checkPassword() == false) {
		return false;
	} else if(checkPassword2() == false) {
		return false;
	} else if(checkPhone() == false) {
		return false;
	} else {
		return true;
	}
	
	/*[- alert Active Event -]*/
//	return alertCallEvent(status, content);
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
function input_focus(_inputId, _status, _content) {
	if(_status === "error") {
		/*[- input Focus ERROR -]*/
		$('label[for='+_inputId+']').nextAll('.input-help').html(_content);
		$('label[for='+_inputId+'] > .input-border').css('transform', 'scaleX(0)');
		$('label[for='+_inputId+'] > .input-border').css('background', '#f4361e');
		$('label[for='+_inputId+'] > .input-label').css('color', '#f4361e');
		$('label[for='+_inputId+'] + .input-help').css('color', '#f4361e');
		$('#'+_inputId).focus();
	} else {
		/*[- input Focus SUCCESS -]*/
		$('label[for='+_inputId+']').nextAll('.input-help').html('');
		$('label[for='+_inputId+'] > .input-border').css('transform', 'scaleX(1)');
		$('label[for='+_inputId+'] > .input-border').css('background', '#28a745');
		$('label[for='+_inputId+'] > .input-label').css('color', '#28a745');
		$('label[for='+_inputId+'] + .input-help').css('color', '#A9A9A9');
	}
}