-- TABLE
CREATE TABLE violet_user (
	username 				VARCHAR(50)
    ,password 				VARCHAR(150) NOT NULL
    ,name					VARCHAR(20)
    ,phone 					VARCHAR(20) 
    ,telecom				VARCHAR(10) 
    ,authority	 			VARCHAR(10) DEFAULT 'USER'
    ,create_date 			VARCHAR(20) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD HH24MISS')
    ,access_date 			VARCHAR(20)
    ,img_thumbnail			VARCHAR(100)
    ,isAccountNonExpired 	CHAR(2)
    ,isAccountNonLocked 	CHAR(2)
    ,isCredentialsNonExpired CHAR(2)
    ,isEnabled 				CHAR(2)
    ,socialType				VARCHAR(10) NOT NULL
    ,socialId				VARCHAR(20)
    ,term_serviceyn			char(2)
	,per_infomationyn		char(2)
	,event_alarmyn			char(2)
);

ALTER TABLE VIOLET_USER ADD CONSTRAINT pk_user PRIMARY KEY (username, socialType);

-- COMMENT
COMMENT ON TABLE violet_user IS '사용자 정보';
COMMENT ON COLUMN violet_user.username IS '사용자 아이디';
COMMENT ON COLUMN violet_user.password IS '사용자 패스워드';
COMMENT ON COLUMN violet_user.name IS '사용자 이름';
COMMENT ON COLUMN violet_user.phone IS '사용자 휴대폰 번호';
COMMENT ON COLUMN violet_user.authority IS '그룹코드(USER,MANAGER,ADMIN)';
COMMENT ON COLUMN violet_user.create_date IS '사용자 생성일자';
COMMENT ON COLUMN violet_user.access_date IS '사용자 접속일자';
COMMENT ON COLUMN violet_user.img_thumbnail IS '사용자 이미지 경로';

-- user pw 1111 : 33275a8aa48ea918bd53a9181aa975f15ab0d0645398f5918a006d08675c1cb27d5c645dbd084eee56e675e25ba4019f2ecea37ca9e2995b49fcb12c096a032e


-- TABLE
CREATE TABLE violet_userrole (
	username				VARCHAR(50) NOT NULL
	,socialType				VARCHAR(10) NOT NULL
	,authority	 			VARCHAR(10) NOT NULL
	,role_name				VARCHAR(20)
);

ALTER TABLE VIOLET_USERROLE ADD CONSTRAINT pk_userrole PRIMARY KEY (authority, username, socialType);


-- TABLE
CREATE TABLE violet_useraddr (
	idx						NUMBER
	,username 				VARCHAR(50) NOT NULL
	,socialType				VARCHAR(10) NOT NULL
	,addr_alias				VARCHAR(20)	
    ,post_code				VARCHAR(10)	NOT NULL
	,addr 					VARCHAR(20) NOT NULL
    ,sub_addr 				VARCHAR(50) NOT NULL
);

-- COMMENT
COMMENT ON TABLE violet_useraddr IS '사용자 상세정보';
COMMENT ON COLUMN violet_useraddr.idx IS '순번';
COMMENT ON COLUMN violet_useraddr.username IS '사용자 아이디';
COMMENT ON COLUMN violet_useraddr.addr_alias IS '주소지 별칭';
COMMENT ON COLUMN violet_useraddr.post_code IS '우편번호';
COMMENT ON COLUMN violet_useraddr.addr IS '주';
COMMENT ON COLUMN violet_useraddr.sub_addr IS '상세주소';


-- TABLE
CREATE TABLE violet_shipping (
	ship_id			NUMBER NOT NULL
    ,username 		VARCHAR(50) NOT NULL
    ,socialType		VARCHAR(10) NOT NULL
    ,add_alias 		VARCHAR(10)
    ,address_code 	NUMBER(15) NOT NULL
    ,address 		VARCHAR(100)
    ,sub_address 	VARCHAR(100)
    ,def_yn 		CHAR(2)
);

-- COMMENT
COMMENT ON TABLE violet_shipping IS '배송지 정보';
COMMENT ON COLUMN violet_shipping.ship_id IS '배송지 고유번호';
COMMENT ON COLUMN violet_shipping.username IS '사용자 아이디';
COMMENT ON COLUMN violet_shipping.add_alias IS '주소 별칭';
COMMENT ON COLUMN violet_shipping.address_code IS '우편 번호';
COMMENT ON COLUMN violet_shipping.address IS '지역';
COMMENT ON COLUMN violet_shipping.sub_address IS '상세 지역';
COMMENT ON COLUMN violet_shipping.def_yn IS '기본 배송지 여부';


-- TABLE
CREATE TABLE violet_item (
	item_id				NUMBER NOT NULL PRIMARY KEY
    ,title 				VARCHAR(20) NOT NULL
    ,description 		VARCHAR(4000)
    ,price 				NUMBER 	NOT NULL
    ,class_item			VARCHAR(10)
    ,stylist_name 		VARCHAR(20)
    ,creater 			VARCHAR(20)
    ,create_date		VARCHAR(20) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD HH24MISS')
    ,last_date			VARCHAR(20)
    ,opening_date		VARCHAR(20)
    ,opening_yn			CHAR(2) DEFAULT 'N'
    ,img_path1			VARCHAR(100)
);

-- COMMENT
COMMENT ON TABLE violet_item IS '상품 정보';
COMMENT ON COLUMN violet_item.item_id IS '상품 아이디';
COMMENT ON COLUMN violet_item.title IS '상품 제목';
COMMENT ON COLUMN violet_item.description IS '상품 설명';
COMMENT ON COLUMN violet_item.price IS '상품 가격';
COMMENT ON COLUMN violet_item.class_item IS '분류(top,outer,bottom,shoes,accessory)';
COMMENT ON COLUMN violet_item.stylist_name IS '상품 코디네이터명';
COMMENT ON COLUMN violet_item.creater IS '등록자';
COMMENT ON COLUMN violet_item.create_date IS '상품 등록일자';
COMMENT ON COLUMN violet_item.last_date IS '상품 최종 수정 일자';
COMMENT ON COLUMN violet_item.opening_date IS '상품 게시 일자';
COMMENT ON COLUMN violet_item.opening_yn IS '상품 게시 여부';
COMMENT ON COLUMN violet_item.img_path1 IS '상품 이미지1';


-- TABLE
CREATE TABLE violet_item_detail (
    option_no			NUMBER NOT NULL
	,item_id			NUMBER NOT NULL
    ,option_name		VARCHAR(20)
    ,stock 				NUMBER
);

-- COMMENT
COMMENT ON TABLE violet_item_detail IS '상품 상세 정보';
COMMENT ON COLUMN violet_item_detail.option_no IS '옵션 번호';
COMMENT ON COLUMN violet_item_detail.item_id IS '상품 아이디';
COMMENT ON COLUMN violet_item_detail.option_name IS '옵션 이름';
COMMENT ON COLUMN violet_item_detail.stock IS '재고';


-- TABLE
CREATE TABLE violet_favorite (
    item_id				NUMBER NOT NULL
	,username			VARCHAR(50) NOT NULL
	,socialType			VARCHAR(10) NOT NULL
    ,favorite_date		VARCHAR(20) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD HH24MISS')
);

-- COMMENT
COMMENT ON TABLE violet_favorite IS '상품 찜 정보';
COMMENT ON COLUMN violet_favorite.item_id IS '상품 아이디';
COMMENT ON COLUMN violet_favorite.username IS '사용자 아이디';
COMMENT ON COLUMN violet_favorite.favorite_date IS '찜한 일자';


-- TABLE
CREATE TABLE violet_cart (
    cart_no				NUMBER NOT NULL
	,item_id			NUMBER NOT NULL
    ,username			VARCHAR(50) NOT NULL
    ,socialType			VARCHAR(10) NOT NULL
    ,add_date 			VARCHAR(20) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD HH24MISS')
    ,approval			char(2) DEFAULT '1'
    ,approval_no		NUMBER NOT NULL
);

-- COMMENT
COMMENT ON TABLE violet_cart IS '장바구니 정보';
COMMENT ON COLUMN violet_cart.cart_no IS '장바구니 순번';
COMMENT ON COLUMN violet_cart.item_id IS '상품 아이디';
COMMENT ON COLUMN violet_cart.username IS '사용자 아이디';
COMMENT ON COLUMN violet_cart.add_date IS '장바구니 등록일자';
COMMENT ON COLUMN violet_cart.approval IS '결재 단계(1:결재 준비, 2:결재 완료, 3:배송 준비중, 4:배송중, 5:배송 완료)';
COMMENT ON COLUMN violet_cart.approval_no IS '결재 고유 일련번호';
  

-- TABLE
CREATE TABLE violet_paper (
	paper_no 				number PRIMARY KEY
    ,paper_title 			VARCHAR(50) NOT NULL
    ,paper_description		VARCHAR(4000) NOT NULL
    ,create_date 			VARCHAR(20) DEFAULT TO_CHAR(SYSDATE, 'YYYYMMDD HH24MISS')
    ,paper_img	 			VARCHAR(100)
);

-- COMMENT
COMMENT ON TABLE violet_paper IS '페이퍼 정보';
COMMENT ON COLUMN violet_paper.paper_no IS '페이퍼 번호';
COMMENT ON COLUMN violet_paper.paper_title IS '페이퍼 제목';
COMMENT ON COLUMN violet_paper.paper_description IS '페이퍼 설명';
COMMENT ON COLUMN violet_paper.create_date IS '페이퍼 생성 일자';
COMMENT ON COLUMN violet_paper.paper_img IS '페이퍼 이미지 경로';


commit;