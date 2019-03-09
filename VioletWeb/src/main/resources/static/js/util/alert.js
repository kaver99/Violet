//*[- ------------------------------------------------- -]*//
//*[- 생성자 / prototype연결 방식 							-]*//
//*[- 사용방법 											-]*//
//*[- status = "error";									-]*//
//*[- content = "prototype test";						-]*//
//*[- var alert = new alertCallEvent(status, content);	-]*//
//*[- return alert.getActive();							-]*//

//var status = "";
//var content = "";

//class alertCallEvent {								
//    constructor(_status, _content) {
//        this.status = _status;
//        this.content = _content;
//    }
//    getActive() {
//        $('#violet-alert').removeClass('alert-active');
//        $('#alert-errMsg').html();
//        // animation restart
//        var el = $('#violet-alert');
//        var newel = el.clone(true);
//        el.before(newel);
//        el.remove();
//        if (status === "error") {
//            $('#violet-alert').addClass('alert-active');
//            $('#alert-errMsg').html(content);
//            return false;
//        }
//        return true;
//    }
//};

//*[- --------------------------------------------- -]*//
//*[- violetAlert Call Event 						-]*//
//*[- 사용방법 : alertCallEvent("error", "errorMsg"); -]*//
function alertCallEvent(_status, _content) {
	$('#violet-alert').removeClass('alert-active');
	$('#alert-errMsg').html();
	// animation restart
	var el = $('#violet-alert');
	var newel = el.clone(true);
	el.before(newel);
	el.remove();
	if (_status === "error") {
	  $('#violet-alert').addClass('alert-active');
	  $('#alert-errMsg').html(_content);
	  return false;
	}
	return true;
}
//*[- --------------------------------------------- -]*//

$("#violet-alert").click(function() {
	$('#violet-alert').removeClass('alert-active');
	$('#alert-errMsg').html();
});
