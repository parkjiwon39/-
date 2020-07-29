<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%>
<link rel="stylesheet" href="/resources/css/mycss.css"/>
<%@include file="../includes/header.jsp" %>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Register</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>            
            <div class="row">
                <div class="col-lg-12">
                	<div class="panel panel-default">
                        <div class="panel-heading">
                           Board Register Page
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                			<form action="" method="post" role="form">
                				<div class="form-group">
                					<label>Title</label>
                					<input class="form-control" name="title">                				
                				</div>  
                				<div class="form-group">
                					<label>Content</label>
                					<textarea class="form-control" rows="3" name="content"></textarea>               				
                				</div> 
                				<div class="form-group">
                					<label>Writer</label>
                					<input class="form-control" name="writer" value='<sec:authentication property="principal.username"/>' readonly>                				
                				</div>  
                				<button type="submit" class="btn btn-default">Submit</button>              			
                				<button type="reset" class="btn btn-default">reset</button> 
                				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>             			
                			</form>
                		</div>
                	</div>
                </div>
            </div> 
<!-- 첨부 파일 영역 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">파일 첨부</div>
			<div class="panel-body">
				<div class="form-group uploadDiv">
					<input type="file" name="uploadFile" multiple="multiple" />
				</div>
				<div class="uploadResult">
					<ul></ul>
				</div>
			</div>
		</div>
	</div>
</div>    
<script>
$(function(){
	$("button[type='submit']").click(function(e){
		// submit 버튼 기능 막기
		e.preventDefault();
		// 게시글 등록 + 파일 첨부 한꺼번에 처리
		// 첨부 파일 내용 수집
		let str = "";
		$(".uploadResult ul li").each(function(i, ele){
			let job = $(ele);
			
			str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+job.data("uuid")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+job.data("path")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+job.data("filename")+"'>";
			str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+job.data("type")+"'>";
		})
		console.log(str);
		// 해당 폼 전송
		$("form[role='form']").append(str).submit();
	})
	//csrf 토큰 값 생성
	let csrfHeaderName = "${_csrf.headerName}";
	let csrfTokenValue = "${_csrf.token}";
	
	$("input[type='file']").change(function(){
		// form 의 형태로 데이터를 구성할 수 있음
		// key, value
		let formData = new FormData();
		
		// 첨부파일 목록 가져오기
		let uploadFile = $("input[name='uploadFile']");
		let files = uploadFile[0].files;
		console.log(files);
		
		//form의 형태로 붙이기
		for(var i=0;i<files.length;i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}			
			formData.append("uploadFile",files[i]);
		}	
		// processData : 데이터를 query string(http://~~~?uploadFile=테스트.txt)로 변환할 것인지 결정
		//               기본값은 application/x-www-form-urlencoded 로 true 이기
		//               때문에 false로 지정
		// contentType : 기본값은 application/x-www-form-urlencoded
		//               파일의 경우에 enctype 은 multipart/form-data로 보내야
		//               하기 때문에 false로 지정
		
		$.ajax({
			url : '/uploadAjax',
			type : 'post',
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			processData : false,
			contentType : false,
			data : formData,			
			success:function(result){
				console.log(result);
				showUploadFile(result);
				$("input[name='uploadFile']").val("");
			},
			error:function(xhr,status,error){
				alert(xhr.responseText);
			}
		})		
	})
	//첨부파일 제한 / 크기 제한
	function checkExtension(fileName, fileSize){
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		let maxSize = 6000000;
		
		if(fileSize > maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
			return false;
		}
		return true;
	}
	//업로드 된 파일 보여주기
	function showUploadFile(uploadResultArr){
		let str="";
		//결과를 보여줄 영역 가져오기
		let uploadResult = $(".uploadResult ul");
		$(uploadResultArr).each(function(i, element) {
			if(element.fileType){ //이미지파일
				//썸네일 이미지 경로
				var fileCallPath = encodeURIComponent(element.uploadPath+"\\s_"+element.uuid+"_"+element.fileName);
				//원본 이미지 경로
				var oriPath = element.uploadPath+"\\"+element.uuid+"_"+element.fileName;
				oriPath = oriPath.replace(new RegExp(/\\/g),"/");
				
				str += "<li data-path='"+ element.uploadPath +"' data-uuid='"+element.uuid+"'";
				str += " data-filename='"+element.fileName+"' data-type='"+element.fileType+"'>";
				str += "<a href=\"javascript:showImage(\'"+oriPath+"\')\">";
				str += "<img src='/display?fileName="+ fileCallPath +"'><div>"+element.fileName+"</a>";	
				str += "<button type='button' class='btn btn-danger btn-circle btn-sm' data-file='"+fileCallPath+"'data-type='image'>";
				str += "<i class='fa fa-times'></i></button>";
				str += "</div></li>"
			}else{ //일반파일
				var fileCallPath = encodeURIComponent(element.uploadPath+"\\"+element.uuid+"_"+element.fileName);
				str += "<li data-path='"+ element.uploadPath +"' data-uuid='"+element.uuid+"'";
				str += " data-filename='"+element.fileName+"' data-type='"+element.fileType+"'>";
				str +="<a href='/download?fileName="+ fileCallPath +"'>";
				str += "<img src='/resources/img/attach.png'><div>"+element.fileName+"</a>";
				str += "<button type='button' class='btn btn-danger btn-circle btn-sm' data-file='"+fileCallPath+"'data-type='file'>";
				str += "<i class='fa fa-times'></i></button>";
				str += "</div></li>";
			}
		})
		uploadResult.append(str);
	}
	// x를 누르면 목록에서 삭제하기
	$(".uploadResult").on("click","button",function(e){			
		
		let targetFile = $(this).data("file");
		let type = $(this).data("type");
		let targetLi = $(this).closest("li");
		
		$.ajax({
			url : '/deleteFile',
			type : 'post',
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			data : {
				fileName : targetFile,
				type : type
			},
			success:function(result){
				targetLi.remove();
			}
		})				
		// 다음 이벤트 발생 막기
		e.stopPropagation();
	}) // 첨부 파일 삭제 종료
})
</script>      
<%@include file="../includes/footer.jsp" %>       