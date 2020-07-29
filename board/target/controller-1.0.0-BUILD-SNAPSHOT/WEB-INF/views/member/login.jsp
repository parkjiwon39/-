<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--
	custom Login page �ۼ� ��Ģ
	1.method = "post", action="/login" ���� �ֱ�
	2.csrf ��ū �� ������ ����
	3.���̵� �Է¹޴� �κ��� �̸��� �ݵ��  username���� �ϱ�
	4.��й�ȣ�� �Է¹޴� �κ��� �̸��� �ݵ�� password�� �ֱ�
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="/resources/css/login.css" />
</head>
<body>
	<form action="/login" method="post" class="form-signin">
		<h1 class="h3 mb-3 font-weight-normal">Sign in</h1>
		<div>
			<label for="id" class="sr-only">���̵�</label>
			<input type="text" name="username" placeholder="���̵�" class="form-control" 
			required="required" autofocus="autofocus"/>
		</div>
		<div>
			<label for="password" class="sr-only">��й�ȣ</label>
			<input type="password" name="password" placeholder="��й�ȣ"
			class="form-control" required="required">
		</div>
		<div class="checkbox mb-3">
			<label for="">
				<input type="checkbox" name="remember-me" />Remember me
			</label>
		</div>
		<div>
			<button class="btn btn-primary btn-block">Sign in</button>
		</div>
		<div>${error}</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>
</body>
</html>