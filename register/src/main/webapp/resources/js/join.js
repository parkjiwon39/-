/**
 * joinForm.jsp 유효성 검증하기
 */
//규칙
//아이디 : 영문자, 숫자, 특수문자 조합으로 6~12자리
//비밀번호 : 영문자, 숫자, 특수문자 조합으로 8~15자리
//이름 : 2~4자리 입력 가능
//성별 : 필수입력
//이메일 : 필수입력, 이메일 검증
$(function(){
	$("#regist").validate({
		rules:{
			userid:{
				required:true,
				validID:true,
				remote : { // ajax에서 데이터를 끌어오기위해 쓰인다.
					url: "/register/checkId",
					type : "post",
					data : {
						userid : function(){
							return $('#userid').val();
						}
					}
				}
			},
			password:{
				required:true,
				validPWD:true
			},
			confirm_password:{
				required:true,
				validPWD:true,
				equalTo:"#password"
			},
			name:{
				required:true,
				rangelength:[2,4]
			},
			gender:{
				required:true
			},
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			userid:{
				required:"아이디는 필수 속성입니다.",
				remote : "이 아이디는 사용중입니다."
			},
			password:{
				required:"비밀번호는 필수 속성입니다.",				
			},
			confirm_password:{
				required:"비밀번호는 필수 속성입니다.",				
				equalTo:"이전 비밀번호와 다릅니다."
			},
			name:{
				required:"이름은 필수 속성입니다."
			},
			gender:{
				required:"성별을 선택해 주세요."
			},
			email:{
				required:"이메일은 필수 속성입니다.",
				email:"이메일을 확인해 주세요"
			}
		},//messages end
		errorPlacement:function(error,element){//에러메시지 위치 지정
			$(element).closest("form").find("small[id='"+element.attr("id")+"']").append(error);		
		}
		
	})
		$.validator.addMethod("validID",function(value){
			const regID = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{6,12}$/;
			return regID.test(value);
		},"아이디는 영문자,숫자,특수문자의 조합으로 6~12자리로 만들어야 합니다.");
		$.validator.addMethod("validPWD",function(value){
			const regPWD =/^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,15}$/;			
			return regPWD.test(value);
		},"비밀번호는 영문자,숫자,특수문자의 조합으로8~15자리로 만들어야 합니다.");
	})
