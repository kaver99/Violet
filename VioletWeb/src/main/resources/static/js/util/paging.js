var _root_Url= "${pageContext.request.contextPath}";

// 이전 버튼 이벤트
function fn_prev(_page, _range, _rangeSize) {
	var _page = ((_range - 2) * _rangeSize) + 1;
	var _range = _range - 1;
	var url = _root_Url+"/main.violet";
	url = url + "?page=" + _page;
	url = url + "&range=" + _range;
	location.href = url;
}

// 페이지 번호 클릭
function fn_pagination(_page, _range, _rangeSize, _searchType, _keyword) {
	var url = _root_Url+"/main.violet";
	url = url + "?page=" + _page;
	url = url + "&range=" + _range;
	location.href = url;	
};
	
// 다음 버튼 이벤트
function fn_next(_page, _range, _rangeSize) {
	var _page = parseInt((_range * _rangeSize)) + 1;
	var _range = parseInt(_range) + 1;
	var url = _root_Url+"/main.violet";
	url = url + "?page=" + _page;
	url = url + "&range=" + _range;
	location.href = url;
}
