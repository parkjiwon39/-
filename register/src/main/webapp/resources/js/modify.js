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
	$("#modifyform").validate({
		//규칙명시
		rules:{		
			current_password:{
				required : true
			},
			new_password:{
				required : true,				
				validPWD : true			
			},
			confirm_password:{
				required : true,				
				validPWD : true,
				equalTo : "#new_password"
			}
		},
		//메세지
		messages:{	
			current_password:{
				required : "현재 비밀번호는 필수 속성입니다."
			},
			new_password:{
				required:"비밀번호는 필수 속성입니다."
			},
			confirm_password:{
				required: "비밀번호는 필수 속성입니다.",
				equalTo : "이전 비밀번호와 다릅니다."
			}
		},//messages end
		errorPlacement:function(error,element){ //에러메시지 위치 지정			
			$(element).closest("form").find("small[id='"+element.attr("id")+"']").append(error);				
		}
	})	
})
//규칙을 검증할 메소드 추가
$.validator.addMethod("validPWD",function(value){
	const regPwd = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,15}$/;
	return regPwd.test(value);	
},"비밀번호는 영문자,숫자,특수문자의 조합으로 8~15자리로 만들어야 합니다.");




